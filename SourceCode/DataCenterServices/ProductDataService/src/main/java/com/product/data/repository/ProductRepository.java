package com.product.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.product.data.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>{
	
	Optional<ProductEntity> findByProductName(String productName);

}
