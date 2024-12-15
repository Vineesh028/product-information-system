package com.user.portal.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Quantity
 */

@Schema(
        name = "Quantity",
        description = "Schema to hold quantity information"
)
@Data
public class QuantityDto {
	
  private long id;

  private Integer quantityTotal;

  private Integer quantityAvailable;


}

