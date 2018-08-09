package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;

/**
 * 
 * 自定义Ribbon配置
 * 规定：这个类不能再@ComponentScan和@SpringBootApplication本包和子包下，否则引起@RibbonClients扫描冲突
 * 注意：随机第一次打断点进入，之后多次刷新就不进入，可能由于本地缓存原因
 */
@Configuration
public class RibbonRuleConfig {
	@Bean
	public IRule ribbonRule(){
		return new RandomRule();
	}
}
