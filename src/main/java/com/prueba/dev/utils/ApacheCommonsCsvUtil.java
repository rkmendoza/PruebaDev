package com.prueba.dev.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;


import com.prueba.dev.model.Product;

public class ApacheCommonsCsvUtil {
	
private static String csvExtension = "csv";

private static DecimalFormat df2 = new DecimalFormat("#.##");
	
	public static List<Product> parseCsvFile(InputStream is) {
		BufferedReader fileReader = null;
		CSVParser csvParser = null;

		List<Product> products = new ArrayList<Product>();
		AtomicInteger line = new AtomicInteger(0);
		try {
			fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			csvParser = new CSVParser(fileReader,
					CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");			  
			
			csvRecords.forEach(csv -> {
				LocalDate fecha = LocalDate.parse(csv.get("fecha"), formatter);
				double precio = 0;				
				int index = ((List<CSVRecord>) csvRecords).indexOf(csv);
				line.set(index+1);
				Product product = new Product(						
						Integer.parseInt(csv.get("tipo")),
						csv.get("nombre"),
						Integer.parseInt(csv.get("cantidad")),
						Double.parseDouble(csv.get("costo")),
						Double.parseDouble(csv.get("precio"))
						);
				product.setFecha(fecha);
				
				//Logica para calcular la comision
				switch (product.getTipo()) {
				case 1:
					precio = calculatePrice(product.getCosto(),  0.12);
					break;
				case 2:
					precio = calculatePrice(product.getCosto(),  0.305);
					break;
				case 3:
					precio = calculatePrice(product.getCosto(),  0.0895);
					break;					
				case 4:
					precio = calculatePrice(product.getCosto(),  0.1033);
				   break;
				default:
					break;
				}				
				product.setPrecio(precio);
				products.add(product);
			});			


		} catch (Exception e) {
			System.out.println("Error leyendo CSV en la linea " + line + " !");
			System.out.println("Se cargaron " + line.decrementAndGet() + " lineas!");
			e.printStackTrace();
		} finally {
			try {
				System.out.println("Se cargaron " + line + " lineas!");
				fileReader.close();
				csvParser.close();
			} catch (IOException e) {
				System.out.println("Closing fileReader/csvParser Error!");
				e.printStackTrace();
			}
		}

		return products;
	}
	
	public static boolean isCSVFile(MultipartFile file) {
		String extension = file.getOriginalFilename().split("\\.")[1];
		
		if(!extension.equals(csvExtension)) {
			return false;
		}
		
		return true;
	}
	
	public static double calculatePrice(double costo, double percent) {
			
		double precio;
		double comision;
		double subTotal;
		double iva;
		
		comision = (int) (costo * percent);
		subTotal = costo + comision;
		iva = (int) (subTotal * 0.19);
		precio = subTotal + iva;
		
		df2.format(precio);		
	
		return precio;		
	}

}
