package net.kokwind.mall.model.dao;

import net.kokwind.mall.model.entity.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category row);

    int insertSelective(Category row);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category row);

    int updateByPrimaryKey(Category row);

    Category selectByName(String name);

    List<Category> selectList();

    List<Category> selectCategoriesByParentId(Integer parentId);

}