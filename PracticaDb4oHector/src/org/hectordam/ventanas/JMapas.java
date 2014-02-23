package org.hectordam.ventanas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.hectordam.beans.Mapa;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class JMapas extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtNombre;
	private JTextField txtDescripcion;
	private JTextField txtDimension;
	private boolean validar = false;
	private Mapa mapa;
	
	
	
	public void insertar(){
		
		txtNombre.setText("");
		txtDescripcion.setText("");
		txtDimension.setText("");
		
		this.mapa = new Mapa();
	}
	
	public void modificar(Mapa mapa){
		
		txtNombre.setText(mapa.getNombre());
		txtDescripcion.setText(mapa.getDescripcion());
		txtDimension.setText(mapa.getDimension());
		
		this.mapa = mapa;
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
	public JMapas() {
		getContentPane().setBackground(Color.LIGHT_GRAY);
		setUndecorated(true);
		setBounds(100, 100, 237, 122);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.LIGHT_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNombre = new JLabel("Nombre");
			lblNombre.setBounds(10, 11, 68, 14);
			contentPanel.add(lblNombre);
		}
		{
			JLabel lblDescripcion = new JLabel("Descripcion");
			lblDescripcion.setBounds(10, 36, 68, 14);
			contentPanel.add(lblDescripcion);
		}
		{
			JLabel lblDimension = new JLabel("Dimension");
			lblDimension.setBounds(10, 61, 68, 14);
			contentPanel.add(lblDimension);
		}
		{
			txtNombre = new JTextField();
			txtNombre.setColumns(10);
			txtNombre.setBounds(88, 8, 136, 20);
			contentPanel.add(txtNombre);
		}
		{
			txtDescripcion = new JTextField();
			txtDescripcion.setColumns(10);
			txtDescripcion.setBounds(88, 33, 136, 20);
			contentPanel.add(txtDescripcion);
		}
		{
			txtDimension = new JTextField();
			txtDimension.setColumns(10);
			txtDimension.setBounds(88, 58, 136, 20);
			contentPanel.add(txtDimension);
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

	public Mapa getMapa() {
		
		mapa.setNombre(txtNombre.getText());
		mapa.setDescripcion(txtDescripcion.getText());
		mapa.setDimension(txtDimension.getText());
		
		return mapa;
	}

	public void setMapa(Mapa mapa) {
		this.mapa = mapa;
	}

}
