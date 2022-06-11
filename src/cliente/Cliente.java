package cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.Scanner;

// Importante: Checkear enconding -> UTF8

public class Cliente {
	
	private HiloEscuchador escuchador;
	private String nombre;
	private DataInputStream entrada;
	private DataOutputStream salida;
	private Socket socket;
	private Scanner teclado;
	private boolean conectado;
	
	public Cliente(String ip, int puerto) throws UnknownHostException, IOException {
		socket = new Socket(ip, puerto);
		entrada = new DataInputStream(socket.getInputStream());
		salida = new DataOutputStream(socket.getOutputStream());
		
		escuchador = new HiloEscuchador(entrada, this);
		
		System.out.print("Ingresar nombre: ");
		teclado = new Scanner(System.in);
		String nombre = teclado.nextLine();
		this.nombre = nombre;
		salida.writeUTF(this.nombre);
		
		String respuesta = entrada.readUTF();
		if (respuesta.equals("aceptado")) {
			System.out.println("conectado");
			conectado = true;
			start();
		}
		else {
			entrada.close();
			salida.close();
			teclado.close();
			System.out.println("conexion rechazada");
		}
		
	}
	
	public void start() {
		String mensaje;
		escuchador.start();
		while (conectado) {
			mensaje = teclado.nextLine();
			try {
				salida.writeUTF(mensaje);
			} catch (IOException e) {
				System.out.println("No se pudo enviar el mensaje");
			}
		}
	}
	
	public synchronized void cerrar() {
		conectado = false;
		try {
			entrada.close();
			salida.close();
		} catch (IOException e) {
			System.out.println("No se pudo cerrar la entrada o la salida");
		}
		teclado.close();
	}

	public static void main(String[] args) {
		try {
			new Cliente("localhost", 20000);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
