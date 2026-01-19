package com.example.TrafficBoard.controller;

import com.example.TrafficBoard.srevice.impl.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private CategoryServiceImpl categoryService;
}
