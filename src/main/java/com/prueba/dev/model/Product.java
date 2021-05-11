package com.prueba.dev.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@Entity
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int tipo;
	private String nombre;
	private int cantidad;
	
	@Column(name = "costo", columnDefinition = "decimal(12,2)")
    private double costo;
	
	@Column(name = "precio", columnDefinition = "decimal(12,2)")
	private double precio;
	
	private LocalDate fecha;
	
	
	public Product() {
		super();
	}

	public Product(int tipo, String nombre, int cantidad, double costo, double precio, LocalDate fecha) {
		super();
		this.tipo = tipo;
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.costo = costo;
		this.precio = precio;
		this.fecha = fecha;
	}
	
	public Product(int tipo, String nombre, int cantidad, double costo, double precio) {
		super();
		this.tipo = tipo;
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.costo = costo;
		this.precio = precio;
	}


	public Product(Long id, int tipo, String nombre, int cantidad, double costo, double precio, LocalDate fecha) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.costo = costo;
		this.precio = precio;
		this.fecha = fecha;
	}	
		
	//nombre de producto, una cantidad, un costo, un precio y fecha desde la cual está disponible el producto

}
