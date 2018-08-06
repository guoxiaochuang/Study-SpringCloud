package com.just.service;

import java.util.List;

import com.just.pojo.User;
public interface UserService {
	public List<User> getAll();
	public User getById(Integer id);
}
