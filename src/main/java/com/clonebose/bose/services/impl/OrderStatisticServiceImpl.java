package com.clonebose.bose.services.impl;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.clonebose.bose.mappers.OrderStatisticMapper;
import com.clonebose.bose.models.OrderStatistic;
import com.clonebose.bose.models.OrderStatsDto;
import com.clonebose.bose.services.OrderStatisticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderStatisticServiceImpl implements OrderStatisticService {
    
    private final OrderStatisticMapper orderStatisticMapper;

    @Override
    public OrderStatsDto getOrderStats() {
        // 전체 방문자 통계 데이터 조회
        List<OrderStatistic> allData = orderStatisticMapper.getAllOrderStatistics();
        
        // 현재 시간 기준으로 필터링 날짜 계산
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneYearAgo = now.minusMonths(11);
        LocalDateTime fiveYearsAgo = now.minusYears(4);
        LocalDateTime sevenDaysAgo = now.minusDays(7);
        
        // 통계용 Map 초기화
        Map<String, Long> monthlyStats = new LinkedHashMap<>();
        Map<String, Long> yearlyStats = new LinkedHashMap<>();
        Map<String, Long> dailyStats = new LinkedHashMap<>();
        
        // 전체 데이터를 순회하면서 통계 계산
        for (OrderStatistic statistic : allData) {          
            LocalDateTime regDate = statistic.getRegDate();
            
            if (regDate != null) {
                // 연별 통계 (최근 5년 데이터만)
                if (regDate.isAfter(fiveYearsAgo)) {
                    String yearKey = String.valueOf(regDate.getYear());
                    yearlyStats.put(yearKey, yearlyStats.getOrDefault(yearKey, 0L) + statistic.getDailyOrderSum());
                }
                
                // 월별 통계 (최근 1년 데이터만)
                if (regDate.isAfter(oneYearAgo)) {
                    String monthKey = String.format("%d-%02d", regDate.getYear(), regDate.getMonthValue());
                    monthlyStats.put(monthKey, monthlyStats.getOrDefault(monthKey, 0L) + statistic.getDailyOrderSum());
                }
                
                // 날짜별 통계 (최근 7일 데이터만)
                if (regDate.isAfter(sevenDaysAgo)) {
                    String dayKey = String.format("%02d-%02d", regDate.getMonthValue(), regDate.getDayOfMonth());
                    dailyStats.put(dayKey, dailyStats.getOrDefault(dayKey, 0L) + statistic.getDailyOrderSum());
                }
            }        
        }
        
        // DTO 빌더 패턴으로 생성
        return OrderStatsDto.builder()
            .monthlyStats(monthlyStats)
            .yearlyStats(yearlyStats)
            .dailyStats(dailyStats)
            .build();
    }

}
