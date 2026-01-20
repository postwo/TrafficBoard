package com.example.TrafficBoard.controller;

import com.example.TrafficBoard.aop.LoginCheck;
import com.example.TrafficBoard.dto.CategoryDTO;
import com.example.TrafficBoard.srevice.impl.CategoryServiceImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 응답할 값을 만든거
    @LoginCheck(type = LoginCheck.UserType.ADMIN) // 사용자 지정 , admin 으로 로그인한 회원만 카테고리 지정할수 있음
    public void registerCategory(String accountId, @RequestBody CategoryDTO categoryDTO) {
        categoryService.register(accountId, categoryDTO);
    }

    @PatchMapping("{categoryId}")
    @LoginCheck(type = LoginCheck.UserType.ADMIN)
    public void updateCategories(String accountId,
                                 @PathVariable(name = "categoryId") int categoryId,
                                 @RequestBody CategoryRequest categoryRequest) {
        CategoryDTO categoryDTO = new CategoryDTO(categoryId, categoryRequest.getName(), CategoryDTO.SortStatus.NEWEST,10,1);
        categoryService.update(categoryDTO);
    }

    @DeleteMapping("{categoryId}")
    @LoginCheck(type = LoginCheck.UserType.ADMIN)
    public void updateCategories(String accountId,
                                 @PathVariable(name = "categoryId") int categoryId) {
        categoryService.delete(categoryId);
    }

    // -------------- request 객체 --------------

    @Setter
    @Getter
    private static class CategoryRequest {
        private int id;
        private String name;
    }
}
