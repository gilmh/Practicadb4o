package org.hectordam.ventanas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;

import org.hectordam.VentanaInicio;
import org.hectordam.beans.Mapa;
import org.hectordam.beans.Usuario;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.swing.JList;
import javax.swing.JScrollPane;

import org.hectordam.Tablas.TablaMapas;

import com.db4o.ObjectSet;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

import javax.swing.JComboBox;

public class JUsuarios extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtPoblacion;
	private JTextField txtCorreo;
	private JTextField txtNombre;
	private JLabel lbNombre;
	private JLabel lbCorreo;
	private JLabel lbPoblacion;
	private boolean validar;
	private Usuario usuario;
	private JLabel lblMapas;
	
	private JComboBox comboBox;
	
	private void aceptar(){
		
		validar = true;
		this.setVisible(false);
		
	}
	
	private void cancelar(){
		
		validar = false;
		this.setVisible(false);
		
	}
	
	public void insertar(){
		comboBox.removeAllItems();
		List<Mapa> mapas = VentanaInicio.db.query(Mapa.class);
		
		for(Mapa mapa: mapas){
			comboBox.addItem(mapa);
		}
		
		txtNombre.setText("");
		txtCorreo.setText("");
		txtPoblacion.setText("");
		
		usuario = new Usuario();
	}
	
	public void modificar(Usuario usuario){
		comboBox.removeAllItems();
		
		List<Mapa> mapas = VentanaInicio.db.query(Mapa.class);
		
		for(Mapa mapa: mapas){
			comboBox.addItem(mapa);
		}
		
		txtNombre.setText(usuario.getNombre());
		txtCorreo.setText(usuario.getCorreo());
		txtPoblacion.setText(usuario.getPais());
		comboBox.setSelectedItem(usuario.getMapa());
		
		this.usuario = usuario;
	}
	
	public void mostrar(){
		
		setVisible(true);
	}
	
	/**
	 * Create the dialog.
	 */
	public JUsuarios() {
		getContentPane().setBackground(Color.LIGHT_GRAY);
		setUndecorated(true);
		setModal(true);
		setBounds(100, 100, 224, 183);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.LIGHT_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		txtPoblacion = new JTextField();
		txtPoblacion.setColumns(10);
		txtPoblacion.setBounds(75, 73, 129, 20);
		contentPanel.add(txtPoblacion);
		
		txtCorreo = new JTextField();
		txtCorreo.setColumns(10);
		txtCorreo.setBounds(75, 42, 129, 20);
		contentPanel.add(txtCorreo);
		
		txtNombre = new JTextField();
		txtNombre.setColumns(10);
		txtNombre.setBounds(75, 11, 129, 20);
		contentPanel.add(txtNombre);
		
		lbNombre = new JLabel("Nombre");
		lbNombre.setBounds(10, 14, 46, 14);
		contentPanel.add(lbNombre);
		
		lbCorreo = new JLabel("Correo");
		lbCorreo.setBounds(10, 45, 46, 14);
		contentPanel.add(lbCorreo);
		
		lbPoblacion = new JLabel("Pais");
		lbPoblacion.setBounds(10, 76, 46, 14);
		contentPanel.add(lbPoblacion);
		
		lblMapas = new JLabel("Mapas");
		lblMapas.setBounds(10, 106, 46, 14);
		contentPanel.add(lblMapas);
		
		comboBox = new JComboBox();
		comboBox.setBounds(75, 104, 129, 20);
		contentPanel.add(comboBox);
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
		
		setLocationRelativeTo(null);
	}

	public boolean isValidar() {
		return validar;
	}

	public void setValidar(boolean validar) {
		this.validar = validar;
	}

	public Usuario getUsuario() {
		
		usuario.setNombre(txtNombre.getText());
		usuario.setCorreo(txtCorreo.getText());
		usuario.setPais(txtPoblacion.getText());
		usuario.setMapa((Mapa) comboBox.getSelectedItem());
		
		return usuario;
	}
}
