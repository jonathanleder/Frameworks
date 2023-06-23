package leder.framework;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

public class Menu {

	private String filePath;
	private Map<Integer, Accion> accions;

	public Menu(String path) {
		this.filePath = path;
		this.accions = new HashMap<>();
	}

	public final void init() {

		this.buscarAccions();

		try {
			Terminal terminal = new DefaultTerminalFactory().createTerminal();
			Screen screen = new TerminalScreen(terminal);
			screen.startScreen();

			mostrarMenu(screen);

			screen.stopScreen();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void mostrarMenu(Screen screen) throws IOException {
		screen.clear();

		TextGraphics textGraphics = screen.newTextGraphics();

		TextColor colorTexto = TextColor.ANSI.GREEN;
		TextColor colorFondo = TextColor.ANSI.BLACK;

		textGraphics.setForegroundColor(colorTexto);
		textGraphics.setBackgroundColor(colorFondo);

		textGraphics.putString(0, 0, "Bienvenido, estas son sus opciones:");
		int row = 2;
		for (Map.Entry<Integer, Accion> entry : accions.entrySet()) {
			String optionText = entry.getKey() + ". " + entry.getValue().nombreItemMenu() + " ("
					+ entry.getValue().descripcionItemMenu() + ")";
			textGraphics.putString(0, row++, optionText);
		}

		screen.refresh();

		leerOpcionIngresada(screen);
	}

	private void leerOpcionIngresada(Screen screen) throws IOException {
		KeyStroke keyStroke;

		boolean opcionValida = false;

		while (!opcionValida) {
			keyStroke = screen.readInput();

			if (keyStroke.getKeyType() == KeyType.Character) {
				char character = keyStroke.getCharacter();
				int opcion = Character.getNumericValue(character);

				if (accions.containsKey(opcion)) {
					accions.get(opcion).ejecutar();
					System.out.println("Finalizada.\n");
					opcionValida = true;
				} else {
					System.out.println("Seleccione una opci�n v�lida.");
				}
			}
		}

		mostrarMenu(screen);
	}

	private void buscarAccions() {

		Properties prop = new Properties();

		try (InputStream configFile = getClass().getResourceAsStream(this.filePath);) {

			prop.load(configFile);

			String clase = prop.getProperty("accions");

			String[] clases = clase.split(";");

			for (int i = 0; i < clases.length; i++) {

				Accion accionTemp = (Accion) Class.forName(clases[i]).getDeclaredConstructor().newInstance();

				this.accions.put((i + 1), accionTemp);
			}

			this.accions.put(clases.length + 1, new AccionSalir());

		} catch (Exception e) {
			throw new RuntimeException("Ocurrio un error con el archivo .config: " + e.getMessage());
		}

	}

}