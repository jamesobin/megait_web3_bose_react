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
                System.out.println("DB에서 데이터를 찾을 수 없습니다. 더미 데이터를 사용합니다.");
                return getFallbackData();
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
                LocalDateTime createdAt = statistic.getCreatedAt();
                
                if (createdAt != null) {
                    String productName = statistic.getProductName(); // 실제 상품명 사용
                    Long soldCount = statistic.getTotalSoldCount();
                    
                    // 전체 기간 통계
                    allTimeStats.put(productName, allTimeStats.getOrDefault(productName, 0L) + soldCount);
                    
                    // 3년 내 통계
                    if (createdAt.isAfter(threeYearsAgo)) {
                        threeYearStats.put(productName, threeYearStats.getOrDefault(productName, 0L) + soldCount);
                    }
                    
                    // 1년 내 통계
                    if (createdAt.isAfter(oneYearAgo)) {
                        yearlyStats.put(productName, yearlyStats.getOrDefault(productName, 0L) + soldCount);
                    }
                    
                    // 6개월 내 통계
                    if (createdAt.isAfter(sixMonthsAgo)) {
                        sixMonthStats.put(productName, sixMonthStats.getOrDefault(productName, 0L) + soldCount);
                    }
                    
                    // 한달 내 통계
                    if (createdAt.isAfter(oneMonthAgo)) {
                        monthlyStats.put(productName, monthlyStats.getOrDefault(productName, 0L) + soldCount);
                    }
                    
                    // 일주일 내 통계
                    if (createdAt.isAfter(oneWeekAgo)) {
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
            System.err.println("DB 조회 실패, 더미 데이터 사용: " + e.getMessage());
            return getFallbackData();
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
    
    /**
     * 에러 발생 시 사용할 폴백 더미 데이터 (8개)
     */
    private PopularProductDto getFallbackData() {
        Map<String, Long> weeklyStats = new LinkedHashMap<>();
        Map<String, Long> monthlyStats = new LinkedHashMap<>();
        Map<String, Long> sixMonthStats = new LinkedHashMap<>();
        Map<String, Long> yearlyStats = new LinkedHashMap<>();
        Map<String, Long> threeYearStats = new LinkedHashMap<>();
        Map<String, Long> allTimeStats = new LinkedHashMap<>();
        
        // 8개 더미 데이터
        String[] productNames = {"QC 헤드폰", "울트라 이어버드", "QC 이어버드", "베이스 모듈", 
                               "서라운드 스피커", "플로어 스탠드", "울트라 헤드폰", "QC 울트라"};
        
        // 일주일 내 데이터 (작은 값들)
        Long[] weeklySales = {45L, 38L, 32L, 28L, 24L, 20L, 18L, 15L};
        
        // 한달 내 데이터
        Long[] monthlySales = {180L, 165L, 142L, 128L, 115L, 98L, 85L, 72L};
        
        // 6개월 내 데이터
        Long[] sixMonthSales = {950L, 820L, 740L, 680L, 620L, 580L, 520L, 480L};
        
        // 1년 내 데이터
        Long[] yearlySales = {2100L, 1850L, 1620L, 1480L, 1350L, 1220L, 1100L, 980L};
        
        // 3년 내 데이터
        Long[] threeYearSales = {8500L, 7200L, 6800L, 6200L, 5800L, 5400L, 5000L, 4600L};
        
        // 전체 기간 데이터 (큰 값들)
        Long[] allTimeSales = {15000L, 13500L, 12200L, 11000L, 9800L, 8900L, 8200L, 7500L};
        
        for (int i = 0; i < productNames.length; i++) {
            weeklyStats.put(productNames[i], weeklySales[i]);
            monthlyStats.put(productNames[i], monthlySales[i]);
            sixMonthStats.put(productNames[i], sixMonthSales[i]);
            yearlyStats.put(productNames[i], yearlySales[i]);
            threeYearStats.put(productNames[i], threeYearSales[i]);
            allTimeStats.put(productNames[i], allTimeSales[i]);
        }
        
        return PopularProductDto.builder()
            .weeklyStats(weeklyStats)
            .monthlyStats(monthlyStats)
            .sixMonthStats(sixMonthStats)
            .yearlyStats(yearlyStats)
            .threeYearStats(threeYearStats)
            .allTimeStats(allTimeStats)
            .build();
    }
}
