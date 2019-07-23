package com.project.planningapp.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource({"classpath:application.properties"})
@ComponentScan({"com.project.planningapp"})
public class SessionFactoryConfig {

	@Autowired
	private DataSource dataSource;
		
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setPackagesToScan(
				new String[] { "com.project.planningapp" });
		return sessionFactory;
	}
	
}
