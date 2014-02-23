package org.hectordam.ventanas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.hectordam.VentanaInicio;
import org.hectordam.beans.Personaje;
import org.hectordam.beans.Usuario;

import com.db4o.ObjectSet;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JComboBox;

import java.awt.Color;
import java.util.List;

public class JPersonajes extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtNombre;
	private JTextField txtRaza;
	private JTextField txtClase;
	private boolean validar;
	private Personaje personaje;
	private JComboBox<String> comboBox;
	private JLabel lblUsuario;
	

	
	public void listar(){
		
		comboBox.removeAllItems();
		
		List<Usuario> usuarios = VentanaInicio.db.query(Usuario.class);
		
		for(Usuario usuario: usuarios){
			comboBox.addItem(usuario.getNombre());
		}
		
	}
	
	public void insertar(){
		
		listar();
		
		txtNombre.setText("");
		txtClase.setText("");
		txtRaza.setText("");
		
		this.personaje = new Personaje();
	}
	
	public void modificar(Personaje personaje){
		
		listar();
		
		txtNombre.setText(personaje.getNombre());
		txtClase.setText(personaje.getClase());
		txtRaza.setText(personaje.getRaza());
		if(personaje.getUsuario().getNombre() == null){
			comboBox.setSelectedIndex(0);
		}
		else{
			comboBox.setSelectedItem(personaje.getUsuario().getNombre());
		}
		
		this.personaje = personaje;
	}
	
	private void aceptar(){
		
		validar = true;
		this.setVisible(false);
		
	}
	
	private void cancelar(){
		
		validar = false;
		this.setVisible(false);
		
	}
	
	public void mostrar(){
		
		this.setVisible(true);
	}
	
	/**
	 * Create the dialog.
	 */
	public JPersonajes() {
		getContentPane().setBackground(Color.LIGHT_GRAY);
		setUndecorated(true);
		
		setBounds(100, 100, 249, 203);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.LIGHT_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNombre = new JLabel("Nombre");
			lblNombre.setBounds(10, 29, 46, 14);
			contentPanel.add(lblNombre);
		}
		{
			JLabel lblRaza = new JLabel("Raza");
			lblRaza.setBounds(10, 54, 46, 14);
			contentPanel.add(lblRaza);
		}
		{
			JLabel lblClase = new JLabel("Clase");
			lblClase.setBounds(10, 79, 46, 14);
			contentPanel.add(lblClase);
		}
		{
			txtNombre = new JTextField();
			txtNombre.setBounds(66, 26, 136, 20);
			contentPanel.add(txtNombre);
			txtNombre.setColumns(10);
		}
		{
			txtRaza = new JTextField();
			txtRaza.setBounds(66, 51, 136, 20);
			contentPanel.add(txtRaza);
			txtRaza.setColumns(10);
		}
		{
			txtClase = new JTextField();
			txtClase.setColumns(10);
			txtClase.setBounds(66, 76, 136, 20);
			contentPanel.add(txtClase);
		}
		
		comboBox = new JComboBox();
		comboBox.setBounds(66, 132, 136, 20);
		contentPanel.add(comboBox);
		
		lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(10, 135, 46, 14);
		contentPanel.add(lblUsuario);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.LIGHT_GRAY);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						aceptar();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						cancelar();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		setModal(true);
		setLocationRelativeTo(null);
	}

	public boolean isValidar() {
		return validar;
	}

	public void setValidar(boolean validar) {
		this.validar = validar;
	}

	public Personaje getPersonaje() {
		
		personaje.setNombre(txtNombre.getText());
		personaje.setClase(txtClase.getText());
		personaje.setRaza(txtRaza.getText());
		
		Usuario usuario = new Usuario();
		usuario.setNombre((String) comboBox.getSelectedItem());
		
		ObjectSet<Usuario> resultado = VentanaInicio.db.queryByExample(usuario);
		usuario = resultado.next();
		
		personaje.setUsuario(usuario);
		
		return personaje;
	}

	public void setPersonaje(Personaje personaje) {
		this.personaje = personaje;
	}
}
