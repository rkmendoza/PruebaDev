package com.prueba.dev.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prueba.dev.model.Product;
import com.prueba.dev.repository.ProductRepository;
import com.prueba.dev.utils.ApacheCommonsCsvUtil;

@Service
public class ProductServices {
	
	@Autowired
	ProductRepository repository;
	
	public Product saveProduct(Product product) {
		
		System.out.println("saveProduct service " + product);
		
		double precio = 0.00;
		
		//Logica para calcular la comision
		switch (product.getTipo()) {
		case 1:
			precio = ApacheCommonsCsvUtil.calculatePrice(product.getCosto(),  0.12);
			break;
		case 2:
			precio = ApacheCommonsCsvUtil.calculatePrice(product.getCosto(),  0.305);
			break;
		case 3:
			precio = ApacheCommonsCsvUtil.calculatePrice(product.getCosto(),  0.0895);
			break;					
		case 4:
			precio = ApacheCommonsCsvUtil.calculatePrice(product.getCosto(),  0.1033);
		   break;
		default:
			break;				
		}
		
		product.setPrecio(precio);		
		return repository.save(product);		
	}
	
	public List<Product> getProductsInfos(){
		return repository.findAll();		
	}
	
	public Optional<Product> getProductById(long id) {
		return repository.findById(id);		
	}
	
	public boolean checkExistedProduct(long id) {
		if(repository.existsById((long) id)) {
			return true;
		}
		return false;
	}
	
	public Product updateProduct(Product product) {
		
		double precio = 0.00;
		
		//Logica para calcular la comision
		switch (product.getTipo()) {
		case 1:
			precio = ApacheCommonsCsvUtil.calculatePrice(product.getCosto(),  0.12);
			break;
		case 2:
			precio = ApacheCommonsCsvUtil.calculatePrice(product.getCosto(),  0.305);
			break;
		case 3:
			precio = ApacheCommonsCsvUtil.calculatePrice(product.getCosto(),  0.0895);
			break;					
		case 4:
			precio = ApacheCommonsCsvUtil.calculatePrice(product.getCosto(),  0.1033);
		   break;
		default:
			break;				
		}
		
		product.setPrecio(precio);	
		
		return repository.save(product);	
	}
	
	public void deleteProductById(long id) {
		repository.deleteById(id);
	}
}