package com.clonebose.bose.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VisititorCount {
    private Long id; // 방문자 카운트 ID
    private String visitorIp; // 방문자 IP
    private LocalDateTime visitedAt; // 전체 방문시간
    private LocalDate visitDate; // 방문 날짜 
    private int visitHour;  // 방문 시간 (0-23)
    private int visitYear; // 방문 연도
    private int visitMonth; // 방문 월 (1-12)    
    private int visitWeek; // 방문 주 (1-53)
    private int visitDayOfWeek; // 방문 요일 (1-7, 1: 월요일, 7: 일요일)
    private LocalDateTime regDate; // 등록 시간 (데이터베이스에 저장된 시간)
}
