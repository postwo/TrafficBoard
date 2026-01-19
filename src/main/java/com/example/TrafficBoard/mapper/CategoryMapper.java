package com.example.TrafficBoard.mapper;

import com.example.TrafficBoard.dto.CategoryDTO;

public interface CategoryMapper {
    public int register(CategoryDTO productDTO);

    public void updateCategory(CategoryDTO categoryDTO);

    public void deleteCategory(int categoryId);
}
