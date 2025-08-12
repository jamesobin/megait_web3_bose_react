package com.clonebose.bose.mappers;

import com.clonebose.bose.models.PopularStatistic;
import com.clonebose.bose.models.ProductWithSalesDto;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 인기 상품 통계 데이터 접근을 위한 MyBatis Mapper 인터페이스
 */
@Mapper
public interface PopularProductMapper {
    
    /**
     * 전체 인기 상품 통계 데이터 조회 (날짜 오름차순 정렬: 과거 -> 현재)
     * @return 모든 인기 상품 통계 데이터 리스트
     */
    @Select("SELECT popular_statistic_id as id, product_id, total_sold_count, reg_date as regDate, edit_date as editDate " +
            "FROM popular_statistic ORDER BY reg_date ASC")
    List<PopularStatistic> getAllPopularStatistics();
    
    /**
     * 실제 상품명과 함께 인기 상품 통계 조회
     * @return 상품명이 포함된 인기 상품 통계 리스트
     */
    @Select("SELECT " +
            "ps.product_id as productId, " +
            "COALESCE(p.product_name, CONCAT('Product_', ps.product_id)) as productName, " +
            "ps.total_sold_count as totalSoldCount, " +
            "ps.reg_date as regDate, " +
            "ps.edit_date as editDate " +
            "FROM popular_statistic ps " +
            "LEFT JOIN products p ON ps.product_id = p.product_id " +
            "ORDER BY ps.reg_date ASC")
    List<ProductWithSalesDto> getAllPopularStatisticsWithProductName();

    @Insert("INSERT INTO popular_statistic (product_id, total_sold_count, reg_date, edit_date) VALUES (#{productId}, #{totalSoldCount}, #{regDate}, #{editDate})")
    int insertPopularStatistic(PopularStatistic statistic);

    @Select("SELECT COUNT(*) FROM popular_statistic WHERE DATE(reg_date) = CURDATE() AND product_id = #{productId}")
    int getTodayStatisticCount(Long productId);

    @Update("UPDATE popular_statistic SET total_sold_count = total_sold_count + #{increment}, edit_date = NOW() WHERE DATE(reg_date) = CURDATE() AND product_id = #{productId}")
    int updateTodayStatistic(Long productId, Long increment);
}
