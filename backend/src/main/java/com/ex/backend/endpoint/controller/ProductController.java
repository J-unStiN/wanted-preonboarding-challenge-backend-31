package com.ex.backend.endpoint.controller;

import com.ex.backend.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {


    @PostMapping
    public ResponseEntity<?> createProduct(Object productDto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(productDto, "Product created successfully"));
    }


    @GetMapping
    public ResponseEntity<ApiResponse<Object>> getProducts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int perPage,
            @RequestParam(defaultValue = "created_at:desc") String sort,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Long seller,
            @RequestParam(required = false) Long brand,
            @RequestParam(required = false) Boolean inStock,
            @RequestParam(required = false) String search) {

        Object result = productService.getProducts(
                page, perPage, sort, status, minPrice, maxPrice,
                category, seller, brand, inStock, search);

        return ResponseEntity.ok(ApiResponse.success(result, "상품 목록을 성공적으로 조회했습니다."));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> getProduct(@PathVariable Long id) {
        Object product = productService.getProductById(id);
        return ResponseEntity.ok(ApiResponse.success(product, "상품 상세 정보를 성공적으로 조회했습니다."));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequestDto requestDto) {
        Object product = productService.updateProduct(id, requestDto);
        return ResponseEntity.ok(ApiResponse.success(product, "상품이 성공적으로 수정되었습니다."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponse.success(null, "상품이 성공적으로 삭제되었습니다."));
    }

    @PostMapping("/{id}/options")
    public ResponseEntity<ApiResponse<Object>> addProductOption(
            @PathVariable Long id,
            @RequestBody Object optionRequestDto) {
        Object option = productService.addProductOption(id, optionRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(option, "상품 옵션이 성공적으로 추가되었습니다."));
    }

    @PutMapping("/{id}/options/{optionId}")
    public ResponseEntity<ApiResponse<Object>> updateProductOption(
            @PathVariable Long id,
            @PathVariable Long optionId,
            @RequestBody Object optionRequestDto) {
        Object option = productService.updateProductOption(id, optionId, optionRequestDto);
        return ResponseEntity.ok(ApiResponse.success(option, "상품 옵션이 성공적으로 수정되었습니다."));
    }

    @DeleteMapping("/{id}/options/{optionId}")
    public ResponseEntity<ApiResponse<Object>> deleteProductOption(
            @PathVariable Long id,
            @PathVariable Long optionId) {
        productService.deleteProductOption(id, optionId);
        return ResponseEntity.ok(ApiResponse.success(null, "상품 옵션이 성공적으로 삭제되었습니다."));
    }

    @PostMapping("/{id}/images")
    public ResponseEntity<ApiResponse<Object>> addProductImage(
            @PathVariable Long id,
            @RequestBody Object imageRequestDto) {
        Object image = productService.addProductImage(id, imageRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(image, "상품 이미지가 성공적으로 추가되었습니다."));
    }



}
