package com.example.TrafficBoard.aop;

import com.example.TrafficBoard.utils.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

// 이게 동작할려면일단 로그인한 상태여야지 권한을 가지고 와서 처리
@Aspect //AOP를 구현한 클래스임을 선언 공통 ,로직(로그인 체크)을 가로채서 실행하는 역할
@Log4j2
@Component
//LOWEST_PRECEDENCE는 가장 낮은 우선순위로, 다른 중요한 AOP들이 먼저 실행된 후 마지막에 실행
@Order(Ordered.LOWEST_PRECEDENCE) //여러 개의 AOP가 겹칠 때 실행 순서를 정한다
public class LoginCheckAspect {
    // @Around = 대상 메서드가 실행되기 전과 후를 모두 제어
    // around 안에 있는 @LoginCheck 어노테이션이 붙은 메서드만 찾아가서 이 로직을 실행하라는 뜻입니다. 뒤의 && @annotation(loginCheck) 덕분에 파라미터로 해당 어노테이션 정보를 바로 받아올 수 있다
    @Around("@annotation(com.example.TrafficBoard.aop.LoginCheck) && @ annotation(loginCheck)")
    //ProceedingJoinPoint: 실행하려던 원래 메서드(컨트롤러 메서드 등)에 대한 정보를 담고 있다
    public Object adminLoginCheck(ProceedingJoinPoint proceedingJoinPoint, LoginCheck loginCheck) throws Throwable {

        // 1. 현재 요청의 세션(Session) 객체를 가져옴
        // 클라이언트가 요청을 보내면, 컨트롤러 메서드가 실행되기 직전에 AOP(LoginCheckAspect)가 먼저 실행
        // 컨트롤러에서 세션을 넘겨주지 않아도, **스프링이 관리하는 현재 요청 문맥(Context)**에서 직접 세션 객체를 꺼내오는 코드입니다. 그래서 컨트롤러의 파라미터와 상관없이 세션에 접근할 수 있었던 것
        // 그리고 이 정보를 컨트롤러에 매개변수인 String accountId 여기로 전달해준다
        HttpSession session = (HttpSession) ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest().getSession();
        String id = null;
        int idIndex = 0; // 0번째 인덱스 지정

        // 2. 어노테이션에 설정된 type(USER/ADMIN) 확인
        // logincheck 에서 type을 가져와서 문자열로 변환
        String userType = loginCheck.type().toString();
        switch (userType) {
            case "ADMIN": {
                id = SessionUtil.getLoginAdminId(session);
                break;
            }
            case "USER": {
                id = SessionUtil.getLoginMemberId(session);
                break;
            }
        }
        if (id == null) {
            log.debug(proceedingJoinPoint.toString()+ "accountName :" + id);
            throw new HttpStatusCodeException(HttpStatus.UNAUTHORIZED, "로그인한 id값을 확인해주세요.") {};
        }

        // *여기서 부터 중요*
        // aop 를 적용한 메서드에 전달된 인자(파라미터) 목록을 가져옴
        Object[] modifiedArgs = proceedingJoinPoint.getArgs();

        //proceedingJoinPoint.getArgs = 현재 실행되려는 컨트롤러 메서드의 파라미터들을 배열(Object[]) 형태로 가져온다
        // 매개변수를 가지고 있는지 검사
        if(proceedingJoinPoint.getArgs()!=null)
            // 첫 번째 파라미터(index 0)에 세션에서 찾은 id를 강제로 주입 해서 매개변수 추가
            modifiedArgs[idIndex] = id;

        // 수정한 파라미터를 담아서 실제 메서드를 실행
        return proceedingJoinPoint.proceed(modifiedArgs);
    }

}