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
@Tag(name = "Popular Products Statistics", description = "인기 상품 통계 관련 API")
public class PopularProductRestController {

    private final PopularProductService popularProductService;
    private final RestHelper restHelper;

    @GetMapping("/products")
    @Operation(
        summary = "인기 상품 통계 조회", 
        description = "최근 7일간 일별, 최근 1년간 월별, 최근 5년간 연별 인기 상품 통계 정보를 리턴합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "인기 상품 통계 조회 성공"),
        @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public Map<String, Object> getPopularProducts() {
        PopularProductDto data = popularProductService.getPopularProductStats();
        return restHelper.sendJson(data);
    }
}
