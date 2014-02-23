package org.hectordam.Tablas;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.hectordam.VentanaInicio;
import org.hectordam.beans.Mapa;
import org.hectordam.beans.Usuario;


public class TablaUsuarios extends JTable{

	private DefaultTableModel modelo;
	
	
	
	public TablaUsuarios(){
		
		String[] columna = {"Nombre", "Pais", "Correo"};
		modelo = new DefaultTableModel(columna, 0);
		
		this.setModel(modelo);
	}
	
	public void listar(){
		
		List<Usuario> usuarios = VentanaInicio.db.query(Usuario.class);
		
		modelo.setNumRows(0);
		
		for (Usuario usuario : usuarios) {
			Object[] fila = new Object[]{ usuario.getNombre(), usuario.getPais(), usuario.getCorreo()};
			
			modelo.addRow(fila);
		}
	}
	
	public void listaAvanzada(String filtro){
		
		List<Usuario> usuarios = VentanaInicio.db.query(Usuario.class);
		
		modelo.setNumRows(0);
		
		for (Usuario usuario : usuarios) {
			if(usuario.getNombre().contains(filtro) || usuario.getPais().contains(filtro) || usuario.getMapa().getNombre().contains(filtro)){
				Object[] fila = new Object[]{ usuario.getNombre(), usuario.getPais(), usuario.getCorreo()};
			
				modelo.addRow(fila);
			}
		}
	}
	
	public void insertar(Usuario usuario){
		
		VentanaInicio.db.store(usuario);
		//VentanaInicio.db.commit();
		
		listar();
	}
	
	public void modificar(Usuario usuario){
		
		VentanaInicio.db.store(usuario);
		//VentanaInicio.db.commit();
		
		listar();
		
	}
	
	public void eliminar(Usuario usuario){
		
		VentanaInicio.db.delete(usuario);
		//VentanaInicio.db.commit();
		
		listar();
		
	}
	
}
