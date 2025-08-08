package com.clonebose.bose.services.impl;

import com.clonebose.bose.mappers.VisitorMapper;
import com.clonebose.bose.models.VisitorStatistic;
import com.clonebose.bose.models.VisitorStatsDto;
import com.clonebose.bose.services.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class VisitorServiceImpl implements VisitorService {

    @Autowired
    private VisitorMapper visitorMapper;    
    
    @Override
    public VisitorStatsDto getVisitorStats() {
        // 전체 방문자 통계 데이터 조회
        List<VisitorStatistic> allData = visitorMapper.getAllVisitorStatistics();
        int totalVisitors = visitorMapper.getTotalVisitorCount();
        
        // 현재 시간 기준으로 필터링 날짜 계산
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneYearAgo = now.minusYears(1);
        LocalDateTime fiveYearsAgo = now.minusYears(5);
        LocalDateTime sevenDaysAgo = now.minusDays(7);
        
        // 통계용 Map 초기화
        Map<String, Integer> monthlyStats = new LinkedHashMap<>();
        Map<String, Integer> yearlyStats = new LinkedHashMap<>();
        Map<String, Integer> dailyStats = new LinkedHashMap<>();
        
        // 전체 데이터를 순회하면서 통계 계산
        for (VisitorStatistic statistic : allData) {          
            LocalDateTime regDate = statistic.getRegDate();
            
            if (regDate != null) {
                // 연별 통계 (최근 5년 데이터만)
                if (regDate.isAfter(fiveYearsAgo)) {
                    String yearKey = String.valueOf(regDate.getYear());
                    yearlyStats.put(yearKey, yearlyStats.getOrDefault(yearKey, 0) + statistic.getDailyVisitorSum());
                }
                
                // 월별 통계 (최근 1년 데이터만)
                if (regDate.isAfter(oneYearAgo)) {
                    String monthKey = String.format("%d-%02d", regDate.getYear(), regDate.getMonthValue());
                    monthlyStats.put(monthKey, monthlyStats.getOrDefault(monthKey, 0) + statistic.getDailyVisitorSum());
                }
                
                // 날짜별 통계 (최근 7일 데이터만)
                if (regDate.isAfter(sevenDaysAgo)) {
                    String dayKey = String.format("%02d-%02d", regDate.getMonthValue(), regDate.getDayOfMonth());
                    dailyStats.put(dayKey, dailyStats.getOrDefault(dayKey, 0) + statistic.getDailyVisitorSum());
                }
            }        }
        
        // 이미 데이터베이스에서 날짜 순으로 정렬되어 온 데이터이므로
        // LinkedHashMap의 순서가 자동으로 과거 -> 현재 순서가 됩니다
        
        // DTO 빌더 패턴으로 생성
        return VisitorStatsDto.builder()
            .monthlyStats(monthlyStats)
            .yearlyStats(yearlyStats)
            .dailyStats(dailyStats)
            .totalVisitors(totalVisitors)
            .build();
    }
}
