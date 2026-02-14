package com.microservice.inventory_service.controller;


import com.microservice.inventory_service.dto.ProductDto;
import com.microservice.inventory_service.repository.ProductRepository;
import com.microservice.inventory_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        log.info("Received request to get all products");
        List<ProductDto> inventories = productService.getAllInventory();
        return ResponseEntity.ok(inventories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(Long id){
        log.info("Received request to get product with id: {}", id);
        ProductDto inventory = productService.getInventoryById(id);
        return ResponseEntity.ok(inventory);
    }

}
