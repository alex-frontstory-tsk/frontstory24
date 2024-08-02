package com.dcauto.front_story_task.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDto {
    private LocalDate date;
    private String campaignId;
    private String campaignName;
    private BigDecimal totalRevenue = BigDecimal.ZERO;
    private BigDecimal totalCost = BigDecimal.ZERO;
    private BigDecimal totalProfit = BigDecimal.ZERO;
    private Integer totalClicks = 0;
    private BigDecimal totalRoi = BigDecimal.ZERO;
    private BigDecimal avgCpc = BigDecimal.ZERO;
    private BigDecimal uv = BigDecimal.ZERO;
    private BigDecimal hourlyAvgRevenue = BigDecimal.ZERO;
    private Integer positiveProfitHours = 0;
}

