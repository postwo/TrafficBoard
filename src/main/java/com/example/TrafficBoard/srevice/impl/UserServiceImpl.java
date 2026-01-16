package com.example.TrafficBoard.srevice.impl;

import com.example.TrafficBoard.dto.UserDTO;
import com.example.TrafficBoard.exception.DuplicateIdException;
import com.example.TrafficBoard.mapper.UserProfileMapper;
import com.example.TrafficBoard.srevice.UserService;
import com.example.TrafficBoard.utils.SHA256Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.example.TrafficBoard.utils.SHA256Util.encryptSHA256;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserProfileMapper userProfileMapper;

    // 회원가입
    @Override
    public void register(UserDTO userDTO) {
        boolean duplIdResult = isDuplicatedId(userDTO.getUserId()); // 중복 검사
        if (duplIdResult){
            throw new DuplicateIdException("중복된 ID입니다.");
        }
        userDTO.setCreateTime(new Date()); // 현재 시간
        userDTO.setPassword(encryptSHA256(userDTO.getPassword()));
        int insertCount = userProfileMapper.register(userDTO);

        if (insertCount != 1) {
            log.error("insertMember ERROR! {}", userDTO);
            throw new RuntimeException(
                    "insertUser ERROR! 회원가입 메서드를 확인해주세요\n" + "Params : " + userDTO);
        }
    }

    // 로그인
    @Override
    public UserDTO login(String id, String password) {
        String cryptoPassword = SHA256Util.encryptSHA256(password);
        UserDTO memberInfo = userProfileMapper.findByIdAndPassword(id, cryptoPassword);
        return memberInfo;
    }

    @Override
    public boolean isDuplicatedId(String id) {
        return userProfileMapper.idCheck(id) == 1;
    }

    @Override
    public UserDTO getUserInfo(String userId) {
        log.info(userId);
        return userProfileMapper.getUserProfile(userId);
    }

    // 비밀번호 수정
    @Override
    public void updatePassword(String id, String beforePassword, String afterPassword) {
        String cryptoPassword = SHA256Util.encryptSHA256(beforePassword);
        UserDTO memberInfo = userProfileMapper.findByIdAndPassword(id, cryptoPassword);

        if (memberInfo != null) {
            memberInfo.setPassword(SHA256Util.encryptSHA256(afterPassword));
            int insertCount = userProfileMapper.updatePassword(memberInfo);
        } else {
            log.error("updatePasswrod ERROR! {}", memberInfo);
            throw new IllegalArgumentException("updatePasswrod ERROR! 비밀번호 변경 메서드를 확인해주세요\n" + "Params : " + memberInfo);
        }
    }

    // 회원삭제
    @Override
    public void deleteId(String id, String passWord) {
        String cryptoPassword = SHA256Util.encryptSHA256(passWord);
        UserDTO memberInfo = userProfileMapper.findByIdAndPassword(id, cryptoPassword);

        if (memberInfo != null) {
            userProfileMapper.deleteUserProfile(memberInfo.getUserId());
        } else {
            log.error("deleteId ERROR! {}", memberInfo);
            throw new RuntimeException("deleteId ERROR! id 삭제 메서드를 확인해주세요\n" + "Params : " + memberInfo);
        }
    }
}
