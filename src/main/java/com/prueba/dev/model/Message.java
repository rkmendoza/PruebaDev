package com.prueba.dev.model;

import java.util.ArrayList;
import java.util.List;

public class Message {
	private String message = "";
	private List<Product> Products = new ArrayList<Product>();
	private String error = "";
	
	public Message(String message, List<Product> Products, String error) {
		this.message = message;
		this.Products = Products;
		this.error = error;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public List<Product> getProducts(){
		return this.Products;
	}
	
	public void setProducts(ArrayList<Product> Products) {
		this.Products = Products;
	}
	
	public void setError(String error) {
		this.error = error;
	}
	
	public String getError() {
		return this.error;
	}
}