# SpringBoot的第一个例子

## （1）创建一个maven项目

```
创建Maven工程，创建simple project，类型为jar
```

## （2）配置pom.xml文件

```
1、添加parent配置
2、修改properties相关配置：编码、jdk版本等
3、添加springboot依赖
```
> 相关配置如下

```
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">	<modelVersion>4.0.0</modelVersion>	<groupId>com.tedu</groupId>	<artifactId>sc-hello</artifactId>	<version>0.0.1-SNAPSHOT</version>	<parent>		<groupId>org.springframework.boot</groupId>		<artifactId>spring-boot-starter-parent</artifactId>		<version>1.5.4.RELEASE</version>		<relativePath /> <!-- lookup parent from repository -->	</parent>	<properties>		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>		<java.version>1.8</java.version>	</properties>	<dependencies>		<dependency>			<groupId>org.springframework.boot</groupId>			<artifactId>spring-boot-starter-web</artifactId>		</dependency>	</dependencies></project>
```

## （3）创建HelloController
> 代码如下

```
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class HelloController {
	@RequestMapping("hello")
	public String Hello(){
		return "Hello World!";
	}
}
```

## (4)创建启动项
> 代码如下

```
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RunApplication {
	public static void main(String[] args) {
		SpringApplication.run(RunApplication.class, args);
	}
}
```

## (5)启动验证
```
启动RunApplication，访问url：http://localhost:8080/hello
浏览器显示：Hello World!。
```




