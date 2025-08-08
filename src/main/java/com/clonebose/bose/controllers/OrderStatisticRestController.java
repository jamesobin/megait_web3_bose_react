package com.clonebose.bose.controllers;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.clonebose.bose.helpers.RestHelper;
import com.clonebose.bose.models.OrderStatsDto;
import com.clonebose.bose.services.OrderStatisticService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/order")
@Slf4j
@RequiredArgsConstructor
public class OrderStatisticRestController {
    
    private final OrderStatisticService orderStatisticService;
    private final RestHelper restHelper;

    @GetMapping("/count")
    @Operation(
        summary = "매출 통계 조회", 
        description = "최근 5년간 연도별, 최근 1년간 달별 매출, 최근 1주일간 매일 매출 통계정보를 담아 리턴합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "매출 통계 조회 성공"),
        @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public Map<String, Object> getOrderCount() {
        OrderStatsDto data = orderStatisticService.getOrderStats();
        return restHelper.sendJson(data);
    }

}
