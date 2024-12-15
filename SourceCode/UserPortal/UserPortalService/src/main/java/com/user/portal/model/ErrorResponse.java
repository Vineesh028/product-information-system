package com.user.portal.model;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Schema to hold error response information
 */
@Schema(
        name = "ErrorResponse",
        description = "Schema to hold error response information"
)
@Data
@AllArgsConstructor
public class ErrorResponse {
	
	private String apiPath;
	
	private HttpStatus errorCode;
	
	private String errorMessage;
	
	private LocalDateTime errorTime;
	
	

}