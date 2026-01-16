package com.example.TrafficBoard.utils;

import jakarta.servlet.http.HttpSession;

public class SessionUtil {
    // 세션에 저장될 키(Key) 값들을 상수로 정의하여 오타 방지 및 유지보수성 향상
    private static final String LOGIN_MEMBER_ID = "LOGIN_MEMBER_ID";
    private static final String LOGIN_ADMIN_ID = "LOGIN_ADMIN_ID";

    /**
     * 인스턴스화 방지 (유틸리티 클래스이므로 객체 생성이 필요 없음)
     */
    private SessionUtil() {
    }

    /**
     * 세션에서 로그인된 일반 사용자의 ID를 가져옴
     * @param session 현재 요청의 HttpSession
     * @return 로그인된 사용자 ID (없으면 null)
     */
    public static String getLoginMemberId(HttpSession session) {
        return (String) session.getAttribute(LOGIN_MEMBER_ID);
    }

    /**
     * 로그인 성공 시 세션에 일반 사용자의 ID를 저장
     * @param session 현재 요청의 HttpSession
     * @param id 저장할 사용자 ID
     */
    public static void setLoginMemberId(HttpSession session, String id) {
        session.setAttribute(LOGIN_MEMBER_ID, id);
    }

    /**
     * 세션에서 로그인된 관리자의 ID를 가져옴
     * @param session 현재 요청의 HttpSession
     * @return 로그인된 관리자 ID (없으면 null)
     */
    public static String getLoginAdminId(HttpSession session) {
        return (String) session.getAttribute(LOGIN_ADMIN_ID);
    }

    /**
     * 로그인 성공 시 세션에 관리자의 ID를 저장
     * @param session 현재 요청의 HttpSession
     * @param id 저장할 관리자 ID
     */
    public static void setLoginAdminId(HttpSession session, String id) {
        session.setAttribute(LOGIN_ADMIN_ID, id);
    }

    /**
     * 세션을 무효화하여 로그아웃 처리
     * 해당 세션에 저장된 모든 데이터(사용자, 관리자 정보 등)가 삭제됨
     * @param session 현재 요청의 HttpSession
     */
    public static void clear(HttpSession session) {
        session.invalidate();
    }
}
