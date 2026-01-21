package com.example.TrafficBoard.controller;

import com.example.TrafficBoard.srevice.UserService;
import com.example.TrafficBoard.srevice.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final UserServiceImpl userService;
//    private final PostServiceImpl
}
