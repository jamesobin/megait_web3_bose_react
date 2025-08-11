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
public class PopularProductControllerTest {

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

        Map<String, Long> sixMonthStats = new LinkedHashMap<>();
        sixMonthStats.put("QC 헤드폰", 850L);
        sixMonthStats.put("울트라 오픈 이어버드", 720L);
        sixMonthStats.put("QC 울트라 헤드폰", 680L);
        sixMonthStats.put("사운드링크 플렉스 스피커", 635L);
        sixMonthStats.put("QC 이어버드", 590L);
        sixMonthStats.put("스마트 사운드바", 540L);
        sixMonthStats.put("베이스 모듈 700", 485L);
        sixMonthStats.put("서라운드 스피커 700", 420L);

        Map<String, Long> yearlyStats = new LinkedHashMap<>();
        yearlyStats.put("QC 헤드폰", 1680L);
        yearlyStats.put("울트라 오픈 이어버드", 1420L);
        yearlyStats.put("QC 울트라 헤드폰", 1350L);
        yearlyStats.put("사운드링크 플렉스 스피커", 1280L);
        yearlyStats.put("QC 이어버드", 1190L);
        yearlyStats.put("스마트 사운드바", 1080L);
        yearlyStats.put("베이스 모듈 700", 950L);
        yearlyStats.put("서라운드 스피커 700", 850L);

