package com.user.portal.util;

import com.user.portal.entity.BrandEntity;
import com.user.portal.entity.ProductEntity;
import com.user.portal.entity.QuantityEntity;
import com.user.portal.entity.SizeEntity;
import com.user.portal.model.BrandDto;
import com.user.portal.model.ProductDto;
import com.user.portal.model.QuantityDto;
import com.user.portal.model.SizeDto;

import lombok.experimental.UtilityClass;

/**
 * Maps dto to entity and vice versa
 */
@UtilityClass
public class ProductMapper {

	public ProductDto mapToProductDto(ProductEntity productEntity, ProductDto productDto) {

		productDto.setProductId(productEntity.getProductId());
		productDto.setProductName(productEntity.getProductName());
		productDto.setDescription(productEntity.getDescription());

		BrandEntity brandEntity = productEntity.getBrand();
		if (brandEntity != null) {
			BrandDto brandDto = new BrandDto();
			brandDto.setBrandId(brandEntity.getBrandId());
			brandDto.setBrandName(brandEntity.getBrandName());
			brandDto.setImageUrl(brandEntity.getImageUrl());
			productDto.setBrand(brandDto);

		}

		productDto.setPrice(productEntity.getPrice());
		productDto.setAgeGroup(productEntity.getAgeGroup());
		productDto.setCategory(productEntity.getCategory());
		productDto.setColour(productEntity.getColour());
		productDto.setCountry(productEntity.getCountry());
		productDto.setMaterial(productEntity.getMaterial());

		QuantityEntity quantityEntity = productEntity.getQuantity();
		if (quantityEntity != null) {
			QuantityDto quantityDto = new QuantityDto();
			quantityDto.setId(quantityEntity.getId());
			quantityDto.setQuantityAvailable(quantityEntity.getQuantityAvailable());
			quantityDto.setQuantityTotal(quantityEntity.getQuantityTotal());
			productDto.setQuantity(quantityDto);

		}

		SizeEntity sizeEntity = productEntity.getSize();
		if (sizeEntity != null) {
			SizeDto sizeDto = new SizeDto();
			sizeDto.setId(sizeEntity.getId());
			sizeDto.setSizeEU(sizeEntity.getSizeEU());
			sizeDto.setSizeUS(sizeEntity.getSizeUS());
			productDto.setSize(sizeDto);

		}

		productDto.setImageUrl(productEntity.getImageUrl());

		return productDto;
	}

	public static ProductEntity mapToProductEntity(ProductDto productDto, ProductEntity productEntity) {

		productEntity.setProductId(productDto.getProductId());
		productEntity.setProductName(productDto.getProductName());
		productEntity.setDescription(productDto.getDescription());

		BrandDto brandDto = productDto.getBrand();
		if (brandDto != null) {
			BrandEntity brandEntity = new BrandEntity();
			brandEntity.setBrandId(brandDto.getBrandId());
			brandEntity.setBrandName(brandDto.getBrandName());
			brandEntity.setImageUrl(brandDto.getImageUrl());
			productEntity.setBrand(brandEntity);

		}

		productEntity.setPrice(productDto.getPrice());
		productEntity.setAgeGroup(productDto.getAgeGroup());
		productEntity.setCategory(productDto.getCategory());
		productEntity.setColour(productDto.getColour());
		productEntity.setCountry(productDto.getCountry());
		productEntity.setMaterial(productDto.getMaterial());

		QuantityDto quantityDto = productDto.getQuantity();

		if (quantityDto != null) {
			QuantityEntity quantityEntity = new QuantityEntity();
			quantityEntity.setId(quantityDto.getId());
			quantityEntity.setQuantityAvailable(quantityDto.getQuantityAvailable());
			quantityEntity.setQuantityTotal(quantityDto.getQuantityTotal());
			productEntity.setQuantity(quantityEntity);
		}

		SizeDto sizeDto = productDto.getSize();
		if (sizeDto != null) {
			SizeEntity sizeEntity = new SizeEntity();
			sizeEntity.setId(sizeDto.getId());
			sizeEntity.setSizeEU(sizeDto.getSizeEU());
			sizeEntity.setSizeUS(sizeDto.getSizeUS());
			productEntity.setSize(sizeEntity);
		}

		productEntity.setImageUrl(productDto.getImageUrl());

		return productEntity;
	}

}
