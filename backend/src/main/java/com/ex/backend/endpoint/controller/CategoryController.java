package com.ex.backend.endpoint.controller;

import com.ex.backend.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiResponse<Object>> getCategories(
            @RequestParam(required = false) Integer level) {
        Object categories = categoryService.getCategories(level);
        return ResponseEntity.ok(ApiResponse.success(categories, "카테고리 목록을 성공적으로 조회했습니다."));
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<ApiResponse<Object>> getCategoryProducts(
            @PathVariable Long id,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int perPage,
            @RequestParam(defaultValue = "created_at:desc") String sort,
            @RequestParam(defaultValue = "true") boolean includeSubcategories) {

        Object result = categoryService.getCategoryProducts(id, page, perPage, sort, includeSubcategories);
        return ResponseEntity.ok(ApiResponse.success(result, "카테고리 상품 목록을 성공적으로 조회했습니다."));
    }

}
