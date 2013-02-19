package com.alvarolara.burgostv.fragment;

import java.util.ArrayList;

import com.alvarolara.burgostv.R;
import com.alvarolara.burgostv.clases.Objeto;
import com.alvarolara.burgostv.utiles.AdaptadorLista;
import com.alvarolara.burgostv.utiles.Utilidades;

import android.support.v4.app.FragmentActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * Fragment que agrupa Lista y Detalle.
 * @author Alvaro Lara Cano
 *
 */
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
	
	/**
	 * Objeto auxiliar.
	 */
	Objeto objeto = null;
	
	
	/**
	 * Llamado cuando la Actividad es creada.
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.listadetalle);
		
		//Alpha al play.
		((ImageView) findViewById(R.id.IVplay)).setAlpha(200);
		
		// Obtener el intent.
		Intent in = getIntent();
		
		menuItems = (ArrayList<Objeto>) in.getSerializableExtra("xml");
		
		if(in.getStringExtra("url")!=null){
			String url = in.getStringExtra("url");

			Log.i("ObjetoActivity","encuentra la url: " + url.toString());
			objeto = Utilidades.creaUrlObjeto(this, in);
			Log.i("ObjetoActivity","el objeto de url: " + objeto.toString());
		}

		//Obtener la lista. 
		lista = (ListView)findViewById(android.R.id.list); 
		// Obtener el adaptador y pasarle los perfiles. 
		adaptador = new AdaptadorLista(this, menuItems);
		
		
		ObjetoListaFragment fragment = (ObjetoListaFragment)getSupportFragmentManager().findFragmentById(R.id.Flista); 
		//Establecer el contexto y el adaptador.
		fragment.setObjetoContexto(objeto, this);
		fragment.setListAdapter(adaptador);

	}
}
