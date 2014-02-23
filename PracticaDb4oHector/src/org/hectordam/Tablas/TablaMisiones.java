package org.hectordam.Tablas;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.hectordam.VentanaInicio;
import org.hectordam.beans.Arma;
import org.hectordam.beans.Mision;

public class TablaMisiones extends JTable{

	private DefaultTableModel modelo;
	
	
	public TablaMisiones(){
		
		String[] columna = {"Nombre", "Descripcion", "Objetivo"};
		modelo = new DefaultTableModel(columna, 0);
		
		this.setModel(modelo);
		
	}
	
	public void listar(){
		
		List<Mision> misiones = VentanaInicio.db.query(Mision.class);
		
		modelo.setNumRows(0);
		
		for (Mision mision : misiones) {
			Object[] fila = new Object[]{mision.getNombre(), mision.getDescripcion(), mision.getObjetivo()};
			
			modelo.addRow(fila);
		}
		
	}
	
	public void listaAvanzada(String filtro){
		
		List<Mision> misiones = VentanaInicio.db.query(Mision.class);
		
		modelo.setNumRows(0);
		
		for (Mision mision : misiones) {
			if(mision.getNombre().contains(filtro) || mision.getDescripcion().contains(filtro) || mision.getUsuarios().getNombre().contains(filtro)){
				Object[] fila = new Object[]{mision.getNombre(), mision.getDescripcion(), mision.getObjetivo()};
			
				modelo.addRow(fila);
			}
		}
	}
	
	public void insertar(Mision mision){
		
		VentanaInicio.db.store(mision);
		//VentanaInicio.db.commit();
		
		listar();
		
	}
	
	public void modificar(Mision mision){
		
		VentanaInicio.db.store(mision);
		//VentanaInicio.db.commit();
		
		listar();
	}
	
	public void eliminar(Mision mision){
		
		VentanaInicio.db.delete(mision);
		//VentanaInicio.db.commit();
		
		listar();
	}
	
}
