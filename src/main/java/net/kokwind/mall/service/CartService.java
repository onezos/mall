package net.kokwind.mall.service;

import net.kokwind.mall.common.Constant;
import net.kokwind.mall.common.Constant.SaleStatus;
import net.kokwind.mall.exception.DdMallException;
import net.kokwind.mall.exception.DdMallExceptionEnum;
import net.kokwind.mall.model.dao.CartMapper;
import net.kokwind.mall.model.dao.ProductMapper;
import net.kokwind.mall.model.entity.Cart;
import net.kokwind.mall.model.entity.Product;
import net.kokwind.mall.model.vo.CartVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述：     购物车Service实现类
 */
@Service
public class CartService{

    @Autowired
    ProductMapper productMapper;

    @Autowired
    CartMapper cartMapper;

    public List<CartVO> list(Integer userId) {
        List<CartVO> cartVOS = cartMapper.selectList(userId);
        for (int i = 0; i < cartVOS.size(); i++) {
            CartVO cartVO = cartVOS.get(i);
            cartVO.setTotalPrice(cartVO.getPrice() * cartVO.getQuantity());
        }
        return cartVOS;
    }

    public List<CartVO> add(Integer userId, Integer productId, Integer count) {
        validProduct(productId, count);

        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart == null) {
            //这个商品之前不在购物车里，需要新增一个记录
            cart = new Cart();
            cart.setProductId(productId);
            cart.setUserId(userId);
            cart.setQuantity(count);
            cart.setSelected(Constant.Cart.CHECKED);
            cartMapper.insertSelective(cart);
        } else {
            //这个商品已经在购物车里了，则数量相加
            count = cart.getQuantity() + count;
            Cart cartNew = new Cart();
            cartNew.setQuantity(count);
            cartNew.setId(cart.getId());
            cartNew.setProductId(cart.getProductId());
            cartNew.setUserId(cart.getUserId());
            cartNew.setSelected(Constant.Cart.CHECKED);
            cartMapper.updateByPrimaryKeySelective(cartNew);
        }
        return this.list(userId);
    }

    private void validProduct(Integer productId, Integer count) {
        Product product = productMapper.selectByPrimaryKey(productId);
        //判断商品是否存在，商品是否上架
        if (product == null || product.getStatus().equals(SaleStatus.NOT_SALE)) {
            throw new DdMallException(DdMallExceptionEnum.NOT_SALE);
        }
        //判断商品库存
        if (count > product.getStock()) {
            throw new DdMallException(DdMallExceptionEnum.NOT_ENOUGH);
        }
    }

    public List<CartVO> update(Integer userId, Integer productId, Integer count) {
        validProduct(productId, count);

        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart == null) {
            //这个商品之前不在购物车里，无法更新
            throw new DdMallException(DdMallExceptionEnum.UPDATE_FAILED);
        } else {
            //这个商品已经在购物车里了，则更新数量
            Cart cartNew = new Cart();
            cartNew.setQuantity(count);
            cartNew.setId(cart.getId());
            cartNew.setProductId(cart.getProductId());
            cartNew.setUserId(cart.getUserId());
            cartNew.setSelected(Constant.Cart.CHECKED);
            cartMapper.updateByPrimaryKeySelective(cartNew);
        }
        return this.list(userId);
    }

    public List<CartVO> delete(Integer userId, Integer productId) {
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart == null) {
            //这个商品之前不在购物车里，无法删除
            throw new DdMallException(DdMallExceptionEnum.DELETE_FAILED);
        } else {
            //这个商品已经在购物车里了，则可以删除
            cartMapper.deleteByPrimaryKey(cart.getId());
        }
        return this.list(userId);
    }

    public List<CartVO> selectOrNot(Integer userId, Integer productId, Integer selected) {
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart == null) {
            //这个商品之前不在购物车里，无法选择/不选中
            throw new DdMallException(DdMallExceptionEnum.UPDATE_FAILED);
        } else {
            //这个商品已经在购物车里了，则可以选中/不选中
            cartMapper.selectOrNot(userId,productId,selected);
        }
        return this.list(userId);
    }

    public List<CartVO> selectAllOrNot(Integer userId, Integer selected) {
        //改变选中状态
        cartMapper.selectOrNot(userId, null, selected);
        return this.list(userId);
    }
}
