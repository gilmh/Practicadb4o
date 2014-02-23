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
import org.hectordam.beans.Mision;
import org.hectordam.beans.Usuario;

import com.db4o.ObjectSet;

import java.awt.Color;
import java.util.List;

public class JMisiones extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtNombre;
	private JTextField txtDescripcion;
	private JTextField txtPuntos;
	private boolean validar = false;
	private Mision mision;
	private JComboBox comboBox;

	
	
	private void listar(){
		
		comboBox.removeAllItems();
		
		List<Usuario> usuarios = VentanaInicio.db.query(Usuario.class);
		
		for(Usuario usuario: usuarios){
			comboBox.addItem(usuario.getNombre());
		}
		
	}
	
	public void insertar(){
		
		listar();
		
		txtNombre.setText("");
		txtDescripcion.setText("");
		txtPuntos.setText("");
		
		this.mision = new Mision();
		
	}
	
	public void modificar(Mision mision){
		
		listar();
		
		txtNombre.setText(mision.getNombre());
		txtDescripcion.setText(mision.getDescripcion());
		txtPuntos.setText(mision.getObjetivo());
		comboBox.setSelectedItem(mision.getUsuarios().getNombre());
		
		this.mision = mision;
		
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
	public JMisiones() {
		getContentPane().setBackground(Color.LIGHT_GRAY);
		setUndecorated(true);
		setBounds(100, 100, 242, 153);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.LIGHT_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNombre = new JLabel("Nombre");
			lblNombre.setBounds(10, 11, 67, 14);
			contentPanel.add(lblNombre);
		}
		{
			JLabel lblDescipcion = new JLabel("Descipcion");
			lblDescipcion.setBounds(10, 36, 67, 14);
			contentPanel.add(lblDescipcion);
		}
		{
			JLabel lblObjetivo = new JLabel("Objetivo");
			lblObjetivo.setBounds(10, 61, 67, 14);
			contentPanel.add(lblObjetivo);
		}
		{
			txtNombre = new JTextField();
			txtNombre.setColumns(10);
			txtNombre.setBounds(87, 8, 136, 20);
			contentPanel.add(txtNombre);
		}
		{
			txtDescripcion = new JTextField();
			txtDescripcion.setColumns(10);
			txtDescripcion.setBounds(87, 33, 136, 20);
			contentPanel.add(txtDescripcion);
		}
		{
			txtPuntos = new JTextField();
			txtPuntos.setColumns(10);
			txtPuntos.setBounds(87, 58, 136, 20);
			contentPanel.add(txtPuntos);
		}
		{
			comboBox = new JComboBox();
			comboBox.setBounds(87, 89, 136, 20);
			contentPanel.add(comboBox);
		}
		{
			JLabel lblUsuario = new JLabel("Usuario");
			lblUsuario.setBounds(10, 92, 46, 14);
			contentPanel.add(lblUsuario);
		}
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

	public Mision getMision() {
		
		mision.setNombre(txtNombre.getText());
		mision.setDescripcion(txtDescripcion.getText());
		mision.setObjetivo(txtPuntos.getText());
		
		Usuario usuario = new Usuario();
		usuario.setNombre((String) comboBox.getSelectedItem());
		
		ObjectSet<Usuario> resultado = VentanaInicio.db.queryByExample(usuario);
		usuario = resultado.next();
		
		mision.setUsuarios(usuario);
		
		return mision;
	}

	public void setMision(Mision mision) {
		this.mision = mision;
	}

}
