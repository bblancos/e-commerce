package net.blanc.back_e_commerce.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages={"net.blanc.back_e_commerce.dto"})
@EnableTransactionManagement
public class HibernateConfig {
	
	 //Change the below based on your DDatabse
//	private final static String DATABASE_URL="jdbc:postgresql://localhost:5433/e_commerce";
//	private final static String DATABASE_DRIVER="org.postgresql.Driver";
//	private final static String DATABASE_DIALECT="org.hibernate.dialect.PostgreSQL95Dialect";
//	private final static String DATABASE_USERNAME="postgres";
//	private final static String DATABASE_PASSWORD="root";
	
	 //Change the below based on your DDatabse
	private final static String DATABASE_URL="jdbc:h2:tcp://localhost/~/ecommerce";
	private final static String DATABASE_DRIVER="org.h2.Driver";
	private final static String DATABASE_DIALECT="org.hibernate.dialect.H2Dialect";
	private final static String DATABASE_USERNAME="sa";
	private final static String DATABASE_PASSWORD="";
	
 //Data Source avaliable throughout project
	@Bean("dataSource")	
	public DataSource getDataSource()
	{
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(DATABASE_DRIVER);
		dataSource.setUrl(DATABASE_URL);
		dataSource.setUsername(DATABASE_USERNAME);
		dataSource.setPassword(DATABASE_PASSWORD);
		
		return dataSource;		
	}
	
	@Bean	
	public SessionFactory getSessionFactory(DataSource dataSource)
	{
		LocalSessionFactoryBuilder builder =  new LocalSessionFactoryBuilder(dataSource);
		builder.addProperties(getHibernateProperties());
		builder.scanPackages("net.blanc.back_e_commerce.dto");
		return builder.buildSessionFactory();
				
	}

	// All the hibernate properties will be returned in this method
	private Properties getHibernateProperties() {
		
		Properties properties= new Properties();
		properties.put("hibernate.dialect",DATABASE_DIALECT);
		properties.put("hibernate.show_sql","true");
		properties.put("hibernate.format_sql","true");
		//properties.put("hibernate.hbm2ddl.auto", "create");

		return properties;
	}
	
	// transaction manager bean 
	@Bean
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory){
		HibernateTransactionManager transactionManager= new HibernateTransactionManager(sessionFactory);
		return transactionManager;
	}
	
	
	
	
}
