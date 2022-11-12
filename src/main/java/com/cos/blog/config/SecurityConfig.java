package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.blog.config.auth.PrincipalDetailService;

//빈등록: 스프링컨테이너에서 객체 관리
@Configuration //빈등록 (IOC관리)
@EnableWebSecurity //시큐리티 필터가 등록된다. = 스프링시큐어리티가 활성화되어있는데 어떤 설정을 해당파일에서 하겠다.
@EnableGlobalMethodSecurity(prePostEnabled = true) //특정주소로 접근하면 권한 및 인증을 미리 체크하겠다.
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private PrincipalDetailService principalDetailService;
	
	@Bean //IOC가됨 리턴하는 값을 스프링이 관리
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	//시큐리티가 대신로그인할때
	//password를 가로채는데
	//해당 password가 뭘로 해쉬되었는지 알아야 같은 해쉬 알고리즘으로 DB 해쉬랑 비교 할 수 있다.
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		  .csrf().disable() //csfr 토큰 비활셩화
		  .authorizeHttpRequests()
			.antMatchers("/","/auth/**","/js/**","/css/**","/image/**")
			.permitAll()
			.anyRequest()
			.authenticated() 
		  .and()
		    .formLogin()
		    .loginPage("/auth/loginForm") //인증이 필요한 폼은 loginform으로 간다.
		    .loginProcessingUrl("/auth/loginProc")
		    .defaultSuccessUrl("/"); //스프링시큐어리티가 해당 주소로 요청오는 로그인을 가로채 대신로그인 한다.
	}
}
