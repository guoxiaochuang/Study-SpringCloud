package com.just.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.just.client.EurekaServiceFeign;

@RestController
public class HelloController {
	@Autowired
	private EurekaServiceFeign eurekaServiceFeign;
	
	@GetMapping("/hello/{name}")
	@ResponseBody
	public String hello(@PathVariable String name){
		return eurekaServiceFeign.hello(name);
	}
}
