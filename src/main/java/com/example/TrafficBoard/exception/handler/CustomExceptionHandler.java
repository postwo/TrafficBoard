package com.example.TrafficBoard.exception.handler;

import com.example.TrafficBoard.dto.response.CommonResponse;
import com.example.TrafficBoard.exception.BoardServerException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler { // 공통 에러 처리
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleRuntimeExceptionException(RuntimeException ex) {
        CommonResponse commonResponse = new CommonResponse(HttpStatus.OK, "RuntimeException", ex.getMessage(), ex.getMessage());
        return new ResponseEntity<>(commonResponse, new HttpHeaders(), commonResponse.getStatus());
    }

    @ExceptionHandler({BoardServerException.class})
    public ResponseEntity<Object> handleBoardServerException(BoardServerException ex) {
        CommonResponse commonResponse = new CommonResponse(HttpStatus.OK, "BoardServerException", ex.getMessage(), ex.getMessage());
        return new ResponseEntity<>(commonResponse, new HttpHeaders(), commonResponse.getStatus());
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<Object> defaultException(Exception ex) {
        CommonResponse commonResponse = new CommonResponse(HttpStatus.OK, "BoardServerException", ex.getMessage(), ex.getMessage());
        return new ResponseEntity<>(commonResponse, new HttpHeaders(), commonResponse.getStatus());
    }

}