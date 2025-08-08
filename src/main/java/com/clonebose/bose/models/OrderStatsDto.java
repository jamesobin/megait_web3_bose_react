package com.clonebose.bose.models;

import java.util.Map;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderStatsDto {
    private Map<String, Long> monthlyStats;  // 지난 1년간 월별 통계 (년-월 : 매출)
    private Map<String, Long> yearlyStats;   // 최근 5년간 연별 통계 (년 : 매출)
    private Map<String, Long> dailyStats;    // 최근 7일간 날짜별 통계 (월-일 : 매출)
    private Long totalOrder;                     // 총 매출
}
