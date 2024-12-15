package com.product.information.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(
        name = "Quantity",
        description = "Schema to hold quantity information"
)
@Data
public class QuantityDto {
	
	private long id;
	
	private int quantityTotal;
	
    private int   quantityAvailable;

}
