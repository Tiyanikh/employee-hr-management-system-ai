package com.portfolio.hr.controller;

import com.portfolio.hr.service.HrAssistantService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/assistant")
public class HrAssistantController {
    private final HrAssistantService assistant;

    public HrAssistantController(HrAssistantService assistant) {
        this.assistant = assistant;
    }

    @PostMapping("/ask")
    public Map<String, Object> ask(@RequestBody Map<String, String> request) {
        return assistant.answer(request.get("question"));
    }

    @GetMapping("/insights")
    public Map<String, Object> insights() {
        return assistant.insights();
    }
}
