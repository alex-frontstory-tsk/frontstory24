package com.dcauto.front_story_task.repo;

import com.dcauto.front_story_task.entities.RevenueReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RevenueReportRepository extends JpaRepository<RevenueReport, Long> {
    List<RevenueReport> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
}

