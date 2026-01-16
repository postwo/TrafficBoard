package com.example.TrafficBoard.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class UserDTO {

    public enum Status {
        DEFAULT, ADMIN, DELETED
    }

    private int id;
    private String userId;
    private String password;
    private String nickName;
    private boolean isAdmin;
    private Date createTime;
    private boolean isWithDraw;
    private Status status;
    private Date updateTime;


    //회원가입에 필수적인 필드(userId, password, nickName) 중 하나라도 null인지 확인하는 유효성 검사 로직
    public static boolean hasNullDataBeforeSignup(UserDTO userDTO) {
        return userDTO.getUserId() == null || userDTO.getPassword() == null
                || userDTO.getNickName() == null;
    }
}
