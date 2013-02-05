package com.alvarolara.burgostv.utiles;

public class Utilidades {

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
}
