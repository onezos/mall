package net.kokwind.mall.service;

import net.kokwind.mall.exception.DdMallException;
import net.kokwind.mall.exception.DdMallExceptionEnum;
import net.kokwind.mall.model.dao.CategoryMapper;
import net.kokwind.mall.model.entity.Category;
import net.kokwind.mall.model.request.AddCategoryReq;
import net.kokwind.mall.model.request.UpdateCategoryReq;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述: 商品分类服务
 */
@Service
public class CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    public void add(AddCategoryReq addCategoryReq) {
        Category category = new Category();
        BeanUtils.copyProperties(addCategoryReq, category);
        Category oldCategory = categoryMapper.selectByName(addCategoryReq.getName());
        if(oldCategory != null) {
            throw new DdMallException(DdMallExceptionEnum.NAME_EXISTED);
        }
        int count = categoryMapper.insertSelective(category);
        if(count == 0) {
            throw new DdMallException(DdMallExceptionEnum.CREATE_FAILED);
        }
    }

    public void update(Category updateCategory) {
        if (updateCategory.getName() != null) {
            Category categoryOld = categoryMapper.selectByName(updateCategory.getName());
            if (categoryOld != null && !categoryOld.getId().equals(updateCategory.getId())) {
                throw new DdMallException(DdMallExceptionEnum.NAME_EXISTED);
            }
        }
        int count = categoryMapper.updateByPrimaryKeySelective(updateCategory);
        if (count == 0) {
            throw new DdMallException(DdMallExceptionEnum.UPDATE_FAILED);
        }
    }

    public void delete(Integer id) {
        Category categoryOld = categoryMapper.selectByPrimaryKey(id);
        //查不到记录，无法删除，删除失败
        if (categoryOld == null) {
            throw new DdMallException(DdMallExceptionEnum.DELETE_FAILED);
        }
        int count = categoryMapper.deleteByPrimaryKey(id);
        if (count == 0) {
            throw new DdMallException(DdMallExceptionEnum.DELETE_FAILED);
        }
    }

}
