package com.just;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.just.cart.mapper")	// 扫描MyBatis接口文件
public class RunApplication {

	public static void main(String[] args) {
		SpringApplication.run(RunApplication.class, args);
	}

}
