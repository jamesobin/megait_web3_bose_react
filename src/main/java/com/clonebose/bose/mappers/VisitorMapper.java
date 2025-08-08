package com.clonebose.bose.mappers;

import com.clonebose.bose.models.VisitorStatistic;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 방문자 통계 데이터 접근을 위한 MyBatis Mapper 인터페이스
 */
@Mapper
public interface VisitorMapper {    
      
    /**
     * 방문자 통계 데이터 삽입
     * @param visitorStatistic 삽입할 방문자 통계 객체
     * @return 삽입된 행의 수
     */
    @Insert("INSERT INTO visitor_statistic (daily_visitor_sum, reg_date, edit_date) " +
            "VALUES (#{dailyVisitorSum}, #{regDate}, #{editDate})")    
    int insertVisitorStatistic(VisitorStatistic visitorStatistic);    
    
    /**
     * 전체 방문자 통계 데이터 조회 (날짜 오름차순 정렬: 과거 -> 현재)
     * @return 모든 방문자 통계 데이터 리스트
     */
    @Select("SELECT visitor_statistic_id, daily_visitor_sum, reg_date, edit_date FROM visitor_statistic ORDER BY reg_date ASC")
    List<VisitorStatistic> getAllVisitorStatistics();
    
    /**
     * 오늘 날짜의 통계가 있는지 확인
     * @return 오늘 날짜의 통계 레코드 개수
     */
    @Select("SELECT COUNT(*) FROM visitor_statistic WHERE DATE(reg_date) = CURDATE()")
    int getTodayStatisticCount();

    /**
     * 오늘 날짜의 통계 업데이트
     * @param increment 증가시킬 방문자 수
     * @return 업데이트된 행의 수
     */
    @Update("UPDATE visitor_statistic SET daily_visitor_sum = daily_visitor_sum + #{increment}, edit_date = NOW() " +
            "WHERE DATE(reg_date) = CURDATE()")
    int updateTodayStatistic(int increment);
}
