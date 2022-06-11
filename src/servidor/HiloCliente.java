package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class HiloCliente extends Thread{
	
	private String nombre;
	private DataInputStream entrada;
	private DataOutputStream salida;
	private Servidor servidor;
	
	public HiloCliente(String nombre, DataInputStream entrada, DataOutputStream salida, Servidor servidor) throws IOException {
		this.nombre = nombre;
		this.servidor = servidor;

		this.entrada = entrada;
		this.salida = salida;
	}
	
	public void run(){
		String mensaje;
		
		while(true) {
			try {
				mensaje = entrada.readUTF();
				Mensaje nuevo = new Mensaje(nombre, mensaje);
				System.out.println(nuevo);
			} catch(IOException e) {
				System.out.println("Se fue " + nombre);
				salir();
				break;
			}
			
			if (mensaje.equals("/salir")) {
				salir();
				break;
			}
			
			servidor.addMensaje(new Mensaje(nombre, mensaje));
		}
		
		try {
			entrada.close();
			salida.close();
		} catch (IOException e) {
			System.out.println("No se pudo cerrar esta mierda");
		}
	}
	
	public synchronized DataOutputStream getSalida() {
		return salida;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	private void salir() {
		servidor.salir(nombre);
	}
}
