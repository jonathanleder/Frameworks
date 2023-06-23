package leder.framework;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Objects;
import java.util.Properties;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public class ArchivoCargarDatos implements CargarAcciones {
	private String path;

	public ArchivoCargarDatos(String path) {
		if (path.isEmpty() || path.isBlank()) {
			throw new RuntimeException("El nombre del archivo no puede estar vacio");
		}
		this.path = Objects.requireNonNull(path);
	}

	@Override
	public HashMap<Integer, Accion> cargar() {
		if (esJson()) {
			return cargarConFormatoJson();
		}
		return cargarConFormatoNormal();
	}

	@Override
	public boolean esJson() {
		return this.path.endsWith(".json");
	}

	private HashMap<Integer, Accion> cargarConFormatoJson() {
		HashMap<Integer, Accion> lista = new HashMap<>();
		Gson gson = new GsonBuilder().create();
		int i = 0;

//		try (InputStream config = getClass().getResourceAsStream(this.path)) {
		try (BufferedReader reader = new BufferedReader(new FileReader(this.path))) {
			JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
			JsonArray jsonArray = jsonObject.getAsJsonArray("accions");
			for (JsonElement jsonElement : jsonArray) {
				String clase = jsonElement.getAsString();
				Accion nuevaAccion = (Accion) Class.forName(clase).getDeclaredConstructor().newInstance();
				lista.put(i, nuevaAccion);
				i++;
			}
		} catch (IOException | JsonSyntaxException | JsonIOException | InstantiationException | IllegalAccessException
				| ClassNotFoundException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return lista;
	}

	private HashMap<Integer, Accion> cargarConFormatoNormal() {
		Properties properties = new Properties();

		HashMap<Integer, Accion> lista = new HashMap<>();

		try (InputStream config = getClass().getResourceAsStream(this.path);) {
			properties.load(config);

			String archivo = properties.getProperty("accions");

			String[] clases = archivo.split(";");

			int i = 0;

			for (String string : clases) {
				Accion nuevaAccion = (Accion) Class.forName(string).getDeclaredConstructor().newInstance();
				lista.put(i, nuevaAccion);
				i++;
			}

		} catch (IOException | InstantiationException | IllegalAccessException | ClassNotFoundException
				| IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			System.out.println("Ocurrio un error inesperado " + e.getMessage());
		}

		return lista;
	}

	@Override
	public int devolverCantidadThreads() {
		int threads = 1;
		Gson gson = new GsonBuilder().create();
		try (BufferedReader reader = new BufferedReader(new FileReader(this.path))) {
			JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
			threads = jsonObject.get("max-threads").getAsInt();
		} catch (IOException | JsonSyntaxException | JsonIOException | IllegalArgumentException | SecurityException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return threads;
	}
}