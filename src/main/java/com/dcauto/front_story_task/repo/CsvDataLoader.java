package com.dcauto.front_story_task.repo;

import com.dcauto.front_story_task.entities.CostReport;
import com.dcauto.front_story_task.entities.RevenueReport;
import jakarta.annotation.PostConstruct;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class CsvDataLoader {

    private static final String COST_URL = "https://s3.amazonaws.com/frontstory-test-data/server-side/cost_1.csv";
    private static final String REVENUE_URL = "https://s3.amazonaws.com/frontstory-test-data/server-side/revenue_1.csv";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("M/d/yy H:mm");

    @Autowired
    private CostReportRepository costReportRepository;

    @Autowired
    private RevenueReportRepository revenueReportRepository;

    @PostConstruct
    public void loadData() {
        loadCostData();
        loadRevenueData();
    }

    public void loadCostData() {
        String data = fetchData(COST_URL);
        try {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .parse(new StringReader(data));
            for (CSVRecord record : records) {
                CostReport costReport = new CostReport();
                costReport.setCampaignId(record.get("campaign_id"));
                costReport.setCampaignName(record.get("campaign_name"));
                costReport.setTimestamp(LocalDateTime.parse(record.get("data_date"), DATE_TIME_FORMATTER));
                costReport.setCost(new BigDecimal(record.get("cost")));
                costReport.setClicks(Integer.parseInt(record.get("clicks")));
                costReportRepository.save(costReport);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadRevenueData() {
        String data = fetchData(REVENUE_URL);
        try {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .parse(new StringReader(data));
            for (CSVRecord record : records) {
                RevenueReport revenueReport = new RevenueReport();
                revenueReport.setCampaignId(record.get("campaign_id"));
                revenueReport.setCampaignName(record.get("campaign_name"));
                revenueReport.setTimestamp(LocalDateTime.parse(record.get("data_date"), DATE_TIME_FORMATTER));
                revenueReport.setRevenue(new BigDecimal(record.get("revenue")));
                revenueReport.setClicks(Integer.parseInt(record.get("clicks")));
                revenueReportRepository.save(revenueReport);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String fetchData(String url) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }
}
