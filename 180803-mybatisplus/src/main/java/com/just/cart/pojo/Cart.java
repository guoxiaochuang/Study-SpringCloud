package com.just.cart.pojo;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@TableName("tb_cart")
public class Cart extends BasePojo{
	private static final long serialVersionUID = 1L;

	//@TableField(exist = false) // 数据库表中不存在的字段，需要标识
	
	@TableId(value = "id", type = IdType.AUTO)//主键自增
	private Long id;
	@TableField("user_id") // 属性和字段映射
	private Long userId;
	@TableField("item_id") // 属性和字段映射
	private Long itemId;
	@TableField("item_title") // 属性和字段映射
	private String itemTitle;
	@TableField("item_image") // 属性和字段映射
	private String itemImage;
	@TableField("item_price") // 属性和字段映射
	private Long itemPrice;
	private Integer num;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public String getItemTitle() {
		return itemTitle;
	}
	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}
	public String getItemImage() {
		return itemImage;
	}
	public void setItemImage(String itemImage) {
		this.itemImage = itemImage;
	}
	public Long getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(Long itemPrice) {
		this.itemPrice = itemPrice;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
}
