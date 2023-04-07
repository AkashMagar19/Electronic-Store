package com.htsspl.ElectronicStore.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity {

    @Column(name = "isactive_switch")
    private String isActive;

    @Column(name = "create_date" , updatable = false)
    @CreationTimestamp
    private LocalDateTime createdOn;

    @Column(name = "update_date" ,insertable = false )
    @UpdateTimestamp
    private LocalDateTime updatedOn;

}
