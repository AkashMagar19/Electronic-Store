package com.htsspl.ElectronicStore.service;

import com.htsspl.ElectronicStore.dtos.CategoryDto;
import com.htsspl.ElectronicStore.dtos.PageableResponse;

import java.util.List;

public interface CategoryService {

    // Create
    CategoryDto create(CategoryDto categoryDto);

    //Update
    CategoryDto update (CategoryDto categoryDto,Long categoryId);

    //Delete
    void delete (Long CategoryId);

    //Get All
    PageableResponse<CategoryDto>getAll(int pageNumber,int pageSize,String sortBy,String sortDir);

    //Get Single Category Detail
    CategoryDto get (Long categoryId);
    List<CategoryDto> searching(String keyword);
}
