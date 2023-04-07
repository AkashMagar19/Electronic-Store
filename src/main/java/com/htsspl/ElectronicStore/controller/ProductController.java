package com.htsspl.ElectronicStore.controller;

import com.htsspl.ElectronicStore.dtos.PageableResponse;
import com.htsspl.ElectronicStore.dtos.ProductDto;
import com.htsspl.ElectronicStore.helper.AppResponse;
import com.htsspl.ElectronicStore.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    private static Logger logger = LoggerFactory.getLogger(ProductController.class);

    // create
    @PostMapping()
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        logger.info("Initiating service request for create product ProductDto {}:" + productDto);
        ProductDto productDto1 = productService.createProduct(productDto);
        logger.info("Completed service request for create product ProductDto {}:" + productDto);
        return new ResponseEntity<>(productDto1, HttpStatus.CREATED);

    }

    //update
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Integer productId, @RequestBody ProductDto productDto) {
        logger.info("Initiating service request for update product ProductId {}:" + productId);
        ProductDto productDto1 = productService.updateProduct(productId, productDto);
        logger.info("Completed service request for update product ProductId {}:" + productId);
        return new ResponseEntity<>(productDto1, HttpStatus.OK);

    }

    // delete

    @DeleteMapping("/{productId}")
    public ResponseEntity<AppResponse> deleteProduct(@PathVariable Integer productId) {
        logger.info("Initiating service request for delete product ProductId {}:" + productId);
        productService.deleteProduct(productId);

        AppResponse response = AppResponse.builder()
                .message("Resource Deleted Successfully !!").status(HttpStatus.OK).success(true).build();
        logger.info("Completed service request for delete product ProductId {}:" + productId);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    // get by id
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Integer productId) {
        logger.info("Initiating service request for get product ProductId {}:" + productId);
        ProductDto productDto = productService.getProductById(productId);
        logger.info("Completed service request for get product ProductId {}:" + productId);
        return new ResponseEntity<>(productDto, HttpStatus.FOUND);

    }


    // get All
    @GetMapping()
    public ResponseEntity<PageableResponse<ProductDto>> getAllProduct(
            @RequestParam(name = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        logger.info("Initiating service request for get All product {}:");
        PageableResponse<ProductDto> allProduct = productService.getAllProduct(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Completed service request for get All product {}:");
        return new ResponseEntity<>(allProduct, HttpStatus.FOUND);
    }

    //get by Live
    @GetMapping("/live")
    public ResponseEntity<PageableResponse<ProductDto>> getAllProductBYLive(
            @RequestParam(name = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {

        logger.info("Initiating service request for get All product by Live {}:");
        PageableResponse<ProductDto> allProduct = productService.getAllLiveProduct(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Completed service request for get All product by Live {}:");
        return new ResponseEntity<>(allProduct, HttpStatus.FOUND);
    }

    // bet by Containing Title
    @GetMapping("/search/{subTitle}")
    public ResponseEntity<PageableResponse<ProductDto>> searchByContainingTitle(
            @PathVariable String subTitle,
            @RequestParam(name = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        logger.info("Initiating service request for search All product by Title {}:");
        PageableResponse<ProductDto> allProduct = productService.seachByTitle(subTitle, pageNumber, pageSize, sortBy, sortDir);
        logger.info("Completed service request for search All product by Title {}:");
        return new ResponseEntity<>(allProduct, HttpStatus.FOUND);
    }
}
