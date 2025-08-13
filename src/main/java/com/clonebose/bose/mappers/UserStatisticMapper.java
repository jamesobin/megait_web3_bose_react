package com.clonebose.bose.mappers;

import com.clonebose.bose.models.UserStatistic;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 사용자 통계 데이터 접근을 위한 MyBatis Mapper 인터페이스
 */
@Mapper
public interface UserStatisticMapper {

    /**
     * 사용자 통계 데이터 삽입
     * @param userStatistic 삽입할 사용자 통계 객체
     * @return 삽입된 행의 수
     */
    @Insert("INSERT INTO user_statistic (daily_user_sum, reg_date, edit_date) " +
            "VALUES (#{dailyUserSum}, #{regDate}, #{editDate})")
    int insertUserStatistic(UserStatistic userStatistic);

    /**
     * 전체 사용자 통계 데이터 조회 (날짜 오름차순 정렬: 과거 -> 현재)
     * @return 모든 사용자 통계 데이터 리스트
     */
    @Select("SELECT user_statistic_id, daily_user_sum, reg_date, edit_date FROM user_statistic ORDER BY reg_date ASC")
    List<UserStatistic> getAllUserStatistics();

    /**
     * 오늘 날짜의 통계가 있는지 확인
     * @return 오늘 날짜의 통계 레코드 개수
     */
    @Select("SELECT COUNT(*) FROM user_statistic WHERE DATE(reg_date) = CURDATE()")
    int getTodayStatisticCount();

    /**
     * 오늘 날짜의 통계 업데이트
     * @param increment 증가시킬 사용자 수
     * @return 업데이트된 행의 수
     */
    @Update("UPDATE user_statistic SET daily_user_sum = daily_user_sum + #{increment}, edit_date = NOW() " +
            "WHERE DATE(reg_date) = CURDATE()")
    int updateTodayStatistic(int increment);
}