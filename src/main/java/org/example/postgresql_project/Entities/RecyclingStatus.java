package org.example.postgresql_project.Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
/*@Entity
@Table(name = "recycling_status")*/
public class RecyclingStatus {
    /*@Id
    @Column(name = "recycling_status_id", nullable = false, length = 36)*/
    private String recyclingStatusId;
    //@Column(name = "recycling_status_name", nullable = false, length = 40)
    private String recyclingStatusName;
    //@Column(name = "current_process_description", nullable = false, length = 400)
    private String currentProcessDescription;
}
