package com.htsspl.ElectronicStore.repository;

import com.htsspl.ElectronicStore.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository  extends JpaRepository<Product, Integer> {

    Page<Product> findByTitleContaining(String subTitle , Pageable pageable);

    Page<Product> findByLiveTrue(Pageable pageable);
}
