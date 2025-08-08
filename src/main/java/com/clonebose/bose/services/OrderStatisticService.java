package com.clonebose.bose.services;

import com.clonebose.bose.models.OrderStatsDto;

public interface OrderStatisticService {
    
    /**
     * 매출 통계를 조회
     * 
     * @return - OrderStatsDto 매출 통계 데이터
     */
    public OrderStatsDto getOrderStats();
    
}
