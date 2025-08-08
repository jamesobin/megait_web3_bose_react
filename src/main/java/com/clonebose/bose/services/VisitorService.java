package com.clonebose.bose.services;

import com.clonebose.bose.models.VisitorStatsDto;

public interface VisitorService {
    
    /**
     * 방문자 통계를 조회합니다.
     * @return VisitorStatsDto 방문자 통계 데이터
     */
    VisitorStatsDto getVisitorStats();
}

