package com.clonebose.bose.mappers;

import com.clonebose.bose.models.VisititorCount;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface VisitorMapper {    
    
    @Insert("INSERT INTO visitorCount (visitor_ip, visited_at, visit_date, visit_hour, visit_year, visit_month, visit_week, visit_day_of_week, reg_date) " +
            "VALUES (#{visitorIp}, #{visitedAt}, #{visitDate}, #{visitHour}, #{visitYear}, #{visitMonth}, #{visitWeek}, #{visitDayOfWeek}, #{regDate})")    
    int insertVisitorCount(VisititorCount visitorCount);    
    
    // 전체 방문자 데이터 조회
    @Select("SELECT visit_year, visit_month, visit_day_of_week FROM visitorCount")
    List<VisititorCount> getAllVisitorData();
    
    // 총 방문자 수
    @Select("SELECT COUNT(*) FROM visitorCount")
    int getTotalVisitorCount();
}
