package com.code.prodapp.inventoryservice.controllers;


import com.code.prodapp.inventoryservice.DTOs.ProductDTO;
import com.code.prodapp.inventoryservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
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
    public String fetchProducts() {
        List<String> services = discoveryClient.getServices();
        String orderServiceId = services.stream()
                .filter(serviceId -> serviceId.equalsIgnoreCase("ORDER-SERVICE"))
                .findFirst()
                .orElse("ORDER-SERVICE");

        List<ServiceInstance> orderServices = discoveryClient.getInstances(orderServiceId);
        if (orderServices.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.SERVICE_UNAVAILABLE,
                    "ORDER-SERVICE is not visible to Inventory-Service. Discovered services: " + services
            );
        }

        ServiceInstance orderService = orderServices.getFirst();
        String response = restClient.get()
                .uri(orderService.getUri()+"/api/v1/orders/testOrders")
                .retrieve()
                .body(String.class);
        return response;
    }

    @GetMapping("/discovered-services")
    public List<String> getDiscoveredServices() {
        return discoveryClient.getServices();
    }



    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllInventory());
    }

    @PostMapping("/{ID}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable(name = "ID") Long id){
        return ResponseEntity.ok(productService.getProductById(id));
    }





}
