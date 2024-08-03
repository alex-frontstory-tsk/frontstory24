package com.dcauto.front_story_task.controller;

import com.dcauto.front_story_task.dto.ReportDto;
import com.dcauto.front_story_task.exception.InvalidDateRangeException;
import com.dcauto.front_story_task.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/report")
    public List<ReportDto> getReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo) {
        if (dateFrom.isAfter(dateTo)) {
            throw new InvalidDateRangeException("The start date must be before the end date.");
        }

        return reportService.getAggregatedReport(dateFrom, dateTo);
    }
}