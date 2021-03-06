package com.adaptive.ui;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

@SpringBootApplication
@Configuration
@EnableResourceServer
@EnableScheduling
public class AdaptiveUiApplication extends ResourceServerConfigurerAdapter {

	@Value("${CHECK_TOKEN_URI}")
	private String CHECK_TOKEN_URI;

	// 调用远程Auth server进行token校验
	@Bean
	public RemoteTokenServices remoteTokenServices() {
		RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
		remoteTokenServices.setCheckTokenEndpointUrl(CHECK_TOKEN_URI);
		return remoteTokenServices;
	}

	public static void main(String[] args) {
		//运行程序
		SpringApplication springApplication = new SpringApplication(AdaptiveUiApplication.class);
		springApplication.run(args);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		//设置拦截的路径，需要验证access_token才能访问
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/userType/getUserType").authenticated();
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/question/getAll").authenticated();
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/model/getAll").authenticated();
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/model/delete").authenticated();
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/userAnswers/getAll").authenticated();
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/userAnswers/delete").authenticated();
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/trainArrayAttributes/getAll").authenticated();
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId("adaptive_ui_service");
	}
}
