package com.example.TrafficBoard.srevice;

import com.example.TrafficBoard.dto.PostDTO;
import com.example.TrafficBoard.dto.request.PostSearchRequest;

import java.util.List;

public interface PostSearchService {
    List<PostDTO> getProducts(PostSearchRequest postSearchRequest);
}
