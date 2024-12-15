package com.user.portal;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.user.portal.entity.ProductEntity;
import com.user.portal.model.ProductDto;
import com.user.portal.util.ProductMapper;

public class TestUtil {

	public static final String PRODUCT_INFO = """
			{
      "productName": "iPhone9",
      "description": "An apple mobile which is nothing like apple",
      "price": 549,
      "colour": "white",
      "country": "Sweden",
      "brand": {
      "brandName" :"Apple"
      
      },
      "category": "smartphones",
      "imageUrl": "https://i.dummyjson.com/data/products/1/1.jpg"
    }
			""";


	public static ProductDto getProductDto() throws JsonProcessingException {
		return buildObjectMapper().readValue(PRODUCT_INFO, ProductDto.class);
	}
	
	public static ProductEntity getDummyProductEntity() throws JsonProcessingException {
		
		
		ProductDto productDto =  buildObjectMapper().readValue(PRODUCT_INFO, ProductDto.class);
		ProductEntity productEntity = ProductMapper.mapToProductEntity(productDto, new ProductEntity());
		productEntity.setProductId(1L);
		return productEntity;
	
	}


	public static ProductDto getDummyUpdatedProductDto() throws JsonProcessingException {
		ProductDto productDto = buildObjectMapper().readValue(PRODUCT_INFO, ProductDto.class);
		productDto.setPrice(600);
		return productDto;
	}


	public static ObjectMapper buildObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		return mapper;
	}

	public static String asJsonString(final Object obj) {
		try {
			return buildObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
