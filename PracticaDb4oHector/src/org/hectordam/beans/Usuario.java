package org.hectordam.beans;

import java.util.ArrayList;
import java.util.List;

public class Usuario{
	
	private String nombre;
	private String pais;
	private String correo;
	
	private Personaje personaje;
	private Mision mision;
	private Mapa mapa;
	
	private ArrayList<Mapa> mapas;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public Personaje getPersonaje() {
		return personaje;
	}
	public void setPersonaje(Personaje personaje) {
		this.personaje = personaje;
	}
	public Mision getMision() {
		return mision;
	}
	public void setMision(Mision mision) {
		this.mision = mision;
	}
	public ArrayList<Mapa> getMapas() {
		return mapas;
	}
	public void setMapas(ArrayList<Mapa> mapas) {
		this.mapas = mapas;
	}
	public Mapa getMapa() {
		return mapa;
	}
	public void setMapa(Mapa mapa) {
		this.mapa = mapa;
	}
	
}
