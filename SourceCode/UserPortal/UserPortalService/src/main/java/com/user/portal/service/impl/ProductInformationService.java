package com.user.portal.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.user.portal.entity.ProductEntity;
import com.user.portal.exception.ResourceNotFoundException;
import com.user.portal.model.ProductDto;
import com.user.portal.model.ProductEvent;
import com.user.portal.model.TextMessage;
import com.user.portal.repository.ProductRepository;
import com.user.portal.service.IProductInformationService;
import com.user.portal.util.ProductMapper;

import lombok.AllArgsConstructor;

/**
 * Implementation for IProductInformationService
 */
@Service
@AllArgsConstructor
public class ProductInformationService implements IProductInformationService {

	private final ProductRepository productRepository;

	private final WebsocketService service;

	@Override
	public List<ProductDto> getAllProducts() {
		List<ProductEntity> productEntities = productRepository.findAll();
		return productEntities.stream().map(e -> ProductMapper.mapToProductDto(e, new ProductDto())).toList();
	}

	@Override
	public ProductDto getProductById(Long id) {
		ProductEntity productEntity = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "Id", String.valueOf(id)));
		return ProductMapper.mapToProductDto(productEntity, new ProductDto());
	}

	@Override
	public List<ProductDto> getProductsByName(String name) {
		List<ProductEntity> productEntities = productRepository.findByProductNameIgnoreCaseContaining(name);
		return productEntities.stream().map(e -> ProductMapper.mapToProductDto(e, new ProductDto())).toList();
	}

	@Override
	public void insertProduct(ProductDto product) {

		ProductEntity productEntity = ProductMapper.mapToProductEntity(product, new ProductEntity());

		productRepository.save(productEntity);

	}

	@Override
	public void updateProduct(Long id, ProductDto product) {

		updateProductDetails(id, product);

	}

	@Override
	public void deleteProduct(Long id) {
		productRepository.deleteById(id);

	}

	@Override
	public void publishChanges(ProductEvent event) {

//		ProductDto productDto = new ProductDto();
//		BeanUtils.copyProperties(event.getProduct(), productDto);
//
//		updateProductDetails(productDto.getProductId(), productDto);
		
		TextMessage message = new TextMessage();

		message.setMessage(event.getMessage());
		service.send(message);

	}

	private void updateProductDetails(Long id, ProductDto product) {

		if (productRepository.findById(id).isPresent()) {

			ProductEntity productEntity = productRepository.findById(id).get();

			ProductEntity updatedProduct = ProductMapper.mapToProductEntity(product, productEntity);

			productRepository.save(updatedProduct);
		}

	}

}
