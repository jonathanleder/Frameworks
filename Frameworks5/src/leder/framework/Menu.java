package leder.framework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Menu {
	private CargarAcciones acciones;
	private Pantalla pantalla;
	private HashMap<Integer, Accion> listaAcciones;

	public Menu(String path) {
		Objects.requireNonNull(path);
		this.acciones = new ArchivoCargarDatos(path);
	}

	public void init() {
		llenarListaAcciones();
		menu();
	}

	private void menu() {

		agregarOpcionSalir(this.listaAcciones.size() + 1);
		this.pantalla = new Pantalla(convertirALista(), obtenerHilos());
		this.pantalla.mostrar();
	}

	private void llenarListaAcciones() {
		this.listaAcciones = acciones.cargar();
	}

	private void agregarOpcionSalir(int opcion) {

		listaAcciones.put(listaAcciones.size(), new AccionSalir());
	}

	private List<Accion> convertirALista() {
		List<Accion> lista = new ArrayList<>();

		for (int i = 0; i < this.listaAcciones.size(); i++) {
			lista.add(this.listaAcciones.get(i));
		}

		return lista;
	}

	private int obtenerHilos() {
		if (acciones.esJson()) {
			return acciones.devolverCantidadThreads();
		}
		return 1;
	}

}