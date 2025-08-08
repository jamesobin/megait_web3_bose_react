package com.clonebose.bose.mappers;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.clonebose.bose.models.OrderStatistic;

@Mapper
public interface OrderStatisticMapper {

    /**
     * 방문자 통계 데이터 삽입
     * @param orderStatistic 삽입할 방문자 통계 객체
     * @return 삽입된 행의 수
     */
    @Insert("INSERT INTO order_statistic (daily_order_sum, reg_date, edit_date) " +
            "VALUES (#{dailyOrderSum}, #{regDate}, #{editDate})")    
    public int insertOrderStatistic(OrderStatistic orderStatistic);    
    
    /**
     * 전체 주문 통계 데이터 조회 (날짜 오름차순 정렬: 과거 -> 현재)
     * @return 모든 주문 통계 데이터 리스트
     */
    @Select("SELECT order_statistic_id, daily_order_sum, reg_date, edit_date FROM order_statistic ORDER BY reg_date ASC")
    public List<OrderStatistic> getAllOrderStatistics();

    /**
     * 오늘 날짜의 통계가 있는지 확인
     * @return 오늘 날짜의 통계 레코드 개수
     */
    @Select("SELECT COUNT(*) FROM order_statistic WHERE DATE(reg_date) = CURDATE()")
    public int getTodayStatisticCount();

    /**
     * 오늘 날짜의 통계 업데이트
     * @param increment 증가시킬 주문 금액
     * @return 업데이트된 행의 수
     */
    @Update("UPDATE order_statistic SET daily_order_sum = daily_order_sum + #{increment}, edit_date = NOW() " +
            "WHERE DATE(reg_date) = CURDATE()")
    public int updateTodayStatistic(Long increment);

}
