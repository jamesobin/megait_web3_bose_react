package com.clonebose.bose.controllers;
import com.clonebose.bose.helpers.RestHelper;
import com.clonebose.bose.models.VisitorStatsDto;
import com.clonebose.bose.services.VisitorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/visitor")
@Slf4j
@RequiredArgsConstructor
public class VisitorRestController {

    private final VisitorService visitorService;
    private final RestHelper restHelper;

    @GetMapping("/count")
    public Map<String, Object> getVisitorCount() {
        VisitorStatsDto data = visitorService.getVisitorStats();
        return restHelper.sendJson(data);
    }
}
