package net.kokwind.mall.service;
/**
 * 用户服务
 */

import net.kokwind.mall.exception.DdMallException;
import net.kokwind.mall.exception.DdMallExceptionEnum;
import net.kokwind.mall.model.dao.UserMapper;
import net.kokwind.mall.model.entity.User;
import net.kokwind.mall.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    /**
     * 测试服务
     */
    public User getUser(){
        return userMapper.selectByPrimaryKey(1);
    }

    /**
     * 用户注册服务
     * @param userName
     * @param password
     * @throws DdMallException
     */
    public void register(String userName, String password) throws DdMallException {
        //查询用户名是否存在，不允许重名
        User result = userMapper.selectByName(userName);
        if(result != null){
            throw new DdMallException(DdMallExceptionEnum.NAME_EXISTED);
        }
        //写到数据库
        User user = new User();
        user.setUsername(userName);
        //user.setPassword(password);
        try {
            user.setPassword(MD5Utils.getMD5Str(password));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        //只给选中的字段赋值用insertSelective，给每个字段赋值用insert
        int count = userMapper.insertSelective(user);
        if(count == 0){
            throw new DdMallException(DdMallExceptionEnum.REGISTER_FAILED);
        }
    }

    /**
     * 用户登录服务
     * @param userName
     * @param password
     * @return 返回用户数据
     * @throws DdMallException
     */
    public User login(String userName, String password) throws DdMallException {
        String md5Password = null;
        try {
            md5Password = MD5Utils.getMD5Str(password);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        User user = userMapper.selectLogin(userName, md5Password);
        if(user == null){
            throw new DdMallException(DdMallExceptionEnum.WRONG_PASSWORD);
        }
        return user;
    }

    /**
     * 用户更新服务
     * @param user
     * @throws DdMallException
     */
    public void updateUserInfo(User user) throws DdMallException {
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if(updateCount > 1){
            throw new DdMallException(DdMallExceptionEnum.UPDATE_FAILED);
        }
    }

    /**
     * 检查是否管理员服务
     * @param user
     * @return true表示是管理员，false表示不是管理员
     */
    public boolean checkAdminRole(User user) {
        //1是普通用户，2是管理员
        return user.getRole().equals(2);
    }
}
