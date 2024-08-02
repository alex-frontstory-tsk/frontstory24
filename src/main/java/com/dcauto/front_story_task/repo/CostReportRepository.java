package com.dcauto.front_story_task.repo;

import com.dcauto.front_story_task.entities.CostReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CostReportRepository extends JpaRepository<CostReport, Long> {
    List<CostReport> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
}
