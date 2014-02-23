package org.hectordam.Tablas;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.hectordam.VentanaInicio;
import org.hectordam.beans.Arma;
import org.hectordam.beans.Personaje;


public class TablaPersonajes extends JTable{

	private DefaultTableModel modelo;
	
	
	public TablaPersonajes(){
		
		String[] columna = {"Nombre", "Raza", "Clase"};
		modelo = new DefaultTableModel(columna, 0);
		
		this.setModel(modelo);
		
	}
	
	public void listar(){
		
		List<Personaje> personajes = VentanaInicio.db.query(Personaje.class);
		
		modelo.setNumRows(0);
		
		for (Personaje personaje : personajes) {
			Object[] fila = new Object[]{personaje.getNombre(), personaje.getRaza(), personaje.getClase()};
			
			modelo.addRow(fila);
		}
		
	}
	
	public void listaAvanzada(String filtro){
		
		List<Personaje> personajes = VentanaInicio.db.query(Personaje.class);
		
		modelo.setNumRows(0);
		
		for (Personaje personaje : personajes) {
			if(personaje.getNombre().contains(filtro) || personaje.getRaza().contains(filtro) || personaje.getUsuario().getNombre().contains(filtro)){
				Object[] fila = new Object[]{personaje.getNombre(), personaje.getRaza(), personaje.getClase()};
			
				modelo.addRow(fila);
			}
		}		
	}
	
	public void insertar(Personaje personaje){
		
		VentanaInicio.db.store(personaje);
		//VentanaInicio.db.commit();
		
		listar();
	}
	
	public void modificar(Personaje personaje){
		
		VentanaInicio.db.store(personaje);
		//VentanaInicio.db.commit();
		
		listar();
		
	}
	
	public void eliminar(Personaje personaje){
		
		VentanaInicio.db.delete(personaje);
		//VentanaInicio.db.commit();
		
		listar();
	
	}

	
	
}
