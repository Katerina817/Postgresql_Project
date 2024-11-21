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
@Table(name = "trash_info")*/
public class TrashInfo {
    /*@Id
    @Column(name = "trash_info_id", nullable = false, length = 36)*/
    private String trashInfoId;
    /*@ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)*/
    private String userId;
    /*@ManyToOne
    @JoinColumn(name = "trash_type_id", referencedColumnName = "trash_type_id", nullable = false)*/
    private String trashTypeId;
    //@Column(name = "trash_quantity", nullable = false)
    private Integer trashQuantity;
}
