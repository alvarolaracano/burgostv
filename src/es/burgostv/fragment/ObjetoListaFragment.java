package es.burgostv.fragment;

import es.burgostv.R;

import es.burgostv.clases.Objeto;

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
	 * Objeto auxiliar.
	 */
	private Objeto objeto = null;
	
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
		
		if(objeto==null){
			//Seleccionar el primer elemento de la lista.
			objeto = (Objeto)getListAdapter().getItem(0);
		}
		ObjetoDetalleFragment fragment = (ObjetoDetalleFragment) getFragmentManager().findFragmentById(R.id.Fdetalle);
		//Le pasamos el objeto seleccionado.
		fragment.setObjeto(actividad, objeto);
		
	}

	
	@Override
	/**
	 * Al hacer click en algun elemento de la lista, se le pasan los valores.
	 */
	public void onListItemClick(ListView l, View v, int position, long id) {
		
		objeto = (Objeto)getListAdapter().getItem(position);
		
		ObjetoDetalleFragment fragment = (ObjetoDetalleFragment) getFragmentManager().findFragmentById(R.id.Fdetalle);
		if (fragment != null && fragment.isInLayout()) {
			//Le pasamos el objeto seleccionado.
			fragment.setObjeto(actividad, objeto);
			
		} else {
			Intent intent = new Intent(getActivity().getApplicationContext(),
					ObjetoDetalleFragment.class);
			intent.putExtra("value", objeto);
			startActivity(intent);
		}
	}
	
	/**
	 * Establece el contexto.
	 * @param actividad
	 */
	public void setObjetoContexto(Objeto objeto, Activity actividad){
		this.objeto = objeto;
		this.actividad = actividad;
	}

}
