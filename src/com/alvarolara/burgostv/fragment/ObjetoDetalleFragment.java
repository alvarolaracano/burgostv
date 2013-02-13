package com.alvarolara.burgostv.fragment;

import java.io.File;
import java.net.URI;

import com.alvarolara.burgostv.R;
import com.alvarolara.burgostv.VideoActivity;
import com.alvarolara.burgostv.utiles.CargadorImagenes;
import com.alvarolara.burgostv.utiles.Utilidades;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * Fragment que define el comportamiento del detalle de la lista una vez se
 * selecciona un elemento en otro Fragment
 * 
 */
public class ObjetoDetalleFragment extends Fragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

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
		TextView  TVtitulo= (TextView) getView().findViewById(R.id.titulo);
		TVtitulo.setText(titulo);
	}
	
	/**
	 * Establece la descripcion.
	 * @param descripcion
	 */
	public void setDescripcion(String descripcion){
		TextView TVdescripcion = (TextView) getView().findViewById(R.id.descripcion);
		TVdescripcion.setText(descripcion);
	}
	
	/**
	 * Establece la imagen.
	 * @param imagen
	 */
	public void setImagen(String imagen){
		
		//Poner la imagen como parametro.
		ImageView IVurl_foto = (ImageView) getView().findViewById(R.id.url_foto);
		
		// Cargar la nueva imagen a partir de la URL.
		CargadorImagenes cargadorImagenes = new CargadorImagenes(getActivity().getApplicationContext());
		//Cargarla en tamano grande G.
		cargadorImagenes.muestraImagen(imagen, IVurl_foto, "G");
		
	}
	
	public void setOnClickListenerImagen(final Activity actividad, final String url_video){
		//Evento en el reproductor.
		ImageView lblUrl_foto = (ImageView) getView().findViewById(R.id.url_foto);
		lblUrl_foto.setAlpha(200);
		
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
