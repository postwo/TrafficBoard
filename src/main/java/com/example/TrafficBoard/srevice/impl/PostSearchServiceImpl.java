package com.example.TrafficBoard.srevice.impl;

import com.example.TrafficBoard.dto.PostDTO;
import com.example.TrafficBoard.dto.request.PostSearchRequest;
import com.example.TrafficBoard.mapper.PostSearchMapper;
import com.example.TrafficBoard.srevice.PostSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class PostSearchServiceImpl implements PostSearchService {


    private final PostSearchMapper productSearchMapper;

    // 검색에서 비동기 사용 x 결과를 기다리지 않기 때문에 
//    @Async // 비동기 , 이거는 결과값을 받아서 바로 화면에 보여줘야 하는 경우에는 사용 x
    //이유: 메인 로직은 @Async 메서드가 끝나기도 전에 "나 다음 줄 실행할래!" 하고 넘어가 버리는데, 아직 데이터(List)는 도착하지 않았기 때문
    //결과: 데이터가 비어 있거나 null인 상태로 화면이 그려질 위험
    //결론: @Async는 주로 "결과를 즉시 돌려받지 않아도 되는 작업"(로그 기록, 메일 발송, 푸시 알람 등)에 주로 사용
    @Override
    // Cacheable은 메서드를 실행하기전에 먼저 key를 가진 데이터가 캐시에 있나 확인후 메서드를 실행
    // 캐시에 데이터가 없으면 메서드를 실행후 데이터를 캐시에 저장
    @Cacheable(value = "getProducts", key = "'getProducts' + #postSearchRequest.getName() + #postSearchRequest.getCategoryId()")
    public List<PostDTO> getProducts(PostSearchRequest postSearchRequest) {
        List<PostDTO> postDTOList = null;
        try {
            postDTOList = productSearchMapper.selectPosts(postSearchRequest);
        } catch (RuntimeException e) {
            log.error("selectPosts 실패");
        }
        return postDTOList;
    }
}
