package com.dcauto.front_story_task.service.impl;

import com.dcauto.front_story_task.dto.ReportDto;
import com.dcauto.front_story_task.entities.CostReport;
import com.dcauto.front_story_task.entities.RevenueReport;
import com.dcauto.front_story_task.exception.DataNotFoundException;
import com.dcauto.front_story_task.exception.DataProcessingException;
import com.dcauto.front_story_task.repo.CostReportRepository;
import com.dcauto.front_story_task.repo.RevenueReportRepository;
import com.dcauto.front_story_task.service.ReportProcessor;
import com.dcauto.front_story_task.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {

    @Autowired
    private CostReportRepository costReportRepository;
    @Autowired
    private RevenueReportRepository revenueReportRepository;
    @Autowired
    private ReportProcessor reportProcessor;

    @Override
    public List<ReportDto> getAggregatedReport(LocalDate dateFrom, LocalDate dateTo) {
        LocalDateTime start = dateFrom.atStartOfDay();
        LocalDateTime end = dateTo.plusDays(1).atStartOfDay();

        List<CostReport> costReports = costReportRepository.findByTimestampBetween(start, end);
        List<RevenueReport> revenueReports = revenueReportRepository.findByTimestampBetween(start, end);

        if (costReports.isEmpty() && revenueReports.isEmpty()) {
            throw new DataNotFoundException("No data found for the given date range.");
        }

        try {
            return aggregateAndEnrichData(costReports, revenueReports);
        } catch (Exception ex) {
            throw new DataProcessingException("Error processing data: " + ex.getMessage());
        }
    }

    @Override
    public List<ReportDto> aggregateAndEnrichData(List<CostReport> costReports, List<RevenueReport> revenueReports) {
        Map<String, ReportDto> reportMap = new HashMap<>();

        reportProcessor.processCostReport(costReports, reportMap);
        reportProcessor.processRevenueReport(revenueReports, reportMap);

        for (ReportDto reportDto : reportMap.values()) {
            reportDto.setAvgCpc(calculateCPC(reportDto.getTotalCost(), reportDto.getTotalClicks()));
            reportDto.setTotalRoi(calculateROI(reportDto.getTotalRevenue(), reportDto.getTotalCost()));
            reportDto.setTotalProfit(calculateProfit(reportDto.getTotalRevenue(), reportDto.getTotalCost()));
            reportDto.setUv(calculateUV(reportDto.getTotalRevenue(), reportDto.getTotalClicks()));

        }

        return new ArrayList<>(reportMap.values());
    }

    @Override
    public BigDecimal calculateUV(BigDecimal revenue, Integer clicks) {
        return clicks != null && clicks != 0 ? revenue.divide(BigDecimal.valueOf(clicks), BigDecimal.ROUND_HALF_UP) : BigDecimal.ZERO;
    }

    @Override
    public BigDecimal calculateCPC(BigDecimal cost, Integer clicks) {
        return clicks != null && clicks != 0 ? cost.divide(BigDecimal.valueOf(clicks), BigDecimal.ROUND_HALF_UP) : BigDecimal.ZERO;
    }

    @Override
    public BigDecimal calculateROI(BigDecimal uv, BigDecimal cpc) {
        return cpc.compareTo(BigDecimal.ZERO) != 0 ? uv.divide(cpc, BigDecimal.ROUND_HALF_UP) : BigDecimal.ZERO;
    }

    @Override
    public BigDecimal calculateProfit(BigDecimal revenue, BigDecimal cost) {
        return revenue.subtract(cost);
    }

    @Override
    public LocalDateTime convertToUTC(LocalDateTime estDateTime) {
        try {
            ZonedDateTime estZonedDateTime = estDateTime.atZone(ZoneId.of("America/New_York"));
            return estZonedDateTime.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        } catch (Exception ex) {
            throw new DataProcessingException("Error converting time zone: " + ex.getMessage());
        }
    }
}
