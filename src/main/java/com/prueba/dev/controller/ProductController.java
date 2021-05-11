package com.prueba.dev.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.dev.model.Product;
import com.prueba.dev.model.Message;
import com.prueba.dev.service.ProductServices;
import com.prueba.dev.utils.ApacheCommonsCsvUtil;


@RestController
@RequestMapping("/api/product")
public class ProductController {
	
	@Autowired
	ProductServices productServices;
	
	@PostMapping("/createProduct")
	public ResponseEntity<Message> addNewProduct(@RequestBody Product product) {
		
		try {
			
			Product returnedProduct = productServices.saveProduct(product);
			
			return new ResponseEntity<Message>(new Message("Producto Creado con Exito!", 
											Arrays.asList(returnedProduct), ""), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<Message>(new Message("Fail to post a new Product!", 
											null, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);			
		}
	}
	
	@GetMapping("/getAllProducts")
	public ResponseEntity<Message> retrieveProductInfo() {
		
		try {
			List<Product> ProductInfos = productServices.getProductsInfos();
			
			return new ResponseEntity<Message>(new Message("Get Products' Infos!", 
												ProductInfos, ""), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<Message>(new Message("Fail!",
												null, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/findone/{id}")
	public ResponseEntity<Message> getProductById(@PathVariable long id) {
		try {
			Optional<Product> optProduct = productServices.getProductById(id);
			
			if(optProduct.isPresent()) {
				return new ResponseEntity<Message>(new Message("Successfully! Retrieve a Product by id = " + id,
															Arrays.asList(optProduct.get()), ""), HttpStatus.OK);
			} else {
				return new ResponseEntity<Message>(new Message("Failure -> NOT Found a Product by id = " + id,
						null, ""), HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			return new ResponseEntity<Message>(new Message("Failure",
					null, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Message> updateProductById(@RequestBody Product _product, @PathVariable long id) {
		try {
			if(productServices.checkExistedProduct(id)) {
				Product product = productServices.getProductById(id).get();
				
				double precio = 0;
				
				//Nuevos valores para el producto				
				product.setNombre(_product.getNombre());
				product.setTipo(_product.getTipo());
				product.setCantidad(_product.getCantidad());
				product.setCosto(_product.getCosto());
				product.setFecha(_product.getFecha());
				
				// save the change to database
				productServices.updateProduct(product);
				
				return new ResponseEntity<Message>(new Message("Successfully! Updated a Product "
																		+ "with id = " + id,
																	Arrays.asList(product), ""), HttpStatus.OK);
			}else {
				return new ResponseEntity<Message>(new Message("Failer! Can NOT Found a Product "
						+ "with id = " + id,
					null, ""), HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			return new ResponseEntity<Message>(new Message("Failure",
					null, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);			
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Message> deleteProductById(@PathVariable long id) {
		try {
			
			// checking the existed of a Product with id
			if(productServices.checkExistedProduct(id)) {
				productServices.deleteProductById(id);
				
				return new ResponseEntity<Message> (new Message("Successfully! Delete a Product with id = " + id, 
														null, ""), HttpStatus.OK);
			}else {
				return new ResponseEntity<Message>(new Message("Failer! Can NOT Found a Product "
														+ "with id = " + id, null, ""), HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			return new ResponseEntity<Message>(new Message("Failure",
					null, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
