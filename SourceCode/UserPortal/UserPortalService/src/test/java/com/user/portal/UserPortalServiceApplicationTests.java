package com.user.portal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.user.portal.controller.UserPortalController;
import com.user.portal.exception.GlobalExceptionHandler;
import com.user.portal.model.ProductDto;
import com.user.portal.service.IProductInformationService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = { UserPortalController.class,
		GlobalExceptionHandler.class })
@AutoConfigureMockMvc
@EnableWebMvc
class UserPortalServiceApplicationTests {

	@MockBean
	private IProductInformationService productInformationService;

	@Autowired
	private MockMvc mvc;
	
	
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

}
