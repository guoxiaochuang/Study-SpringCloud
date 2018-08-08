package com.just.cart.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.just.cart.pojo.Cart;
import com.just.cart.service.CartService;
import com.just.common.vo.SysResult;

@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	@RequestMapping("/save")
	public SysResult save(Cart cart) {
		return cartService.save(cart);
	}

	@RequestMapping("/query/{userId}")
	public SysResult query(@PathVariable Long userId, @RequestParam(value = "page",defaultValue="1") Integer page,@RequestParam(value = "rows", defaultValue="20")Integer row){
		return cartService.query(userId, page, row);
	}
	
	@RequestMapping("/update/num/{userId}/{itemId}/{num}")
	public SysResult update(@PathVariable Long userId,@PathVariable Long itemId, @PathVariable Integer num){
		Cart cart = new Cart();
		cart.setUserId(userId);
		cart.setItemId(itemId);
		cart.setNum(num);
		cart.setUpdated(new Date());
		return cartService.updateNum(cart);
	}
	@RequestMapping("/delete/{userId}/{itemId}")
	public SysResult delete(@PathVariable Long userId,@PathVariable Long itemId){
		Cart cart = new Cart();
		cart.setUserId(userId);
		cart.setItemId(itemId);
		return cartService.deleteItem(cart);
	}
}
