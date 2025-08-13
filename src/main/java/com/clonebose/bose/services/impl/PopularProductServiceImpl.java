package com.clonebose.bose.services.impl;

import com.clonebose.bose.mappers.PopularProductMapper;
import com.clonebose.bose.models.PopularProductDto;
import com.clonebose.bose.models.ProductWithSalesDto;
import com.clonebose.bose.services.PopularProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PopularProductServiceImpl implements PopularProductService {

    @Autowired
    private PopularProductMapper popularProductMapper;

    @Override
    public PopularProductDto getPopularProductStats() {
        try {
            // 실제 상품명과 함께 통계 데이터 조회
            List<ProductWithSalesDto> allData = popularProductMapper.getAllPopularStatisticsWithProductName();
            
            if (allData.isEmpty()) {
                System.out.println("DB에서 데이터를 찾을 수 없습니다.");
                return null;
            }
            
            // 현재 시간 기준으로 필터링 날짜 계산
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime oneWeekAgo = now.minusDays(7);
            LocalDateTime oneMonthAgo = now.minusMonths(1);
            LocalDateTime sixMonthsAgo = now.minusMonths(6);
            LocalDateTime oneYearAgo = now.minusYears(1);
            LocalDateTime threeYearsAgo = now.minusYears(3);
            
            // 통계용 Map 초기화
            Map<String, Long> weeklyStats = new LinkedHashMap<>();
            Map<String, Long> monthlyStats = new LinkedHashMap<>();
            Map<String, Long> sixMonthStats = new LinkedHashMap<>();
            Map<String, Long> yearlyStats = new LinkedHashMap<>();
            Map<String, Long> threeYearStats = new LinkedHashMap<>();
            Map<String, Long> allTimeStats = new LinkedHashMap<>();
            
            // 전체 데이터를 순회하면서 통계 계산
            for (ProductWithSalesDto statistic : allData) {
                LocalDateTime regDate = statistic.getRegDate();
                
                if (regDate != null) {
                    String productName = statistic.getProductName(); // 실제 상품명 사용
                    Long soldCount = statistic.getTotalSoldCount();
                    
                    // 전체 기간 통계
                    allTimeStats.put(productName, allTimeStats.getOrDefault(productName, 0L) + soldCount);
                    
                    // 3년 내 통계
                    if (regDate.isAfter(threeYearsAgo)) {
                        threeYearStats.put(productName, threeYearStats.getOrDefault(productName, 0L) + soldCount);
                    }
                    
                    // 1년 내 통계
                    if (regDate.isAfter(oneYearAgo)) {
                        yearlyStats.put(productName, yearlyStats.getOrDefault(productName, 0L) + soldCount);
                    }
                    
                    // 6개월 내 통계
                    if (regDate.isAfter(sixMonthsAgo)) {
                        sixMonthStats.put(productName, sixMonthStats.getOrDefault(productName, 0L) + soldCount);
                    }
                    
                    // 한달 내 통계
                    if (regDate.isAfter(oneMonthAgo)) {
                        monthlyStats.put(productName, monthlyStats.getOrDefault(productName, 0L) + soldCount);
                    }
                    
                    // 일주일 내 통계
                    if (regDate.isAfter(oneWeekAgo)) {
                        weeklyStats.put(productName, weeklyStats.getOrDefault(productName, 0L) + soldCount);
                    }
                }
            }
            
            // 상위 8개만 선택하고 정렬
            weeklyStats = getTop8Products(weeklyStats);
            monthlyStats = getTop8Products(monthlyStats);
            sixMonthStats = getTop8Products(sixMonthStats);
            yearlyStats = getTop8Products(yearlyStats);
            threeYearStats = getTop8Products(threeYearStats);
            allTimeStats = getTop8Products(allTimeStats);
            
            // DTO 빌더 패턴으로 생성
            return PopularProductDto.builder()
                .weeklyStats(weeklyStats)
                .monthlyStats(monthlyStats)
                .sixMonthStats(sixMonthStats)
                .yearlyStats(yearlyStats)
                .threeYearStats(threeYearStats)
                .allTimeStats(allTimeStats)
                .build();
                
        } catch (Exception e) {
            // 에러 발생 시 더미 데이터로 폴백
            System.err.println("DB 조회 실패: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * 상위 8개 상품을 선택하여 정렬된 Map으로 반환
     * @param productStats 전체 상품 통계
     * @return 상위 8개 상품 통계 (판매량 내림차순)
     */
    private Map<String, Long> getTop8Products(Map<String, Long> productStats) {
        return productStats.entrySet()
            .stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(8) // 8개로 설정
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (e1, e2) -> e1,
                LinkedHashMap::new
            ));
    }
}
