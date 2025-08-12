package com.clonebose.bose.mappers;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import com.clonebose.bose.models.OrderStatistic;

@SpringBootTest
@Transactional
@DisplayName("OrderStatisticMapper 테스트")
@TestMethodOrder(OrderAnnotation.class)
public class OrderStatisticMapperTest {

    @Autowired
    private OrderStatisticMapper orderStatisticMapper;

    private OrderStatistic testOrderStatistic;

    @BeforeEach
    void setUp() {
        testOrderStatistic = OrderStatistic.builder()
                .dailyOrderSum(1000000L)
                .regDate(LocalDateTime.now())
                .editDate(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("주문 통계 데이터 삽입 테스트")
    void insertOrderStatisticTest() {
        // When
        int result = orderStatisticMapper.insertOrderStatistic(testOrderStatistic);

        // Then
        assertThat(result).isEqualTo(1);
    }

    @Test
    @DisplayName("전체 주문 통계 데이터 조회 테스트")
    void getAllOrderStatisticsTest() {
        // When
        List<OrderStatistic> statistics = orderStatisticMapper.getAllOrderStatistics();

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
        orderStatisticMapper.insertOrderStatistic(testOrderStatistic);

        // When
        int count = orderStatisticMapper.getTodayStatisticCount();

        // Then
        assertThat(count).isGreaterThanOrEqualTo(1);
    }

    @Test
    @DisplayName("오늘 날짜 통계 업데이트 테스트")
    void updateTodayStatisticTest() {
        // Given - 오늘 날짜로 데이터 삽입
        orderStatisticMapper.insertOrderStatistic(testOrderStatistic);
        Long incrementValue = 50L;
        
        // 삽입 전 오늘의 카운트 확인
        int beforeCount = orderStatisticMapper.getTodayStatisticCount();

        // When
        int updateResult = orderStatisticMapper.updateTodayStatistic(incrementValue);

        // Then
        assertThat(updateResult).isGreaterThanOrEqualTo(1); // 오늘 날짜의 레코드가 업데이트됨
        
        // 오늘의 카운트가 여전히 존재하는지 확인
        int afterCount = orderStatisticMapper.getTodayStatisticCount();
        assertThat(afterCount).isGreaterThanOrEqualTo(beforeCount);
    }

    @Test
    @DisplayName("오늘 날짜에 통계가 없을 때 업데이트 테스트")
    void updateTodayStatisticWhenNoDataTest() {
        // Given - 현재 오늘 날짜에 데이터가 있는지 확인
        int todayCount = orderStatisticMapper.getTodayStatisticCount();
        Long incrementValue = 10L;

        // When
        int updateResult = orderStatisticMapper.updateTodayStatistic(incrementValue);

        // Then
        if (todayCount > 0) {
            // 오늘 날짜에 이미 데이터가 있다면 업데이트됨
            assertThat(updateResult).isGreaterThanOrEqualTo(1);
        } else {
            // 오늘 날짜에 데이터가 없다면 업데이트되지 않음
            assertThat(updateResult).isEqualTo(0);
        }
    }

    @Test
    @DisplayName("음수 값으로 통계 업데이트 테스트")
    void updateTodayStatisticWithNegativeValueTest() {
        // Given - 오늘 날짜로 데이터 삽입
        OrderStatistic stat = OrderStatistic.builder()
                .dailyOrderSum(1000000L)
                .regDate(LocalDateTime.now())
                .editDate(LocalDateTime.now())
                .build();
        orderStatisticMapper.insertOrderStatistic(stat);

        Long decrementValue = -30L;

        // When
        int updateResult = orderStatisticMapper.updateTodayStatistic(decrementValue);

        // Then
        assertThat(updateResult).isGreaterThanOrEqualTo(1); // 최소 1개 이상의 레코드가 업데이트됨
        
        // 오늘 날짜의 통계가 여전히 존재하는지 확인
        int todayCount = orderStatisticMapper.getTodayStatisticCount();
        assertThat(todayCount).isGreaterThan(0);
    }
}
