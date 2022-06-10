package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class Servidor extends Thread{
	private ServerSocket servidor;
	private Servidor instancia;
	private HashMap<String, HiloServidor> usuarios;
	//private ArrayList<String> usuarios = new ArrayList<>();
	private HashMap<String, Sala> salas;
	private boolean abierto = false;
	
	public Servidor(int puerto) throws IOException {
		servidor = new ServerSocket(puerto);
		usuarios = new HashMap<String, HiloServidor>();
		//usuarios = new ArrayList<String>();
		this.instancia = this;
	}

	@Override
	public void run() {
		abierto = true;
		
		while(abierto) {
			try {
				Socket usuario = servidor.accept();
				
				new HiloServidor(instancia, usuario).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean estaAbierto() {
		return abierto;
	}

	public void abrirServidor() {
		this.start();
	}
	
	public void cerrarServidor() {
		abierto = false;
		servidor = null;
		usuarios = null;
	}
	
	public boolean existeUsuario(String nombre) {
		return this.usuarios.containsKey(nombre);
	}
	
	public boolean existeUsuarioEnSala(String nombreSala, String nombreUsuario) {
		if( this.salas.containsKey(nombreSala))
			return this.salas.get(nombreSala).existeUsuario(nombreUsuario);
		
		return false;
	}
	
	public void agregarUsuario(String nombre, HiloServidor hilo) {
		this.usuarios.put(nombre, hilo);
	}
	
	public void agregarUsuarioSala(String nombreSala, String nombreUsuario, HiloServidor hilo) {
		this.salas.get(nombreSala).agregarUsuario(nombreUsuario, hilo);
	}
	
	public void enviarSalas() throws IOException {
		String mensaje = "CargarSalaS|";
		
		for(String sala : this.salas.keySet()) {
			mensaje+=
					sala+ 
					" - Usuarios: " + 
					this.salas.get(sala).cantidadUsuarios()+
					"|";
		}
		
		enviarMensajeATodos(mensaje);
	}

	
	public Set<String> obtenerUsuariosSala(String nombre){
		return this.salas.get(nombre).obtenerUsuarios();
	}
	
	public Collection<HiloServidor> obtenerHilosUsuarios(){
		return this.usuarios.values();
	}
	
	public void eliminarUsuario(String nombre) {
		this.usuarios.remove(nombre);
	}
	
	public void enviarMensajeATodos(String mensaje) {
		for(HiloServidor hilo : obtenerHilosUsuarios()) {
			hilo.enviarMensaje(mensaje);
		}
	}
}
