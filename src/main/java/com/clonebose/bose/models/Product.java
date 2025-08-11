package com.clonebose.bose.models;
import lombok.Data;

@Data
public class Product {
    private Long id;
    private String name;
    private Long categoryId;
    private java.time.LocalDateTime regDate;
    private java.time.LocalDateTime editDate;
}