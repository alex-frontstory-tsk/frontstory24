package com.dcauto.front_story_task.service.impl;

import com.dcauto.front_story_task.entities.CostReport;
import com.dcauto.front_story_task.entities.RevenueReport;
import com.dcauto.front_story_task.dto.ReportDto;
import com.dcauto.front_story_task.service.ReportProcessor;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ReportProcessorImpl implements ReportProcessor {

    @Override
    public void processCostReport(List<CostReport> costReports, Map<String, ReportDto> reportMap) {
        for (CostReport costReport : costReports) {
            LocalDateTime timestampUTC = convertToUTC(costReport.getTimestamp());
            LocalDate date = timestampUTC.toLocalDate();
            String campaignId = costReport.getCampaignId();
            String key = date.toString() + "_" + campaignId;

            ReportDto reportDto = reportMap.getOrDefault(key, new ReportDto());
            reportDto.setDate(date);
            reportDto.setCampaignId(campaignId);
            reportDto.setCampaignName(costReport.getCampaignName());
            reportDto.setTotalCost(reportDto.getTotalCost().add(costReport.getCost()));
            reportDto.setTotalClicks(reportDto.getTotalClicks() + costReport.getClicks());

            reportMap.put(key, reportDto);
        }
    }

    @Override
    public void processRevenueReport(List<RevenueReport> revenueReports, Map<String, ReportDto> reportMap) {
        for (RevenueReport revenueReport : revenueReports) {
            LocalDateTime timestampUTC = convertToUTC(revenueReport.getTimestamp());
            LocalDate date = timestampUTC.toLocalDate();
            String campaignId = revenueReport.getCampaignId();
            String key = date.toString() + "_" + campaignId;

            ReportDto reportDto = reportMap.getOrDefault(key, new ReportDto());
            reportDto.setDate(date);
            reportDto.setCampaignId(campaignId);
            reportDto.setCampaignName(revenueReport.getCampaignName());
            reportDto.setTotalRevenue(reportDto.getTotalRevenue().add(revenueReport.getRevenue()));
            reportDto.setTotalClicks(reportDto.getTotalClicks() + revenueReport.getClicks());

            reportMap.put(key, reportDto);
        }
    }

    private LocalDateTime convertToUTC(LocalDateTime estDateTime) {
        ZonedDateTime estZonedDateTime = estDateTime.atZone(ZoneId.of("America/New_York"));
        return estZonedDateTime.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
    }
}
