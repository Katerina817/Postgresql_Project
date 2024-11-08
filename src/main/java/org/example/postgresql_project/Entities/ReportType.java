package org.example.postgresql_project.Entities;

import lombok.Data;


@Data
/*@Entity
@Table(name = "report_type")*/
public class ReportType {
    /*@Id
    @Column(name = "report_type_id", nullable = false, length = 36)*/
    private String reportTypeId;
    //@Column(name = "report_type_name", nullable = false, length = 40)
    private String reportTypeName;
}
