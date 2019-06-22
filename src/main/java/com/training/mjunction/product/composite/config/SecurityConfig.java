package com.training.mjunction.product.composite.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@Configuration
@EnableWebSecurity
@EnableResourceServer
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(final WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/actuator/**", "/js/**", "/css/**", "/*.html", "/*.htm", "/*.jsp",
				"/swagger-ui.html", "/v2/**");
	}

}