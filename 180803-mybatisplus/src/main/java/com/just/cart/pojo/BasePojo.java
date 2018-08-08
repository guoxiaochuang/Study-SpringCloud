package com.just.cart.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;

public class BasePojo implements Serializable{
	private static final long serialVersionUID = 1L;
	@TableField(value = "created", fill = FieldFill.INSERT)
	private Date created;
	@TableField(value = "updated", fill = FieldFill.INSERT_UPDATE)
	private Date updated;
	
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
}
