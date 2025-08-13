package com.clonebose.bose.models;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Product {
    private Long id;
    private String name;
    private Long categoryId;
    private LocalDateTime regDate;
    private LocalDateTime editDate;
}