package com.alvarolara.burgostv.utiles;

import java.util.ArrayList;

import com.alvarolara.burgostv.R;
import com.alvarolara.burgostv.clases.Objeto;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Adaptador para cargar la lista de objetos.
 * @author Alvaro Lara Cano
 *
 */
public class AdaptadorLista extends BaseAdapter {

	/**
	 * Actividad.
	 */
	private Activity actividad;
	
	/**
	 * Items del Menu.
	 */
	private ArrayList<Objeto> menuItems;
	
	/**
	 * Inflater.
	 */
	private static LayoutInflater inflater = null;
	
	/**
	 * Cargador de imagenes.
	 */
	public CargadorImagenes cargadorImagenes;

	
	/**
	 * Constructor.
	 * @param actividad
	 * @param menuItems
	 */
	public AdaptadorLista(Activity actividad, ArrayList<Objeto> menuItems) {
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
	public Object getItem(int posicion) {
		return menuItems.get(posicion);
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
		
		//Titulo.
		TextView TVtitulo = (TextView) vista.findViewById(R.id.TVtitulo);
		TVtitulo.setText(menuItems.get(position).getTitulo());
		
		//Descripcion.
		TextView TVdescripcion = (TextView) vista.findViewById(R.id.TVdescripcion);
		TVdescripcion.setText(menuItems.get(position).getDescripcion());
		
		//Url_video.
		TextView TVurl_video = (TextView) vista.findViewById(R.id.TVurl_video);
		TVurl_video.setText(menuItems.get(position).getUrl_video());
		
		//Fecha
		TextView TVfecha = (TextView) vista.findViewById(R.id.TVfecha);
		TVfecha.setText(menuItems.get(position).getFecha());
		
		// Url_foto del textview Oculto.
		TextView TVurl_foto = (TextView) vista.findViewById(R.id.TVurl_foto);
		TVurl_foto.setText(menuItems.get(position).getUrl_foto());
		

		// Dibujar la imagen.
		//Campo oculto en objetoxml para saber si ha sido cargada o no.
		cargadorImagenes.muestraImagen(menuItems.get(position).getUrl_foto(), (ImageView) vista.findViewById(R.id.IVurl_foto), "P");
		
		
		return vista;
	}
}