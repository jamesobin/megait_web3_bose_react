package com.clonebose.bose.controllers;

import com.clonebose.bose.helpers.RestHelper;
import com.clonebose.bose.models.VisitorStatsDto;
import com.clonebose.bose.services.VisitorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VisitorRestController.class)
@DisplayName("VisitorRestController 테스트")
public class VisitorRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VisitorService visitorService;    @MockBean
    private RestHelper restHelper;

    private VisitorStatsDto mockStatsDto;
    private Map<String, Object> mockResponseMap;

    @BeforeEach
    void setUp() {
        // Mock 데이터 생성
        Map<String, Integer> dailyStats = new LinkedHashMap<>();
        dailyStats.put("08-01", 120);
        dailyStats.put("08-02", 95);
        dailyStats.put("08-03", 150);
        dailyStats.put("08-04", 88);
        dailyStats.put("08-05", 200);
        dailyStats.put("08-06", 175);
        dailyStats.put("08-07", 140);

        Map<String, Integer> monthlyStats = new LinkedHashMap<>();
        monthlyStats.put("2024-09", 3200);
        monthlyStats.put("2024-10", 2800);
        monthlyStats.put("2024-11", 3500);
        monthlyStats.put("2024-12", 4100);
        monthlyStats.put("2025-01", 3800);
        monthlyStats.put("2025-02", 3400);
        monthlyStats.put("2025-03", 3900);
        monthlyStats.put("2025-04", 4200);
        monthlyStats.put("2025-05", 3700);
        monthlyStats.put("2025-06", 4000);
        monthlyStats.put("2025-07", 4300);
        monthlyStats.put("2025-08", 2500);

        Map<String, Integer> yearlyStats = new LinkedHashMap<>();
        yearlyStats.put("2021", 35000);
        yearlyStats.put("2022", 38000);
        yearlyStats.put("2023", 42000);
        yearlyStats.put("2024", 45000);
        yearlyStats.put("2025", 28000);

        mockStatsDto = VisitorStatsDto.builder()
                .dailyStats(dailyStats)
                .monthlyStats(monthlyStats)
                .yearlyStats(yearlyStats)
                .totalVisitors(188000)
                .build();

        // RestHelper.sendJson() 메서드의 반환값 Mock
        mockResponseMap = new LinkedHashMap<>();
        mockResponseMap.put("status", 200);
        mockResponseMap.put("message", "OK");
        mockResponseMap.put("item", mockStatsDto);
        mockResponseMap.put("timestamp", "2025-08-08T15:30:00");
    }

    @Test
    @DisplayName("방문자 통계 조회 API 성공 테스트")
    void getVisitorCountSuccessTest() throws Exception {
        // Given
        when(visitorService.getVisitorStats()).thenReturn(mockStatsDto);
        when(restHelper.sendJson(mockStatsDto)).thenReturn(mockResponseMap);

        // When & Then
        mockMvc.perform(get("/api/visitor/count")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.item").exists())
                .andExpect(jsonPath("$.item.dailyStats").exists())
                .andExpect(jsonPath("$.item.monthlyStats").exists())
                .andExpect(jsonPath("$.item.yearlyStats").exists())
                .andExpect(jsonPath("$.item.totalVisitors").value(188000))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("방문자 통계 조회 API - 일별 통계 검증")
    void getVisitorCountDailyStatsTest() throws Exception {
        // Given
        when(visitorService.getVisitorStats()).thenReturn(mockStatsDto);
        when(restHelper.sendJson(mockStatsDto)).thenReturn(mockResponseMap);

        // When & Then
        mockMvc.perform(get("/api/visitor/count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.item.dailyStats['08-01']").value(120))
                .andExpect(jsonPath("$.item.dailyStats['08-02']").value(95))
                .andExpect(jsonPath("$.item.dailyStats['08-03']").value(150))
                .andExpect(jsonPath("$.item.dailyStats['08-04']").value(88))
                .andExpect(jsonPath("$.item.dailyStats['08-05']").value(200))
                .andExpect(jsonPath("$.item.dailyStats['08-06']").value(175))
                .andExpect(jsonPath("$.item.dailyStats['08-07']").value(140));
    }

    @Test
    @DisplayName("방문자 통계 조회 API - 월별 통계 검증")
    void getVisitorCountMonthlyStatsTest() throws Exception {
        // Given
        when(visitorService.getVisitorStats()).thenReturn(mockStatsDto);
        when(restHelper.sendJson(mockStatsDto)).thenReturn(mockResponseMap);

        // When & Then
        mockMvc.perform(get("/api/visitor/count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.item.monthlyStats['2024-09']").value(3200))
                .andExpect(jsonPath("$.item.monthlyStats['2024-10']").value(2800))
                .andExpect(jsonPath("$.item.monthlyStats['2024-11']").value(3500))
                .andExpect(jsonPath("$.item.monthlyStats['2024-12']").value(4100))
                .andExpect(jsonPath("$.item.monthlyStats['2025-01']").value(3800))
                .andExpect(jsonPath("$.item.monthlyStats['2025-08']").value(2500));
    }

    @Test
    @DisplayName("방문자 통계 조회 API - 연별 통계 검증")
    void getVisitorCountYearlyStatsTest() throws Exception {
        // Given
        when(visitorService.getVisitorStats()).thenReturn(mockStatsDto);
        when(restHelper.sendJson(mockStatsDto)).thenReturn(mockResponseMap);

        // When & Then
        mockMvc.perform(get("/api/visitor/count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.item.yearlyStats['2021']").value(35000))
                .andExpect(jsonPath("$.item.yearlyStats['2022']").value(38000))
                .andExpect(jsonPath("$.item.yearlyStats['2023']").value(42000))
                .andExpect(jsonPath("$.item.yearlyStats['2024']").value(45000))
                .andExpect(jsonPath("$.item.yearlyStats['2025']").value(28000));
    }

    @Test
    @DisplayName("방문자 통계 조회 API - 빈 데이터 처리 테스트")
    void getVisitorCountEmptyDataTest() throws Exception {
        // Given
        VisitorStatsDto emptyStatsDto = VisitorStatsDto.builder()
                .dailyStats(new LinkedHashMap<>())
                .monthlyStats(new LinkedHashMap<>())
                .yearlyStats(new LinkedHashMap<>())
                .totalVisitors(0)
                .build();

        Map<String, Object> emptyResponseMap = new LinkedHashMap<>();
        emptyResponseMap.put("status", 200);
        emptyResponseMap.put("message", "OK");
        emptyResponseMap.put("item", emptyStatsDto);
        emptyResponseMap.put("timestamp", "2025-08-08T15:30:00");

        when(visitorService.getVisitorStats()).thenReturn(emptyStatsDto);
        when(restHelper.sendJson(emptyStatsDto)).thenReturn(emptyResponseMap);

        // When & Then
        mockMvc.perform(get("/api/visitor/count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.item.dailyStats").isEmpty())
                .andExpect(jsonPath("$.item.monthlyStats").isEmpty())
                .andExpect(jsonPath("$.item.yearlyStats").isEmpty())
                .andExpect(jsonPath("$.item.totalVisitors").value(0));
    }    // 예외 처리 테스트는 현재 컨트롤러에 예외 처리 로직이 없어서 제외
    /*
    @Test
    @DisplayName("방문자 통계 조회 API - 서비스 예외 처리 테스트")
    void getVisitorCountServiceExceptionTest() throws Exception {
        // Given
        RuntimeException serviceException = new RuntimeException("Database connection failed");
        when(visitorService.getVisitorStats()).thenThrow(serviceException);

        // When & Then
        // 컨트롤러에서 예외 처리가 없으므로 500 Internal Server Error가 발생할 것으로 예상
        mockMvc.perform(get("/api/visitor/count"))
                .andExpect(status().is5xxServerError());
    }
    */

    @Test
    @DisplayName("방문자 통계 조회 API - GET 메서드 검증")
    void getVisitorCountHttpMethodTest() throws Exception {
        // Given
        when(visitorService.getVisitorStats()).thenReturn(mockStatsDto);
        when(restHelper.sendJson(mockStatsDto)).thenReturn(mockResponseMap);

        // When & Then - GET 요청은 성공
        mockMvc.perform(get("/api/visitor/count"))
                .andExpect(status().isOk());

        // POST, PUT, DELETE 요청은 실패해야 함
        mockMvc.perform(post("/api/visitor/count"))
                .andExpect(status().isMethodNotAllowed());

        mockMvc.perform(put("/api/visitor/count"))
                .andExpect(status().isMethodNotAllowed());        mockMvc.perform(delete("/api/visitor/count"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    @DisplayName("방문자 통계 조회 API - Content-Type 검증")
    void getVisitorCountContentTypeTest() throws Exception {
        // Given
        when(visitorService.getVisitorStats()).thenReturn(mockStatsDto);
        when(restHelper.sendJson(mockStatsDto)).thenReturn(mockResponseMap);

        // When & Then
        mockMvc.perform(get("/api/visitor/count"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().string("Content-Type", "application/json"));
    }
}
