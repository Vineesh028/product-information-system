package com.product.information.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.product.information.model.ProductDto;
import com.product.information.model.ProductEventModel;

import jakarta.validation.Valid;

/**
 * Interface declaring product info services
 */
public interface IProductInformationService {

	void deleteProductById(Long id) throws InterruptedException, ExecutionException;

	List<ProductDto> getAllProducts();

	ProductDto getProductById(Long id);

	List<ProductDto> getProductsByName(String name);

	void publishProductEvent(@Valid ProductEventModel productEvent) throws InterruptedException, ExecutionException ;

	ProductDto updateProduct(Long id, @Valid ProductDto product) throws InterruptedException, ExecutionException;

}
