package com.just;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages="com.just.mapper")//扫描Mybatis接口文件
public class RunApplication {
	public static void main(String[] args) {
		SpringApplication.run(RunApplication.class, args);
	}
}
