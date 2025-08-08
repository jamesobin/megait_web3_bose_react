package com.clonebose.bose.controllers;
import com.clonebose.bose.helpers.RestHelper;
import com.clonebose.bose.models.VisitorStatsDto;
import com.clonebose.bose.services.VisitorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

/**
 * 방문자 통계 관련 REST API 컨트롤러
 */
@RestController
@RequestMapping("/api/visitor")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Visitor Statistics", description = "방문자 통계 관련 API")
public class VisitorRestController {

    private final VisitorService visitorService;
    private final RestHelper restHelper;

    @GetMapping("/count")
    @Operation(
        summary = "방문자 통계 조회", 
        description = "최근 5년간 연도별, 최근 1년간 달별 방문자 수, 최근 1주일간 매일 방문자수 통계정보를 담아 리턴합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "방문자 통계 조회 성공"),
        @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public Map<String, Object> getVisitorCount() {
        VisitorStatsDto data = visitorService.getVisitorStats();
        return restHelper.sendJson(data);
    }
}
