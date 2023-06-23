package leder.framework;

import java.util.HashMap;

public interface CargarAcciones {
	HashMap<Integer, Accion> cargar();

	boolean esJson();

	int devolverCantidadThreads();
}