package com.microservice.inventory_service.controller;


import com.microservice.inventory_service.clients.OrdersFeignClient;
import com.microservice.inventory_service.dto.OrderRequestDto;
import com.microservice.inventory_service.dto.ProductDto;
import com.microservice.inventory_service.repository.ProductRepository;
import com.microservice.inventory_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/core")
public class ProductController {

    private final ProductService productService;
    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;
    private final OrdersFeignClient ordersFeignClient;

    @GetMapping("/fetchProducts")
    public ResponseEntity<String> fetchProducts() {
        // Returns 200 OK with a simple message
//        ServiceInstance serviceInstance = discoveryClient.getInstances("order-service")
//                .getFirst();
//
//        String response = restClient.get().uri(serviceInstance.getUri() + "/orders/core/helloOrders")
//                .retrieve()
//                .body(String.class);
        String response = ordersFeignClient.helloOrders();

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


    @PutMapping("reduce-stocks")
    public ResponseEntity<Double> reduceStocks(@RequestBody OrderRequestDto orderRequestDto) {
        Double totalPrice = productService.reduceStocks(orderRequestDto);
        return ResponseEntity.ok(totalPrice);
    }

}
