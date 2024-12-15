package com.product.information.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(
        name = "ProductEvent",
        description = "Schema to product event information"
)
@Data
public class ProductEventModel {
	
	private String eventType;
	
	private String message;
	
	private Long productId;

}
