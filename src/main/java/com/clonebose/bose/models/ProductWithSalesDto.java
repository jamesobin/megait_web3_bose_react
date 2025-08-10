package com.clonebose.bose.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 상품명이 포함된 인기 상품 통계 데이터 전송 객체
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductWithSalesDto {
    private Long productId;
    private String productName;  // 실제 상품명
    private Long totalSoldCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
