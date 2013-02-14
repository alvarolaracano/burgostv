package com.alvarolara.burgostv.utiles;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

/**
 * Utilidades Varias.
 * @author Alvaro Lara Cano
 *
 */
public class Utilidades {
	
	/**
	 * Constantes para las URLS.
	 * 
	 */
	public static final String URL_NOTICIAS = "http://www.burgostv.es/android/xml-noticias.php";
	public static final String URL_REPORTAJES = "http://www.burgostv.es/android/xml-reportajes.php";
	public static final String URL_MAS_DEPORTE = "http://www.burgostv.es/android/xml-mas-deporte.php";
	public static final String URL_VIDEOENCUENTRO = "http://www.burgostv.es/android/xml-burgos-en-persona.php";
	public static final String URL_DIRECTO = "http://www.burgostv.es/android/xml-directo.php";
	public static final String URL_BUSCAR = "http://www.burgostv.es/android/xml-buscar.php";
	
	
	/**
	 * Definiciones XML para el STREAMING.
	 */
	public static final String KEY_ITEM_DIRECTO = "directo";
	public static final String KEY_STREAMING = "streaming";
	public static final String KEY_URL_STREAMING = "url_streaming";
	public static final String KEY_URL_STREAM = "url_stream";
	
	/**
	 * Definiciones XML para un OBJETO.
	 */
	public static final String KEY_ITEM_OBJETO = "item";
	public static final String KEY_TITULO = "titulo";
	public static final String KEY_DESCRIPCION = "descripcion";
	public static final String KEY_URL_FOTO = "url_foto";
	public static final String KEY_URL_VIDEO = "url_video";
	public static final String KEY_FECHA = "fecha";
	

	/**
	 * Funcion que convierte milisegundos a formato Timer.
	 * Horas:Minutos:Segundos
	 * */
	public String milisegundosATimer(long milisegundos) {
		String tiempoFinal = "";
		String secondsString = "";

		// Convertir la duración total a tiempo.
		int horas = (int) (milisegundos / (1000 * 60 * 60));
		int minutos = (int) (milisegundos % (1000 * 60 * 60)) / (1000 * 60);
		int segundos = (int) ((milisegundos % (1000 * 60 * 60)) % (1000 * 60) / 1000);
		// Anadir horas si las hay.
		if (horas > 0) {
			tiempoFinal = horas + ":";
		}

		// Prepending 0 to seconds if it is one digit
		if (segundos < 10) {
			secondsString = "0" + segundos;
		} else {
			secondsString = "" + segundos;
		}

		tiempoFinal = tiempoFinal + minutos + ":" + secondsString;

		// Devolver el tiempoFinal.
		return tiempoFinal;
	}

	/**
	 * Porcentaje de progreso.
	 * 
	 * @param duracionActual
	 * @param duracionTotal
	 * */
	public int porcentajeProgreso(long duracionActual, long duracionTotal) {
		Double porcentaje = (double) 0;

		long segundosActuales = (int) (duracionActual / 1000);
		long segundostTotales = (int) (duracionTotal / 1000);

		// Calculamos el porcejtaje.
		porcentaje = (((double) segundosActuales) / segundostTotales) * 100;

		// Devolvemos el porcentaje.
		return porcentaje.intValue();
	}

	/**
	 * Cambia de progreso a Timer.
	 * 
	 * @param progreso
	 *            progreso del video
	 * @param duracionTotal
	 *            duracion actual en milisegundos.
	 * */
	public int progresoATimer(int progreso, int duracionTotal) {
		int duracionActual = 0;
		duracionTotal = (int) (duracionTotal / 1000);
		duracionActual = (int) ((((double) progreso) / 100) * duracionTotal);

		// Duracion actual en milisegundos.
		return duracionActual * 1000;
	}
	
	
	/**
	 * Funcion que comprueba si hay internet o no.
	 * 
	 * @param salir
	 * @param actividad
	 */
	public static boolean hayInternet(Activity actividad, Boolean salir) {
		boolean hayWifi = false;
		boolean hayMobile = false;

		ConnectivityManager cm = (ConnectivityManager) actividad
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] netInfo = cm.getAllNetworkInfo();

		for (NetworkInfo ni : netInfo) {
			if (ni.getTypeName().equalsIgnoreCase("wifi"))
				if (ni.isConnected()) {
					hayWifi = true;
				}
			if (ni.getTypeName().equalsIgnoreCase("mobile"))
				if (ni.isConnected()) {
					hayMobile = true;
				}
		}

		// Si no hay wifi o no hay conexión de red, cerramos la aplicación.
		if (hayWifi == false && hayMobile == false) {
			Log.i("internet", "no hay internet");
			Toast.makeText(actividad, "Debe disponer de conexión a internet",
					Toast.LENGTH_LONG).show();
			if(salir)
			((Activity)actividad).finish();
			return false;
		}
		return true;
	}
}
