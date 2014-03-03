package org.hectordam;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;

import org.hectordam.beans.Login;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.cs.Db4oClientServer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

public class VentanaInicio {

	private JFrame frame;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JTextField txtUsuario;
	private JLabel lblUsuario;
	private JTextField txtPassword;
	private JLabel lblPassword;
	private VentanaPrincipal principal;
	
	public static ObjectContainer db;
	private JButton btnRegistrar;
	
	private boolean correcto;
	
	private void aceptar(){
		
		if(txtUsuario.getText().equalsIgnoreCase("") || txtPassword.getText().equalsIgnoreCase("")){
			JOptionPane.showMessageDialog(null, "no hay datos en las cajas de texto", "Error", JOptionPane.ERROR_MESSAGE);
		}
		else{
			
			List<Login> lista = db.query(Login.class);
			
			for(Login login: lista){
				if(txtUsuario.getText().equalsIgnoreCase(login.getUsuario()) && txtPassword.getText().equalsIgnoreCase(login.getPassword())){
					correcto = true;
					frame.setVisible(false);
					principal.mostrar();
				}
			}
			if(!correcto){
				JOptionPane.showMessageDialog(null, "usuario o contraseña incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		
	}
	
	private void registrar(){
		
		if(txtUsuario.getText().equalsIgnoreCase("") || txtPassword.getText().equalsIgnoreCase("")){
			JOptionPane.showMessageDialog(null, "no hay datos en las cajas de texto", "Error", JOptionPane.ERROR_MESSAGE);
		}
		else{
			Login login = new Login();
			login.setUsuario(txtUsuario.getText());
			login.setPassword(txtPassword.getText());
			
			db.store(login);
			db.commit();
		}
		
	}
	
	private void inicializar(){
		
		//db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "JuegoDb4o");
		db = Db4oClientServer.openClient("localhost", 1990, "usuario", "contrasena");
		
		principal = new VentanaPrincipal(this);
		
	}
	
	public void mostrar(){
		
		txtPassword.setText("");
		txtUsuario.setText("");
		frame.setVisible(true);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					VentanaInicio window = new VentanaInicio();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VentanaInicio() {
		initialize();
		inicializar();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 322, 148);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				aceptar();
			}
		});
		btnAceptar.setBounds(109, 73, 89, 23);
		frame.getContentPane().add(btnAceptar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnCancelar.setBounds(208, 73, 89, 23);
		frame.getContentPane().add(btnCancelar);
		
		txtUsuario = new JTextField();
		txtUsuario.setBounds(87, 11, 210, 20);
		frame.getContentPane().add(txtUsuario);
		txtUsuario.setColumns(10);
		
		lblUsuario = new JLabel("Usuario: ");
		lblUsuario.setBounds(10, 11, 67, 14);
		frame.getContentPane().add(lblUsuario);
		
		txtPassword = new JTextField();
		txtPassword.setBounds(87, 42, 210, 20);
		frame.getContentPane().add(txtPassword);
		txtPassword.setColumns(10);
		
		lblPassword = new JLabel("Password: ");
		lblPassword.setBounds(10, 42, 67, 14);
		frame.getContentPane().add(lblPassword);
		
		btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				registrar();
			}
		});
		btnRegistrar.setBounds(10, 73, 89, 23);
		frame.getContentPane().add(btnRegistrar);
		
		frame.setLocationRelativeTo(null);
	}
}
