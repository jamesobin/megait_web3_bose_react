package com.clonebose.bose.models;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserStatistic {
    private int userStatisticId;      // 사용자 통계 ID
    private int dailyUserSum;         // 일일 사용자 합계
    private LocalDateTime regDate;    // 등록 시간
    private LocalDateTime editDate;   // 수정 시간
}