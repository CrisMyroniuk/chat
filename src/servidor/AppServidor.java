package servidor;

import java.io.IOException;
import java.util.Scanner;

public class AppServidor {
	
	public static void main(String[] args) throws IOException {
		
		//VentanaServidor servidor =  new VentanaServidor();
		int puerto;
		System.out.println("Ingrese un puerto para abrir el servidor");
		String entradaTeclado = "";
	    Scanner entradaEscaner = new Scanner (System.in);
	    entradaTeclado = entradaEscaner.nextLine ();
	    puerto = Integer.parseInt(entradaTeclado);
	    
	    if(puerto < 1000 || puerto > 60000) {
	    	System.out.println("Utilice un puerto que este en un rango seguro de conflicto.");
			return;
		}
	    
	    Servidor servidor = new Servidor(puerto);
	    servidor.abrirServidor();
	    
	}
}
