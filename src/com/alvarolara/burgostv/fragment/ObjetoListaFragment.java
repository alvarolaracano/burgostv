package com.alvarolara.burgostv.fragment;

import com.alvarolara.burgostv.R;
import com.alvarolara.burgostv.clases.Objeto;
import com.alvarolara.burgostv.utiles.AdaptadorLista;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ObjetoListaFragment extends ListFragment {


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	// al hacer tap en algun elemento de la vista, setea el texto del elemento
	// en el nuevo fragment
	public void onListItemClick(ListView l, View v, int position, long id) {
		
		Objeto item = (Objeto)getListAdapter().getItem(position);
		
		//System.out.println(item.toString());
		//System.out.println("DESCRIPCION: " + item.getDescripcion());
		
		ObjetoDetalleFragment fragment = (ObjetoDetalleFragment) getFragmentManager().findFragmentById(R.id.Fdetalle);
		if (fragment != null && fragment.isInLayout()) {
			//fragment.ocultaDetalleVacio();
			fragment.setTitulo(item.getTitulo());
			//fragment.setFecha(item.getFecha());
			fragment.setDescripcion(item.getDescripcion());
			fragment.setImagen(item.getUrl_foto());
			
		} else {
			Intent intent = new Intent(getActivity().getApplicationContext(),
					ObjetoDetalleFragment.class);
			intent.putExtra("value", item);
			startActivity(intent);
		}
	}

}
