package com.clonebose.bose.services.impl;

import com.clonebose.bose.mappers.VisitorMapper;
import com.clonebose.bose.models.VisititorCount;
import com.clonebose.bose.models.VisitorStatsDto;
import com.clonebose.bose.services.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VisitorServiceImpl implements VisitorService {

    @Autowired
    private VisitorMapper visitorMapper;    @Override
    public VisitorStatsDto getVisitorStats() {
        // 전체 방문자 데이터 조회
        List<VisititorCount> allData = visitorMapper.getAllVisitorData();
        int totalVisitors = visitorMapper.getTotalVisitorCount();
        
        // 현재 시간 기준으로 필터링 날짜 계산
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneYearAgo = now.minusYears(1);
        LocalDateTime oneWeekAgo = now.minusWeeks(1);
        
        // 통계용 Map 초기화
        Map<String, Integer> monthlyStats = new HashMap<>();
        Map<String, Integer> yearlyStats = new HashMap<>();
        Map<String, Integer> dayOfWeekStats = new HashMap<>();
        
        // 전체 데이터를 순회하면서 통계 계산
        for (VisititorCount visitor : allData) {          
            
            // 연별 통계 (전체 데이터)
            String yearKey = String.valueOf(visitor.getVisitYear());
            yearlyStats.put(yearKey, yearlyStats.getOrDefault(yearKey, 0) + 1);
            
            // 월별 통계 (최근 1년 데이터만)
            if (visitor.getVisitedAt() != null && visitor.getVisitedAt().isAfter(oneYearAgo)) {
                String monthKey = visitor.getVisitMonth() + "월";
                monthlyStats.put(monthKey, monthlyStats.getOrDefault(monthKey, 0) + 1);
            }
            
            // 요일별 통계 (최근 1주일 데이터만)
            if (visitor.getVisitedAt() != null && visitor.getVisitedAt().isAfter(oneWeekAgo)) {
                String dayKey = getDayOfWeekName(visitor.getVisitDayOfWeek());
                dayOfWeekStats.put(dayKey, dayOfWeekStats.getOrDefault(dayKey, 0) + 1);
            }
        }
        
        // DTO 빌더 패턴으로 생성
        return VisitorStatsDto.builder()
            .monthlyStats(monthlyStats)
            .yearlyStats(yearlyStats)
            .dayOfWeekStats(dayOfWeekStats)
            .totalVisitors(totalVisitors)
            .build();
    }
    
    /**
     * 요일 숫자를 한글 요일명으로 변환
     * @param dayOfWeek 1=월요일, 2=화요일, ..., 7=일요일
     * @return 한글 요일명
     */
    private String getDayOfWeekName(int dayOfWeek) {
        switch (dayOfWeek) {
            case 1: return "월요일";
            case 2: return "화요일";
            case 3: return "수요일";
            case 4: return "목요일";
            case 5: return "금요일";
            case 6: return "토요일";
            case 7: return "일요일";
            default: return "알 수 없음";
        }
    }
}
