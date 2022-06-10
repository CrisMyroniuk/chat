package servidor;

import java.io.IOException;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class ServidorChat {
	public int puerto;
	
	public ServidorChat ventana;
	
	public Servidor servidor;
	public void ingresarDatos() {
		System.out.println("Ingrese un puerto para abrir el servidor");
		String entradaTeclado = "";
	    Scanner entradaEscaner = new Scanner (System.in);
	    entradaTeclado = entradaEscaner.nextLine();
	    puerto = Integer.parseInt(entradaTeclado);
	}
	
	public ServidorChat() throws IOException {
		this.ventana = this;
	    ingresarDatos();
		if (servidor == null) {
			
			System.out.println(puerto);
			servidor = new Servidor(puerto, ventana);
		}
		
		if(!servidor.estaAbierto()) {
			servidor.abrirServidor();
		}else {
			servidor.cerrarServidor();
			servidor = null;
		}
		
	}
	
    
    
    
}
