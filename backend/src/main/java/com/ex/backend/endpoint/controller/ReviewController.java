package com.ex.backend.endpoint.controller;

import com.ex.backend.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/api/products/{id}/reviews")
    public ResponseEntity<ApiResponse<Object>> getProductReviews(
            @PathVariable Long id,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int perPage,
            @RequestParam(defaultValue = "created_at:desc") String sort,
            @RequestParam(required = false) Integer rating) {

        Object reviews = reviewService.getProductReviews(id, page, perPage, sort, rating);
        return ResponseEntity.ok(ApiResponse.success(reviews, "상품 리뷰를 성공적으로 조회했습니다."));
    }

    @PostMapping("/api/products/{id}/reviews")
    public ResponseEntity<ApiResponse<Object>> createReview(
            @PathVariable Long id,
            @RequestBody Object reviewRequestDto) {
        Object review = reviewService.createReview(id, reviewRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(review, "리뷰가 성공적으로 등록되었습니다."));
    }

    @PutMapping("/api/reviews/{id}")
    public ResponseEntity<ApiResponse<Object>> updateReview(
            @PathVariable Long id,
            @RequestBody Object reviewRequestDto) {
        Object review = reviewService.updateReview(id, reviewRequestDto);
        return ResponseEntity.ok(ApiResponse.success(review, "리뷰가 성공적으로 수정되었습니다."));
    }

    @DeleteMapping("/api/reviews/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok(ApiResponse.success(null, "리뷰가 성공적으로 삭제되었습니다."));
    }
}
