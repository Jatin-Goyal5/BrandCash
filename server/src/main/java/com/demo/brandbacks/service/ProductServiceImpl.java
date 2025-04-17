package com.demo.brandbacks.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.brandbacks.dto.ProductDto;
import com.demo.brandbacks.model.Brand;
import com.demo.brandbacks.model.Product;
import com.demo.brandbacks.repository.BrandRepository;
import com.demo.brandbacks.repository.ProductRepository;
import com.demo.brandbacks.security.UserPrincipal;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepo;

	@Autowired
	BrandRepository brandRepository;

	@Override
	@Transactional
	public Product saveProduct(ProductDto productDto, UserPrincipal userDetails) {
		// TODO Auto-generated method stub
		Brand brand = brandRepository.findByUserId(userDetails.getId());
		Product product = new Product();
		product.setDescription(productDto.getDescription());
		product.setName(productDto.getTitle());
		product.setPrice(productDto.getPrice());
		product.setCreatedAt(Instant.now());
		product.setUpdatedAt(Instant.now());
		if (brand.getProducts() == null) {
			brand.setProducts(new ArrayList<>());
		}
		productRepo.save(product);
		brand.getProducts().add(product);
		brandRepository.save(brand);

		return product;
	}
	
	public List<Product> getProducts(UserPrincipal userDetails){
		Brand brand = brandRepository.findByUserId(userDetails.getId());
		return brand.getProducts();
	}

}
