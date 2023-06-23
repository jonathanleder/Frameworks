package leder.main;

import java.io.IOException;

//Mostrar como se instancia y su uso del framework
import leder.framework.Menu;

public class Main {

	public static void main(String[] args) throws IOException, Exception {

//		"C:\\\\\\\\Users\\\\\\\\jonyl\\\\\\\\Documents\\\\\\\\POO2\\\\\\\\Frameworks4\\\\\\\\resources\\\\\\\\accions.json"
		Menu menu = new Menu("C:\\\\Users\\\\jonyl\\\\Documents\\\\POO2\\\\Frameworks4\\\\resources\\\\accions.json");
		menu.init();
	}
}
