package com.htsspl.ElectronicStore.repository;

import com.htsspl.ElectronicStore.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<List<Category>> findByTitleContaining(String keyword);
}
