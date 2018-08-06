package com.just.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.just.pojo.User;
import com.just.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	@RequestMapping("/getAll")
	public List<User> getAll(){
		return userService.getAll();
	}
	@RequestMapping("/getById/{id}")
	public User getById(@PathVariable Integer id){
		return userService.getById(id);
	}
	@RequestMapping("/hello")
	public String hello(){
		return "Hello World!";
	}
}
