package com.example.TrafficBoard.srevice.impl;

import com.example.TrafficBoard.dto.PostDTO;
import com.example.TrafficBoard.dto.UserDTO;
import com.example.TrafficBoard.mapper.PostMapper;
import com.example.TrafficBoard.mapper.UserProfileMapper;
import com.example.TrafficBoard.srevice.PostService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Log4j2
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserProfileMapper userProfileMapper;

    //동작 방식: postMapper.register(postDTO)가 성공적으로 실행되어 DB에 새 글이 들어가면, @CacheEvict가 즉시 호출되어 getProducts 캐시 영역을 싹 비웁니다.
    // @CacheEvict 사용 이유: 다음번에 누군가 목록 조회를 요청했을 때, 비어있는 캐시 대신 DB에서 방금 등록된 새 게시글까지 포함된 최신 리스트를 가져오게 하기 위함입니다.
    @CacheEvict(value="getProducts", allEntries = true)
    @Override
    public void register(String id, PostDTO postDTO) { // 게시글 작성
        UserDTO memberInfo = userProfileMapper.getUserProfile(id);
        postDTO.setUserId(memberInfo.getId());
        postDTO.setCreateTime(new Date());

        if (memberInfo != null) {
            postMapper.register(postDTO);
        } else {
            log.error("register ERROR! {}", postDTO);
            throw new RuntimeException("register ERROR! 상품 등록 메서드를 확인해주세요\n" + "Params : " + postDTO);
        }
    }

    @Override
    public List<PostDTO> getMyProducts(int accountId) { // 내가 작성한글
        List<PostDTO> postDTOList = postMapper.selectMyProducts(accountId);
        return postDTOList;
    }

    @Override
    public void updateProducts(PostDTO postDTO) { // 게시글 수정
        if (postDTO != null && postDTO.getId() != 0 && postDTO.getUserId() != 0) {
            postMapper.updateProducts(postDTO);
        } else {
            log.error("updateProducts ERROR! {}", postDTO);
            throw new RuntimeException("updateProducts ERROR! 물품 변경 메서드를 확인해주세요\n" + "Params : " + postDTO);
        }
    }

    @Override
    public void deleteProduct(int userId, int productId) { // 게시글 삭제
        if (userId != 0 && productId != 0) {
            postMapper.deleteProduct(productId);
        } else {
            log.error("deleteProudct ERROR! {}", productId);
            throw new RuntimeException("updateProducts ERROR! 물품 삭제 메서드를 확인해주세요\n" + "Params : " + productId);
        }
    }
}