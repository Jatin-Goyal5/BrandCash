package com.demo.brandbacks.dto;

import lombok.Data;

@Data
public class OrderRequest {

    private String address;
    private String productId;
    private Long quantity;
    private double cashbackAmount;
}
