package com.htsspl.ElectronicStore.service.impl;

import com.htsspl.ElectronicStore.dtos.PageableResponse;
import com.htsspl.ElectronicStore.dtos.ProductDto;
import com.htsspl.ElectronicStore.entities.Product;
import com.htsspl.ElectronicStore.exception.ResourceNotFoundException;
import com.htsspl.ElectronicStore.helper.Helper;
import com.htsspl.ElectronicStore.repository.ProductRepository;
import com.htsspl.ElectronicStore.service.ProductService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

public class ProductServiceImpl implements ProductService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ProductDto createProduct(ProductDto productDto) {
        logger.info("Initiating Dao request for create Product ProductDto ");
        Product product = modelMapper.map(productDto, Product.class);
        Product saveProduct = productRepository.save(product);
        ProductDto productDto1 = modelMapper.map(saveProduct, ProductDto.class);
        logger.info("Completed Dao request for create Product ProductDto ");
        return productDto1;
    }


    @Override
    public ProductDto updateProduct(Integer productId, ProductDto productDto) {
        logger.info("Initiating Dao request for update Product ProductId ");
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Resource not Found !!"));
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setQuantity(productDto.getQuantity());
        product.setAddedDate(productDto.getAddedDate());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());

        Product saveProduct = productRepository.save(product);

        ProductDto productDto1 = modelMapper.map(saveProduct, ProductDto.class);
        logger.info("Completed Dao request for update Product ProductId ");
        return productDto1;
    }

    @Override
    public void deleteProduct(Integer productId) {
        logger.info("Initiating Dao request for delete Product ProductId ");
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Resource not Found !!"));
        productRepository.delete(product);
        logger.info("Completed Dao request for delete Product ProductId ");
    }

    @Override
    public ProductDto getProductById(Integer productId) {
        logger.info("Initiating Dao request for get Product ProductId ");
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Resource not Found !!"));
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        logger.info("Completed Dao request for get Product ProductId ");
        return productDto;
    }

    @Override
    public PageableResponse<ProductDto> getAllProduct(Integer pageNumber, Integer pagSize, String sortBy, String sortDir) {
        logger.info("Initiating Dao request for get All Product ");
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();


        PageRequest pageRequest = PageRequest.of(pageNumber, pagSize, sort);

        Page<Product> page = productRepository.findAll(pageRequest);
        List<Product> content = page.getContent();
        List<ProductDto> productDtos = content.stream().map(pro -> modelMapper.map(pro, ProductDto.class)).collect(Collectors.toList());

        PageableResponse<ProductDto> pageableResponse = new PageableResponse();
        pageableResponse.setContent(productDtos);
        pageableResponse.setPageNumber(page.getNumber());
        pageableResponse.setPageSize(page.getSize());
        pageableResponse.setTotalPages(page.getTotalPages());
        pageableResponse.setTotalElement(page.getTotalElements());
        pageableResponse.setLastPage(page.isLast());
        logger.info("Completed Dao request for get All Product ");
        return pageableResponse;
    }

    @Override
    public PageableResponse<ProductDto> getAllLiveProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        logger.info("Initiating Dao request for get All live Product ");
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Product> page = productRepository.findByLiveTrue(pageable);

        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(page, ProductDto.class);
        logger.info("Completed Dao request for get All live Product ");
        return pageableResponse;
    }

    @Override
    public PageableResponse<ProductDto> seachByTitle(String subTitle, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        logger.info("Initiating Dao request for get All by Title ");
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Product> page = productRepository.findByTitleContaining(subTitle, pageable);

        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(page, ProductDto.class);
        logger.info("Completed Dao request for get All by Title ");
        return pageableResponse;

    }
}
