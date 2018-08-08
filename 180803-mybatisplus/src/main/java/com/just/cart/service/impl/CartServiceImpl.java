package com.just.cart.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.github.pagehelper.PageInfo;
import com.just.cart.mapper.CartMapper;
import com.just.cart.pojo.Cart;
import com.just.cart.service.CartService;
import com.just.common.vo.SysResult;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartMapper cartMapper;

	@Override
	public SysResult save(Cart cart) {
		try {
			// 查询这个货物是否已经在本购物车，必须按userid+itemid
			Map<String, Long> mapParam = new HashMap<String, Long>();
			mapParam.put("userId", cart.getUserId());
			mapParam.put("itemId", cart.getItemId());

			Integer count = cartMapper.queryByUserIdAndItemId(mapParam);
			if (count > 0) {
				EntityWrapper<Cart> wrapper = new EntityWrapper<Cart>();
				wrapper.eq("user_id", cart.getUserId());
				List<Cart> cartList = cartMapper.selectList(wrapper);
				if (cartList != null && cartList.size() > 0) {
					Cart oldCart = cartList.get(0);
					oldCart.setNum(oldCart.getNum() + 1);
					this.updateNum(oldCart);
				}
				return SysResult.build(202, "该商品已经存在购物车中！");
			} else {
				cart.setCreated(new Date());
				cart.setUpdated(cart.getCreated());
				cartMapper.insert(cart);
				return SysResult.ok();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201, "添加商品到购物车失败!");
		}
	}

	@Override
	public SysResult query(Long userId, Integer page, Integer row) {
		Cart param = new Cart();
		param.setUserId(userId);
		EntityWrapper<Cart> wrapper = new EntityWrapper<Cart>();
		wrapper.setEntity(param);
		PageHelper.startPage(page, row);
		List<Cart> cartList = cartMapper.selectList(wrapper);
		PageInfo<Cart> info = new PageInfo<Cart>(cartList);
		return SysResult.ok(info);
	}

	@Override
	public SysResult updateNum(Cart cart) {
		try {
			cartMapper.updateNum(cart);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201, "更新商品数量错误! " + cart.getItemId());
		}
	}

	@Override
	public SysResult deleteItem(Cart cart) {
		try {
			cartMapper.deleteItem(cart);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201, "删除商品失败!");
		}
	}
}
