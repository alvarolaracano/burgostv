package com.alvarolara.burgostv.async;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.alvarolara.burgostv.ErrorActivity;
import com.alvarolara.burgostv.ListaActivity;
import com.alvarolara.burgostv.utiles.ParseadorXML;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

public class CargaXML extends AsyncTask<Void, Void, Void> {

	/**
	 * ProgressDialog cargando datos.
	 */
	private ProgressDialog progress;
	
	/**
	 * Contexto de la Activity.
	 */
	private Context contexto;

	/**
	 * Arraylist para los elementos.
	 */
	ArrayList<HashMap<String, String>> menuItems;

	/**
	 * Definiciones para el XML.
	 */
	public static final String KEY_ITEM = "item";
	public static final String KEY_TITULO = "titulo";
	public static final String KEY_DESCRIPCION = "descripcion";
	public static final String KEY_URL_FOTO = "url_foto";
	public static final String KEY_URL_VIDEO = "url_video";
	public static final String KEY_FECHA = "fecha";

	/**
	 * URL de donde obtener los datos.
	 */
	public static String URL = "";

	/**
	 * Variable donde se guardara el XML.
	 */
	String xml = "";

	/**
	 * Variable para saber si ha habido algun error.
	 */
	boolean error = false;

	/**
	 * Constructor del AsycTask.
	 * 
	 * @param contexto
	 */
	public CargaXML(Context contexto, String URL) {
		this.contexto = contexto;
		this.URL = URL;

		// Preparar el ProgressDialog.
		progress = new ProgressDialog(contexto);
		progress.setCancelable(false);
		progress.setMessage("Obteniendo datos...");
		progress.setTitle("Por favor, espere");
		progress.setIndeterminate(true);
	}

	/**
	 * Antes de ejecutarse.
	 */
	@Override
	protected void onPreExecute() {
		progress.show();
	}

	@SuppressLint("ParserError")
	@Override
	protected Void doInBackground(Void... params) {

		Log.i("boinbackground(): ", "proceso en background");

		menuItems = new ArrayList<HashMap<String, String>>();

		ParseadorXML parser = new ParseadorXML();

		try {
			xml = parser.getXmlFromUrl(URL);

			Document doc = parser.getElementoDom(xml);

			NodeList nl = doc.getElementsByTagName(KEY_ITEM);

			// Si la longitud del nodelist es 0 al buscar la clave 'item',
			// lanzamos excepcion para que aparezca ErrorActivity.
			if (nl.getLength() == 0) {
				throw new Exception();
			} else {
				// Todos los <item>.
				for (int i = 0; i < nl.getLength(); i++) {
					Log.i("for: ", "ejecuta el for");
					// Nuevo HashMap.
					HashMap<String, String> map = new HashMap<String, String>();
					Element e = (Element) nl.item(i);
					// Añadir cada nodo hijo al hashmap con clave=> valor.
					map.put(KEY_TITULO, parser.getValor(e, KEY_TITULO));
					map.put(KEY_DESCRIPCION,
							parser.getValor(e, KEY_DESCRIPCION));
					map.put(KEY_URL_FOTO, parser.getValor(e, KEY_URL_FOTO));
					map.put(KEY_URL_VIDEO, parser.getValor(e, KEY_URL_VIDEO));
					map.put(KEY_FECHA, parser.getValor(e, KEY_FECHA));

					// Añadir el arraylist al hashmap.
					menuItems.add(map);
				}
				Log.i("acaba el for: ", "acaba el for");
			}
		} catch (Exception e) {
			error = true;
			contexto.startActivity(new Intent(contexto
					.getApplicationContext(), ErrorActivity.class));
		}
		return null;
	}

	@Override
	/**
	 * Cuando acabe de ejecutarse todo.
	 */
	protected void onPostExecute(Void params) {

		// Ocultar el ProgressDialog.
		if (progress != null && progress.isShowing()) {
			progress.dismiss();
		}

		if (!error) {
			// Llamar a la activity.
			Intent in = new Intent(contexto.getApplicationContext(),
					ListaActivity.class);
			in.putExtra("xml", menuItems);
			contexto.startActivity(in);
		}
	}
}
