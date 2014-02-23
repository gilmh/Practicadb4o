package org.hectordam.beans;

import java.util.List;

public class Mapa{
	
	private String nombre;
	private String Descripcion;
	private String dimension;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return Descripcion;
	}
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}
	public String getDimension() {
		return dimension;
	}
	public void setDimension(String dimension) {
		this.dimension = dimension;
	}
	@Override
	public String toString() {
		return nombre;
	}
	
}
