package com.dcauto.front_story_task.repo;

import com.dcauto.front_story_task.entities.CostReport;
import com.dcauto.front_story_task.entities.RevenueReport;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvDataLoader {

    @PostConstruct
    public void loadData() throws IOException {
        loadCostData();
        loadRevenueData();
    }

    private void loadCostData() throws IOException {
        List<CostReport> costReports = new ArrayList<>();
        try (Reader reader = new FileReader("path/to/cost_report.csv");
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                     .withHeader("timestamp", "campaign_id", "campaign_name", "clicks", "cost")
                     .withIgnoreHeaderCase()
                     .withTrim())) {

            for (CSVRecord csvRecord : csvParser) {
                CostReport costReport = new CostReport();
                costReport.setTimestamp(LocalDateTime.parse(csvRecord.get("timestamp")));
                costReport.setCampaignId(csvRecord.get("campaign_id"));
                costReport.setCampaignName(csvRecord.get("campaign_name"));
                costReport.setClicks(Integer.parseInt(csvRecord.get("clicks")));
                costReport.setCost(new BigDecimal(csvRecord.get("cost")));
                costReports.add(costReport);
            }
        }
    }

    private void loadRevenueData() throws IOException {
        List<RevenueReport> revenueReports = new ArrayList<>();
        try (Reader reader = new FileReader("path/to/revenue_report.csv");
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                     .withHeader("timestamp", "campaign_id", "campaign_name", "clicks", "revenue")
                     .withIgnoreHeaderCase()
                     .withTrim())) {

            for (CSVRecord csvRecord : csvParser) {
                RevenueReport revenueReport = new RevenueReport();
                revenueReport.setTimestamp(LocalDateTime.parse(csvRecord.get("timestamp")));
                revenueReport.setCampaignId(csvRecord.get("campaign_id"));
                revenueReport.setCampaignName(csvRecord.get("campaign_name"));
                revenueReport.setClicks(Integer.parseInt(csvRecord.get("clicks")));
                revenueReport.setRevenue(new BigDecimal(csvRecord.get("revenue")));
                revenueReports.add(revenueReport);
            }
        }
    }
}
