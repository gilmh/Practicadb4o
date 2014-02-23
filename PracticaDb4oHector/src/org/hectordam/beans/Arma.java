package org.hectordam.beans;

import java.util.List;

public class Arma{
	
	private String nombre;
	private String Descripcion;
	private int Puntos;
	
	private Personaje personajes;

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

	public int getPuntos() {
		return Puntos;
	}

	public void setPuntos(int puntos) {
		Puntos = puntos;
	}

	public Personaje getPersonajes() {
		return personajes;
	}

	public void setPersonajes(Personaje personajes) {
		this.personajes = personajes;
	}
	
}
