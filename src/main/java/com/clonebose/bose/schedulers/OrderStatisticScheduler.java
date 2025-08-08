package com.clonebose.bose.schedulers;

import java.time.LocalDateTime;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.clonebose.bose.mappers.OrderStatisticMapper;
import com.clonebose.bose.models.OrderStatistic;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OrderStatisticScheduler {
    @Autowired
    private OrderStatisticMapper orderStatisticMapper;
    
    private Random random = new Random();

    /**
     * 5분마다 실행되어 오늘의 매출 통계를 업데이트합니다.
     * 오늘 데이터가 없으면 새로 생성하고, 있으면 기존 데이터에 추가합니다.
     */
    @Scheduled(fixedRate = 300000) // 5분 = 300,000ms
    public void generateOrderData() {
        try {
            // 5분 동안 랜덤하게 주문 금액을 추가(금액은 5만원 이상 100원 단위까지만 추가)
            Long orderIncrement = (long) ((random.nextInt(1000) + 50000) / 100 * 100);

            log.info("매출 데이터 업데이트 시작 - 증가량: {} 원", orderIncrement);

            // 오늘 날짜의 통계 데이터가 있는지 확인
            int todayCount = orderStatisticMapper.getTodayStatisticCount();
            
            if (todayCount == 0) {
                // 오늘 데이터가 없으면 새로 생성
                LocalDateTime now = LocalDateTime.now();
                OrderStatistic newStatistic = OrderStatistic.builder()
                    .dailyOrderSum(orderIncrement)
                    .regDate(now)
                    .editDate(now)
                    .build();

                orderStatisticMapper.insertOrderStatistic(newStatistic);
                log.info("새로운 일일 통계 생성 완료 - 오늘 매출: {}", orderIncrement);
            } else {
                // 오늘 데이터가 있으면 업데이트
                orderStatisticMapper.updateTodayStatistic(orderIncrement);
                log.info("기존 일일 통계 업데이트 완료 - 매출 증가량: {}", orderIncrement);
            }
            
        } catch (Exception e) {
            log.error("매출 데이터 업데이트 중 오류 발생", e);
        }
    }
}
