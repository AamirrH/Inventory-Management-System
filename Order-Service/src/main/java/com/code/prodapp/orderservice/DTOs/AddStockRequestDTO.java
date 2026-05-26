package com.code.prodapp.orderservice.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddStockRequestDTO {

    private Long productId;
    private Integer quantity;

}
