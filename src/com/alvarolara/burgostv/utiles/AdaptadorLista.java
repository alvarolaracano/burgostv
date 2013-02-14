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

		TextView titulo = (TextView) vista.findViewById(R.id.titulo);
		TextView descripcion = (TextView) vista.findViewById(R.id.descripcion);
		TextView url_video = (TextView) vista.findViewById(R.id.url_video);
		ImageView url_foto = (ImageView) vista.findViewById(R.id.url_foto);
		TextView fecha = (TextView) vista.findViewById(R.id.fecha);
		// Url de la foto del textview Oculto.
		TextView url_foto_textview = (TextView) vista
				.findViewById(R.id.url_foto_textview);

		Objeto objeto = new Objeto();
		objeto = menuItems.get(position);

		// Colocar los valores en el ListView.
		titulo.setText(objeto.getTitulo());
		descripcion.setText(objeto.getDescripcion());
		url_video.setText(objeto.getUrl_video());
		fecha.setText(objeto.getFecha());

		// Cargar la URL para pasarla mediante un Intent.
		url_foto_textview.setText(objeto.getUrl_foto());

		// Dibujar la imagen.
		cargadorImagenes.muestraImagen(objeto.getUrl_foto(), url_foto,
				"P");
		return vista;
	}
}