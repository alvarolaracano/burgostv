package com.alvarolara.burgostv.fragment;

import com.alvarolara.burgostv.R;
import com.alvarolara.burgostv.VideoActivity;
import com.alvarolara.burgostv.utiles.CargadorImagenes;
import com.alvarolara.burgostv.utiles.Utilidades;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * Fragment para ver un objeto seleccionado de la lista.
 * @author Alvaro Lara Cano
 * 
 */
public class ObjetoDetalleFragment extends Fragment {
	
	/**
	 * Cuando se crea la actividad.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	/**
	 * Cuando se crea la actividad.
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	/**
	 * Al crearse la vista.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.objeto, container, false);
		return view;
	}

	/**
	 * Establece el titulo.
	 * @param titulo
	 */
	public void setTitulo(String titulo) {
		TextView  TVtitulo= (TextView) getView().findViewById(R.id.TVtitulo);
		TVtitulo.setText(titulo);
	}
	
	/**
	 * Establece la descripcion.
	 * @param descripcion
	 */
	public void setDescripcion(String descripcion){
		TextView TVdescripcion = (TextView) getView().findViewById(R.id.TVdescripcion);
		TVdescripcion.setText(descripcion);
	}
	
	/**
	 * Establece la imagen.
	 * @param imagen
	 */
	public void setImagen(String imagen){
		
		//Poner la imagen como parametro.
		ImageView IVurl_foto = (ImageView) getView().findViewById(R.id.IVurl_foto);
		
		// Cargar la nueva imagen a partir de la URL.
		CargadorImagenes cargadorImagenes = new CargadorImagenes(getActivity().getApplicationContext());
		//Cargarla en tamano grande G.
		cargadorImagenes.muestraImagen(imagen, IVurl_foto, "G");
		
	}
	
	/**
	 * Establece el clickLIstener a la imagen.
	 * @param actividad
	 * @param url_video
	 */
	public void setOnClickListenerImagen(final Activity actividad, final String url_video){
		//Evento en el reproductor.
		ImageView lblUrl_foto = (ImageView) getView().findViewById(R.id.IVurl_foto);
		
		
		lblUrl_foto.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// Comprobar la conexion a internet.
				if (Utilidades.hayInternet(actividad, false)) {
					
					// Intent para videoactivity.
					Intent in = new Intent(getActivity().getApplicationContext(),
							VideoActivity.class);
					in.putExtra(Utilidades.KEY_URL_VIDEO, url_video);
					startActivity(in);
				}
			}
		});
	}
	
}
