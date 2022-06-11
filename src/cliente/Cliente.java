package cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

// Importante: Checkear enconding -> UTF8

public class Cliente {
	
	private String nombre;
	
	public Cliente(String ip, int puerto) throws UnknownHostException, IOException {
		// Conecta a un Server. Si no esta activo da Exception:
		// java.net.ConnectException
		Socket socket = new Socket(ip, puerto);

		// Flujos de información
		DataInputStream entrada = new DataInputStream(socket.getInputStream());
		DataOutputStream salida = new DataOutputStream(socket.getOutputStream());

		// Recibir mensaje
		//System.out.println("Soy el cliente número: " + entrada.readUTF());
		
		System.out.println("Mi nombre es: ");

		// Enviar mensaje escrito por consola
		Scanner scanner = new Scanner(System.in);
		String nombre = scanner.nextLine();
		
		this.nombre = nombre;
		salida.writeUTF(this.nombre);
		System.out.println("Escribe algo...: ");
		String mensaje = "";
		while(!mensaje.equals("Salir")) {
			mensaje = scanner.nextLine();
			salida.writeUTF(mensaje);
			System.out.println("Escribe algo...: ");
		}
		
		salida.writeUTF(nombre + " ha salido");
		
		// Cierre de recursos
		scanner.close();
		entrada.close();
		salida.close();
		socket.close();
		System.out.println("Me cierro");
	}

	public static void main(String[] args) {
		try {
			new Cliente("localhost", 20000);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
