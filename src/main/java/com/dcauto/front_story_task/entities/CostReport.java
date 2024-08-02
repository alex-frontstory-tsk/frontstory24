package com.dcauto.front_story_task.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "cost_report")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CostReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String campaignId;
    private String campaignName;
    private LocalDateTime timestamp;
    private BigDecimal cost;
    private Integer clicks;

}

