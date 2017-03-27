package com.revanow.news.share.config;
//package com.revanow.auth.share.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.persistence.EntityManagerFactory;
//import java.util.Properties;
//
//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(basePackages = {"com.revanow.auth.persistence.repository"})
//@PropertySource(value = {"classpath:config.properties"})
//class RevaNowHibernateConfig extends RevaNowDataSource {
//
//    @Value("${hibernate.hbm2ddl.auto}")
//    private String hbm2ddlAuto;
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        vendorAdapter.setGenerateDdl(Boolean.TRUE);
//        vendorAdapter.setShowSql(Boolean.TRUE);
//
//        Properties jpaProperties = new Properties();
//        jpaProperties.put("hibernate.hbm2ddl.auto", hbm2ddlAuto);
//        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
//        jpaProperties.put("hibernate.show_sql", true);
//
//        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
//        factory.setDataSource(dataSource());
//        factory.setJpaVendorAdapter(vendorAdapter);
//        factory.setPackagesToScan("com.revanow.auth.domain.dte");
//        factory.setJpaProperties(jpaProperties);
//        factory.afterPropertiesSet();
//        factory.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
//
//        return factory;
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager() {
//        EntityManagerFactory factory = entityManagerFactory().getObject();
//        return new JpaTransactionManager(factory);
//    }
//
//}
