package com.example.TrafficBoard.srevice;

import com.example.TrafficBoard.dto.CommentDTO;
import com.example.TrafficBoard.dto.PostDTO;
import com.example.TrafficBoard.dto.TagDTO;

import java.util.List;

public interface PostService {

    void register(String id, PostDTO postDTO);

    List<PostDTO> getMyProducts(int accountId);

    void updateProducts(PostDTO postDTO);

    void deleteProduct(int userId, int productId);

    // comment
    void registerComment(CommentDTO commentDTO);

    void updateComment(CommentDTO commentDTO);

    void deletePostComment(int userId, int commentId);

    // tag
    void registerTag(TagDTO tagDTO);

    void updateTag(TagDTO tagDTO);

    void deletePostTag(int userId, int tagId);

}
