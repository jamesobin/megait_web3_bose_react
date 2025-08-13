package com.clonebose.bose.services.impl;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.clonebose.bose.mappers.UserStatisticMapper;
import com.clonebose.bose.models.UserStatistic;
import com.clonebose.bose.models.UserStatsDto;
import com.clonebose.bose.services.UserStatisticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserStatisticServiceImpl implements UserStatisticService {

    private final UserStatisticMapper userStatisticMapper;

    @Override
    public UserStatsDto getUserStats() {
        // 전체 사용자 통계 데이터 조회
        List<UserStatistic> allData = userStatisticMapper.getAllUserStatistics();

        // 현재 시간 기준으로 필터링 날짜 계산
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneYearAgo = now.minusMonths(11);
        LocalDateTime fiveYearsAgo = now.minusYears(4);
        LocalDateTime sevenDaysAgo = now.minusDays(7);

        // 통계용 Map 초기화
        Map<String, Integer> monthlyStats = new LinkedHashMap<>();
        Map<String, Integer> yearlyStats = new LinkedHashMap<>();
        Map<String, Integer> dailyStats = new LinkedHashMap<>();
        int totalUsers = 0;

        // 전체 데이터를 순회하면서 통계 계산
        for (UserStatistic statistic : allData) {
            LocalDateTime regDate = statistic.getRegDate();
            int dailyUserSum = statistic.getDailyUserSum();
            totalUsers += dailyUserSum;

            if (regDate != null) {
                // 연별 통계 (최근 3년 데이터만)
                if (regDate.isAfter(fiveYearsAgo)) {
                    String yearKey = String.valueOf(regDate.getYear());
                    yearlyStats.put(yearKey, yearlyStats.getOrDefault(yearKey, 0) + dailyUserSum);
                }

                // 월별 통계 (최근 1년 데이터만)
                if (regDate.isAfter(oneYearAgo)) {
                    String monthKey = String.format("%d-%02d", regDate.getYear(), regDate.getMonthValue());
                    monthlyStats.put(monthKey, monthlyStats.getOrDefault(monthKey, 0) + dailyUserSum);
                }

                // 날짜별 통계 (최근 7일 데이터만)
                if (regDate.isAfter(sevenDaysAgo)) {
                    String dayKey = String.format("%02d-%02d", regDate.getMonthValue(), regDate.getDayOfMonth());
                    dailyStats.put(dayKey, dailyStats.getOrDefault(dayKey, 0) + dailyUserSum);
                }
            }
        }

        // DTO 빌더 패턴으로 생성
        return UserStatsDto.builder()
            .monthlyStats(monthlyStats)
            .yearlyStats(yearlyStats)
            .dailyStats(dailyStats)
            .totalUsers(totalUsers)
            .build();
    }
}