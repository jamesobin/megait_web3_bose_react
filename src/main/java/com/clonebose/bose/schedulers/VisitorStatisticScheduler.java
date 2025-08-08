package com.clonebose.bose.schedulers;

import com.clonebose.bose.mappers.VisitorMapper;
import com.clonebose.bose.models.VisitorStatistic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

@Component
@Slf4j
public class VisitorStatisticScheduler {
    @Autowired
    private VisitorMapper visitorMapper;
    
    private Random random = new Random();
    
    /**
     * 5분마다 실행되어 오늘의 방문자 통계를 업데이트합니다.
     * 오늘 데이터가 없으면 새로 생성하고, 있으면 기존 데이터에 추가합니다.
     */
    @Scheduled(fixedRate = 300000) // 5분 = 300,000ms
    public void generateVisitorData() {
        try {
            // 5분 동안 랜덤하게 1~10명의 방문자 증가
            int visitorIncrement = random.nextInt(10) + 1;
            
            log.info("방문자 데이터 업데이트 시작 - 증가량: {} 명", visitorIncrement);
            
            // 오늘 날짜의 통계 데이터가 있는지 확인
            int todayCount = visitorMapper.getTodayStatisticCount();
            
            if (todayCount == 0) {
                // 오늘 데이터가 없으면 새로 생성
                LocalDateTime now = LocalDateTime.now();
                VisitorStatistic newStatistic = VisitorStatistic.builder()
                    .dailyVisitorSum(visitorIncrement)
                    .regDate(now)
                    .editDate(now)
                    .build();
                
                visitorMapper.insertVisitorStatistic(newStatistic);
                log.info("새로운 일일 통계 생성 완료 - 방문자수: {}", visitorIncrement);
            } else {
                // 오늘 데이터가 있으면 업데이트
                visitorMapper.updateTodayStatistic(visitorIncrement);
                log.info("기존 일일 통계 업데이트 완료 - 증가량: {}", visitorIncrement);
            }
            
        } catch (Exception e) {
            log.error("방문자 데이터 업데이트 중 오류 발생", e);
        }
    }
}
