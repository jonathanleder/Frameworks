package leder.utilizacion;

import leder.framework.Accion;

public class AccionUno implements Accion {

	public void ejecutar() {
		System.out.print("Ejecutando AccionUno...");
	}

	public String nombreItemMenu() {
		return "Accion 1";
	}

	public String descripcionItemMenu() {
		return "Esto Ejecuta la accion numero 1...";
	}

}