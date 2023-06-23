package leder.main;

import leder.framework.Menu;

//Mostrar como se instancia y su uso del framework

public class Main {

	public static void main(String[] args) {

		Menu menu = new Menu("/accions.properties");
		menu.init();
	}
}
