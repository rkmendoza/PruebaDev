package com.prueba.dev.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.prueba.dev.model.Product;

public class OpenCsvUtil {

	public static List<Product> parseCsvFile(InputStream is) {
		String[] CSV_HEADER = { "tipo", "nombre", "cantidad", "costo", "precio", "fecha" };
		Reader fileReader = null;
		CsvToBean<Product> csvToBean = null;
	
		List<Product> products = new ArrayList<Product>();
		
		try {
			fileReader = new InputStreamReader(is);
	
			ColumnPositionMappingStrategy<Product> mappingStrategy = new ColumnPositionMappingStrategy<Product>();
	
			mappingStrategy.setType(Product.class);
			mappingStrategy.setColumnMapping(CSV_HEADER);
	
			csvToBean = new CsvToBeanBuilder<Product>(fileReader).withMappingStrategy(mappingStrategy).withSkipLines(1)
					.withIgnoreLeadingWhiteSpace(true).build();
	
			products = csvToBean.parse();
			
			return products;
		} catch (Exception e) {
			System.out.println("Error leyendo archivo CSV!");
			e.printStackTrace();
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				System.out.println("Closing fileReader/csvParser Error!");
				e.printStackTrace();
			}
		}
		
		return products;
	}

}