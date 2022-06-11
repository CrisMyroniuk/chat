package cliente;

import java.io.DataInputStream;
import java.io.IOException;

public class HiloEscuchador extends Thread{

	private DataInputStream entrada;
	private Cliente cliente;
	private boolean conectado;
	
	public HiloEscuchador(DataInputStream entrada, Cliente cliente) {
		this.entrada = entrada;
		this.cliente = cliente;
	}
	
	public void run () {
		conectado = true;
		String mensaje;
		while(conectado) {
			try {
				mensaje = entrada.readUTF();
				System.out.println(mensaje);
			} catch (IOException e) {
				System.out.println("Se cerro la conexion con el servidor");
				salir();
			}
		}
	}
	
	private void salir() {
		cliente.cerrar();
	}
}
