package com.example.TrafficBoard.srevice;

import com.example.TrafficBoard.dto.CategoryDTO;

public interface CategoryService {

    void register(String accountId, CategoryDTO categoryDTO);

    void update(CategoryDTO categoryDTO);

    void delete(int categoryId);
}
