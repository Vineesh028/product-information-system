package com.user.portal.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.user.portal.model.ErrorResponse;
import com.user.portal.model.ProductDto;
import com.user.portal.service.IProductInformationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Validated
@Tag(name = "User", description = "The User API")
@AllArgsConstructor
@RestController
public class UserPortalController {

	private final IProductInformationService productInformationService;

	/**
	 * GET /v1/products : Get all products
	 *
	 * @return successful operation (status code 200) or Invalid ID supplied (status
	 *         code 400) or Not found (status code 404)
	 */
	@Operation(operationId = "getAllProducts", summary = "Get all products", tags = { "Product" }, responses = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "Not found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }) })
	@RequestMapping(method = RequestMethod.GET, value = "/v1/products", produces = { "application/json" })
	public ResponseEntity<List<ProductDto>> getAllProducts() {

		List<ProductDto> productList = productInformationService.getAllProducts();

		return ResponseEntity.status(HttpStatus.OK).body(productList);

	}

	/**
	 * GET /v1/product/{id} : Get product by id
	 *
	 * @param id Product Id (required)
	 * @return successful operation (status code 200) or Invalid ID supplied (status
	 *         code 400) or Not found (status code 404) or HTTP Status Internal
	 *         Server Error (status code 500)
	 */
	@Operation(operationId = "getProductById", summary = "Get product by id", tags = { "Product" }, responses = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "Not found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
			@ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }) })
	@RequestMapping(method = RequestMethod.GET, value = "/v1/product/{id}", produces = { "application/json" })
	public ResponseEntity<ProductDto> getProductById(
			@Parameter(name = "id", description = "Product Id", required = true) @PathVariable("id") Long id) {

		ProductDto product = productInformationService.getProductById(id);
		return ResponseEntity.status(HttpStatus.OK).body(product);

	}

	/**
	 * GET /v1/product/name/{name} : Get all products by name
	 *
	 * @param name Product name (required)
	 * @return successful operation (status code 200) or Invalid ID supplied (status
	 *         code 400) or Not found (status code 404)
	 */
	@Operation(operationId = "getProductsByName", summary = "Get all products by name", tags = {
			"Product" }, responses = {
					@ApiResponse(responseCode = "200", description = "successful operation", content = {
							@Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class)) }),
					@ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = {
							@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
					@ApiResponse(responseCode = "404", description = "Not found", content = {
							@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }) })
	@RequestMapping(method = RequestMethod.GET, value = "/v1/product/name/{name}", produces = { "application/json" })
	public ResponseEntity<List<ProductDto>> getProductsByName(
			@Parameter(name = "name", description = "Product name", required = true) @PathVariable("name") String name) {

		List<ProductDto> products = productInformationService.getProductsByName(name);
		return ResponseEntity.status(HttpStatus.OK).body(products);

	}

}
