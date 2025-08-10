package com.clonebose.bose.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRankingDto {
    private int productId;
    private String productName;
    private long totalSoldCount;
    private int ranking;
}
