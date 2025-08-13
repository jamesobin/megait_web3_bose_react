package com.clonebose.bose.services.impl;

import com.clonebose.bose.mappers.PopularProductMapper;
import com.clonebose.bose.models.PopularProductDto;
import com.clonebose.bose.models.ProductWithSalesDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("PopularProductServiceImpl 테스트")
public class PopularProductServiceImplTest {

    @Mock
    private PopularProductMapper popularProductMapper;

    @InjectMocks
    private PopularProductServiceImpl popularProductService;

    private List<ProductWithSalesDto> mockData;

    @BeforeEach
    void setUp() {
        // 테스트용 목 데이터 생성
        mockData = new ArrayList<>();
        
        LocalDateTime now = LocalDateTime.now();
        
        // 최근 1주일 데이터
        mockData.add(createProductWithSales(1L, "[BOSE] 보스 QC 헤드폰", 25L, now.minusDays(2)));
        mockData.add(createProductWithSales(2L, "[BOSE] 보스 울트라 오픈 이어버드", 20L, now.minusDays(3)));
        mockData.add(createProductWithSales(3L, "[BOSE] 보스 QC 울트라 헤드폰", 18L, now.minusDays(1)));
        
        // 최근 1개월 데이터
        mockData.add(createProductWithSales(4L, "[BOSE] 보스 사운드링크 플렉스 스피커", 15L, now.minusDays(10)));
        mockData.add(createProductWithSales(5L, "[BOSE] 보스 QC 이어버드", 12L, now.minusDays(15)));
        mockData.add(createProductWithSales(6L, "[BOSE] 보스 스마트 사운드바", 10L, now.minusDays(20)));
        
        // 최근 6개월 데이터
        mockData.add(createProductWithSales(7L, "[BOSE] 보스 베이스 모듈 700", 8L, now.minusMonths(2)));
        mockData.add(createProductWithSales(8L, "[BOSE] 보스 서라운드 스피커 700", 7L, now.minusMonths(3)));
        
        // 최근 1년 데이터
        mockData.add(createProductWithSales(9L, "[BOSE] 보스 사운드링크 맥스 포터블 스피커", 6L, now.minusMonths(8)));
        
        // 범위를 벗어나는 오래된 데이터
        mockData.add(createProductWithSales(100L, "[BOSE] 보스 오래된 상품", 1000L, now.minusYears(10)));
    }
    
    private ProductWithSalesDto createProductWithSales(Long productId, String productName, Long totalSold, LocalDateTime regDate) {
        return ProductWithSalesDto.builder()
                .productId(productId)
                .productName(productName)
                .totalSoldCount(totalSold)
                .regDate(regDate)
                .editDate(regDate)
                .build();
    }

