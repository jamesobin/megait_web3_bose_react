package com.clonebose.bose.controllers;

import com.clonebose.bose.helpers.RestHelper;
import com.clonebose.bose.models.PopularProductDto;
import com.clonebose.bose.services.PopularProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 인기 상품 통계 관련 REST API 컨트롤러
 */
@RestController
@RequestMapping("/api/popular")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "인기 상품 API", description = "상품 판매 통계 및 인기 상품 순위 관련 API")
public class PopularProductRestController {

    private final PopularProductService popularProductService;
    private final RestHelper restHelper;

    @GetMapping("/products")
    @Operation(
        summary = "📊 6단계 시간별 인기 상품 통계 조회", 
        description = """
            **6단계 시간 구간별 인기 상품 TOP 8 통계를 조회합니다.**
            
            **📅 시간 구간:**
            - **일주일**: 최근 7일간 인기 상품
            - **한달**: 최근 30일간 인기 상품  
            - **6개월**: 최근 6개월간 인기 상품
            - **1년**: 최근 1년간 인기 상품
            - **3년**: 최근 3년간 인기 상품
            - **전체**: 전체 기간 인기 상품
            
            **📈 반환 데이터:**
            각 시간 구간별로 상품명과 판매량이 내림차순으로 정렬된 TOP 8 리스트
            """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "인기 상품 통계 조회 성공"
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "서버 내부 오류가 발생했습니다"
        )
    })
    public Map<String, Object> getPopularProducts() {
        PopularProductDto data = popularProductService.getPopularProductStats();
        return restHelper.sendJson(data);
    }
}
