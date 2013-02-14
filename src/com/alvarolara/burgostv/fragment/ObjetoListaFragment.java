package com.alvarolara.burgostv.fragment;

import com.alvarolara.burgostv.R;
import com.alvarolara.burgostv.clases.Objeto;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

/**
 * Fragment para la lista de Objetos.
 * @author Alvaro Lara Cano
 *
 */
public class ObjetoListaFragment extends ListFragment {

	/**
	 * Actividad para el contexto.
	 */
	private Activity actividad;

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
		
		//Seleccionar el primer elemento de la lista.
		Objeto item = (Objeto)getListAdapter().getItem(0);
		ObjetoDetalleFragment fragment = (ObjetoDetalleFragment) getFragmentManager().findFragmentById(R.id.Fdetalle);
		fragment.setTitulo(item.getTitulo());
		fragment.setDescripcion(item.getDescripcion());
		fragment.setImagen(item.getUrl_foto());
		fragment.setOnClickListenerImagen(actividad, item.getUrl_video());
	}

	
	@Override
	/**
	 * Al hacer click en algun elemento de la lista, se le pasan los valores.
	 */
	public void onListItemClick(ListView l, View v, int position, long id) {
		
		Objeto item = (Objeto)getListAdapter().getItem(position);
		Log.i("item", Integer.toString(position));
		
		ObjetoDetalleFragment fragment = (ObjetoDetalleFragment) getFragmentManager().findFragmentById(R.id.Fdetalle);
		if (fragment != null && fragment.isInLayout()) {
			fragment.setTitulo(item.getTitulo());
			fragment.setDescripcion(item.getDescripcion());
			fragment.setImagen(item.getUrl_foto());
			fragment.setOnClickListenerImagen(actividad, item.getUrl_video());
			
		} else {
			Intent intent = new Intent(getActivity().getApplicationContext(),
					ObjetoDetalleFragment.class);
			intent.putExtra("value", item);
			startActivity(intent);
		}
	}
	
	/**
	 * Establece el contexto.
	 * @param actividad
	 */
	public void setContexto(Activity actividad){
		this.actividad = actividad;
	}

}
