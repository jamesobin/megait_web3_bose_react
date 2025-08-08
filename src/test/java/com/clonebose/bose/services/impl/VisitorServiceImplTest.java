package com.clonebose.bose.services.impl;

import com.clonebose.bose.mappers.VisitorMapper;
import com.clonebose.bose.models.VisitorStatistic;
import com.clonebose.bose.models.VisitorStatsDto;
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
@DisplayName("VisitorServiceImpl 테스트")
public class VisitorServiceImplTest {

    @Mock
    private VisitorMapper visitorMapper;

    @InjectMocks
    private VisitorServiceImpl visitorService;

    private List<VisitorStatistic> mockData;

    @BeforeEach
    void setUp() {
        // 테스트용 목 데이터 생성
        mockData = new ArrayList<>();
        
        LocalDateTime now = LocalDateTime.now();
        
        // 최근 7일 데이터
        for (int i = 1; i <= 7; i++) {
            mockData.add(VisitorStatistic.builder()
                    .visitorStatisticId(i)
                    .dailyVisitorSum(i * 10)
                    .regDate(now.minusDays(i))
                    .editDate(now.minusDays(i))
                    .build());
        }
        
        // 최근 1년 월별 데이터 (일부)
        for (int i = 1; i <= 6; i++) {
            mockData.add(VisitorStatistic.builder()
                    .visitorStatisticId(i + 10)
                    .dailyVisitorSum(i * 20)
                    .regDate(now.minusMonths(i))
                    .editDate(now.minusMonths(i))
                    .build());
        }
        
        // 최근 5년 연별 데이터 (일부)
        for (int i = 1; i <= 3; i++) {
            mockData.add(VisitorStatistic.builder()
                    .visitorStatisticId(i + 20)
                    .dailyVisitorSum(i * 50)
                    .regDate(now.minusYears(i))
                    .editDate(now.minusYears(i))
                    .build());
        }
        
        // 범위를 벗어나는 오래된 데이터
        mockData.add(VisitorStatistic.builder()
                .visitorStatisticId(100)
                .dailyVisitorSum(1000)
                .regDate(now.minusYears(10)) // 10년 전 데이터
                .editDate(now.minusYears(10))
                .build());
    }

