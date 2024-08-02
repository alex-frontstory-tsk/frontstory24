package com.dcauto.front_story_task.service;

import com.dcauto.front_story_task.dto.ReportDto;
import com.dcauto.front_story_task.entities.CostReport;
import com.dcauto.front_story_task.entities.RevenueReport;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ReportService {
    List<ReportDto> getAggregatedReport(LocalDate dateFrom, LocalDate dateTo);


    List<ReportDto> aggregateAndEnrichData(List<CostReport> costReports, List<RevenueReport> revenueReports);

    BigDecimal calculateUV(BigDecimal revenue, Integer clicks);

    BigDecimal calculateCPC(BigDecimal cost, Integer clicks);

    BigDecimal calculateROI(BigDecimal uv, BigDecimal cpc);

    BigDecimal calculateProfit(BigDecimal revenue, BigDecimal cost);

    LocalDateTime convertToUTC(LocalDateTime estDateTime);
}
