package net.kokwind.mall.controller;

/**
 * 描述： 用户接口
 */

import net.kokwind.mall.common.ApiRestResponse;
import net.kokwind.mall.common.Constant;
import net.kokwind.mall.exception.DdMallException;
import net.kokwind.mall.exception.DdMallExceptionEnum;
import net.kokwind.mall.model.entity.User;
import net.kokwind.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 测试接口
     * @return 返回用户信息
     */
    @GetMapping("/user")
    public User getUser() {
        return userService.getUser();
    }

    /**
     * 用户注册接口
     * @param userName
     * @param password
     * @return 返回携带用户信息的ApiRestResponse
     * @throws DdMallException
     */
    @PostMapping("/register")
    public ApiRestResponse register(@RequestParam("userName") String userName,
                                    @RequestParam("password") String password) throws DdMallException {
        if(ObjectUtils.isEmpty(userName)) {
            return ApiRestResponse.error(DdMallExceptionEnum.NEED_USER_NAME);
        }
        if(ObjectUtils.isEmpty(password)) {
            return ApiRestResponse.error(DdMallExceptionEnum.NEED_PASSWORD);
        }
        if(password.length()<8) {
            return ApiRestResponse.error(DdMallExceptionEnum.PASSWORD_TOO_SHORT);
        }
        userService.register(userName, password);
        return ApiRestResponse.success();
    }

    /**
     * 用户登录接口
     * @param userName
     * @param password
     * @param session
     * @return 返回携带用户信息的ApiRestResponse
     * @throws DdMallException
     */
    @PostMapping("/login")
    public ApiRestResponse login(@RequestParam("userName") String userName,
                                 @RequestParam("password") String password,
                                 HttpSession session) throws DdMallException {
        if(ObjectUtils.isEmpty(userName)) {
            return ApiRestResponse.error(DdMallExceptionEnum.NEED_USER_NAME);
        }
        if(ObjectUtils.isEmpty(password)) {
            return ApiRestResponse.error(DdMallExceptionEnum.NEED_PASSWORD);
        }
        User user = userService.login(userName, password);
        //保存用户信息时，不保存密码
        user.setPassword(null);
        session.setAttribute(Constant.DD_MALL_USER, user);
        return ApiRestResponse.success(user);
    }

    /**
     * 用户信息更新接口
     * @param session
     * @param signature
     * @return 返回成功信息
     * @throws DdMallException
     */
    @PostMapping("/user/update")
    public ApiRestResponse updateUserInfo(HttpSession session,
                                          @RequestParam String signature) throws DdMallException {
        User currentUser = (User) session.getAttribute(Constant.DD_MALL_USER);
        if(ObjectUtils.isEmpty(currentUser)) {
            return ApiRestResponse.error(DdMallExceptionEnum.NEED_LOGIN);
        }
        User user = new User();
        user.setPersonalizedSignature(signature);
        user.setId(currentUser.getId());
        userService.updateUserInfo(user);
        return ApiRestResponse.success();
    }

    /**
     * 用户登出接口
     * @param session
     * @return 返回成功信息
     */
    @PostMapping("/user/logout")
    public ApiRestResponse logout(HttpSession session){
        session.removeAttribute(Constant.DD_MALL_USER);
        return ApiRestResponse.success();
    }

    /**
     * 管理员接口
     * @param userName
     * @param password
     * @param session
     * @return 返回携带用户信息的ApiRestResponse
     * @throws DdMallException
     */
    @PostMapping("/admin/login")
    public ApiRestResponse adminLogin(@RequestParam("userName") String userName,
                                 @RequestParam("password") String password,
                                 HttpSession session) throws DdMallException {
        if(ObjectUtils.isEmpty(userName)) {
            return ApiRestResponse.error(DdMallExceptionEnum.NEED_USER_NAME);
        }
        if(ObjectUtils.isEmpty(password)) {
            return ApiRestResponse.error(DdMallExceptionEnum.NEED_PASSWORD);
        }
        User user = userService.login(userName, password);
        //校验是否是管理员
        if (userService.checkAdminRole(user)) {
            user.setPassword(null);
            session.setAttribute(Constant.DD_MALL_USER, user);
            return ApiRestResponse.success(user);
        }else {
            return ApiRestResponse.error(DdMallExceptionEnum.NEED_ADMIN_ROLE);
        }
    }
}
