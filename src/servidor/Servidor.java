package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;

public class Servidor {
	
	private LinkedList<Mensaje> historial = new LinkedList<Mensaje>();
	private HashMap<String, HiloCliente> clientes = new HashMap<String, HiloCliente>();
	private Enviador enviador;

	public Servidor(int puerto) throws IOException {
		try (ServerSocket servidor = new ServerSocket(puerto)) {
			enviador = new Enviador(this);
			
			System.out.println("Server iniciado");
			
			while(true) {
				System.out.println("esperando conexion");
				Socket socket = servidor.accept();
				DataInputStream entrada = new DataInputStream(socket.getInputStream());
				DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
				HiloCliente nuevo;
				
				String nombre = entrada.readUTF();
				if (!clientes.containsKey(nombre)) {
					salida.writeUTF("aceptado");
					nuevo = new HiloCliente(nombre, entrada, salida, this);
					clientes.put(nombre, nuevo);
					enviador.actualizarMensajes(nombre);
					addMensaje(new Mensaje("info", nombre + " entro al chat"));
					System.out.println("Conectado cliente: " + nombre);
					nuevo.start();
				} else { 
					System.out.println("Se rechazo la conexion a" + nombre);
				}
			}
		}
	}
	
	public synchronized void addMensaje(Mensaje mensajeNuevo) {
		historial.add(mensajeNuevo);
		enviador.enviarATodos(mensajeNuevo);
	}
	
	public synchronized void salir(String usuario) {
		Mensaje mensajeNuevo = new Mensaje("info", usuario + " salio");
		historial.add(mensajeNuevo);
		clientes.remove(usuario);
		enviador.enviarATodos(mensajeNuevo);
	}

	public static void main(String[] args) {
		try {
			new Servidor(20000);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public LinkedList<Mensaje> getHistorial() {
		return historial;
	}
	
	public HashMap<String, HiloCliente> getClientes() {
		return clientes;
	}

}
