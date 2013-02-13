package com.alvarolara.burgostv.fragment;

import java.io.File;
import java.net.URI;

import com.alvarolara.burgostv.R;
import com.alvarolara.burgostv.utiles.CargadorImagenes;

import android.support.v4.app.Fragment;
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
		Log.e("Test", "hello");
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

	public void setTitulo(String titulo) {
		TextView  TVtitulo= (TextView) getView().findViewById(R.id.titulo);
		TVtitulo.setText(titulo);
	}
	/*
	public void setFecha(String fecha) {
		TextView TVfechaDetalle = (TextView) getView().findViewById(R.id.TVfechaDetalle);
		TVfechaDetalle.setText(fecha);
	}*/
	/*
	public void ocultaDetalleVacio(){
		TextView view = (TextView) getView().findViewById(R.id.TVdetalleVacio);
		view.setVisibility(View.GONE);
		RelativeLayout rel = (RelativeLayout) getView().findViewById(R.id.RLdetalleVacio);
		rel.setVisibility(View.GONE);
	}*/
	public void setDescripcion(String descripcion){
		TextView TVdescripcion = (TextView) getView().findViewById(R.id.descripcion);
		TVdescripcion.setText(descripcion);
	}
	public void setImagen(String imagen){
		
		//Poner la imagen como parametro.
		ImageView IVurl_foto = (ImageView) getView().findViewById(R.id.url_foto);
		
		// Cargar la nueva imagen a partir de la URL.
		CargadorImagenes cargadorImagenes = new CargadorImagenes(getActivity().getApplicationContext());
		//Cargarla en tamano grande G.
		cargadorImagenes.muestraImagen("url_foto", IVurl_foto, "G");
		
	}
	
}
