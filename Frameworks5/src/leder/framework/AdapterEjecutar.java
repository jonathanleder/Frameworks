package leder.framework;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class AdapterEjecutar implements Callable<AdapterEjecutar> {
	private Accion accion;

	public AdapterEjecutar(Accion accion) {
		this.accion = accion;
	}

	public AdapterEjecutar call() throws Exception {
		// TODO Auto-generated method stub
		this.accion.ejecutar();
		TimeUnit.MILLISECONDS.sleep(2000);
		return null;
	}
}