package org.example.postgresql_project.Entities;

import lombok.Data;

@Data
/*@Entity
@Table(name = "trash_type")*/
public class TrashType {
   /* @Id
    @Column(name = "trash_type_id", nullable = false, length = 36)*/
    private String trashTypeId;
    //@Column(name = "trash_type_name", nullable = false, length = 60)
    private String trashTypeName;
}