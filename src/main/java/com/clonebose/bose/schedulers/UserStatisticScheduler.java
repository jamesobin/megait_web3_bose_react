package com.clonebose.bose.schedulers;

import com.clonebose.bose.mappers.UserStatisticMapper;
import com.clonebose.bose.models.UserStatistic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

@Component
@Slf4j
public class UserStatisticScheduler {
    @Autowired
    private UserStatisticMapper userStatisticMapper;

    private Random random = new Random();

    /**
     * 5분마다 실행되어 오늘의 사용자 통계를 업데이트합니다.
     * 오늘 데이터가 없으면 새로 생성하고, 있으면 기존 데이터에 추가합니다.
     */
    @Scheduled(fixedRate = 300000) // 5분 = 300,000ms
    public void generateUserData() {
        try {
            // 5분 동안 랜덤하게 1~10명의 사용자 증가
            int userIncrement = random.nextInt(10) + 1;

            log.info("사용자 데이터 업데이트 시작 - 증가량: {} 명", userIncrement);

            // 오늘 날짜의 통계 데이터가 있는지 확인
            int todayCount = userStatisticMapper.getTodayStatisticCount();

            if (todayCount == 0) {
                // 오늘 데이터가 없으면 새로 생성
                LocalDateTime now = LocalDateTime.now();
                UserStatistic newStatistic = UserStatistic.builder()
                        .dailyUserSum(userIncrement)
                        .regDate(now)
                        .editDate(now)
                        .build();

                userStatisticMapper.insertUserStatistic(newStatistic);
                log.info("새로운 일일 사용자 통계 생성 완료 - 사용자수: {}", userIncrement);
            } else {
                // 오늘 데이터가 있으면 업데이트
                userStatisticMapper.updateTodayStatistic(userIncrement);
                log.info("기존 일일 사용자 통계 업데이트 완료 - 증가량: {}", userIncrement);
            }

        } catch (Exception e) {
            log.error("사용자 데이터 업데이트 중 오류 발생", e);
        }
    }
}