package com.just.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.just.mapper.UserMapper;
import com.just.pojo.User;
import com.just.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;
	@Override
	public List<User> getAll() {
		return userMapper.getAll();
	}
	@Override
	public User getById(Integer id) {
		return userMapper.getById(id);
	}

}
