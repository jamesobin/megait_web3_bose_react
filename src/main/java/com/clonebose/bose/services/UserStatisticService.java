package com.clonebose.bose.services;

import com.clonebose.bose.models.UserStatsDto;

public interface UserStatisticService {
    
    /**
     * 사용자 통계를 조회합니다.
     * @return UserStatsDto 사용자 통계 데이터
     */
    UserStatsDto getUserStats();
}