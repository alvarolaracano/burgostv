package com.alvarolara.burgostv.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import com.alvarolara.burgostv.R;
import com.alvarolara.burgostv.VideoActivity;
import com.alvarolara.burgostv.clases.Objeto;
import com.alvarolara.burgostv.utiles.AdaptadorLista;
import com.alvarolara.burgostv.utiles.Utilidades;

import android.support.v4.app.FragmentActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

public class ObjetoActivity extends FragmentActivity {
	
	/**
	 * Elementos de la lista.
	 */
	ArrayList<Objeto> menuItems;

	/**
	 * ListView para listar objetos.
	 */
	ListView lista;

	/**
	 * Adaptador para rellenar la lista.
	 */
	AdaptadorLista adaptador;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.listadetalle);
		
		// Recoger el hashmap mediante un intent.
		menuItems = (ArrayList<Objeto>) getIntent()
				.getSerializableExtra("xml");
		
		
		//Obtener la lista. 
		lista = (ListView)findViewById(android.R.id.list); 
		// Obtener el adaptador y pasarle los perfiles. 
		adaptador = new AdaptadorLista(this, menuItems);
		
		
		ObjetoListaFragment fragment = (ObjetoListaFragment)getSupportFragmentManager().findFragmentById(R.id.Flista); 
		fragment.setContexto(this);
		fragment.setListAdapter(adaptador);
		
		
	}
}
