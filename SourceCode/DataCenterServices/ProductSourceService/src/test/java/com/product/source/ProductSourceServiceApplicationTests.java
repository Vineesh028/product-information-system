package com.product.source;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.product.source.util.Constants;

@SpringBootTest
class ProductSourceServiceApplicationTests {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mvc;

	@BeforeEach
	public void setup() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	}

	@Test
	void testAddProductSuccessfully() throws Exception {

		mvc.perform(post("/v1/products").content(TestUtil.SINGLE_PRODUCT).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_201))
				.andExpect(MockMvcResultMatchers.jsonPath("$.statusMessage").value(Constants.MESSAGE_201));

	}

	@Test
	void testAddProductListSuccessfully() throws Exception {

		mvc.perform(post("/v1/products").content(TestUtil.PRODUCT_LIST).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_201))
				.andExpect(MockMvcResultMatchers.jsonPath("$.statusMessage").value(Constants.MESSAGE_201));

	}

	@Test
	void testInvalidInput() throws Exception {

		mvc.perform(post("/v1/products").content(TestUtil.INVALID_JSON).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isInternalServerError())
				.andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value("INTERNAL_SERVER_ERROR"));

	}

}
