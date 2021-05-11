package com.prueba.dev.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.prueba.dev.model.Product;
import com.prueba.dev.service.ProductServices;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SpringBootInventarioCsvApplicationTests {

	Product product, product2, product3;
	
	@Autowired
	ProductServices productServices;
	
	@BeforeAll
	void setUp() {		
		LocalDate date = LocalDate.now();
		product = new Product(2,"ProductoTest1",10,125.23,0.00,date);	
		product2 = new Product(1,"ProductoTest2",12,200.25,0.00,date);
		product3 = new Product(3,"ProductoTest3",13,500.30,0.00,date);
		productServices.saveProduct(product);
		productServices.saveProduct(product2);
		productServices.saveProduct(product3);
	}
	
	
	@Test
	public void testNuevoProducto() {	
		Optional<Product> pw = productServices.getProductById(product.getId());
		assertNotNull(product);
		assertThat(pw.get().getId()).isEqualTo(1);
		assertThat(pw.get().getTipo()).isEqualTo(2);
		assertThat(pw.get().getNombre()).isEqualTo("ProductoTest1");
		assertEquals(10, product.getCantidad());	
		assertThat(pw.get().getCosto()).isEqualTo(125.23);
		assertThat(pw.get().getPrecio()).isEqualTo(194.23);		
	}
	
	@Test
	public void testProductUpdate() {
		assertNotNull(product2);
		
		//chequa el valor del costo y precio antes de actualizar
		assertThat(product2.getCosto()).isEqualTo(200.25);
		assertThat(product2.getPrecio()).isEqualTo(266.25);		
		
		product2.setCantidad(10);
		product2.setNombre("LaptopActualizada");
		
		///se cambia el valor del tipo y costo para recalcular el precio de venta
		product2.setCosto(20.10);
		product2.setTipo(3);
		productServices.saveProduct(product2);
		
		Optional<Product> pw = productServices.getProductById(product2.getId());
		
		assertNotNull(product2);
		assertEquals(product2.getNombre(), "LaptopActualizada");
		assertEquals(10, product2.getCantidad());		
		assertThat(pw.get().getCosto()).isEqualTo(20.10);
		assertThat(pw.get().getPrecio()).isEqualTo(25.1);	
		
	}
	
	@Test
	public void testProductDelete() {
		assertNotNull(product3);		
		productServices.deleteProductById(product3.getId());	
		boolean pw2 = productServices.checkExistedProduct(product3.getId());	
		assertEquals(pw2, false);
	}

}
