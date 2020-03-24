package com.leopaulmartin.spring.leboncoinecole.config;

import com.leopaulmartin.spring.leboncoinecole.security.RestAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private RestAccessDeniedHandler accessDeniedHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http
//				.authorizeRequests()
//				.antMatchers(
//						"/",
//						"/js/**",
//						"/css/**",
//						"/img/**",
//						"/webjars/**").permitAll()
//				.antMatchers("/user/**").hasRole("USER")
//				.anyRequest().authenticated()
////				REST API
////				.antMatchers("/admin/**").hasAnyRole("ROLE_ADMIN")
////				.and()
////				.exceptionHandling().accessDeniedHandler(accessDeniedHandler)
//				.and()
//				.formLogin()
//				.loginPage("/login")
//				.permitAll()
//				.and()
//				.logout()
//				.invalidateHttpSession(true)
//				.clearAuthentication(true)
//				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//				.logoutSuccessUrl("/login?logout")
//				.permitAll()
//				.and()
//				.exceptionHandling()
//				.accessDeniedHandler(accessDeniedHandler);
////				.and().csrf().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication()
//				.withUser("user").password("password").roles("USER")
//				.and()
//				.withUser("manager").password("password").roles("MANAGER");
	}
}
