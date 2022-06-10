package cliente;

import java.util.Scanner;

public class VentanaCliente2 {


	private int puerto;
	private String ip = "127.0.0.1";
	private Cliente cliente;
	private VentanaCliente2 instancia;
	private String nombre;
	
	public VentanaCliente2() {
		this.instancia = this;
		
		this.ingresarDatos();
		
		if(cliente == null) {
			cliente = new Cliente(this,ip,puerto);
		}
		
		cliente.conectar(this.nombre);
	}
	
	public void ingresarDatos() {
		System.out.println("Ingrese un puerto :");
		String entradaTeclado = "";
		Scanner entradaEscaner = new Scanner(System.in);
		entradaTeclado = entradaEscaner.nextLine();
		puerto = Integer.parseInt(entradaTeclado);
		
		System.out.println("Ingrese su nombre :");
		entradaTeclado = entradaEscaner.nextLine();
		nombre = entradaTeclado;
		
	}
	
	
	
}
