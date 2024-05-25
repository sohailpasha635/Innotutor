package com.sohail.main.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService userService;
	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
		provider.setUserDetailsService(userService);
		//provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
		provider.setPasswordEncoder(new BCryptPasswordEncoder());
		
		return provider;
		
	}
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		// TODO Auto-generated method stub
//		http
//		 .csrf().disable()
//	     .authorizeHttpRequests()
//	     .antMatchers("/api/user/**").permitAll()
//	     .antMatchers(HttpMethod.GET,"/registration/all").hasAnyAuthority("ROAL_ADMIN")
//	     .antMatchers(HttpMethod.GET,"/registration/one/**").hasAnyAuthority("ROAL_USER")
//	     .anyRequest()
//	     .authenticated();
//	     
//	}
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable()
		
				.authorizeRequests()
				.antMatchers("/auth","/forgotPassword").permitAll()
				.antMatchers("/user/**").permitAll()				
//				 .antMatchers(HttpMethod.GET,"/admin/**").hasAnyAuthority("ROLE_ADMIN")
//			     .antMatchers(HttpMethod.GET,"/tutor/**").hasAnyAuthority("ROLE_TUTOR")
//			     .antMatchers(HttpMethod.GET,"/learner/**").hasAnyAuthority("ROLE_TUTOR","ROLE_LEARNER")
		
				.anyRequest().permitAll()
				
		
				.and()				
				.exceptionHandling().and().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

						
}
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
}
