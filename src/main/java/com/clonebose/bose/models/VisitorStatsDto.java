package com.clonebose.bose.models;

import lombok.Builder;
import lombok.Data;
import java.util.Map;

@Data
@Builder
public class VisitorStatsDto {
    private Map<String, Integer> monthlyStats;  // 지난 1년간 월별 통계 (년-월 : 방문자수)
    private Map<String, Integer> yearlyStats;   // 최근 5년간 연별 통계 (년 : 방문자수)
    private Map<String, Integer> dailyStats;    // 최근 7일간 날짜별 통계 (월-일 : 방문자수)
    private int totalVisitors;                  // 총 방문자수
}
