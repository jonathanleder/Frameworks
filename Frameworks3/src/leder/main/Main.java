package leder.main;
//Mostrar como se instancia y su uso del framework
import leder.framework.Menu;

public class Main {

	public static void main(String[] args) {

		Menu menu = new Menu("/accions.properties");
		menu.init();
	}
}
