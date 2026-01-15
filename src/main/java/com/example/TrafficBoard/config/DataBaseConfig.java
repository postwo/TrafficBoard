package com.example.TrafficBoard.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataBaseConfig {

    //데이터베이스 연결을 위한 DataSource 객체를 수동으로 생성하고 설정하기 위한 설정 클래스
    //yml 파일에 적었던 데이터베이스 정보(URL, ID, PW)를 바탕으로 **실제 자바 프로그램이 데이터베이스와 통신할 수 있는 통로(Connection)**를 만드는 코드
    @Bean
    //application.yml 파일에서 spring.datasource로 시작하는 설정들(url, username, password 등)을 찾아서 이 메소드가 생성하는 객체의 필드에 자동으로 주입
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource(){
        //DataSource 객체를 생성합니다. 이때 실제 값(URL 등)은 위에서 언급한 ConfigurationProperties에 의해 자동으로 채워 진다
        return DataSourceBuilder.create().build();
    }
}
