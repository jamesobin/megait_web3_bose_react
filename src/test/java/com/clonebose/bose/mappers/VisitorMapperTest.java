package com.clonebose.bose.mappers;

import com.clonebose.bose.models.VisitorStatistic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@DisplayName("VisitorMapper 테스트")
@TestMethodOrder(OrderAnnotation.class)
public class VisitorMapperTest {

    @Autowired
    private VisitorMapper visitorMapper;

    private VisitorStatistic testVisitorStatistic;

    @BeforeEach
    void setUp() {
        testVisitorStatistic = VisitorStatistic.builder()
                .dailyVisitorSum(100)
                .regDate(LocalDateTime.now())
                .editDate(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("방문자 통계 데이터 삽입 테스트")
    void insertVisitorStatisticTest() {
        // When
        int result = visitorMapper.insertVisitorStatistic(testVisitorStatistic);

        // Then
        assertThat(result).isEqualTo(1);
    }    @Test
    @DisplayName("전체 방문자 통계 데이터 조회 테스트")
    void getAllVisitorStatisticsTest() {
        // When
        List<VisitorStatistic> statistics = visitorMapper.getAllVisitorStatistics();

        // Then
        assertThat(statistics).isNotEmpty();
        
        // 최소 1개 이상의 데이터가 있는지 확인
        assertThat(statistics.size()).isGreaterThan(0);
        
        // 날짜 오름차순 정렬 확인 (null 체크 포함)
        for (int i = 0; i < statistics.size() - 1; i++) {
            LocalDateTime current = statistics.get(i).getRegDate();
            LocalDateTime next = statistics.get(i + 1).getRegDate();
            if (current != null && next != null) {
                assertThat(current).isBeforeOrEqualTo(next);
            }
        }
    }

    @Test
    @DisplayName("오늘 날짜 통계 개수 조회 테스트")
    void getTodayStatisticCountTest() {
        // Given - 오늘 날짜로 데이터 삽입
        visitorMapper.insertVisitorStatistic(testVisitorStatistic);

        // When
        int count = visitorMapper.getTodayStatisticCount();

        // Then
        assertThat(count).isGreaterThanOrEqualTo(1);
    }    @Test
    @DisplayName("오늘 날짜 통계 업데이트 테스트")
    void updateTodayStatisticTest() {
        // Given - 오늘 날짜로 데이터 삽입
        visitorMapper.insertVisitorStatistic(testVisitorStatistic);
        int incrementValue = 50;
        
        // 삽입 전 오늘의 카운트 확인
        int beforeCount = visitorMapper.getTodayStatisticCount();

        // When
        int updateResult = visitorMapper.updateTodayStatistic(incrementValue);

        // Then
        assertThat(updateResult).isGreaterThanOrEqualTo(1); // 오늘 날짜의 레코드가 업데이트됨
        
        // 오늘의 카운트가 여전히 존재하는지 확인
        int afterCount = visitorMapper.getTodayStatisticCount();
        assertThat(afterCount).isGreaterThanOrEqualTo(beforeCount);
    }    @Test
    @DisplayName("오늘 날짜에 통계가 없을 때 업데이트 테스트")
    void updateTodayStatisticWhenNoDataTest() {
        // Given - 현재 오늘 날짜에 데이터가 있는지 확인
        int todayCount = visitorMapper.getTodayStatisticCount();
        int incrementValue = 10;

        // When
        int updateResult = visitorMapper.updateTodayStatistic(incrementValue);

        // Then
        if (todayCount > 0) {
            // 오늘 날짜에 이미 데이터가 있다면 업데이트됨
            assertThat(updateResult).isGreaterThanOrEqualTo(1);
        } else {
            // 오늘 날짜에 데이터가 없다면 업데이트되지 않음
            assertThat(updateResult).isEqualTo(0);
        }
    }@Test
    @DisplayName("여러 일자의 통계 데이터 삽입 및 조회 테스트")
    void multipleVisitorStatisticsTest() {
        // Given - 현재 데이터 개수 확인
        List<VisitorStatistic> beforeStats = visitorMapper.getAllVisitorStatistics();
        int beforeCount = beforeStats.size();
        
        // 여러 날짜의 데이터 삽입
        LocalDateTime baseDate = LocalDateTime.now().minusDays(10);
        int insertCount = 3;
        
        for (int i = 0; i < insertCount; i++) {
            VisitorStatistic stat = VisitorStatistic.builder()
                    .dailyVisitorSum((i + 1) * 10)
                    .regDate(baseDate.plusDays(i))
                    .editDate(baseDate.plusDays(i))
                    .build();
            visitorMapper.insertVisitorStatistic(stat);
        }

        // When
        List<VisitorStatistic> afterStats = visitorMapper.getAllVisitorStatistics();

        // Then
        assertThat(afterStats.size()).isEqualTo(beforeCount + insertCount);
        
        // 날짜 순서 확인 (기본적인 정렬 확인)
        if (afterStats.size() > 1) {
            boolean isSorted = true;
            for (int i = 0; i < afterStats.size() - 1; i++) {
                LocalDateTime current = afterStats.get(i).getRegDate();
                LocalDateTime next = afterStats.get(i + 1).getRegDate();
                if (current != null && next != null && current.isAfter(next)) {
                    isSorted = false;
                    break;
                }
            }
            assertThat(isSorted).isTrue();
        }
    }    @Test
    @DisplayName("음수 값으로 통계 업데이트 테스트")
    void updateTodayStatisticWithNegativeValueTest() {
        // Given - 오늘 날짜로 데이터 삽입
        VisitorStatistic stat = VisitorStatistic.builder()
                .dailyVisitorSum(100)
                .regDate(LocalDateTime.now())
                .editDate(LocalDateTime.now())
                .build();
        visitorMapper.insertVisitorStatistic(stat);
        
        int decrementValue = -30;

        // When
        int updateResult = visitorMapper.updateTodayStatistic(decrementValue);

        // Then
        assertThat(updateResult).isGreaterThanOrEqualTo(1); // 최소 1개 이상의 레코드가 업데이트됨
        
        // 오늘 날짜의 통계가 여전히 존재하는지 확인
        int todayCount = visitorMapper.getTodayStatisticCount();
        assertThat(todayCount).isGreaterThan(0);
    }
}
