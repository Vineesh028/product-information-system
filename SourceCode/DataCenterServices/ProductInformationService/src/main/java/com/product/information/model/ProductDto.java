package com.product.information.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Schema(
        name = "Product",
        description = "Schema to hold product information"
)
@Data
public class ProductDto {
	
	private long productId;
	
	@NotEmpty(message = "Product name can not be a null or empty")
	private String productName;
	
	private String description;
	
	@NotNull(message = "Brand can not be a null")
	private BrandDto brand;
	
	private String material;
	
	private SizeDto size;
	
	private String colour;
	
	private String ageGroup;
	
	private String category;
	
	private String imageUrl;
	
	@NotNull(message = "Price can not be a null")
	@Positive(message = "Price must be greater than zero.")
	private float price;
	
	private String country;

	private QuantityDto quantity;

}
