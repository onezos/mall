package net.kokwind.mall.controller;

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

    @GetMapping("/user")
    public User getUser() {
        return userService.getUser();
    }

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


}
