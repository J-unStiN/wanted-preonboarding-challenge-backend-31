package com.ex.backend.endpoint.controller;

import com.ex.backend.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/main")
@RequiredArgsConstructor
public class MainPageController {

//    private final MainService mainService;

    @GetMapping
    public ResponseEntity<ApiResponse<Object>> getMainPageData() {
//        Object mainPageData = mainService.getMainPageData();
        return ResponseEntity.ok(ApiResponse.success(null, "메인 페이지 상품 목록을 성공적으로 조회했습니다."));
    }
}