package com.dcauto.front_story_task.service;

import com.dcauto.front_story_task.entities.CostReport;
import com.dcauto.front_story_task.entities.RevenueReport;
import com.dcauto.front_story_task.dto.ReportDto;

import java.util.List;
import java.util.Map;

public interface ReportProcessor {
    void processCostReport(List<CostReport> costReports, Map<String, ReportDto> reportMap);
    void processRevenueReport(List<RevenueReport> revenueReports, Map<String, ReportDto> reportMap);
}
