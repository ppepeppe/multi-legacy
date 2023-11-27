package com.example.legacydb.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(value = "com.example.legacydb.dao.mariadb")
public class MariadbConfig {

    private final String mariadbDatabase = "mariadbDatabase";

    // A database DataSource
    @Primary
    @Bean(mariadbDatabase)
    @ConfigurationProperties(prefix = "spring.mariadb.datasource")
    public DataSource MariadbDatabase() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    // SqlSessionTemplate 에서 사용할 SqlSession 을 생성하는 Factory
    @Primary
    @Bean
    public SqlSessionFactory MariadbSqlSessionFactory(DataSource dataSource) throws Exception {
        /*
         * MyBatis 는 JdbcTemplate 대신 Connection 객체를 통한 질의를 위해서 SqlSession 을 사용한다.
         * 내부적으로 SqlSessionTemplate 가 SqlSession 을 구현한다.
         * Thread-Safe 하고 여러 개의 Mapper 에서 공유할 수 있다.
         */
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);

        // MyBatis Mapper Source
        // MyBatis 의 SqlSession 에서 불러올 쿼리 정보
        Resource[] res = new PathMatchingResourcePatternResolver().getResources("classpath:mapper/mariadb/*.xml");
        bean.setMapperLocations(res);

        return bean.getObject();
    }

    // DataSource 에서 Transaction 관리를 위한 Manager 클래스 등록
    @Primary
    @Bean
    public DataSourceTransactionManager MariadbTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}