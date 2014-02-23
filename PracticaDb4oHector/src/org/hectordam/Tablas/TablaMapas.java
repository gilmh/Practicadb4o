package org.hectordam.Tablas;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.hectordam.VentanaInicio;
import org.hectordam.beans.Arma;
import org.hectordam.beans.Mapa;

public class TablaMapas extends JTable{

	private DefaultTableModel modelo;
	
	
	public TablaMapas(){
		
		String[] columna = {"Nombre", "Descripcion", "Dimension"};
		modelo = new DefaultTableModel(columna, 0);
		
		this.setModel(modelo);
		
	}
	
	public void listar(){
		
		List<Mapa> mapas = VentanaInicio.db.query(Mapa.class);
		
		modelo.setNumRows(0);
		
		for (Mapa mapa : mapas) {
			Object[] fila = new Object[]{mapa.getNombre(), mapa.getDescripcion(), mapa.getDimension()};
			
			modelo.addRow(fila);
		}
		
	}
	
	public void listaAvanzada(String filtro){
		
		List<Mapa> mapas = VentanaInicio.db.query(Mapa.class);
		
		modelo.setNumRows(0);
		
		for (Mapa mapa : mapas) {
			if(mapa.getNombre().contains(filtro) || mapa.getDescripcion().contains(filtro)){
				Object[] fila = new Object[]{mapa.getNombre(), mapa.getDescripcion(), mapa.getDimension()};
			
				modelo.addRow(fila);
			}
		}
	}
	
	public void insertar(Mapa mapa){
		
		VentanaInicio.db.store(mapa);
		//VentanaInicio.db.commit();
		
		listar();
		
	}
	
	public void modificar(Mapa mapa){
		
		VentanaInicio.db.store(mapa);
		//VentanaInicio.db.commit();
		
		listar();
		
	}
	
	public void eliminar(Mapa mapa){
		
		VentanaInicio.db.delete(mapa);
		//VentanaInicio.db.commit();
		
		listar();
	}
	
}
