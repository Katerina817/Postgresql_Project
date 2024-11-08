package org.example.postgresql_project.Entities;

import lombok.Data;


@Data
/*@Entity
@Table(name = "recycling_rule")*/
public class RecyclingRule {
    /*@Id
    @Column(name = "rule_id", nullable = false, length = 36)*/
    private String ruleId;
    //@Column(name = "content", nullable = false, length=500)
    private String content;
}
