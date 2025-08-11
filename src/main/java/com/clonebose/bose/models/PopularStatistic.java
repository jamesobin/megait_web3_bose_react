package com.clonebose.bose.models;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PopularStatistic {
    private Long id;
    private Long productId;
    private Long totalSoldCount;
    private LocalDateTime regDate;
    private LocalDateTime editDate;
}
