package org.example.postgresql_project.Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportAndAdmin {
    private String reportId;
    private String content;
    private Date reportDate;
    private String name;
    private String surname;
}
