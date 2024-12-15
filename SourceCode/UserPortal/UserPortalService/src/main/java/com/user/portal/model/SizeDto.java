package com.user.portal.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Size
 */
@Schema(
        name = "Size",
        description = "Schema to size quantity information"
)
@Data
public class SizeDto {

	private long id;
	
	private String sizeUS;
	
    private String sizeEU;

  
}

