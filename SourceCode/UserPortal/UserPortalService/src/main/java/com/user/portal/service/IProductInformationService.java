package com.user.portal.service;

import java.util.List;

import com.user.portal.model.ProductDto;
import com.user.portal.model.ProductEvent;

/**
 * Interface declaring product info services
 */
public interface IProductInformationService {


	List<ProductDto> getAllProducts();

	ProductDto getProductById(Long id);

	List<ProductDto> getProductsByName(String name);
	
	void insertProduct(ProductDto product);
	
	void updateProduct(Long id, ProductDto product);
	
	void deleteProduct(Long id);
	
	void publishChanges(ProductEvent event);

}
