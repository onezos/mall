package net.kokwind.mall.model.dao;

import net.kokwind.mall.model.entity.User;
import org.springframework.stereotype.Repository;
@Repository
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User row);

    int insertSelective(User row);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User row);

    int updateByPrimaryKey(User row);
}