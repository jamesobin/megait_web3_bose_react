package com.clonebose.bose.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PopularProductDto {
    private Map<String, Long> weeklyStats;     // 일주일 내 통계
    private Map<String, Long> monthlyStats;    // 한달 내 통계  
    private Map<String, Long> sixMonthStats;   // 6개월 내 통계
    private Map<String, Long> yearlyStats;     // 1년 내 통계
    private Map<String, Long> threeYearStats;  // 3년 내 통계
    private Map<String, Long> allTimeStats;    // 전체 기간 통계
}
