package com.clonebose.bose.controllers;

import com.clonebose.bose.helpers.RestHelper;
import com.clonebose.bose.models.PopularProductDto;
import com.clonebose.bose.services.PopularProductService;
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

@WebMvcTest(PopularProductRestController.class)
@DisplayName("PopularProductRestController 테스트")
public class PopularProductRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PopularProductService popularProductService;

    @MockBean
    private RestHelper restHelper;

    private PopularProductDto mockProductDto;
    private Map<String, Object> mockResponseMap;

    @BeforeEach
    void setUp() {
        // Mock 데이터 생성
        Map<String, Long> weeklyStats = new LinkedHashMap<>();
        weeklyStats.put("QC 헤드폰", 45L);
        weeklyStats.put("울트라 오픈 이어버드", 38L);
        weeklyStats.put("QC 울트라 헤드폰", 32L);
        weeklyStats.put("사운드링크 플렉스 스피커", 28L);
        weeklyStats.put("QC 이어버드", 25L);
        weeklyStats.put("스마트 사운드바", 22L);
        weeklyStats.put("베이스 모듈 700", 18L);
        weeklyStats.put("서라운드 스피커 700", 15L);

        Map<String, Long> monthlyStats = new LinkedHashMap<>();
        monthlyStats.put("QC 헤드폰", 180L);
        monthlyStats.put("울트라 오픈 이어버드", 165L);
        monthlyStats.put("QC 울트라 헤드폰", 145L);
        monthlyStats.put("사운드링크 플렉스 스피커", 138L);
        monthlyStats.put("QC 이어버드", 125L);
        monthlyStats.put("스마트 사운드바", 118L);
        monthlyStats.put("베이스 모듈 700", 95L);
        monthlyStats.put("서라운드 스피커 700", 88L);

        Map<String, Long> allTimeStats = new LinkedHashMap<>();
        allTimeStats.put("QC 헤드폰", 8500L);
        allTimeStats.put("울트라 오픈 이어버드", 7800L);
        allTimeStats.put("QC 울트라 헤드폰", 7200L);
        allTimeStats.put("사운드링크 플렉스 스피커", 6850L);
        allTimeStats.put("QC 이어버드", 6200L);
        allTimeStats.put("스마트 사운드바", 5750L);
        allTimeStats.put("베이스 모듈 700", 5200L);
        allTimeStats.put("서라운드 스피커 700", 4680L);

        mockProductDto = PopularProductDto.builder()
                .weeklyStats(weeklyStats)
                .monthlyStats(monthlyStats)
                .sixMonthStats(new LinkedHashMap<>())
                .yearlyStats(new LinkedHashMap<>())
                .threeYearStats(new LinkedHashMap<>())
                .allTimeStats(allTimeStats)
                .build();

        // RestHelper.sendJson() 메서드의 반환값 Mock
        mockResponseMap = new LinkedHashMap<>();
        mockResponseMap.put("status", 200);
        mockResponseMap.put("message", "OK");
        mockResponseMap.put("item", mockProductDto);
        mockResponseMap.put("timestamp", "2025-08-11T15:30:00");
    }

    @Test
    @DisplayName("인기 상품 통계 조회 API 성공 테스트")
    void getPopularProductsSuccessTest() throws Exception {
        // Given
        when(popularProductService.getPopularProductStats()).thenReturn(mockProductDto);
        when(restHelper.sendJson(mockProductDto)).thenReturn(mockResponseMap);

        // When & Then
        mockMvc.perform(get("/api/popular/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.item").exists())
                .andExpect(jsonPath("$.item.weeklyStats").exists())
                .andExpect(jsonPath("$.item.monthlyStats").exists())
                .andExpect(jsonPath("$.item.allTimeStats").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("인기 상품 통계 조회 API - 일주일 통계 검증")
    void getPopularProductsWeeklyStatsTest() throws Exception {
        // Given
        when(popularProductService.getPopularProductStats()).thenReturn(mockProductDto);
        when(restHelper.sendJson(mockProductDto)).thenReturn(mockResponseMap);

        // When & Then
        mockMvc.perform(get("/api/popular/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.item.weeklyStats['QC 헤드폰']").value(45))
                .andExpect(jsonPath("$.item.weeklyStats['울트라 오픈 이어버드']").value(38))
                .andExpect(jsonPath("$.item.weeklyStats['QC 울트라 헤드폰']").value(32))
                .andExpect(jsonPath("$.item.weeklyStats['사운드링크 플렉스 스피커']").value(28));
    }

    @Test
    @DisplayName("인기 상품 통계 조회 API - GET 메서드 검증")
    void getPopularProductsHttpMethodTest() throws Exception {
        // Given
        when(popularProductService.getPopularProductStats()).thenReturn(mockProductDto);
        when(restHelper.sendJson(mockProductDto)).thenReturn(mockResponseMap);

        // When & Then - GET 요청은 성공
        mockMvc.perform(get("/api/popular/products"))
                .andExpect(status().isOk());

        // POST, PUT, DELETE 요청은 실패해야 함
        mockMvc.perform(post("/api/popular/products"))
                .andExpect(status().isMethodNotAllowed());

        mockMvc.perform(put("/api/popular/products"))
                .andExpect(status().isMethodNotAllowed());

        mockMvc.perform(delete("/api/popular/products"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    @DisplayName("인기 상품 통계 조회 API - Content-Type 검증")
    void getPopularProductsContentTypeTest() throws Exception {
        // Given
        when(popularProductService.getPopularProductStats()).thenReturn(mockProductDto);
        when(restHelper.sendJson(mockProductDto)).thenReturn(mockResponseMap);

        // When & Then
        mockMvc.perform(get("/api/popular/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().string("Content-Type", "application/json"));
    }
}
