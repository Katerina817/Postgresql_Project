package org.example.postgresql_project.Entities;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
/*@Entity
@Table(name = "recycling")*/
public class Recycling {
    /*@Id
    @Column(name = "recycling_id")*/
    private String recyclingId;
    /*@ManyToOne
    @JoinColumn(name = "recycling_status_id", referencedColumnName = "recycling_status_id", nullable = false)*/
    private String recyclingStatusId;
    /*@ManyToOne
    @JoinColumn(name = "rule_id", referencedColumnName = "rule_id", nullable = false)*/
    private String ruleId;
    /*@ManyToOne
    @JoinColumn(name = "trash_info_id", referencedColumnName = "trash_info_id", nullable = false),*/
    private String trashInfoId;
    /*@Column(name = "recycling_date", nullable = false)*/
    private Date recyclingDate;
}
