package net.kokwind.mall.controller;

import io.swagger.annotations.ApiOperation;
import net.kokwind.mall.common.ApiRestResponse;
import net.kokwind.mall.common.Constant;
import net.kokwind.mall.exception.DdMallExceptionEnum;
import net.kokwind.mall.model.entity.Category;
import net.kokwind.mall.model.entity.User;
import net.kokwind.mall.model.request.AddCategoryReq;
import net.kokwind.mall.model.request.UpdateCategoryReq;
import net.kokwind.mall.service.CategoryService;
import net.kokwind.mall.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * 描述：目录Controller
 */
@RestController
public class CategoryController {
    @Autowired
    UserService userService;
    @Autowired
    CategoryService categoryService;

    @ApiOperation("后台添加目录")
    @PostMapping("admin/categoty/add")
    public ApiRestResponse addCategory(HttpSession session,
                                       @Valid @RequestBody AddCategoryReq addCategoryReq)  {
        //判断是否登录
        User currentUser = (User) session.getAttribute(Constant.DD_MALL_USER);
        if (currentUser == null) {
            return ApiRestResponse.error(DdMallExceptionEnum.NEED_LOGIN);
        }
        //校验是否是管理员
        boolean adminRole = userService.checkAdminRole(currentUser);
        if(adminRole){
            categoryService.add(addCategoryReq);
            return ApiRestResponse.success();
        }else{
            return ApiRestResponse.error(DdMallExceptionEnum.NEED_ADMIN_ROLE);
        }
    }
    @ApiOperation("后台更新目录")
    @PostMapping("admin/categoty/update")
    public ApiRestResponse updateCategory(HttpSession session,
                                       @Valid @RequestBody UpdateCategoryReq updateCategoryReq)  {
        //判断是否登录
        User currentUser = (User) session.getAttribute(Constant.DD_MALL_USER);
        if (currentUser == null) {
            return ApiRestResponse.error(DdMallExceptionEnum.NEED_LOGIN);
        }
        //校验是否是管理员
        boolean adminRole = userService.checkAdminRole(currentUser);
        if(adminRole){
            Category category = new Category();
            BeanUtils.copyProperties(updateCategoryReq, category);
            categoryService.update(category);
            return ApiRestResponse.success();
        }else{
            return ApiRestResponse.error(DdMallExceptionEnum.NEED_ADMIN_ROLE);
        }
    }

    @ApiOperation("后台删除目录")
    @PostMapping("admin/category/delete")
    public ApiRestResponse deleteCategory(@RequestParam Integer id) {
        categoryService.delete(id);
        return ApiRestResponse.success();
    }
}