        Map<String, Long> threeYearStats = new LinkedHashMap<>();
        threeYearStats.put("QC 헤드폰", 4200L);
        threeYearStats.put("울트라 오픈 이어버드", 3850L);
        threeYearStats.put("QC 울트라 헤드폰", 3620L);
        threeYearStats.put("사운드링크 플렉스 스피커", 3380L);
        threeYearStats.put("QC 이어버드", 3150L);
        threeYearStats.put("스마트 사운드바", 2890L);
        threeYearStats.put("베이스 모듈 700", 2650L);
        threeYearStats.put("서라운드 스피커 700", 2420L);

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
                .sixMonthStats(sixMonthStats)
                .yearlyStats(yearlyStats)
                .threeYearStats(threeYearStats)
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
        when(popularProductService.getPopularProducts()).thenReturn(mockProductDto);
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
                .andExpect(jsonPath("$.item.sixMonthStats").exists())
                .andExpect(jsonPath("$.item.yearlyStats").exists())
                .andExpect(jsonPath("$.item.threeYearStats").exists())
                .andExpect(jsonPath("$.item.allTimeStats").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("인기 상품 통계 조회 API - 일주일 통계 검증")
    void getPopularProductsWeeklyStatsTest() throws Exception {
        // Given
        when(popularProductService.getPopularProducts()).thenReturn(mockProductDto);
        when(restHelper.sendJson(mockProductDto)).thenReturn(mockResponseMap);

        // When & Then
        mockMvc.perform(get("/api/popular/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.item.weeklyStats['QC 헤드폰']").value(45))
                .andExpect(jsonPath("$.item.weeklyStats['울트라 오픈 이어버드']").value(38))
                .andExpect(jsonPath("$.item.weeklyStats['QC 울트라 헤드폰']").value(32))
                .andExpect(jsonPath("$.item.weeklyStats['사운드링크 플렉스 스피커']").value(28))
                .andExpect(jsonPath("$.item.weeklyStats['QC 이어버드']").value(25))
                .andExpect(jsonPath("$.item.weeklyStats['스마트 사운드바']").value(22))
                .andExpect(jsonPath("$.item.weeklyStats['베이스 모듈 700']").value(18))
                .andExpect(jsonPath("$.item.weeklyStats['서라운드 스피커 700']").value(15));
    }

    @Test
    @DisplayName("인기 상품 통계 조회 API - 한달 통계 검증")
    void getPopularProductsMonthlyStatsTest() throws Exception {
        // Given
        when(popularProductService.getPopularProducts()).thenReturn(mockProductDto);
        when(restHelper.sendJson(mockProductDto)).thenReturn(mockResponseMap);

        // When & Then
        mockMvc.perform(get("/api/popular/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.item.monthlyStats['QC 헤드폰']").value(180))
                .andExpect(jsonPath("$.item.monthlyStats['울트라 오픈 이어버드']").value(165))
                .andExpect(jsonPath("$.item.monthlyStats['QC 울트라 헤드폰']").value(145))
                .andExpect(jsonPath("$.item.monthlyStats['사운드링크 플렉스 스피커']").value(138));
    }

    @Test
    @DisplayName("인기 상품 통계 조회 API - 전체 기간 통계 검증")
    void getPopularProductsAllTimeStatsTest() throws Exception {
        // Given
        when(popularProductService.getPopularProducts()).thenReturn(mockProductDto);
        when(restHelper.sendJson(mockProductDto)).thenReturn(mockResponseMap);

        // When & Then
        mockMvc.perform(get("/api/popular/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.item.allTimeStats['QC 헤드폰']").value(8500))
                .andExpect(jsonPath("$.item.allTimeStats['울트라 오픈 이어버드']").value(7800))
                .andExpect(jsonPath("$.item.allTimeStats['QC 울트라 헤드폰']").value(7200))
                .andExpect(jsonPath("$.item.allTimeStats['사운드링크 플렉스 스피커']").value(6850));
    }

    @Test
    @DisplayName("인기 상품 통계 조회 API - 빈 데이터 처리 테스트")
    void getPopularProductsEmptyDataTest() throws Exception {
        // Given
        PopularProductDto emptyProductDto = PopularProductDto.builder()
                .weeklyStats(new LinkedHashMap<>())
                .monthlyStats(new LinkedHashMap<>())
                .sixMonthStats(new LinkedHashMap<>())
                .yearlyStats(new LinkedHashMap<>())
                .threeYearStats(new LinkedHashMap<>())
                .allTimeStats(new LinkedHashMap<>())
                .build();

        Map<String, Object> emptyResponseMap = new LinkedHashMap<>();
        emptyResponseMap.put("status", 200);
        emptyResponseMap.put("message", "OK");
        emptyResponseMap.put("item", emptyProductDto);
        emptyResponseMap.put("timestamp", "2025-08-11T15:30:00");

        when(popularProductService.getPopularProducts()).thenReturn(emptyProductDto);
        when(restHelper.sendJson(emptyProductDto)).thenReturn(emptyResponseMap);

        // When & Then
        mockMvc.perform(get("/api/popular/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.item.weeklyStats").isEmpty())
                .andExpect(jsonPath("$.item.monthlyStats").isEmpty())
                .andExpect(jsonPath("$.item.sixMonthStats").isEmpty())
                .andExpect(jsonPath("$.item.yearlyStats").isEmpty())
                .andExpect(jsonPath("$.item.threeYearStats").isEmpty())
                .andExpect(jsonPath("$.item.allTimeStats").isEmpty());
    }

    @Test
    @DisplayName("인기 상품 통계 조회 API - GET 메서드 검증")
    void getPopularProductsHttpMethodTest() throws Exception {
        // Given
        when(popularProductService.getPopularProducts()).thenReturn(mockProductDto);
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
        when(popularProductService.getPopularProducts()).thenReturn(mockProductDto);
        when(restHelper.sendJson(mockProductDto)).thenReturn(mockResponseMap);

        // When & Then
        mockMvc.perform(get("/api/popular/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().string("Content-Type", "application/json"));
    }

    @Test
    @DisplayName("인기 상품 통계 조회 API - 6단계 시간 필터 데이터 존재 검증")
    void getPopularProductsAllTimePeriodsTest() throws Exception {
        // Given
        when(popularProductService.getPopularProducts()).thenReturn(mockProductDto);
        when(restHelper.sendJson(mockProductDto)).thenReturn(mockResponseMap);

        // When & Then
        mockMvc.perform(get("/api/popular/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.item.weeklyStats").isNotEmpty())
                .andExpect(jsonPath("$.item.monthlyStats").isNotEmpty())
                .andExpect(jsonPath("$.item.sixMonthStats").isNotEmpty())
                .andExpect(jsonPath("$.item.yearlyStats").isNotEmpty())
                .andExpect(jsonPath("$.item.threeYearStats").isNotEmpty())
                .andExpect(jsonPath("$.item.allTimeStats").isNotEmpty());
    }
}
