package com.alvarolara.burgostv.utiles;

import java.util.ArrayList;
import java.util.HashMap;

import com.alvarolara.burgostv.R;
import com.alvarolara.burgostv.async.CargaXML;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdaptadorLista extends BaseAdapter {

	/**
	 * Actividad.
	 */
	private Activity actividad;
	
	/**
	 * Items del Menu.
	 */
	private ArrayList<HashMap<String, String>> menuItems;
	
	/**
	 * Inflater.
	 */
	private static LayoutInflater inflater = null;
	
	/**
	 * Cargador de imagenes.
	 */
	public CargadorImagenes cargadorImagenes;

	
	/**
	 * 
	 * @param actividad
	 * @param menuItems
	 */
	public AdaptadorLista(Activity actividad, ArrayList<HashMap<String, String>> menuItems) {
		this.actividad = actividad;
		this.menuItems = menuItems;
		inflater = (LayoutInflater) this.actividad
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		cargadorImagenes = new CargadorImagenes(this.actividad.getApplicationContext());
	}

	
	/**
	 * Numero de elementos.
	 */
	public int getCount() {
		return menuItems.size();
	}

	
	/**
	 * Devuelve el item del entero que solicitas.
	 */
	public Object getItem(int position) {
		return position;
	}

	
	/**
	 * Devuelve la posicion.
	 */
	public long getItemId(int position) {
		return position;
	}

	
	/**
	 * Adapta el contenido al layout.
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		View vista = convertView;
		if (convertView == null)
			vista = inflater.inflate(R.layout.lista_filas, null);

		TextView titulo = (TextView) vista.findViewById(R.id.titulo);
		TextView descripcion = (TextView) vista.findViewById(R.id.descripcion);
		TextView url_video = (TextView) vista.findViewById(R.id.url_video);
		ImageView url_foto = (ImageView) vista.findViewById(R.id.url_foto);
		TextView fecha = (TextView) vista.findViewById(R.id.fecha);
		// Url de la foto del textview Oculto.
		TextView url_foto_textview = (TextView) vista
				.findViewById(R.id.url_foto_textview);

		HashMap<String, String> objeto = new HashMap<String, String>();
		objeto = menuItems.get(position);

		// Colocar los valores en el ListView.
		titulo.setText(objeto.get(Utilidades.KEY_TITULO));
		descripcion.setText(objeto.get(Utilidades.KEY_DESCRIPCION));
		url_video.setText(objeto.get(Utilidades.KEY_URL_VIDEO));
		fecha.setText(objeto.get(Utilidades.KEY_FECHA));

		// Cargar la URL para pasarla mediante un Intent.
		url_foto_textview.setText(objeto.get(Utilidades.KEY_URL_FOTO));

		// Dibujar la imagen.
		cargadorImagenes.muestraImagen(objeto.get(Utilidades.KEY_URL_FOTO), url_foto,
				"P");
		return vista;
	}
}