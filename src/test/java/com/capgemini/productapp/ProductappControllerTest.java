package com.capgemini.productapp;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.capgemini.productapp.controller.ProductController;
import com.capgemini.productapp.entity.Product;
import com.capgemini.productapp.service.ProductService;

@RunWith(SpringRunner.class)
@SpringBootTest

public class ProductappControllerTest {
	
	@InjectMocks
	private ProductController productController;
	
	@Mock
	public ProductService productService;
	private MockMvc mockMvc;
	
	Product product;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
		product = new Product();
		product.setProductId(15);
		product.setProductName("MOTO");
		product.setProductCategory("Mobile");
		product.setProductPrice(20000);
	}
	
	@Test
	public void testaddproduct() throws Exception{
		
		
		when(productService.addProduct(Mockito.isA(Product.class))).thenReturn(product);
		
		mockMvc.perform(post("/product/")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				 .content("{ \"productId\": \"15\", \"productName\" : \"MOTO\", \"productCategory\" : \"Mobile\",\"productPrice\":\"20000\" }")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.productId"). exists())
				.andExpect(jsonPath("$.productName").exists())
				.andExpect(jsonPath("$.productCategory").exists())
				.andExpect(jsonPath("$.productPrice").exists())
				.andDo(print());
	}
	
	@Test
	public void testupdateproduct() throws Exception{
		
		when(productService.findProductById(15)).thenReturn(product);
				product.setProductName("MOTO G4 Plus");
				product.setProductPrice(25000);
		when(productService.updateProduct(Mockito.isA(Product.class))).thenReturn(product);
		
		mockMvc.perform(put("/product")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{ \"productId\": \"15\", \"productName\" : \"MOTO G4 Plus\", \"productCategory\" : \"Mobile\",\"productPrice\":\"25000\" }")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.productName").value("MOTO G4 Plus"))
		.andExpect(jsonPath("$.productCategory").value("Mobile"))
		.andExpect(jsonPath("$.productPrice").value(25000))
		.andDo(print());
		
	}
			@Test
			public void testfindProductById() throws Exception{
				
				when(productService.findProductById(15)).thenReturn(product);
				
				mockMvc.perform(get("/products/15")
						
						.accept(MediaType.APPLICATION_JSON))
						.andExpect(jsonPath("$.productId"). exists())
						.andExpect(jsonPath("$.productName").exists())
						.andExpect(jsonPath("$.productCategory").exists())
						.andExpect(jsonPath("$.productPrice").exists())
						
						
						.andExpect(jsonPath("$.productId").value(15))
						.andExpect(jsonPath("$.productName").value("MOTO"))
						.andExpect(jsonPath("$.productCategory").value("Mobile"))
						.andExpect(jsonPath("$.productPrice").value(20000))
						.andDo(print());
						
						
	}
			@Test
			public void testdeleteproduct() throws Exception{
			
				when(productService.findProductById(15)).thenReturn(product);
				
				mockMvc.perform(delete("/products/15"))
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.message").value("product deleted"));
				}
			

}

