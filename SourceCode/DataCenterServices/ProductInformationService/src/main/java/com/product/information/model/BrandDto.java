package com.product.information.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Schema(
        name = "Brand",
        description = "Schema to hold brand information"
)
@Data
public class BrandDto {
	
	private long brandId;
	
	@NotEmpty(message = "Brand name can not be a null or empty")
	private String brandName;
	
	private String imageUrl;

}
