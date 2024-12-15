package com.product.information.util;

import org.springframework.beans.BeanUtils;

import com.product.information.entity.ProductEntity;
import com.product.information.model.ProductDto;

import lombok.experimental.UtilityClass;


/**
 * Maps dto to entity and vice versa
 */
@UtilityClass
public class ProductMapper {
	
	public  ProductDto mapToProductDto(ProductEntity productEntity, ProductDto productDto) {
		
		BeanUtils.copyProperties(productEntity, productDto);

		return productDto;
	}

	public static ProductEntity mapToProductEntity(ProductDto productDto, ProductEntity productEntity) {
		
		BeanUtils.copyProperties(productDto, productEntity);

		return productEntity;
	}


}
