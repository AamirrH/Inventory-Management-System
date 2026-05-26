package com.code.prodapp.orderservice.DTOs;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReduceStockRequestDTO {

    private Long productId;
    private Integer quantity;




}
