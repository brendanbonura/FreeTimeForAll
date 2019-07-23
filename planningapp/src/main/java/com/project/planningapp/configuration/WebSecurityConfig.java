package com.project.planningapp.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.project.planningapp.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImpl	userDetailsService;
	
	@Autowired
	private DataSource dataSource;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
	
	// assigns custom user details and password encoder to DaoAuthProvider
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userDetailsService);
		auth.setPasswordEncoder(passwordEncoder());
		return auth;
	}
	
	// used for remember me in login form
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);
        return db;
    }
	
    // links password encoder bean to userDetialsService
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// pages accessable by anyone
		http.authorizeRequests().antMatchers("/","/login","/logout","/register").permitAll();
		
		http.authorizeRequests().antMatchers("/home/**").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");		
		
		// admin page
		http.authorizeRequests().antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')");
		
		// accessDeniedPage
		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
		
		// Login  and logout form config
		http.authorizeRequests()
			.and().formLogin()
				.loginProcessingUrl("/j_spring_security_check")
				.loginPage("/login")
				.defaultSuccessUrl("/home")
				.failureUrl("/login?error=true")
				.usernameParameter("email")
				.passwordParameter("password")
			.and().logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/logoutSuccessful");
			
		// remember me
		http.authorizeRequests().and()
			.rememberMe().tokenRepository(this.persistentTokenRepository());
	}

}
