package com.product.information;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.product.information.controller.ProductInformationController;
import com.product.information.exception.GlobalExceptionHandler;
import com.product.information.exception.ResourceNotFoundException;
import com.product.information.model.ProductDto;
import com.product.information.service.IProductInformationService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = { ProductInformationController.class,
		GlobalExceptionHandler.class })
@AutoConfigureMockMvc
@EnableWebMvc
class ProductInformationServiceApplicationTests {

	@MockBean
	private IProductInformationService productInformationService;

	@Autowired
	private MockMvc mvc;

	@Test
	void updateProductSuccessfully() throws Exception {
		Long productId = 1L;

		ProductDto productDto = TestUtil.getDummyUpdatedProductDto();

		Mockito.when(productInformationService.updateProduct(productId, productDto)).thenReturn(productDto);

		mvc.perform(put("/v1/product/{id}", productId).content(TestUtil.asJsonString(productDto))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.productName").value(productDto.getProductName()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.price").value(productDto.getPrice()));
	}

	@Test
	void testGetProductByIdSuccessfully() throws Exception {
		Long productId = 1L;
		ProductDto productDto = TestUtil.getProductDto();

		Mockito.when(productInformationService.getProductById(productId)).thenReturn(productDto);

		mvc.perform(get("/v1/product/{id}", productId).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.productName").value(productDto.getProductName()));
	}

	@Test
	void testGetProductByNameSuccessfully() throws Exception {
		String name = "iPhone9";
		ProductDto productDto = TestUtil.getProductDto();

		Mockito.when(productInformationService.getProductsByName(name)).thenReturn(List.of(productDto));

		mvc.perform(get("/v1/product/name/{name}", name).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.[0]").exists())
		.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
		.andExpect(MockMvcResultMatchers.jsonPath("$.[0].productName").value(productDto.getProductName()));
	}

	@Test
	void testGetAllProdcutsSuccessfully() throws Exception {

		ProductDto productDto = TestUtil.getProductDto();

		Mockito.when(productInformationService.getAllProducts()).thenReturn(List.of(productDto));

		mvc.perform(get("/v1/products").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0]").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].productName").value(productDto.getProductName()));
	}

//	@Test
//	void testUpdateProductThrowResourceNotFoundException() throws Exception {
//
//		Long productId = 100L;
//		
//		ProductDto productDto = TestUtil.getDummyUpdatedProductDto();
//
//		doThrow(new ResourceNotFoundException("Product", "Id", String.valueOf(productId))).when(productInformationService)
//		.updateProduct( eq(productId, any()));
//
//		mvc.perform(put("/v1/product/{id}", productId).content(TestUtil.asJsonString(productDto))
//				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().isNotFound())
//				.andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value("NOT_FOUND"));
//	}
//
//	@Test
//	void testDeleteProductThrowResourceNotFoundException() throws Exception {
//		Long productId = 100L;
//
//		doThrow(new ResourceNotFoundException("Product", "Id", String.valueOf(productId))).when(productInformationService)
//		.updateProduct( eq(productId, any()));
//		mvc.perform(delete("/v1/product/{id}", productId).accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().isNotFound())
//				.andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value("NOT_FOUND"));
//	}

	@Test
	void productDeleteSuccessfully() throws Exception {
		Long productId = 1L;
		mvc.perform(delete("/v1/product/{id}", productId).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

}
