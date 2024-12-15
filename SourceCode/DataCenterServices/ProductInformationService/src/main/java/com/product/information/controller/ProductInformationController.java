package com.product.information.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.product.information.model.ErrorResponse;
import com.product.information.model.ProductDto;
import com.product.information.model.ProductEventModel;
import com.product.information.model.Response;
import com.product.information.service.IProductInformationService;
import com.product.information.util.Constants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Validated
@Tag(name = "Product", description = "The Product Information API")
@AllArgsConstructor
@RestController
public class ProductInformationController {

	private final IProductInformationService productInformationService;

	/**
	 * DELETE /v1/product/{id} : Delete product by id
	 *
	 * @param id Product Id (required)
	 * @return HTTP Status OK (status code 200) or Invalid Id (status code 400) or
	 *         Not found (status code 404) or HTTP Status Internal Server Error
	 *         (status code 500)
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	@Operation(operationId = "deleteProductById", summary = "Delete product by id", tags = { "Product" }, responses = {
			@ApiResponse(responseCode = "200", description = "HTTP Status OK", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid Id", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "Not found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
			@ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }) })
	@RequestMapping(method = RequestMethod.DELETE, value = "/v1/product/{id}", produces = { "application/json" })

	public ResponseEntity<Response> deleteProductById(
			@Parameter(name = "id", description = "Product Id", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id) throws InterruptedException, ExecutionException {

		productInformationService.deleteProductById(id);
		
		return ResponseEntity.status(HttpStatus.OK).body(new Response(Constants.MESSAGE_200, Constants.DELETE_MESSAGE));

	}

	/**
	 * GET /v1/products : Get all products
	 *
	 * @return successful operation (status code 200) or Invalid ID supplied (status
	 *         code 400) or Not found (status code 404)
	 */
	@Operation(operationId = "getAllProducts", summary = "Get all products", tags = { "Product" }, responses = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = {
					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ProductDto.class))) }),
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
			@Parameter(name = "id", description = "Product Id", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id) {

		ProductDto product = productInformationService.getProductById(id);
		return ResponseEntity.status(HttpStatus.OK).body(product);

	}

	/**
	 * GET /v1/product/{name} : Get all products by name
	 *
	 * @param name Product name (required)
	 * @return successful operation (status code 200) or Invalid ID supplied (status
	 *         code 400) or Not found (status code 404)
	 */
	@Operation(operationId = "getProductsByName", summary = "Get all products by name", tags = {
			"Product" }, responses = {
					@ApiResponse(responseCode = "200", description = "successful operation", content = {
							@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ProductDto.class))) }),
					@ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = {
							@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
					@ApiResponse(responseCode = "404", description = "Not found", content = {
							@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }) })
	@RequestMapping(method = RequestMethod.GET, value = "/v1/product/name/{name}", produces = { "application/json" })

	public ResponseEntity<List<ProductDto>> getProductsByName(
			@Parameter(name = "name", description = "Product name", required = true, in = ParameterIn.PATH) @PathVariable("name") String name) {

		List<ProductDto> products = productInformationService.getProductsByName(name);
		return ResponseEntity.status(HttpStatus.OK).body(products);

	}

	/**
	 * PUT /v1/product/{id} : Update product information
	 *
	 * @param id      Product Id (required)
	 * @param product (required)
	 * @return successful operation (status code 200) or Invalid ID supplied (status
	 *         code 400) or Not found (status code 404) or HTTP Status Internal
	 *         Server Error (status code 500)
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	@Operation(operationId = "updateProduct", summary = "Update product information", tags = {
			"Product" }, responses = {
					@ApiResponse(responseCode = "200", description = "successful operation", content = {
							@Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class)) }),
					@ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = {
							@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
					@ApiResponse(responseCode = "404", description = "Not found", content = {
							@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
					@ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = {
							@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }) })
	@RequestMapping(method = RequestMethod.PUT, value = "/v1/product/{id}", produces = {
			"application/json" }, consumes = { "application/json" })

	public ResponseEntity<ProductDto> updateProduct(
			@Parameter(name = "id", description = "Product Id", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id,
			@Parameter(name = "Product", description = "", required = true) @Valid @RequestBody ProductDto product) throws InterruptedException, ExecutionException {

		ProductDto updatedProduct = productInformationService.updateProduct(id, product);

		return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);

	}

	/**
	 * POST /v1/product/publish : Publish a product event
	 *
	 * @param productEvent (required)
	 * @return HTTP Status CREATED (status code 201) or HTTP Status Internal Server
	 *         Error (status code 500)
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	@Operation(operationId = "publishProductEvent", summary = "Publish a product event", tags = {
			"Product" }, responses = {
					@ApiResponse(responseCode = "201", description = "HTTP Status CREATED", content = {
							@Content(mediaType = "application/json", schema = @Schema(implementation = ProductEventModel.class)) }),
					@ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = {
							@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }) })
	@RequestMapping(method = RequestMethod.POST, value = "/v1/product/publish", produces = {
			"application/json" }, consumes = { "application/json" })

	public ResponseEntity<Response> publishProductEvent(
			@Parameter(name = "ProductEvent", description = "", required = true) @Valid @RequestBody ProductEventModel productEvent) throws InterruptedException, ExecutionException {

		productInformationService.publishProductEvent(productEvent);

		return ResponseEntity.status(HttpStatus.OK).body(new Response(Constants.MESSAGE_200, Constants.UPDATE_MESSAGE));

	}

}
