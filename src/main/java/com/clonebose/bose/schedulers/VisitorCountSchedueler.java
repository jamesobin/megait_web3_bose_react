package com.clonebose.bose.schedulers;

import com.clonebose.bose.mappers.VisitorMapper;
import com.clonebose.bose.models.VisititorCount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.Random;

@Component
@Slf4j
public class VisitorCountSchedueler {
    @Autowired
    private VisitorMapper visitorMapper;
    
    private Random random = new Random();
    
    /**
     * 5분마다 랜덤한 방문자 수 데이터를 생성하여 저장합니다.
     * 1~10명의 방문자를 랜덤하게 생성합니다.
     */
    @Scheduled(fixedRate = 300000) 
    public void generateVisitorData() {
        try {
            // 5분 동안 랜덤하게 1~10명의 방문자 생성
            int visitorCount = random.nextInt(10) + 1;
            
            log.info("방문자 데이터 생성 시작 - {} 명", visitorCount);
            
            for (int i = 0; i < visitorCount; i++) {
                LocalDateTime now = LocalDateTime.now();
                
                // 날짜/시간 정보 추출
                LocalDate visitDate = now.toLocalDate();
                int visitHour = now.getHour();
                int visitYear = now.getYear();
                int visitMonth = now.getMonthValue();
                int visitWeek = now.get(WeekFields.of(Locale.getDefault()).weekOfYear());
                int visitDayOfWeek = now.getDayOfWeek().getValue();
                
                // 랜덤 IP 생성
                String randomIp;
                int ipType = random.nextInt(3);
                
                switch (ipType) {
                    case 0: // 192.168.x.x (사설 IP)
                        randomIp = String.format("192.168.%d.%d", 
                            random.nextInt(256), 
                            random.nextInt(254) + 1);
                        break;
                    case 1: // 10.x.x.x (사설 IP)
                        randomIp = String.format("10.%d.%d.%d", 
                            random.nextInt(256), 
                            random.nextInt(256), 
                            random.nextInt(254) + 1);
                        break;
                    default: // 공인 IP
                        randomIp = String.format("%d.%d.%d.%d", 
                            random.nextInt(223) + 1, 
                            random.nextInt(256), 
                            random.nextInt(256), 
                            random.nextInt(254) + 1);
                        break;
                }
                
                // VisititorCount 객체를 빌더 패턴으로 생성
                VisititorCount visitorCountObj = VisititorCount.builder()
                    .visitorIp(randomIp)
                    .visitedAt(now)
                    .visitDate(visitDate)
                    .visitHour(visitHour)
                    .visitYear(visitYear)
                    .visitMonth(visitMonth)
                    .visitWeek(visitWeek)
                    .visitDayOfWeek(visitDayOfWeek)
                    .regDate(now)
                    .build();
                
                // 직접 매퍼를 통해 DB에 저장
                visitorMapper.insertVisitorCount(visitorCountObj);
                log.debug("방문자 데이터 저장 완료 - IP: {}", randomIp);
            }
            
            log.info("방문자 데이터 생성 완료 - {} 명", visitorCount);
            
        } catch (Exception e) {
            log.error("방문자 데이터 생성 중 오류 발생", e);
        }
    }
}
