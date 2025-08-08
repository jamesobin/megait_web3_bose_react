package com.clonebose.bose.models;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderStatistic {
    private int orderStatisticId;   // 매출 정보 일련번호
    private Long dailyOrderSum;     // 일일 주문 합계
    private LocalDateTime regDate;         // 주문 정보를 등록한 날짜 시간 정보
    private LocalDateTime editDate;        // 주문 정보를 수정한 날짜 시간 정보
}
