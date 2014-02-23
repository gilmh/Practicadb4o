package org.hectordam.ventanas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JComboBox;

import org.hectordam.VentanaInicio;
import org.hectordam.beans.Arma;
import org.hectordam.beans.Personaje;

import com.db4o.ObjectSet;
import com.db4o.query.Query;

import java.awt.Color;
import java.util.List;

public class JArmas extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtDescripcion;
	private JTextField txtNombre;
	private JTextField txtAtaque;
	private boolean validar = false;
	private JComboBox comboBox;
	private JLabel lblPersonaje;
	private Arma arma;
	
	private void listar(){
		
		comboBox.removeAllItems();
		
		List<Personaje> personajes = VentanaInicio.db.query(Personaje.class);
		
		for(Personaje personaje: personajes){
			comboBox.addItem(personaje.getNombre());
		}
		
	}
	
	public void insertar(){
		
		listar();
		
		txtNombre.setText("");
		txtDescripcion.setText("");
		txtAtaque.setText("");
		
		this.arma = new Arma();
		
	}
	
	public void modificar(Arma arma){
		
		listar();
		
		txtNombre.setText(arma.getNombre());
		txtDescripcion.setText(arma.getDescripcion());
		txtAtaque.setText(Integer.toString(arma.getPuntos()));
		comboBox.setSelectedItem(arma.getPersonajes().getNombre());
		
		this.arma = arma;
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
	public JArmas() {
		getContentPane().setBackground(Color.LIGHT_GRAY);
		setUndecorated(true);
		setBounds(100, 100, 237, 154);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.LIGHT_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNombre = new JLabel("Nombre");
			lblNombre.setBounds(10, 11, 64, 14);
			contentPanel.add(lblNombre);
		}
		{
			JLabel lblDescripcion = new JLabel("Descripcion");
			lblDescripcion.setBounds(10, 36, 64, 14);
			contentPanel.add(lblDescripcion);
		}
		{
			JLabel lblAtaque = new JLabel("Ataque");
			lblAtaque.setBounds(10, 61, 64, 14);
			contentPanel.add(lblAtaque);
		}
		{
			txtDescripcion = new JTextField();
			txtDescripcion.setColumns(10);
			txtDescripcion.setBounds(84, 33, 136, 20);
			contentPanel.add(txtDescripcion);
		}
		{
			txtNombre = new JTextField();
			txtNombre.setColumns(10);
			txtNombre.setBounds(84, 8, 136, 20);
			contentPanel.add(txtNombre);
		}
		{
			txtAtaque = new JTextField();
			txtAtaque.setColumns(10);
			txtAtaque.setBounds(84, 58, 136, 20);
			contentPanel.add(txtAtaque);
		}
		
		comboBox = new JComboBox();
		comboBox.setBounds(84, 89, 136, 20);
		contentPanel.add(comboBox);
		
		lblPersonaje = new JLabel("Personaje");
		lblPersonaje.setBounds(10, 92, 64, 14);
		contentPanel.add(lblPersonaje);
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

	public Arma getArma() {
		
		arma.setNombre(txtNombre.getText());
		arma.setDescripcion(txtDescripcion.getText());
		arma.setPuntos(Integer.parseInt(txtAtaque.getText()));
		
		Personaje personaje = new Personaje();
		personaje.setNombre((String) comboBox.getSelectedItem());
		
		ObjectSet<Personaje> resultado = VentanaInicio.db.queryByExample(personaje);
		personaje = resultado.next();
		
		arma.setPersonajes(personaje);
		
		return arma;
	}

	public void setArma(Arma arma) {
		this.arma = arma;
	}
	
	
}
