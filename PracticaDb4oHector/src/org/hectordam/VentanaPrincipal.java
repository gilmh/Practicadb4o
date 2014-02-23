package org.hectordam;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTabbedPane;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.hectordam.ventanas.JArmas;
import org.hectordam.ventanas.JMapas;
import org.hectordam.ventanas.JMisiones;
import org.hectordam.ventanas.JPersonajes;
import org.hectordam.ventanas.JUsuarios;
import org.hectordam.Tablas.TablaUsuarios;
import org.hectordam.Tablas.TablaMisiones;
import org.hectordam.Tablas.TablaPersonajes;
import org.hectordam.Tablas.TablaArmas;
import org.hectordam.Tablas.TablaMapas;
import org.hectordam.beans.Arma;
import org.hectordam.beans.Mapa;
import org.hectordam.beans.Mision;
import org.hectordam.beans.Personaje;
import org.hectordam.beans.Usuario;
import org.w3c.dom.*;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class VentanaPrincipal extends JFrame {

	private JPanel contentPane;
	private JPanel panelEstado;
	private JPanel panelPrincipal;
	private JMenuBar menuBar;
	private JMenu mnConexion;
	private VentanaInicio ventanaInicio;
	private JMenuItem mntmDesconectar;
	private JTabbedPane tabbedPane;
	private JLayeredPane PestanaMisiones;
	private JLayeredPane PestanaUsuarios;
	private JLayeredPane PestanaPersonajes;
	private JLayeredPane PestanaArmas;
	private JLayeredPane PestanaMapas;
	private JButton btnInsertarUsuarios;
	private JButton btnModificarUsuarios;
	private JButton btnEliminarUsuarios;
	private JScrollPane scrollPaneUsuarios;
	private JLabel lblBuscar;
	private JTextField txtUsuarios;
	private JButton btnLimpiarUsuarios;
	private JScrollPane scrollPaneMisiones;
	private JButton btnInsertarMisiones;
	private JButton btnModificarMisiones;
	private JButton btnEliminarMisiones;
	private JLabel label;
	private JTextField txtBuscarMisiones;
	private JButton btnLimpiarMisiones;
	private JScrollPane scrollPane_1;
	private JButton btnInsertarPersonajes;
	private JButton btnModificarPersonajes;
	private JButton btnEliminarPersonajes;
	private JLabel label_1;
	private JTextField txtPersonajes;
	private JButton btnLimpiarPersonajes;
	private JScrollPane scrollPaneArmas;
	private JButton btnInsertarArmas;
	private JButton btnModificarArmas;
	private JButton btnEliminarArmas;
	private JLabel label_2;
	private JTextField txtArmas;
	private JButton btnLimpiarArmas;
	private JScrollPane scrollPaneMapas;
	private JButton btnInsertarMapas;
	private JButton btnModificarMapas;
	private JButton btnEliminarMapas;
	private JLabel label_3;
	private JTextField txtMapas;
	private JButton btnLimpiarMapas;
	private TablaUsuarios tablaUsuarios;
	private TablaMisiones tablaMisiones;
	private TablaPersonajes tablaPersonajes;
	private TablaArmas tablaArmas;
	private TablaMapas tablaMapas;
	
	private JUsuarios jUsuarios;
	private JMisiones jMisiones;
	private JArmas jArmas;
	private JPersonajes jPersonajes;
	private JMapas jMapas;
	private JPanel panel;
	private JLabel lblFilas;
	private JLabel lbNum;
	private JLabel lblFilaActual;
	private JLabel lblUltimaAccion;
	private JLabel lbResultadoFilaActual;
	private JLabel lbResultadoAccion;
	private JMenu mnXml;
	private JMenuItem mntmExportarMapas;
	private boolean contarTab;
	private JMenu mnConsultas;
	private JMenuItem mntmCommit;
	private JMenuItem mntmRolkback;
	
	
	private void exportarMapas(){
		
		List<Mapa> mapas = VentanaInicio.db.query(Mapa.class);

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Document documento = null;
		
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			DOMImplementation dom = builder.getDOMImplementation();
			documento = dom.createDocument(null,  "xml", null);
			
			Element raiz = documento.createElement("Mapas");
			documento.getDocumentElement().appendChild(raiz);
			
			Element nodoEquipo = null, nodoDatos = null;
			
			Text texto = null;
			for (Mapa mapa : mapas) {
				nodoEquipo = documento.createElement("Mapa");
				raiz.appendChild(nodoEquipo);
				
				nodoDatos = documento.createElement("Nombre");
				nodoEquipo.appendChild(nodoDatos);
				
				texto = documento.createTextNode(mapa.getNombre());
				nodoDatos.appendChild(texto);
				
				nodoDatos = documento.createElement("Descripcion");
				nodoEquipo.appendChild(nodoDatos);
				
				texto = documento.createTextNode(mapa.getDescripcion());
				nodoDatos.appendChild(texto);
				
				nodoDatos = documento.createElement("Dimension");
				nodoEquipo.appendChild(nodoDatos);
				
				texto = documento.createTextNode(mapa.getDimension());
				nodoDatos.appendChild(texto);
			}
			
			Source source = new DOMSource(documento);
			Result resultado = new StreamResult(new File("Mapas.xml"));
			
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(source, resultado);
			
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerConfigurationException tce) {
			tce.printStackTrace();
		} catch (TransformerException te) {
			te.printStackTrace();
		}
		
	}
	
	private void cambiarTab(){
		
		if(contarTab){
			switch(tabbedPane.getSelectedIndex()){
			
			case 0:
				lbNum.setText(Integer.toString(tablaUsuarios.getRowCount()));
				if(tablaUsuarios.getSelectedRow() == -1){
					lbResultadoFilaActual.setText(Integer.toString(0));
				}
				break;
				
			case 1:
				lbNum.setText(Integer.toString(tablaPersonajes.getRowCount()));
				if(tablaPersonajes.getSelectedRow() == -1){
					lbResultadoFilaActual.setText(Integer.toString(0));
				}
				break;
				
			case 2:
				lbNum.setText(Integer.toString(tablaArmas.getRowCount()));
				if(tablaArmas.getSelectedRow() == -1){
					lbResultadoFilaActual.setText(Integer.toString(0));
				}
				break;
				
			case 3:
				lbNum.setText(Integer.toString(tablaMisiones.getRowCount()));
				if(tablaMisiones.getSelectedRow() == -1){
					lbResultadoFilaActual.setText(Integer.toString(0));
				}
				break;
				
			case 4:
				lbNum.setText(Integer.toString(tablaMapas.getRowCount()));
				if(tablaMapas.getSelectedRow() == -1){
					lbResultadoFilaActual.setText(Integer.toString(0));
				}
				break;
				
			default:
					break;
			}
		}
		
	}
	
	private void inicializar(){
		
		jUsuarios = new JUsuarios();
		jMisiones = new JMisiones();
		jMapas = new JMapas();
		jArmas = new JArmas();
		jPersonajes = new JPersonajes();
		
		contarTab = true;
		refrescarListas();
	}
	
	public void refrescarListas(){
		
		tablaUsuarios.listar();
		tablaArmas.listar();
		tablaMapas.listar();
		tablaMisiones.listar();
		tablaPersonajes.listar();
		
		cambiarTab();
	}
	
	private void habilitarUsuarios(){
		
		btnEliminarUsuarios.setEnabled(true);
		btnModificarUsuarios.setEnabled(true);
	}
	private void deshabilitarUsuarios(){
		
		btnEliminarUsuarios.setEnabled(false);
		btnModificarUsuarios.setEnabled(false);
	}
	
	private void habilitarPersonajes(){
		
		btnEliminarPersonajes.setEnabled(true);
		btnModificarPersonajes.setEnabled(true);
	}
	private void deshabilitarPersonajes(){
		
		btnEliminarPersonajes.setEnabled(false);
		btnModificarPersonajes.setEnabled(false);
	}
	
	private void habilitarArmas(){
		
		btnEliminarArmas.setEnabled(true);
		btnModificarArmas.setEnabled(true);
	}
	private void deshabilitarArmas(){
		
		btnEliminarArmas.setEnabled(false);
		btnModificarArmas.setEnabled(false);
	}
	
	private void habilitarMisiones(){
		
		btnEliminarMisiones.setEnabled(true);
		btnModificarMisiones.setEnabled(true);
	}
	private void deshabilitarMisiones(){
		
		btnEliminarMisiones.setEnabled(false);
		btnModificarMisiones.setEnabled(false);
	}
	
	private void habilitarMapas(){
		
		btnEliminarMapas.setEnabled(true);
		btnModificarMapas.setEnabled(true);
	}
	private void deshabilitarMapas(){
		
		btnEliminarMapas.setEnabled(false);
		btnModificarMapas.setEnabled(false);
	}

	public void mostrar(){
		
		this.setVisible(true);
	}
	
	public VentanaPrincipal(final VentanaInicio ventanaInicio) {

		this.ventanaInicio = ventanaInicio;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 533, 487);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnConexion = new JMenu("Conexion");
		menuBar.add(mnConexion);
		
		mntmDesconectar = new JMenuItem("Desconectar");
		mntmDesconectar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				setVisible(false);
				ventanaInicio.mostrar();
			}
		});
		mnConexion.add(mntmDesconectar);
		
		mnXml = new JMenu("XML");
		menuBar.add(mnXml);
		
		mntmExportarMapas = new JMenuItem("Exportar Mapas");
		mntmExportarMapas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				exportarMapas();
			}
		});
		mnXml.add(mntmExportarMapas);
		
		mnConsultas = new JMenu("Consultas");
		menuBar.add(mnConsultas);
		
		mntmCommit = new JMenuItem("Commit");
		mntmCommit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentanaInicio.db.commit();
			}
		});
		mnConsultas.add(mntmCommit);
		
		mntmRolkback = new JMenuItem("Rollback");
		mntmRolkback.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentanaInicio.db.rollback();
			}
		});
		mnConsultas.add(mntmRolkback);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		panelEstado = new JPanel();
		panelEstado.setPreferredSize(new Dimension(30, 30));
		contentPane.add(panelEstado, BorderLayout.SOUTH);
		panelEstado.setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		panelEstado.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		lblFilas = new JLabel("Filas: ");
		lblFilas.setBounds(10, 11, 46, 14);
		panel.add(lblFilas);
		
		lbNum = new JLabel("");
		lbNum.setBounds(66, 11, 46, 14);
		panel.add(lbNum);
		
		lblFilaActual = new JLabel("Fila actual: ");
		lblFilaActual.setBounds(122, 11, 69, 14);
		panel.add(lblFilaActual);
		
		lblUltimaAccion = new JLabel("Ultima Accion: ");
		lblUltimaAccion.setBounds(257, 11, 91, 14);
		panel.add(lblUltimaAccion);
		
		lbResultadoFilaActual = new JLabel("");
		lbResultadoFilaActual.setBounds(201, 11, 46, 14);
		panel.add(lbResultadoFilaActual);
		
		lbResultadoAccion = new JLabel("");
		lbResultadoAccion.setBounds(358, 11, 139, 14);
		panel.add(lbResultadoAccion);
		
		panelPrincipal = new JPanel();
		contentPane.add(panelPrincipal, BorderLayout.CENTER);
		panelPrincipal.setLayout(new BorderLayout(0, 0));
		
		tabbedPane = new JTabbedPane(SwingConstants.TOP);
		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				
				cambiarTab();
			}
		});
		panelPrincipal.add(tabbedPane, BorderLayout.CENTER);
		
		PestanaUsuarios = new JLayeredPane();
		tabbedPane.addTab("Usuarios", null, PestanaUsuarios, null);
		
		btnInsertarUsuarios = new JButton("Insertar");
		btnInsertarUsuarios.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				jUsuarios.insertar();
				jUsuarios.mostrar();
				if(!jUsuarios.isValidar()){
					deshabilitarUsuarios();
					return;
				}
				
				tablaUsuarios.insertar(jUsuarios.getUsuario());
				
				deshabilitarUsuarios();
				lbResultadoAccion.setText("Insertar Usuario");
				lbNum.setText(Integer.toString(tablaUsuarios.getRowCount()));
				if(tablaUsuarios.getSelectedRow() == -1){
					lbResultadoFilaActual.setText(Integer.toString(0));
				}
			}
		});
		btnInsertarUsuarios.setBounds(10, 10, 83, 23);
		PestanaUsuarios.add(btnInsertarUsuarios);
		
		btnModificarUsuarios = new JButton("Modificar");
		btnModificarUsuarios.setEnabled(false);
		btnModificarUsuarios.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int filaSeleccionada = 0;
				
				filaSeleccionada = tablaUsuarios.getSelectedRow();
				if (filaSeleccionada == -1){
					return;
				}
				
				String nombre = (String) tablaUsuarios.getValueAt(filaSeleccionada, 0);
				Usuario usuario = new Usuario();
				usuario.setNombre(nombre);
				
				ObjectSet<Usuario> resultado = VentanaInicio.db.queryByExample(usuario);
				usuario = resultado.next();
				
				jUsuarios.modificar(usuario);
				jUsuarios.mostrar();
				if(!jUsuarios.isValidar()){
					deshabilitarUsuarios();
					return;
				}
				
				tablaUsuarios.modificar(jUsuarios.getUsuario());
				
				deshabilitarUsuarios();
				lbResultadoAccion.setText("Modificar Usuario");
				lbNum.setText(Integer.toString(tablaUsuarios.getRowCount()));
				if(tablaUsuarios.getSelectedRow() == -1){
					lbResultadoFilaActual.setText(Integer.toString(0));
				}				
			}
		});
		btnModificarUsuarios.setBounds(103, 10, 83, 23);
		PestanaUsuarios.add(btnModificarUsuarios);
		
		btnEliminarUsuarios = new JButton("Eliminar");
		btnEliminarUsuarios.setEnabled(false);
		btnEliminarUsuarios.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int filaSeleccionada = 0;
				
				filaSeleccionada = tablaUsuarios.getSelectedRow();
				if (filaSeleccionada == -1){
					deshabilitarUsuarios();
					return;
				}
				
				String nombre = (String) tablaUsuarios.getValueAt(filaSeleccionada, 0);
				Usuario usuario = new Usuario();
				usuario.setNombre(nombre);
				
				ObjectSet<Usuario> resultado = VentanaInicio.db.queryByExample(usuario);
				usuario = resultado.next();
				
				tablaUsuarios.eliminar(usuario);
				
				deshabilitarUsuarios();
				lbResultadoAccion.setText("Eliminar Usuario");
				lbNum.setText(Integer.toString(tablaUsuarios.getRowCount()));
				if(tablaUsuarios.getSelectedRow() == -1){
					lbResultadoFilaActual.setText(Integer.toString(0));
				}
			}
		});
		btnEliminarUsuarios.setBounds(196, 10, 83, 23);
		PestanaUsuarios.add(btnEliminarUsuarios);
		
		scrollPaneUsuarios = new JScrollPane();
		scrollPaneUsuarios.setBounds(10, 39, 482, 310);
		PestanaUsuarios.add(scrollPaneUsuarios);
		
		tablaUsuarios = new TablaUsuarios();
		tablaUsuarios.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				
				lbResultadoFilaActual.setText(Integer.toString(tablaUsuarios.getSelectedRow() + 1));
				habilitarUsuarios();
			}
		});
		scrollPaneUsuarios.setViewportView(tablaUsuarios);
		
		lblBuscar = new JLabel("Buscar");
		lblBuscar.setBounds(289, 14, 46, 14);
		PestanaUsuarios.add(lblBuscar);
		
		txtUsuarios = new JTextField();
		txtUsuarios.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				if(txtUsuarios.getText().equals("")){
					tablaUsuarios.listar();
				}
				else{
					tablaUsuarios.listaAvanzada(txtUsuarios.getText());
				}
				
			}
		});
		txtUsuarios.setBounds(334, 11, 109, 20);
		PestanaUsuarios.add(txtUsuarios);
		txtUsuarios.setColumns(10);
		
		btnLimpiarUsuarios = new JButton("X");
		btnLimpiarUsuarios.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txtUsuarios.setText("");
				tablaUsuarios.listar();
			}
		});
		btnLimpiarUsuarios.setBounds(446, 10, 46, 23);
		PestanaUsuarios.add(btnLimpiarUsuarios);
		
		PestanaPersonajes = new JLayeredPane();
		tabbedPane.addTab("Personajes", null, PestanaPersonajes, null);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 40, 482, 309);
		PestanaPersonajes.add(scrollPane_1);
		
		tablaPersonajes = new TablaPersonajes();
		tablaPersonajes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				lbResultadoFilaActual.setText(Integer.toString(tablaPersonajes.getSelectedRow() + 1));
				habilitarPersonajes();
			}
		});
		scrollPane_1.setViewportView(tablaPersonajes);
		
		btnInsertarPersonajes = new JButton("Insertar");
		btnInsertarPersonajes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				jPersonajes.insertar();
				jPersonajes.mostrar();
				if(!jPersonajes.isValidar()){
					deshabilitarPersonajes();
					return;
				}
				
				tablaPersonajes.insertar(jPersonajes.getPersonaje());
				
				deshabilitarPersonajes();
				lbResultadoAccion.setText("Insertar Personaje");
				lbNum.setText(Integer.toString(tablaPersonajes.getRowCount()));
				if(tablaUsuarios.getSelectedRow() == -1){
					lbResultadoFilaActual.setText(Integer.toString(0));
				}
				
			}
		});
		btnInsertarPersonajes.setBounds(10, 11, 83, 23);
		PestanaPersonajes.add(btnInsertarPersonajes);
		
		btnModificarPersonajes = new JButton("Modificar");
		btnModificarPersonajes.setEnabled(false);
		btnModificarPersonajes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int filaSeleccionada = 0;
				
				filaSeleccionada = tablaPersonajes.getSelectedRow();
				if (filaSeleccionada == -1){
					
					return;
				}
				
				String nombre = (String) tablaPersonajes.getValueAt(filaSeleccionada, 0);
				Personaje personaje = new Personaje();
				personaje.setNombre(nombre);
				
				ObjectSet<Personaje> resultado = VentanaInicio.db.queryByExample(personaje);
				personaje = resultado.next();
				
				jPersonajes.modificar(personaje);
				jPersonajes.mostrar();
				if(!jPersonajes.isValidar()){
					deshabilitarPersonajes();
					return;
				}
				
				tablaPersonajes.modificar(jPersonajes.getPersonaje());
				
				deshabilitarPersonajes();
				lbResultadoAccion.setText("Modificar Usuario");
				lbNum.setText(Integer.toString(tablaPersonajes.getRowCount()));
				if(tablaPersonajes.getSelectedRow() == -1){
					lbResultadoFilaActual.setText(Integer.toString(0));
				}			
			}
		});
		btnModificarPersonajes.setBounds(103, 11, 83, 23);
		PestanaPersonajes.add(btnModificarPersonajes);
		
		btnEliminarPersonajes = new JButton("Eliminar");
		btnEliminarPersonajes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				int filaSeleccionada = 0;
				
				filaSeleccionada = tablaPersonajes.getSelectedRow();
				if (filaSeleccionada == -1){
					return;
				}
					
				
				String nombre = (String) tablaPersonajes.getValueAt(filaSeleccionada, 0);
				Personaje personaje = new Personaje();
				personaje.setNombre(nombre);
				
				ObjectSet<Personaje> resultado = VentanaInicio.db.queryByExample(personaje);
				personaje = resultado.next();
				
				tablaPersonajes.eliminar(personaje);
				
				deshabilitarPersonajes();
				lbResultadoAccion.setText("Eliminar Personaje");
				lbNum.setText(Integer.toString(tablaPersonajes.getRowCount()));
				if(tablaUsuarios.getSelectedRow() == -1){
					lbResultadoFilaActual.setText(Integer.toString(0));
				}
				
			}
		});
		btnEliminarPersonajes.setEnabled(false);
		btnEliminarPersonajes.setBounds(196, 11, 83, 23);
		PestanaPersonajes.add(btnEliminarPersonajes);
		
		label_1 = new JLabel("Buscar");
		label_1.setBounds(289, 15, 46, 14);
		PestanaPersonajes.add(label_1);
		
		txtPersonajes = new JTextField();
		txtPersonajes.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				if(txtPersonajes.getText().equals("")){
					tablaPersonajes.listar();
				}
				else{
					tablaPersonajes.listaAvanzada(txtPersonajes.getText());
				}
			}
		});
		txtPersonajes.setColumns(10);
		txtPersonajes.setBounds(334, 12, 109, 20);
		PestanaPersonajes.add(txtPersonajes);
		
		btnLimpiarPersonajes = new JButton("X");
		btnLimpiarPersonajes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tablaPersonajes.listar();
				txtPersonajes.setText("");
			}
		});
		btnLimpiarPersonajes.setBounds(446, 11, 46, 23);
		PestanaPersonajes.add(btnLimpiarPersonajes);
		
		PestanaArmas = new JLayeredPane();
		tabbedPane.addTab("Armas", null, PestanaArmas, null);
		
		scrollPaneArmas = new JScrollPane();
		scrollPaneArmas.setBounds(10, 40, 482, 309);
		PestanaArmas.add(scrollPaneArmas);
		
		tablaArmas = new TablaArmas();
		tablaArmas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				lbResultadoFilaActual.setText(Integer.toString(tablaArmas.getSelectedRow() + 1));
				habilitarArmas();
			}
		});
		scrollPaneArmas.setViewportView(tablaArmas);
		
		btnInsertarArmas = new JButton("Insertar");
		btnInsertarArmas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				jArmas.insertar();
				jArmas.mostrar();
				if(!jArmas.isValidar()){
					deshabilitarArmas();
					return;
				}
				
				tablaArmas.insertar(jArmas.getArma());
				
				deshabilitarArmas();
				lbResultadoAccion.setText("Insertar Arma");
				lbNum.setText(Integer.toString(tablaArmas.getRowCount()));
				if(tablaArmas.getSelectedRow() == -1){
					lbResultadoFilaActual.setText(Integer.toString(0));
				}
			}
		});
		btnInsertarArmas.setBounds(10, 11, 83, 23);
		PestanaArmas.add(btnInsertarArmas);
		
		btnModificarArmas = new JButton("Modificar");
		btnModificarArmas.setEnabled(false);
		btnModificarArmas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int filaSeleccionada = 0;
				
				filaSeleccionada = tablaArmas.getSelectedRow();
				if (filaSeleccionada == -1)
					return;
				
				String nombre = (String) tablaArmas.getValueAt(filaSeleccionada, 0);
				Arma arma = new Arma();
				arma.setNombre(nombre);
				
				ObjectSet<Arma> resultado = VentanaInicio.db.queryByExample(arma);
				arma = resultado.next();
				
				jArmas.modificar(arma);
				jArmas.mostrar();
				if(!jArmas.isValidar()){
					deshabilitarArmas();
					return;
				}
				
				tablaArmas.modificar(jArmas.getArma());
				
				deshabilitarArmas();
				lbResultadoAccion.setText("Modificar Arma");
				lbNum.setText(Integer.toString(tablaArmas.getRowCount()));
				if(tablaArmas.getSelectedRow() == -1){
					lbResultadoFilaActual.setText(Integer.toString(0));
				}	
			}
		});
		btnModificarArmas.setBounds(103, 11, 83, 23);
		PestanaArmas.add(btnModificarArmas);
		
		btnEliminarArmas = new JButton("Eliminar");
		btnEliminarArmas.setEnabled(false);
		btnEliminarArmas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int filaSeleccionada = 0;
				
				filaSeleccionada = tablaArmas.getSelectedRow();
				if (filaSeleccionada == -1){
					return;
				}
				
				String nombre = (String) tablaArmas.getValueAt(filaSeleccionada, 0);
				Arma arma = new Arma();
				arma.setNombre(nombre);
				
				ObjectSet<Arma> resultado = VentanaInicio.db.queryByExample(arma);
				arma = resultado.next();
				
				tablaArmas.eliminar(arma);
				
				deshabilitarArmas();
				lbResultadoAccion.setText("Eliminar Arma");
				lbNum.setText(Integer.toString(tablaArmas.getRowCount()));
				if(tablaArmas.getSelectedRow() == -1){
					lbResultadoFilaActual.setText(Integer.toString(0));
				}
			}
		});
		btnEliminarArmas.setBounds(196, 11, 83, 23);
		PestanaArmas.add(btnEliminarArmas);
		
		label_2 = new JLabel("Buscar");
		label_2.setBounds(289, 15, 46, 14);
		PestanaArmas.add(label_2);
		
		txtArmas = new JTextField();
		txtArmas.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(txtArmas.getText().equals("")){
					tablaArmas.listar();
				}
				else{
					tablaArmas.listaAvanzada(txtArmas.getText());
				}
			}
		});
		txtArmas.setColumns(10);
		txtArmas.setBounds(334, 12, 109, 20);
		PestanaArmas.add(txtArmas);
		
		btnLimpiarArmas = new JButton("X");
		btnLimpiarArmas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tablaArmas.listar();
				txtArmas.setText("");
			}
		});
		btnLimpiarArmas.setBounds(446, 11, 46, 23);
		PestanaArmas.add(btnLimpiarArmas);
		
		PestanaMisiones = new JLayeredPane();
		tabbedPane.addTab("Misiones", null, PestanaMisiones, null);
		
		scrollPaneMisiones = new JScrollPane();
		scrollPaneMisiones.setBounds(10, 40, 482, 309);
		PestanaMisiones.add(scrollPaneMisiones);
		
		tablaMisiones = new TablaMisiones();
		tablaMisiones.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				lbResultadoFilaActual.setText(Integer.toString(tablaMisiones.getSelectedRow() + 1));
				habilitarMisiones();
			}
		});
		scrollPaneMisiones.setViewportView(tablaMisiones);
		
		btnInsertarMisiones = new JButton("Insertar");
		btnInsertarMisiones.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				jMisiones.insertar();
				jMisiones.mostrar();
				if(!jMisiones.isValidar()){
					deshabilitarMisiones();
					return;
				}
				
				tablaMisiones.insertar(jMisiones.getMision());
				
				deshabilitarMisiones();
				lbResultadoAccion.setText("Insertar Mision");
				lbNum.setText(Integer.toString(tablaMisiones.getRowCount()));
				if(tablaMisiones.getSelectedRow() == -1){
					lbResultadoFilaActual.setText(Integer.toString(0));
				}
			}
		});
		btnInsertarMisiones.setBounds(10, 11, 83, 23);
		PestanaMisiones.add(btnInsertarMisiones);
		
		btnModificarMisiones = new JButton("Modificar");
		btnModificarMisiones.setEnabled(false);
		btnModificarMisiones.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int filaSeleccionada = 0;
				
				filaSeleccionada = tablaMisiones.getSelectedRow();
				if (filaSeleccionada == -1){
					return;
				}
				
				String nombre = (String) tablaMisiones.getValueAt(filaSeleccionada, 0);
				Mision mision = new Mision();
				mision.setNombre(nombre);
				
				ObjectSet<Mision> resultado = VentanaInicio.db.queryByExample(mision);
				mision = resultado.next();
				
				jMisiones.modificar(mision);
				jMisiones.mostrar();
				if(!jMisiones.isValidar()){
					deshabilitarMisiones();
					return;
				}
				
				tablaMisiones.modificar(jMisiones.getMision());
				
				deshabilitarMisiones();
				lbResultadoAccion.setText("Modificar Mision");
				lbNum.setText(Integer.toString(tablaMisiones.getRowCount()));
				if(tablaMisiones.getSelectedRow() == -1){
					lbResultadoFilaActual.setText(Integer.toString(0));
				}
			}
		});
		btnModificarMisiones.setBounds(103, 11, 83, 23);
		PestanaMisiones.add(btnModificarMisiones);
		
		btnEliminarMisiones = new JButton("Eliminar");
		btnEliminarMisiones.setEnabled(false);
		btnEliminarMisiones.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int filaSeleccionada = 0;
				
				filaSeleccionada = tablaMisiones.getSelectedRow();
				if (filaSeleccionada == -1){
					return;
				}
				
				String nombre = (String) tablaMisiones.getValueAt(filaSeleccionada, 0);
				Mision mision = new Mision();
				mision.setNombre(nombre);
				
				ObjectSet<Mision> resultado = VentanaInicio.db.queryByExample(mision);
				mision = resultado.next();
				
				tablaMisiones.eliminar(mision);
				
				deshabilitarMisiones();
				lbResultadoAccion.setText("Eliminar Mision");
				lbNum.setText(Integer.toString(tablaMisiones.getRowCount()));
				if(tablaMisiones.getSelectedRow() == -1){
					lbResultadoFilaActual.setText(Integer.toString(0));
				}
			}
		});
		btnEliminarMisiones.setBounds(196, 11, 83, 23);
		PestanaMisiones.add(btnEliminarMisiones);
		
		label = new JLabel("Buscar");
		label.setBounds(289, 15, 46, 14);
		PestanaMisiones.add(label);
		
		txtBuscarMisiones = new JTextField();
		txtBuscarMisiones.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(txtBuscarMisiones.getText().equals("")){
					tablaMisiones.listar();
				}
				else{
					tablaMisiones.listaAvanzada(txtBuscarMisiones.getText());
				}
			}
		});
		txtBuscarMisiones.setColumns(10);
		txtBuscarMisiones.setBounds(334, 12, 109, 20);
		PestanaMisiones.add(txtBuscarMisiones);
		
		btnLimpiarMisiones = new JButton("X");
		btnLimpiarMisiones.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tablaMisiones.listar();
				txtBuscarMisiones.setText("");
			}
		});
		btnLimpiarMisiones.setBounds(446, 11, 46, 23);
		PestanaMisiones.add(btnLimpiarMisiones);
		
		PestanaMapas = new JLayeredPane();
		tabbedPane.addTab("Mapas", null, PestanaMapas, null);
		
		scrollPaneMapas = new JScrollPane();
		scrollPaneMapas.setBounds(10, 40, 482, 309);
		PestanaMapas.add(scrollPaneMapas);
		
		tablaMapas = new TablaMapas();
		tablaMapas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				lbResultadoFilaActual.setText(Integer.toString(tablaMapas.getSelectedRow() + 1));
				habilitarMapas();
			}
		});
		scrollPaneMapas.setViewportView(tablaMapas);
		
		btnInsertarMapas = new JButton("Insertar");
		btnInsertarMapas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				jMapas.insertar();
				jMapas.mostrar();
				if(!jMapas.isValidar()){
					deshabilitarMapas();
					return;
				}
				
				tablaMapas.insertar(jMapas.getMapa());
				
				deshabilitarMapas();
				lbResultadoAccion.setText("Insertar Mapa");
				lbNum.setText(Integer.toString(tablaMapas.getRowCount()));
				if(tablaMapas.getSelectedRow() == -1){
					lbResultadoFilaActual.setText(Integer.toString(0));
				}
			}
		});
		btnInsertarMapas.setBounds(10, 11, 83, 23);
		PestanaMapas.add(btnInsertarMapas);
		
		btnModificarMapas = new JButton("Modificar");
		btnModificarMapas.setEnabled(false);
		btnModificarMapas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int filaSeleccionada = 0;
				
				filaSeleccionada = tablaMapas.getSelectedRow();
				if (filaSeleccionada == -1){
					return;
				}
				
				String nombre = (String) tablaMapas.getValueAt(filaSeleccionada, 0);
				Mapa mapa = new Mapa();
				mapa.setNombre(nombre);
				
				ObjectSet<Mapa> resultado = VentanaInicio.db.queryByExample(mapa);
				mapa = resultado.next();
				
				jMapas.modificar(mapa);
				jMapas.mostrar();
				if(!jMapas.isValidar()){
					deshabilitarMapas();
					return;
				}
				
				tablaMapas.modificar(jMapas.getMapa());
				
				deshabilitarMapas();
				lbResultadoAccion.setText("Modificar Mapa");
				lbNum.setText(Integer.toString(tablaMapas.getRowCount()));
				if(tablaMapas.getSelectedRow() == -1){
					lbResultadoFilaActual.setText(Integer.toString(0));
				}
			}
		});
		btnModificarMapas.setBounds(103, 11, 83, 23);
		PestanaMapas.add(btnModificarMapas);
		
		btnEliminarMapas = new JButton("Eliminar");
		btnEliminarMapas.setEnabled(false);
		btnEliminarMapas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int filaSeleccionada = 0;
				
				filaSeleccionada = tablaMapas.getSelectedRow();
				if (filaSeleccionada == -1){
					return;
				}
				
				String nombre = (String) tablaMapas.getValueAt(filaSeleccionada, 0);
				Mapa mapa = new Mapa();
				mapa.setNombre(nombre);
				
				ObjectSet<Mapa> resultado = VentanaInicio.db.queryByExample(mapa);
				mapa = resultado.next();
				
				tablaMapas.eliminar(mapa);
				
				deshabilitarMapas();
				lbResultadoAccion.setText("Eliminar Mapa");
				lbNum.setText(Integer.toString(tablaMapas.getRowCount()));
				if(tablaMapas.getSelectedRow() == -1){
					lbResultadoFilaActual.setText(Integer.toString(0));
				}
			}
		});
		btnEliminarMapas.setBounds(196, 11, 83, 23);
		PestanaMapas.add(btnEliminarMapas);
		
		label_3 = new JLabel("Buscar");
		label_3.setBounds(289, 15, 46, 14);
		PestanaMapas.add(label_3);
		
		txtMapas = new JTextField();
		txtMapas.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(txtMapas.getText().equals("")){
					tablaMapas.listar();
				}
				else{
					tablaMapas.listaAvanzada(txtMapas.getText());
				}
			}
		});
		txtMapas.setColumns(10);
		txtMapas.setBounds(334, 12, 109, 20);
		PestanaMapas.add(txtMapas);
		
		btnLimpiarMapas = new JButton("X");
		btnLimpiarMapas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tablaMapas.listar();
				txtMapas.setText("");
			}
		});
		btnLimpiarMapas.setBounds(446, 11, 46, 23);
		PestanaMapas.add(btnLimpiarMapas);
		
		setLocationRelativeTo(null);
		
		inicializar();
	}
}
