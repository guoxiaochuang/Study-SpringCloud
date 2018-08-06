package com.just.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.just.pojo.User;
/**
 * 
 * @author guoxiaochuang
 * 注解和xml方式混合
 */
public interface UserMapper {
	// xml方式
	public List<User> getAll();
	// 注解方式
	@Select("select * from user where id=#{id}")
	public User getById(@Param("id") Integer id);
}
