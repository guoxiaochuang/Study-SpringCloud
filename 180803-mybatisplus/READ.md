# SpringBoot+MyBatis+MyBatisPlus整合
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
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
public class Cart extends BasePojo{
	private static final long serialVersionUID = 1L;
	@TableField(exist = false) // 数据库表中不存在的字段，需要标识
	@TableId(value = "id", type = IdType.AUTO)//主键自增
	private Long id;
	@TableField(value = "USER_ID") // 属性和字段映射
	private Long userId;
	@TableField(value = "ITEM_ID") // 属性和字段映射
	private Long itemId;
	@TableField(value = "ITEM_TITLE") // 属性和字段映射
	private String itemTitle;
	@TableField(value = "ITEM_IMAGE") // 属性和字段映射
	private String itemImage;
	@TableField(value = "ITEM_PRICE") // 属性和字段映射
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
package com.just.common;
/**
 * 
 * 自定义响应结构
 */
public class SysResult {
	/* 响应业务状态
	 * 200 成功
	 * 201 错误
	 * 400 参数错误
	 */
	private Integer status;
	// 响应消息
	private String msg;
	// 响应中的数据
	private Object data;
	public SysResult(){
	}
	public SysResult(Object data){
		this.status = 200;
		this.msg = "OK";
		this.data = data;
	}
	public SysResult(Integer status, String msg, Object data){
		this.status = status;
		this.msg = msg;
		this.data = data;
	}
	public static SysResult ok(){
		return new SysResult(null);
	}
	public static SysResult ok(Object data){
		return new SysResult(data);
	}
	public static SysResult build(Integer status, String msg){
		return new SysResult(status, msg, null);
	}
	public static SysResult build(Integer status, String msg, Object data){
		return new SysResult(status, msg, data);
	}
	public Boolean isOK(){
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
继承MybatisPlus提供的BaseMapper，它会自动产生SQL语句。采用注解开发方式，SQL直接写在方法上面。特殊方法通过自定义接口实现CartMapper，通用的方法通过BaseMapper实现。这里的方法和业务接口的方法不一致，业务接口中的部分方法基类BaseMapper已经实现，其他方法按业务需要规划。
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
import com.just.common.SysResult;
public interface CartService {
	//保存 查询 更新 删除
	public SysResult save(Cart cart);
	public SysResult query(Long userId, Integer page, Integer row);
	public SysResult updateNum(Cart cart);
	public SysResult deleteItem(Cart cart);
}
```
## （8）创建业务层接口实现：CartServiceImpl
## （9）创建控制层控制类：CartController
## （10）配置application.yml文件
## （11）配置日志文件：log4j.properties
## （12）创建启动类：RunApp
## （13）模拟Post请求工具类

## （14）测试验证
```
http://localhost:8070/cart/query/1

```