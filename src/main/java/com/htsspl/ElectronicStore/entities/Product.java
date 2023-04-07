package com.htsspl.ElectronicStore.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    private String title;
    @Column(length = 10000)
    private String description;

    private  Integer price;

    private Integer discountedPrice;

    private Integer quantity;

    private Date addedDate;

    private boolean live;

    private boolean stock;





}
