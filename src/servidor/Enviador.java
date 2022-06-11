package servidor;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

public class Enviador {
	
	private LinkedList<Mensaje> historial;
	private HashMap<String, HiloCliente> clientes;
	
	public Enviador(Servidor servidor) {
		historial = servidor.getHistorial();
		clientes = servidor.getClientes();
	}
	
	public synchronized void enviarATodos(Mensaje mensajeNuevo) {
		for (HiloCliente cliente : clientes.values()) {
			try {
				cliente.getSalida().writeUTF(mensajeNuevo.toString());
			} catch (IOException e) {
				System.out.println("No se pudo enviar un mensaje a " + cliente.getNombre());
			}
		}
	}
	
	public synchronized void actualizarMensajes(String usuario) {
		HiloCliente cliente = clientes.get(usuario);
		DataOutputStream salida = cliente.getSalida();
		
		for (Mensaje mensaje : historial) {
			try {
				salida.writeUTF(mensaje.toString());
			} catch (IOException e) {
				System.out.println("No se pudo enviar un mensaje a " + cliente.getNombre());
			}
		}
	}
}
