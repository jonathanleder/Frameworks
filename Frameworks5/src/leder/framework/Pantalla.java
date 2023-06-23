package leder.framework;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.CheckBox;
import com.googlecode.lanterna.gui2.Component;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

public class Pantalla {
	private List<Accion> listaAcciones;
	private int maxthreads;

	public Pantalla(List<Accion> listaAcciones, int maxthreads) {
		this.listaAcciones = Objects.requireNonNull(listaAcciones);
		this.maxthreads = Objects.requireNonNull(maxthreads);
	}

	public void mostrar() {
		DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
		Screen screen = null;
		Window window = new BasicWindow("Bienvenido, ¿Que desea hacer?");
		WindowBasedTextGUI textGUI = null;
		try {
			screen = terminalFactory.createScreen();
			screen.startScreen();
			textGUI = new MultiWindowTextGUI(screen);
		} catch (Exception e) {

		}

		Panel panel = new Panel();
		for (Accion accion : listaAcciones) {
			panel.addComponent(new CheckBox(accion.nombreItemMenu()));
			panel.addComponent(new Label(accion.descripcionItemMenu())).setLayoutData(
					GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.END));
		}

		panel.addComponent(new Button("Confirmar", new Runnable() {
			public void run() {
				List<Callable<AdapterEjecutar>> accionesSelecionadas = new ArrayList<>();
				int i = 0;
				for (Component component : panel.getChildren()) {
					if (component instanceof CheckBox) {
						CheckBox check = (CheckBox) component;

						if (check.isChecked()) {
							Callable<AdapterEjecutar> callable = new AdapterEjecutar(listaAcciones.get(i));
							accionesSelecionadas.add(callable);
						}
						i++;
					}
				}

				try {
					if (maxthreads == 1) {
						for (Callable<AdapterEjecutar> callable : accionesSelecionadas) {
							callable.call();

						}
					} else {
						ExecutorService executor = Executors.newFixedThreadPool(maxthreads);

						executor.invokeAll(accionesSelecionadas);

						executor.shutdown();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}));

		window.setComponent(panel);
		textGUI.addWindowAndWait(window);
	}
}