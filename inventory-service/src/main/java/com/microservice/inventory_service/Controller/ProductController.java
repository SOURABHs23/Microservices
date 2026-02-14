package com.microservice.inventory_service.controller;


import com.microservice.inventory_service.dto.ProductDto;
import com.microservice.inventory_service.repository.ProductRepository;
import com.microservice.inventory_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;

    @GetMapping("/fetchProducts")
    public ResponseEntity<String> fetchProducts() {
        // Returns 200 OK with a simple message
        ServiceInstance serviceInstance = discoveryClient.getInstances("order-service")
                .getFirst();

        String response = restClient.get().uri(serviceInstance.getUri() + "/api/v1/orders/helloOrders")
                .retrieve()
                .body(String.class);

        return ResponseEntity.ok(response);
    }

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
