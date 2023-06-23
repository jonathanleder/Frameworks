package leder.utilizacion;

import leder.framework.Accion;

public class AccionTres implements Accion {

	public void ejecutar() {
		System.out.print("Ejecutando Accion Tres...\n");
	}

	public String nombreItemMenu() {
		return "Accion 3";
	}

	public String descripcionItemMenu() {
		return "Esto ejecuta la accion numero tres...";
	}

}
