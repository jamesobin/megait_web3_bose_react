package com.clonebose.bose.models;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VisitorStatistic {
    private int visitorStatisticId; // 방문자 통계 ID
    private int dailyVisitorSum; // 일일 방문자 합계
    private LocalDateTime regDate; // 등록 시간
    private LocalDateTime editDate; // 수정 시간
}
