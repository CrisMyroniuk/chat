package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class HiloServidor extends Thread{
	private String nombre;
	private Socket socket; 
	private Servidor servidor;
	
	public HiloServidor(Servidor servidor, Socket socket) {
		this.socket = socket;
		this.servidor = servidor;
	}
	
	@Override
	public void run() {
		try {
			String mensaje = new DataInputStream(socket.getInputStream()).readUTF();
			
			while(!mensaje.equals("SaliR")) {
				procesarMensaje(mensaje);
				mensaje = new DataInputStream(socket.getInputStream()).readUTF();
			}
			
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void procesarMensaje(String mensaje) throws IOException {
		String[] cadena = mensaje.split("\\|");
		
		switch(cadena[0]){
			case "LogiN":
				if(!servidor.existeUsuario(cadena[1])) {
					enviarMensaje("LogiNOK|"+cadena[1]);
					this.nombre = cadena[1];
					servidor.agregarUsuario(nombre, this);
					servidor.enviarSalas();
				}else {
					enviarMensaje("LogiNErroR");
				}
				break;

			case "MensajeS":
				servidor.enviarMensajeATodos(mensaje);
//				if(servidor.existeSala(cadena[1]) && servidor.existeUsuarioEnSala(cadena[1], cadena[2])) {
//					servidor.enviarMensajeATodos(cadena[1], mensaje);
//				}else {
//					enviarMensaje("MensajeSErroR");
//				}
				break;
				
			case "DesconectaR":
				enviarMensaje("SaliR");
				servidor.eliminarUsuario(nombre);
				break;
				
			default:
				break;
		}
	}
	
	public void enviarMensaje(String mensaje) {
		try {
			new DataOutputStream(socket.getOutputStream()).writeUTF(mensaje);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
