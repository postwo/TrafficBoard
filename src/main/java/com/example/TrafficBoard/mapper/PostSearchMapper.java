package com.example.TrafficBoard.mapper;

import com.example.TrafficBoard.dto.PostDTO;
import com.example.TrafficBoard.dto.request.PostSearchRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostSearchMapper {
    public List<PostDTO> selectPosts(PostSearchRequest postSearchRequest);
}
