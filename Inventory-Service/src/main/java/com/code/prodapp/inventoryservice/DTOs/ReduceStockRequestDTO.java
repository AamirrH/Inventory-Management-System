package com.code.prodapp.inventoryservice.DTOs;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReduceStockRequestDTO {

    private Long productId;
    private Integer quantity;




}
