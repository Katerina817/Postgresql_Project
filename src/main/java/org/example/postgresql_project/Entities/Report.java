package org.example.postgresql_project.Entities;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;

@Data
/*@Entity
@Table(name = "report")*/
public class Report {
    /*@Id
    @Column(name = "report_id")*/
    private String reportId;
    /*@ManyToOne
    @JoinColumn(name = "report_type_id", referencedColumnName = "report_type_id", nullable = false)*/
    private String reportTypeId;
    /*@ManyToOne
    @JoinColumn(name = "admin_id", referencedColumnName = "admin_id", nullable = false)*/
    private String adminId;
    //@Column(name = "content", nullable = false, length=500)
    private String content;
    //@Column(name = "report_date", nullable = false)
    private Date reportDate;
    /*@ManyToOne
    @JoinColumn(name = "recycling_id", referencedColumnName = "recycling_id", nullable = false)*/
    private String recyclingId;
}
