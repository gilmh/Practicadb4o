package org.hectordam.servidor;

import com.db4o.ObjectServer;
import com.db4o.cs.Db4oClientServer;

/**
 * Inicia un servidor db4o que puede ser utilizado por múltiples clientes
 * de forma concurrente a través de la red
 * @author Santi
 *
 */
public class Servidor {
	
	public static void main(String args[]) {
		
		new Servidor().iniciarServidor();
	}
	
	public void iniciarServidor() {
		
		synchronized (this) {
			final ObjectServer servidor = Db4oClientServer.openServer("JuegoDb4o", 1990);
			servidor.grantAccess("usuario", "contrasena");
			
			try {
				wait(Long.MAX_VALUE);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}