    @Test
    @DisplayName("방문자 통계 조회 기본 테스트")
    void getVisitorStatsBasicTest() {
        // Given
        when(visitorMapper.getAllVisitorStatistics()).thenReturn(mockData);

        // When
        VisitorStatsDto result = visitorService.getVisitorStats();

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getDailyStats()).isNotNull();
        assertThat(result.getMonthlyStats()).isNotNull();
        assertThat(result.getYearlyStats()).isNotNull();
    }    @Test
    @DisplayName("일별 통계 데이터 검증 테스트")
    void dailyStatsTest() {
        // Given
        when(visitorMapper.getAllVisitorStatistics()).thenReturn(mockData);

        // When
        VisitorStatsDto result = visitorService.getVisitorStats();
        Map<String, Integer> dailyStats = result.getDailyStats();

        // Then
        assertThat(dailyStats).isNotEmpty();
        
        // isAfter()를 사용하므로 정확히 7일 전은 제외됨
        // 1~6일 전 데이터만 포함되어야 함
        LocalDateTime now = LocalDateTime.now();
        for (int i = 1; i <= 6; i++) {
            LocalDateTime date = now.minusDays(i);
            String expectedKey = String.format("%02d-%02d", date.getMonthValue(), date.getDayOfMonth());
            if (dailyStats.containsKey(expectedKey)) {
                assertThat(dailyStats.get(expectedKey)).isEqualTo(i * 10);
            }
        }
        
        // 7일 전 데이터는 제외되어야 함 (isAfter 때문에)
        LocalDateTime sevenDaysAgo = now.minusDays(7);
        String sevenDaysKey = String.format("%02d-%02d", sevenDaysAgo.getMonthValue(), sevenDaysAgo.getDayOfMonth());
        assertThat(dailyStats).doesNotContainKey(sevenDaysKey);
    }

    @Test
    @DisplayName("월별 통계 데이터 검증 테스트")
    void monthlyStatsTest() {
        // Given
        when(visitorMapper.getAllVisitorStatistics()).thenReturn(mockData);

        // When
        VisitorStatsDto result = visitorService.getVisitorStats();
        Map<String, Integer> monthlyStats = result.getMonthlyStats();

        // Then
        assertThat(monthlyStats).isNotEmpty();
        
        // 최근 1년(11개월) 데이터만 포함되어야 함
        LocalDateTime now = LocalDateTime.now();
        for (int i = 1; i <= 6; i++) {
            LocalDateTime date = now.minusMonths(i);
            String expectedKey = String.format("%d-%02d", date.getYear(), date.getMonthValue());
            assertThat(monthlyStats).containsKey(expectedKey);
            assertThat(monthlyStats.get(expectedKey)).isEqualTo(i * 20);
        }
    }

    @Test
    @DisplayName("연별 통계 데이터 검증 테스트")
    void yearlyStatsTest() {
        // Given
        when(visitorMapper.getAllVisitorStatistics()).thenReturn(mockData);

        // When
        VisitorStatsDto result = visitorService.getVisitorStats();
        Map<String, Integer> yearlyStats = result.getYearlyStats();

        // Then
        assertThat(yearlyStats).isNotEmpty();
        
        // 최근 5년(4년전) 데이터만 포함되어야 함
        LocalDateTime now = LocalDateTime.now();
        for (int i = 1; i <= 3; i++) {
            LocalDateTime date = now.minusYears(i);
            String expectedKey = String.valueOf(date.getYear());
            assertThat(yearlyStats).containsKey(expectedKey);
            assertThat(yearlyStats.get(expectedKey)).isEqualTo(i * 50);
        }
        
        // 10년 전 데이터는 포함되지 않아야 함
        String oldYearKey = String.valueOf(now.minusYears(10).getYear());
        assertThat(yearlyStats).doesNotContainKey(oldYearKey);
    }

    @Test
    @DisplayName("빈 데이터 처리 테스트")
    void emptyDataTest() {
        // Given
        when(visitorMapper.getAllVisitorStatistics()).thenReturn(new ArrayList<>());

        // When
        VisitorStatsDto result = visitorService.getVisitorStats();

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getDailyStats()).isEmpty();
        assertThat(result.getMonthlyStats()).isEmpty();
        assertThat(result.getYearlyStats()).isEmpty();
    }

    @Test
    @DisplayName("null regDate 처리 테스트")
    void nullRegDateTest() {
        // Given
        List<VisitorStatistic> dataWithNull = new ArrayList<>();
        dataWithNull.add(VisitorStatistic.builder()
                .visitorStatisticId(1)
                .dailyVisitorSum(100)
                .regDate(null) // null 날짜
                .editDate(LocalDateTime.now())
                .build());
        
        dataWithNull.add(VisitorStatistic.builder()
                .visitorStatisticId(2)
                .dailyVisitorSum(200)
                .regDate(LocalDateTime.now().minusDays(1))
                .editDate(LocalDateTime.now())
                .build());
        
        when(visitorMapper.getAllVisitorStatistics()).thenReturn(dataWithNull);

        // When
        VisitorStatsDto result = visitorService.getVisitorStats();

        // Then
        assertThat(result).isNotNull();
        
        // null 날짜는 무시되고, 유효한 데이터만 처리되어야 함
        assertThat(result.getDailyStats()).hasSize(1);
        
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        String expectedKey = String.format("%02d-%02d", yesterday.getMonthValue(), yesterday.getDayOfMonth());
        assertThat(result.getDailyStats()).containsKey(expectedKey);
        assertThat(result.getDailyStats().get(expectedKey)).isEqualTo(200);
    }

    @Test
    @DisplayName("같은 날짜 여러 데이터 합계 테스트")
    void sameDateMultipleDataTest() {
        // Given
        LocalDateTime today = LocalDateTime.now();
        List<VisitorStatistic> sameDate = new ArrayList<>();
        
        // 같은 날짜에 여러 통계 데이터
        sameDate.add(VisitorStatistic.builder()
                .visitorStatisticId(1)
                .dailyVisitorSum(50)
                .regDate(today.minusDays(1))
                .editDate(today)
                .build());
        
        sameDate.add(VisitorStatistic.builder()
                .visitorStatisticId(2)
                .dailyVisitorSum(30)
                .regDate(today.minusDays(1))
                .editDate(today)
                .build());
        
        when(visitorMapper.getAllVisitorStatistics()).thenReturn(sameDate);

        // When
        VisitorStatsDto result = visitorService.getVisitorStats();

        // Then
        assertThat(result.getDailyStats()).hasSize(1);
        
        LocalDateTime yesterday = today.minusDays(1);
        String expectedKey = String.format("%02d-%02d", yesterday.getMonthValue(), yesterday.getDayOfMonth());
        assertThat(result.getDailyStats().get(expectedKey)).isEqualTo(80); // 50 + 30
    }    @Test
    @DisplayName("경계값 테스트 - 정확히 7일, 11개월, 4년 전 데이터")
    void boundaryValueTest() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        List<VisitorStatistic> boundaryData = new ArrayList<>();
        
        // 6일 전 (포함되어야 함 - isAfter 조건에 맞음)
        boundaryData.add(VisitorStatistic.builder()
                .visitorStatisticId(1)
                .dailyVisitorSum(100)
                .regDate(now.minusDays(6))
                .editDate(now)
                .build());
        
        // 정확히 7일 전 (포함되지 않아야 함 - isAfter이므로)
        boundaryData.add(VisitorStatistic.builder()
                .visitorStatisticId(2)
                .dailyVisitorSum(200)
                .regDate(now.minusDays(7))
                .editDate(now)
                .build());
        
        // 8일 전 (포함되지 않아야 함)
        boundaryData.add(VisitorStatistic.builder()
                .visitorStatisticId(3)
                .dailyVisitorSum(300)
                .regDate(now.minusDays(8))
                .editDate(now)
                .build());
        
        when(visitorMapper.getAllVisitorStatistics()).thenReturn(boundaryData);

        // When
        VisitorStatsDto result = visitorService.getVisitorStats();

        // Then
        // 6일 전 데이터만 포함되어야 함
        assertThat(result.getDailyStats()).hasSize(1);
        
        LocalDateTime sixDaysAgo = now.minusDays(6);
        String expectedKey = String.format("%02d-%02d", sixDaysAgo.getMonthValue(), sixDaysAgo.getDayOfMonth());
        assertThat(result.getDailyStats()).containsKey(expectedKey);
        assertThat(result.getDailyStats().get(expectedKey)).isEqualTo(100);
    }
}
