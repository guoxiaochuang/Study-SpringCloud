# SpringBoot+MyBatis+MyBatisPlus整合

```
购物车功能实现
```

- 技术栈

```
SpringBoot + MyBatis + MyBatisPlus
```

- 系统架构图
![架构图](https://raw.githubusercontent.com/guoxiaochuang/IMG/master/MD/Study-SpringCloud/SpringBoot%2BMyBatisPlus%E7%9A%84%E6%9E%B6%E6%9E%84%E5%9B%BE.png)

> 准备工作

```
创建数据库表(tb_cart)、添加数据
创建脚本所在位置：./脚本/tb_cart.sql
```
- 创建工程步骤：

## （1）创建一个Maven工程

```
创建Maven工程，创建simple project，类型为jar
```

## （2）配置pom.xml文件

```
1、添加parent配置
2、修改properties相关配置：编码、jdk版本等
3、添加相关依赖：springboot、mysql驱动包、阿里的数据源druid包等
```
> 相关配置如下

```
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<groupId>com.tedu</groupId>
	<artifactId>jt-cart</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>1.5.4.RELEASE</version>
		<relativePath />
	</parent>
	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		<java.version>1.8</java.version>
	</properties>
	<dependencies>
		<dependency>
			<groupId>org.mybatis.spring.boot</groupId>
			<artifactId>mybatis-spring-boot-starter</artifactId>
			<version>1.3.0</version>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
		</dependency>
		<!-- mybatisplus与springboot整合 -->
		<dependency>
			<groupId>com.baomidou</groupId>
			<artifactId>mybatisplus-spring-boot-starter</artifactId>
			<version>1.0.5</version>
		</dependency>
		<!-- MP 核心库 -->
		<dependency>
			<groupId>com.baomidou</groupId>
			<artifactId>mybatis-plus</artifactId>
			<version>2.1.8</version>
		</dependency>
		<dependency>
			<groupId>com.github.pagehelper</groupId>
			<artifactId>pagehelper-spring-boot-starter</artifactId>
			<version>1.2.2</version>
		</dependency>
		<dependency>
			<groupId>org.apache.httpcomponents</groupId>
			<artifactId>httpclient</artifactId>
		</dependency>
		<!-- alibaba的druid数据库连接池 -->
		<dependency>
			<groupId>com.alibaba</groupId>
			<artifactId>druid-spring-boot-starter</artifactId>
			<version>1.1.0</version>
		</dependency>
	</dependencies>
</project>
```

## （3）创建对象基类：BasePojo

```
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
```

## （4）创建购物车对象：Cart

```
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
```

## （5）创建自定义响应结构：SysResult

```
package com.just.common.vo;

/**
 * 
 * @author guoxiaochuang 自定义相应结构
 */
public class SysResult {
	/*
	 * 响应业务状态 200 成功 201 错误 400 参数错误
	 */
	private Integer status;
	// 响应消息
	private String msg;
	// 响应中的数据
	private Object data;

	public SysResult() {
	}

	public SysResult(Object data) {
		this.status = 200;
		this.msg = "OK";
		this.data = data;
	}

	public SysResult(Integer status, String msg, Object data) {
		this.status = status;
		this.msg = msg;
		this.data = data;
	}

	public static SysResult ok() {
		return new SysResult(null);
	}

	public static SysResult ok(Object data) {
		return new SysResult(data);
	}

	public static SysResult build(Integer status, String msg) {
		return new SysResult(status, msg, null);
	}

	public static SysResult build(Integer status, String msg, Object data) {
		return new SysResult(status, msg, data);
	}

	public Boolean isOK() {
		return this.status == 200;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
```

## （6）创建持久层接口：CartMapper

```
继承MybatisPlus提供的BaseMapper，它会自动产生SQL语句。采用注解开发方式，SQL直接写在方法上面。
特殊方法通过自定义接口实现CartMapper，通用的方法通过BaseMapper实现。
这里的方法和业务接口的方法不一致，业务接口中的部分方法基类BaseMapper已经实现，其他方法按业务需要规划。
```
> 代码如下

```
package com.just.cart.mapper;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.just.cart.pojo.Cart;
@Repository
public interface CartMapper extends BaseMapper<Cart> {
	// 根据用户id和商品id查询商品是否存在
	@Select("select count(1) from tb_cart where user_id = #{userId} and item_id = #{itemId}")
	public Integer queryByUserIdAndItemId(Map<String, Long> map);
	/*
	 *  根据用户id和商品id，更新信息
	 *  注意updateNum方法名不能叫update，会与BaseMapper中重复，无法正确执行
	 */
	@Update("updte tb_cart set num = #{num} where user_id=#{user_id} and item_id=#{itemId}")
	public void updateNum(Cart cart);
	// 删除某个人的商品
	@Delete("delete from tb_cart where  user_id=#{user_id} and item_id=#{itemId}")
	public void deleteItem(Cart cart);
}
```

## （7）创建业务层接口：CartService

```
package com.just.cart.service;

import com.just.cart.pojo.Cart;
import com.just.common.vo.SysResult;

public interface CartService {
	//保存 查询 更新 删除
	public SysResult save(Cart cart);
	public SysResult query(Long userId, Integer page, Integer row);
	public SysResult updateNum(Cart cart);
	public SysResult deleteItem(Cart cart);
}
```

## （8）创建业务层接口实现：CartServiceImpl

```
package com.just.cart.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.github.pagehelper.PageInfo;
import com.just.cart.mapper.CartMapper;
import com.just.cart.pojo.Cart;
import com.just.cart.service.CartService;
import com.just.common.vo.SysResult;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartMapper cartMapper;

	@Override
	public SysResult save(Cart cart) {
		try {
			// 查询这个货物是否已经在本购物车，必须按userid+itemid
			Map<String, Long> mapParam = new HashMap<String, Long>();
			mapParam.put("userId", cart.getUserId());
			mapParam.put("itemId", cart.getItemId());

			Integer count = cartMapper.queryByUserIdAndItemId(mapParam);
			if (count > 0) {
				EntityWrapper<Cart> wrapper = new EntityWrapper<Cart>();
				wrapper.eq("user_id", cart.getUserId());
				List<Cart> cartList = cartMapper.selectList(wrapper);
				if (cartList != null && cartList.size() > 0) {
					Cart oldCart = cartList.get(0);
					oldCart.setNum(oldCart.getNum() + 1);
					this.updateNum(oldCart);
				}
				return SysResult.build(202, "该商品已经存在购物车中！");
			} else {
				cart.setCreated(new Date());
				cart.setUpdated(cart.getCreated());
				cartMapper.insert(cart);
				return SysResult.ok();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201, "添加商品到购物车失败!");
		}
	}

	@Override
	public SysResult query(Long userId, Integer page, Integer row) {
		Cart param = new Cart();
		param.setUserId(userId);
		EntityWrapper<Cart> wrapper = new EntityWrapper<Cart>();
		wrapper.setEntity(param);
		PageHelper.startPage(page, row);
		List<Cart> cartList = cartMapper.selectList(wrapper);
		PageInfo<Cart> info = new PageInfo<Cart>(cartList);
		return SysResult.ok(info);
	}

	@Override
	public SysResult updateNum(Cart cart) {
		try {
			cartMapper.updateNum(cart);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201, "更新商品数量错误! " + cart.getItemId());
		}
	}

	@Override
	public SysResult deleteItem(Cart cart) {
		try {
			cartMapper.deleteItem(cart);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201, "删除商品失败!");
		}
	}
}
```

## （9）创建控制层控制类：CartController

```
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
	
	@RequestMapping("/update/num/{userId}/{itemId}")
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
```

## （10）配置application.yml文件

```
server:
  port: 8070

spring:
    datasource:
        driver-class-name: com.mysql.jdbc.Driver
        url: jdbc:mysql://127.0.0.1:3306/db-1808?jdbc:mysql://127.0.0.1:3306/db-1808?useUnicode=true&characterEncoding=utf8&autoReconnect=true&allowMultiQueries=true
        username: root
        password: root

mybatis:
  mapUnderscoreToCamelCase: true
  typeAliasesPackage: com.just.cart.pojo
  mapperLocations: classpath:mappers/*.xml
  
mybatis-plus:
  configuration:
    map-underscore-to-camel-case: true

logging:
  level: 
    com.just.cart.mapper: debug
```

## （11）配置日志文件：log4j.properties

```
log4j.rootLogger=DEBUG, Console
#Console
log4j.appender.Console=org.apache.log4j.ConsoleAppender
log4j.appender.Console.layout=org.apache.log4j.PatternLayout
log4j.appender.Console.layout.ConversionPattern=%d [%t] %-5p [%c] - %m%n
log4j.logger.java.sql.ResultSet=INFO
log4j.logger.org.apache=INFO
log4j.logger.java.sql.Connection=DEBUG
log4j.logger.java.sql.Statement=DEBUG
log4j.logger.java.sql.PreparedStatement=DEBUG
```

## （12）创建启动类：RunApp

```
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
```

## （13）模拟Post请求工具类

```
package com.just.post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.junit.Test;

public class HttpClientPost {
	@Test
	public void form() throws Exception {
		String url = "http://localhost:8070/cart/save";
		for (int i = 0; i < 30; i++) {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("userId", "5");
			paramMap.put("itemId", ""+i);
			paramMap.put("itemTitle", "某商品 超值 物美价廉");
			paramMap.put("itemPrice", "100");
			paramMap.put("itemImage", "image");
			paramMap.put("num", "1");
			String result = httpPostWithForm(url, paramMap);
			System.out.println(result);
		}
	}

	public static String httpPostWithForm(String url, Map<String, String> paramMap) throws Exception {
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpClient client = HttpClients.createDefault();
		String respContent = null;
		List<BasicNameValuePair> pairList = new ArrayList<BasicNameValuePair>();
		for (Map.Entry<String, String> entry : paramMap.entrySet()) {
			// String key = entry.getKey();
			// String value = entry.getValue();
			// BasicNameValuePair pair = new BasicNameValuePair(key, value);
			// pairList.add(pair);
			pairList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		httpPost.setEntity(new UrlEncodedFormEntity(pairList, "utf-8"));
		HttpResponse resp = client.execute(httpPost);
		if (resp.getStatusLine().getStatusCode() == 200) {
			HttpEntity he = resp.getEntity();
			respContent = EntityUtils.toString(he, "utf-8");
		}
		return respContent;
	}

	@Test
	public void json() throws Exception {
		String url = "http://localhost:8070/cart/query/5?page=1&row1";
		String result = httpPostWithJSON(url);
		System.out.println(result);
	}

	public static String httpPostWithJSON(String url) throws Exception {
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpClient client = HttpClients.createDefault();
		String respContent = null;
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("name", "admin");
		jsonParam.put("pass", "123456");
		StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8"); // 解决中文乱码问题
		entity.setContentEncoding("utf-8");
		entity.setContentType("application/json");
		httpPost.setEntity(entity);

		HttpResponse resp = client.execute(httpPost);
		if (resp.getStatusLine().getStatusCode() == 200) {
			HttpEntity he = resp.getEntity();
			respContent = EntityUtils.toString(he, "UTF-8");
		}
		return respContent;
	}
}
```

## （14）测试验证

```
查询：
	http://localhost:8070/cart/query/1
修改数量：
	http://localhost:8070/cart/update/num/1/2/3
删除购物车信息：
	http://localhost:8070/cart/delete/4/1
添加信息：
	调用HttpClientPost.form测试方法添加数据。
```