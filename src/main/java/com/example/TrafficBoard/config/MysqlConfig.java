package com.example.TrafficBoard.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.example.TrafficBoard.mapper") // Mapper 인터페이스 위치 스캔
public class MysqlConfig {

    @Bean// 반드시 Bean으로 등록해야 MyBatis가 작동합니다.
    //DataSource는 DataBaseConfig에서 설정한 DataSource를 사용합니다.
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception{
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        // 1. 실제 쿼리가 담긴 Mapper XML 파일 위치 설정
        sessionFactory.setMapperLocations(resolver.getResources("classpath:mappers/*.xml"));

        // 2. MyBatis 세부 설정 파일 위치 (CamelCase 설정 등)
        Resource myBatisConfig = new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml");
        // mybatis-config.xml 파일의 설정을 사용하여 SqlSessionFactory를 생성
        sessionFactory.setConfigLocation(myBatisConfig);

        return sessionFactory.getObject();

    }
}
