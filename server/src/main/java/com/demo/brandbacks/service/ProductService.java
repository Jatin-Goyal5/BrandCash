package com.demo.brandbacks.service;

import java.util.List;

import com.demo.brandbacks.dto.ProductDto;
import com.demo.brandbacks.model.Product;
import com.demo.brandbacks.security.UserPrincipal;

public interface ProductService {

	Product saveProduct(ProductDto productDto, UserPrincipal userDetails);

	List<Product> getProducts(UserPrincipal userDetails);
}
