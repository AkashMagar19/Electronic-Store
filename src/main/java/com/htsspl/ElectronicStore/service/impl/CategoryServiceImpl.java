package com.htsspl.ElectronicStore.service.impl;

import com.htsspl.ElectronicStore.dtos.CategoryDto;
import com.htsspl.ElectronicStore.dtos.PageableResponse;
import com.htsspl.ElectronicStore.entities.Category;
import com.htsspl.ElectronicStore.exception.ResourceNotFoundException;
import com.htsspl.ElectronicStore.helper.Helper;
import com.htsspl.ElectronicStore.repository.CategoryRepository;
import com.htsspl.ElectronicStore.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        logger.info("Initiating Dao request for create Category CategoryDto");
        Category category = modelMapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepository.save(category);
        logger.info("Completed Dao request for create Category CategoryDto");
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, Long categoryId) {
        logger.info("Initiating Dao request for update Category CategoryDto & CategoryId");
        //Get Category Of Given Id
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category Not Founfd Exception  !!"));
        // Update category Details
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        Category updatedCategory = categoryRepository.save(category);
        logger.info("Completed Dao request for update Category CategoryDto & CategoryId");
        return modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public void delete(Long categoryId) {
        logger.info("Initiating Dao request for delete Category CategoryId");
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category Not Founfd Exception  !!"));
        categoryRepository.delete(category);
        logger.info("Completed Dao request for delete Category CategoryId");
    }

    @Override
    public PageableResponse<CategoryDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir) {
        logger.info("Initiating Dao request for Get All Category ");

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) :( Sort.by(sortBy).ascending());

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Category> page = categoryRepository.findAll(pageable);
        PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(page,CategoryDto.class);
        logger.info("Completed Dao request for Get All Category");
        return pageableResponse;
    }

    @Override
    public CategoryDto get(Long categoryId) {
        logger.info("Initiating Dao request for Get Category BYId CategoryId");
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category Not Founfd Exception  !!"));
        logger.info("Completed Dao request for Get Category BYId CategoryId");
        return modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> searching(String keyword) {
        logger.info("Initiating Dao request for Search Category by Title");
        List<Category> categories = categoryRepository.findByTitleContaining(keyword).orElseThrow(() -> new ResourceNotFoundException("Category with this title not Found!!"));
        List<CategoryDto> categoryDtos = categories.stream().map((category) -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
        logger.info("Completed Dao request for Search Category by Title");
        return categoryDtos;
    }
}
