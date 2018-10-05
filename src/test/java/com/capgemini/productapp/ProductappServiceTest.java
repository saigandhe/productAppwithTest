package com.capgemini.productapp;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.capgemini.productapp.entity.Product;
import com.capgemini.productapp.repository.ProductRepository;
import com.capgemini.productapp.service.ProductService;
import com.capgemini.productapp.service.impl.ProductServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductappServiceTest{

	@Mock
	private ProductRepository productRepository;
	public MockMvc mockMvc;
	
	@InjectMocks
	private ProductServiceImpl productServiceImpl;
	Product product;
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		product = new Product();
		product.setProductId(15);
		product.setProductName("MOTO");
		product.setProductCategory("Mobile");
		product.setProductPrice(20000);
	}
	
	@Test
	public void addProductTest() throws Exception{
		when(productRepository.save(Mockito.isA(Product.class))).thenReturn(product);
		assertEquals(product,productServiceImpl.addProduct(product));
	}
	
	@Test
	public void updateProductTest() throws Exception{
		product.setProductName("MOTO G4 Plus");
		product.setProductPrice(25000);
		
		when(productRepository.save(Mockito.isA(Product.class))).thenReturn(product);
		assertEquals(product, productServiceImpl.updateProduct(product));
	}
	
	@Test
	public void findProductById() throws Exception{
		Optional<Product> optionalproduct = Optional.of(product);
		
		when(productRepository.findById(Mockito.isA(Integer.class))).thenReturn(optionalproduct);
		assertEquals(optionalproduct.get(),productServiceImpl.findProductById(15));
	}
	
	@Test
	public void deleteproductTest() throws Exception{
		productServiceImpl.deleteProduct(product);
		verify(productRepository).delete(product);
	}
}
