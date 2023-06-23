package leder.utilizacion;

import leder.framework.Accion;

public class AccionDos implements Accion {

	public void ejecutar() {
		System.out.print("Ejecutando AccionDos...");
	}

	public String nombreItemMenu() {
		return "Accion 2";
	}

	public String descripcionItemMenu() {
		return "Esto trae las primeras diez personas de la BD...";
	}

}