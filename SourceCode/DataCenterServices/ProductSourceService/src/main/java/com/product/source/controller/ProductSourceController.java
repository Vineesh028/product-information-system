package com.product.source.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.product.source.model.ErrorResponse;
import com.product.source.model.Response;
import com.product.source.service.IProductSourceService;
import com.product.source.util.Constants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "Product", description = "the Product API")
@Controller
@AllArgsConstructor
public class ProductSourceController {

	private final IProductSourceService productSourceService;


	/**
	 * POST /v1/products : Insert list of products
	 *
	 * @param body (required)
	 * @return Created (status code 201) or HTTP Status Internal Server Error
	 *         (status code 500)
	 */
	@Operation(operationId = "postProducts", summary = "Insert list of products", tags = { "Product" }, responses = {
			@ApiResponse(responseCode = "201", description = "Created", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }),
			@ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }) })
	@RequestMapping(method = RequestMethod.POST, value = "/v1/products", produces = { "application/json" }, consumes = {
			"application/json" })
	public ResponseEntity<Response> postProducts(@RequestBody String products) {
		
		productSourceService.addProducts(products);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new Response(Constants.STATUS_201, Constants.MESSAGE_201));

	}

}
