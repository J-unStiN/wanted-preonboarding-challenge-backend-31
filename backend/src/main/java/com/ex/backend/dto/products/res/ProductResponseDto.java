package com.ex.backend.dto.products.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {

    private Long id;
    private String name;
    private String slug;
    private String shortDescription;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
