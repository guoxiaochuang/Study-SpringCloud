package com.just.cart.service;

import com.just.cart.pojo.Cart;
import com.just.common.SysResult;

public interface CartService {
	//保存 查询 更新 删除
	public SysResult save(Cart cart);
	public SysResult query(Long userId, Integer page, Integer row);
	public SysResult updateNum(Cart cart);
	public SysResult deleteItem(Cart cart);
}
