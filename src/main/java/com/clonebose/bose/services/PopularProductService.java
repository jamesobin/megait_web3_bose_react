package com.clonebose.bose.services;

import com.clonebose.bose.models.PopularProductDto;

public interface PopularProductService {
    
    /**
     * 인기 상품 통계 조회
     * @return PopularProductDto 월별, 연별, 일별 인기 상품 통계 데이터
     */
    PopularProductDto getPopularProductStats();
}
