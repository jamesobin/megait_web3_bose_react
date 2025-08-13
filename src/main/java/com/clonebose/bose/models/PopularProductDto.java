package com.clonebose.bose.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "📊 6단계 시간별 인기 상품 통계 데이터")
public class PopularProductDto {
    
    @Schema(
        description = "📅 일주일 내 인기 상품 통계 (최근 7일)", 
        example = "{\"QC 헤드폰\": 45, \"울트라 오픈 이어버드\": 38, \"QC 울트라 헤드폰\": 32}"
    )
    private Map<String, Long> weeklyStats;     // 일주일 내 통계
    
    @Schema(
        description = "📅 한달 내 인기 상품 통계 (최근 30일)", 
        example = "{\"QC 헤드폰\": 180, \"울트라 오픈 이어버드\": 165, \"QC 울트라 헤드폰\": 145}"
    )
    private Map<String, Long> monthlyStats;    // 한달 내 통계
    
    @Schema(
        description = "📅 6개월 내 인기 상품 통계", 
        example = "{\"QC 헤드폰\": 850, \"울트라 오픈 이어버드\": 720, \"QC 울트라 헤드폰\": 680}"
    )
    private Map<String, Long> sixMonthStats;   // 6개월 내 통계
    
    @Schema(
        description = "📅 1년 내 인기 상품 통계", 
        example = "{\"QC 헤드폰\": 1680, \"울트라 오픈 이어버드\": 1420, \"QC 울트라 헤드폰\": 1350}"
    )
    private Map<String, Long> yearlyStats;     // 1년 내 통계
    
    @Schema(
        description = "📅 3년 내 인기 상품 통계", 
        example = "{\"QC 헤드폰\": 4200, \"울트라 오픈 이어버드\": 3850, \"QC 울트라 헤드폰\": 3620}"
    )
    private Map<String, Long> threeYearStats;  // 3년 내 통계
    
    @Schema(
        description = "📅 전체 기간 인기 상품 통계", 
        example = "{\"QC 헤드폰\": 8500, \"울트라 오픈 이어버드\": 7800, \"QC 울트라 헤드폰\": 7200}"
    )
    private Map<String, Long> allTimeStats;    // 전체 기간 통계
}
