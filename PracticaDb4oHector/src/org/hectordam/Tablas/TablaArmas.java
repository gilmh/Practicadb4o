package org.hectordam.Tablas;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.hectordam.VentanaInicio;
import org.hectordam.beans.Arma;
import org.hectordam.beans.Usuario;

public class TablaArmas extends JTable{

	private DefaultTableModel modelo;
	
	
	public TablaArmas(){
		
		String[] columna = {"Nombre", "Descripcion", "Puntos"};
		modelo = new DefaultTableModel(columna, 0);
		
		this.setModel(modelo);
		
	}
	
	public void listar(){
		
		List<Arma> armas = VentanaInicio.db.query(Arma.class);
		
		modelo.setNumRows(0);
		
		for (Arma arma : armas) {
			Object[] fila = new Object[]{arma.getNombre(), arma.getDescripcion(), arma.getPuntos()};
			
			modelo.addRow(fila);
		}
		
	}
	
	public void listaAvanzada(String filtro){
		
		List<Arma> armas = VentanaInicio.db.query(Arma.class);
		
		modelo.setNumRows(0);
		
		for (Arma arma : armas) {
			if(arma.getNombre().contains(filtro) || arma.getDescripcion().contains(filtro) || arma.getPersonajes().getNombre().contains(filtro)){
				Object[] fila = new Object[]{arma.getNombre(), arma.getDescripcion(), arma.getPuntos()};
			
				modelo.addRow(fila);
			}
		}		
	}
	
	public void insertar(Arma armas){
		
		VentanaInicio.db.store(armas);
		//VentanaInicio.db.commit();
		
		listar();
		
	}
	
	public void modificar(Arma armas){
		
		VentanaInicio.db.store(armas);
		//VentanaInicio.db.commit();
		
		listar();
		
	}
	
	public void eliminar(Arma armas){
		
		VentanaInicio.db.delete(armas);
		//VentanaInicio.db.commit();
		
		listar();
	}
	
}
