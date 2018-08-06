# SpringBoot+MyBatis整合

> 准备工作

```
创建数据库表、添加数据
创建脚本所在位置：./脚本/user.sql
```

## （1）创建一个maven项目

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
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">	<modelVersion>4.0.0</modelVersion>	<groupId>cn.tedu</groupId>	<artifactId>sc-mybatis</artifactId>	<version>0.0.1-SNAPSHOT</version>	<packaging>jar</packaging>	<name>sc-mybatis</name>	<url>http://maven.apache.org</url>	<parent>		<groupId>org.springframework.boot</groupId>		<artifactId>spring-boot-starter-parent</artifactId>		<version>1.5.4.RELEASE</version>		<relativePath /> <!-- lookup parent from repository -->	</parent>	<properties>		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>		<java.version>1.8</java.version>	</properties>	<dependencies>		<dependency>			<groupId>org.mybatis.spring.boot</groupId>			<artifactId>mybatis-spring-boot-starter</artifactId>			<version>1.3.0</version>		</dependency>		<dependency>			<groupId>org.springframework.boot</groupId>			<artifactId>spring-boot-starter-web</artifactId>		</dependency>		<dependency>			<groupId>org.springframework.boot</groupId>			<artifactId>spring-boot-starter-test</artifactId>			<scope>test</scope>		</dependency>		<dependency>			<groupId>mysql</groupId>			<artifactId>mysql-connector-java</artifactId>		</dependency>		<dependency>			<groupId>com.alibaba</groupId>			<artifactId>druid-spring-boot-starter</artifactId>			<version>1.1.0</version>		</dependency>	</dependencies></project>
```
## （3）创建User对象
> 属性定义：id、name、birthday、address
> 代码如下

```
import java.util.Date;
public class User {
	private Integer id;
	private String name;
	private Date birthday;
	private String address;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
```
## （4）创建持久层接口UserMapper
> 实现两种方式实现调用：xml方式和注解方式
> 代码如下：

```
import java.util.List;
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
```

## （5）创建UserMapper.xml配置文件

> 配置信息如下：

```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
	PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
	"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.just.mapper.UserMapper">
	<select id="getAll" resultType="User">
		select id,name,birthday,address from user
	</select>
</mapper>
```- 注意：
```
UserMapper.xml中
namespace是命名空间，唯一特性，需要与UserMapper接口对应。
select的id需要与方法名对应。
```
## (6)创建业务层UserService接口
> 代码如下：

```
import java.util.List;
import com.just.pojo.User;
public interface UserService {
	public List<User> getAll();
	public User getById(Integer id);
}
```

## (7)创建业务层UserServiceImpl实现类
> 代码如下：

```
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
```
- 注意：

```
*Impl实现类头上的注解@Service不能缺少。
如果缺少，会有如下报错：
Description:
***************************
APPLICATION FAILED TO START
***************************
Field userService in com.just.controller.UserController required a bean of type 'com.just.service.UserService' that could not be found.
Action:
Consider defining a bean of type 'com.just.service.UserService' in your configuration.
```

## （8）创建控制层UserController
> 代码如下

```
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.just.pojo.User;
import com.just.service.UserService;
@RestController
@RequestMapping("/user")
public class UserController {
	private UserService userService;
	@RequestMapping("/getAll")
	public List<User> getAll(){
		return userService.getAll();
	}
	@RequestMapping("/getById/{id}")
	public User getById(@PathVariable Integer id){
		return userService.getById(id);
	}
}
```

### （9）创建启动类
> 代码如下

```
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
@MapperScan("com.just.mapper")//扫描Mybatis接口文件
public class RunApplication {
	public static void main(String[] args) {
		SpringApplication.run(RunApplication.class, args);
	}
}
```

## （10）配置application.yml配置
> 配置如下：

```
server:
  port: 8070
spring:
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3306/db-1808
    username: root
    password: root
mybatis:
  typeAliasesPackage: com.just.pojo
  mapperLocations: classpath:mappers/*.xml
logging:
  level: 
    com.tedu.mapper: debug
```
- 注意：

```
数据库配置要配置正确，例子中使用的是mysql数据库。
mybatis中的mapperLocations的信息不要只配置错了。需要是在resources下的对应路径。
```

## (11)启动验证
```
启动RunApplication，
(1)访问url：http://localhost:8070/user/getAll
浏览器显示：全部用户信息。
(2)访问url：http://localhost:8070/user/getById/1
浏览器显示：对应id的用户信息。
```




