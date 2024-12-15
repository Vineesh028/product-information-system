package com.user.portal.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Schema to hold product information
 */

@Schema(name = "ProductDto", description = "Schema to hold product dto information")
@Data
public class ProductEvent {
	
	private String eventType;
	
	private String message;
	
	private ProductDto product;

}
