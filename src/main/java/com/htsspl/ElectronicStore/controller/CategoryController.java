package com.htsspl.ElectronicStore.controller;

import com.htsspl.ElectronicStore.dtos.ApiResponseMessage;
import com.htsspl.ElectronicStore.dtos.CategoryDto;
import com.htsspl.ElectronicStore.dtos.PageableResponse;
import com.htsspl.ElectronicStore.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private static Logger logger = LoggerFactory.getLogger(CategoryController.class);
    @Autowired
    private CategoryService categoryService;

    //create
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        logger.info("Initiating Service request for create Category CategoryDto");
        // Call Service To Get object
        CategoryDto categoryDto1 = categoryService.create(categoryDto);
        logger.info("Completed Service request for create Category CategoryDto ");
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }

    //Update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable("categoryId") Long categoryId) {
        logger.info("Initiating Service request for update Category CategoryDto & categoryId");
        CategoryDto updatedCategory = categoryService.update(categoryDto, categoryId);
        logger.info("Completed Service request for update Category CategoryDto & categoryId ");
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable Long categoryId) {
        logger.info("Initiating Service request for delete Category categoryId");
        categoryService.delete(categoryId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder().message("Category Deleted Successfully")
                .status(HttpStatus.OK).success(true).build();
        logger.info("Completed Service request for delete Category categoryId");
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    //Get All
    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>> getAll(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10 ", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        logger.info("Initiating Service request for GetAll Category {}:" + "PageNumber:" + pageNumber,
                "PageSize:" + pageSize, "SortBy:" + sortBy, "SortDirection:" + sortDir);
        PageableResponse<CategoryDto> pageableResponse = categoryService.getAll(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Completed Service request for GetAll Category {}:");
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
    }

    // Get Single
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getSingle(@PathVariable Long categoryId) {
        logger.info("Initiating Service request for get Category ById categoryId ");
        CategoryDto categoryDto = categoryService.get(categoryId);
        logger.info("Completed Service request for get Category ById categoryId ");
        return new ResponseEntity<>(categoryDto, HttpStatus.FOUND);
        //return ResponseEntity<>(categoryDto,HttpStatus.FOUND);
    }

    //Search By Title
    @GetMapping("/keyword/{keyword}")
    public ResponseEntity<List<CategoryDto>> serchByTitle(@PathVariable String keyword) {
        logger.info("Initiating Service request for get Category By Title Title {}:" + keyword);
        List<CategoryDto> categoryDtos = categoryService.searching(keyword);
        logger.info("Completed Service request for get Category By Title Title {}:" + keyword);
        return new ResponseEntity<>(categoryDtos, HttpStatus.FOUND);

    }
}