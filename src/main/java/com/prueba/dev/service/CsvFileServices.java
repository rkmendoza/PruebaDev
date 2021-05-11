package com.prueba.dev.service;

import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prueba.dev.model.Product;
import com.prueba.dev.repository.ProductRepository;
import com.prueba.dev.utils.ApacheCommonsCsvUtil;

@Service
public class CsvFileServices {
	
	@Autowired
	//CustomerRepository customerRepository;
	ProductRepository productRepository;

	// Store Csv File's data to database
	public void store(InputStream file) {
		try {
			// Using ApacheCommons Csv Utils to parse CSV file
			List<Product> lstProducts = ApacheCommonsCsvUtil.parseCsvFile(file);
			
			// Save customers to database
			productRepository.saveAll(lstProducts);
		} catch(Exception e) {
			throw new RuntimeException("FALLO! -> message = " + e.getMessage());
		}
	}	

}