    @Test
    @DisplayName("인기 상품 통계 조회 기본 테스트")
    void getPopularProductStatsBasicTest() {
        // Given
        when(popularProductMapper.getAllPopularStatisticsWithProductName()).thenReturn(mockData);

        // When
        PopularProductDto result = popularProductService.getPopularProductStats();

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getWeeklyStats()).isNotNull();
        assertThat(result.getMonthlyStats()).isNotNull();
        assertThat(result.getSixMonthStats()).isNotNull();
        assertThat(result.getYearlyStats()).isNotNull();
        assertThat(result.getThreeYearStats()).isNotNull();
        assertThat(result.getAllTimeStats()).isNotNull();
    }

    @Test
    @DisplayName("일주일 통계 데이터 검증 테스트")
    void weeklyStatsTest() {
        // Given
        when(popularProductMapper.getAllPopularStatisticsWithProductName()).thenReturn(mockData);

        // When
        PopularProductDto result = popularProductService.getPopularProductStats();
        Map<String, Long> weeklyStats = result.getWeeklyStats();

        // Then
        assertThat(weeklyStats).isNotEmpty();
        
        // 최근 7일 데이터만 포함되어야 함
        assertThat(weeklyStats).containsKey("[BOSE] 보스 QC 헤드폰");
        assertThat(weeklyStats).containsKey("[BOSE] 보스 울트라 오픈 이어버드");
        assertThat(weeklyStats).containsKey("[BOSE] 보스 QC 울트라 헤드폰");
        
        // 7일 이전 데이터는 포함되지 않아야 함
        assertThat(weeklyStats).doesNotContainKey("[BOSE] 보스 사운드링크 플렉스 스피커");
        
        // 판매량 검증
        assertThat(weeklyStats.get("[BOSE] 보스 QC 헤드폰")).isEqualTo(25L);
        assertThat(weeklyStats.get("[BOSE] 보스 울트라 오픈 이어버드")).isEqualTo(20L);
        assertThat(weeklyStats.get("[BOSE] 보스 QC 울트라 헤드폰")).isEqualTo(18L);
    }

    @Test
    @DisplayName("한달 통계 데이터 검증 테스트")
    void monthlyStatsTest() {
        // Given
        when(popularProductMapper.getAllPopularStatisticsWithProductName()).thenReturn(mockData);

        // When
        PopularProductDto result = popularProductService.getPopularProductStats();
        Map<String, Long> monthlyStats = result.getMonthlyStats();

        // Then
        assertThat(monthlyStats).isNotEmpty();
        
        // 최근 1개월 데이터 포함 확인
        assertThat(monthlyStats).containsKey("[BOSE] 보스 QC 헤드폰");
        assertThat(monthlyStats).containsKey("[BOSE] 보스 사운드링크 플렉스 스피커");
        assertThat(monthlyStats).containsKey("[BOSE] 보스 QC 이어버드");
        assertThat(monthlyStats).containsKey("[BOSE] 보스 스마트 사운드바");
        
        // 같은 상품이 있으면 합계가 되어야 함
        assertThat(monthlyStats.get("[BOSE] 보스 QC 헤드폰")).isEqualTo(25L);
    }

    @Test
    @DisplayName("6개월 통계 데이터 검증 테스트")
    void sixMonthStatsTest() {
        // Given
        when(popularProductMapper.getAllPopularStatisticsWithProductName()).thenReturn(mockData);

        // When
        PopularProductDto result = popularProductService.getPopularProductStats();
        Map<String, Long> sixMonthStats = result.getSixMonthStats();

        // Then
        assertThat(sixMonthStats).isNotEmpty();
        
        // 최근 6개월 데이터 포함 확인
        assertThat(sixMonthStats).containsKey("[BOSE] 보스 베이스 모듈 700");
        assertThat(sixMonthStats).containsKey("[BOSE] 보스 서라운드 스피커 700");
        
        // 6개월 이전 데이터는 포함되지 않아야 함
        assertThat(sixMonthStats).doesNotContainKey("[BOSE] 보스 사운드링크 맥스 포터블 스피커");
    }

    @Test
    @DisplayName("1년 통계 데이터 검증 테스트")
    void yearlyStatsTest() {
        // Given
        when(popularProductMapper.getAllPopularStatisticsWithProductName()).thenReturn(mockData);

        // When
        PopularProductDto result = popularProductService.getPopularProductStats();
        Map<String, Long> yearlyStats = result.getYearlyStats();

        // Then
        assertThat(yearlyStats).isNotEmpty();
        
        // 최근 1년 데이터 포함 확인
        assertThat(yearlyStats).containsKey("[BOSE] 보스 사운드링크 맥스 포터블 스피커");
        
        // 1년 이전 데이터는 포함되지 않아야 함
        assertThat(yearlyStats).doesNotContainKey("[BOSE] 보스 오래된 상품");
    }

    @Test
    @DisplayName("TOP 8 제한 테스트")
    void top8LimitTest() {
        // Given - 10개 이상의 데이터 생성
        List<ProductWithSalesDto> manyProducts = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        
        for (int i = 1; i <= 12; i++) {
            manyProducts.add(createProductWithSales((long) i, "[BOSE] 상품 " + i, (long) (100 - i), now.minusDays(1)));
        }
        
        when(popularProductMapper.getAllPopularStatisticsWithProductName()).thenReturn(manyProducts);

        // When
        PopularProductDto result = popularProductService.getPopularProductStats();

        // Then
        assertThat(result.getWeeklyStats()).hasSize(8); // TOP 8만 포함
        assertThat(result.getMonthlyStats()).hasSize(8);
        assertThat(result.getSixMonthStats()).hasSize(8);
        assertThat(result.getYearlyStats()).hasSize(8);
        assertThat(result.getThreeYearStats()).hasSize(8);
        assertThat(result.getAllTimeStats()).hasSize(8);
    }

    @Test
    @DisplayName("빈 데이터 처리 테스트")
    void emptyDataTest() {
        // Given
        when(popularProductMapper.getAllPopularStatisticsWithProductName()).thenReturn(new ArrayList<>());

        // When
        PopularProductDto result = popularProductService.getPopularProductStats();

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getWeeklyStats()).isEmpty();
        assertThat(result.getMonthlyStats()).isEmpty();
        assertThat(result.getSixMonthStats()).isEmpty();
        assertThat(result.getYearlyStats()).isEmpty();
        assertThat(result.getThreeYearStats()).isEmpty();
        assertThat(result.getAllTimeStats()).isEmpty();
    }

    @Test
    @DisplayName("null regDate 처리 테스트")
    void nullRegDateTest() {
        // Given
        List<ProductWithSalesDto> dataWithNull = new ArrayList<>();
        dataWithNull.add(createProductWithSales(1L, "[BOSE] 보스 상품1", 100L, null)); // null 날짜
        dataWithNull.add(createProductWithSales(2L, "[BOSE] 보스 상품2", 200L, LocalDateTime.now().minusDays(1)));
        
        when(popularProductMapper.getAllPopularStatisticsWithProductName()).thenReturn(dataWithNull);

        // When
        PopularProductDto result = popularProductService.getPopularProductStats();

        // Then
        assertThat(result).isNotNull();
        
        // null 날짜는 무시되고, 유효한 데이터만 처리되어야 함
        assertThat(result.getWeeklyStats()).hasSize(1);
        assertThat(result.getWeeklyStats()).containsKey("[BOSE] 보스 상품2");
        assertThat(result.getWeeklyStats().get("[BOSE] 보스 상품2")).isEqualTo(200L);
    }

    @Test
    @DisplayName("같은 상품 여러 통계 합계 테스트")
    void sameProductMultipleStatsTest() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        List<ProductWithSalesDto> sameProductData = new ArrayList<>();
        
        // 같은 상품에 여러 판매 통계
        sameProductData.add(createProductWithSales(1L, "[BOSE] 보스 QC 헤드폰", 50L, now.minusDays(1)));
        sameProductData.add(createProductWithSales(1L, "[BOSE] 보스 QC 헤드폰", 30L, now.minusDays(2)));
        sameProductData.add(createProductWithSales(1L, "[BOSE] 보스 QC 헤드폰", 20L, now.minusDays(3)));
        
        when(popularProductMapper.getAllPopularStatisticsWithProductName()).thenReturn(sameProductData);

        // When
        PopularProductDto result = popularProductService.getPopularProductStats();

        // Then
        assertThat(result.getWeeklyStats()).hasSize(1);
        assertThat(result.getWeeklyStats().get("[BOSE] 보스 QC 헤드폰")).isEqualTo(100L); // 50 + 30 + 20
    }
}
