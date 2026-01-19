package com.example.TrafficBoard.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD) //이 어노테이션을 메서드에만 붙일 수 있도록 제한 설정
@Retention(RetentionPolicy.RUNTIME) //어노테이션 정보를 **런타임(프로그램 실행 중)**까지 유지 설정
public @interface LoginCheck { // 이렇게 @interfac를 붙이고 만들면 이제 @LoginCheck(type = LoginCheck.UserType.USER)  이런 어노테이션 형식으로 사용할수 있게됨(controller 보면 됨)
    public static enum UserType {
        USER, ADMIN
    }
    UserType type(); //어노테이션을 사용할 때 추가 정보를 전달하는 설정값
}