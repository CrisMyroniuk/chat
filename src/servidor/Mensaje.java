package servidor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Mensaje {
	private String mensaje;
	private String usuario;
	private Date fechaYHora;
	private DateFormat formato;
	
	public Mensaje(String usuario, String mensaje) {
		this.usuario = usuario;
		this.mensaje = mensaje;
		fechaYHora = new Date();
		formato = new SimpleDateFormat("dd/MM HH:mm");
	}
	
	public String toString() {
		return "[" + formato.format(fechaYHora) + "]" + "<" + usuario + ">: " + mensaje;
	}
}
