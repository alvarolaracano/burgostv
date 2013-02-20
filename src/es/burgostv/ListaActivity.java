package es.burgostv;

import java.util.ArrayList;

import es.burgostv.R;

import es.burgostv.clases.Objeto;
import es.burgostv.utiles.AdaptadorLista;
import es.burgostv.utiles.Utilidades;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

/**
 * Listado con los objetos.
 * @author Alvaro Lara Cano
 *
 */
public class ListaActivity extends ListActivity {

	/**
	 * Elementos de la lista.
	 */
	ArrayList<Objeto> menuItems;

	/**
	 * Listview.
	 */
	ListView lista;
	
	/**
	 * Adaptador de la lista.
	 */
	AdaptadorLista adaptador;

	
	/**
	 * Llamado cuando la Actividad es creada.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.lista);

		// Recoger el hashmap mediante un intent.

		menuItems = (ArrayList<Objeto>) getIntent()
				.getSerializableExtra("xml");

		lista = (ListView) findViewById(android.R.id.list);

		// Obtener el adaptador y parsear todo el XML.
		adaptador = new AdaptadorLista(this, menuItems);
		lista.setAdapter(adaptador);

		lista.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// Comprobar la conexion a internet.
				if (Utilidades.hayInternet(ListaActivity.this, false)) {

					// Nuevo intent para OjetoActivity.
					Intent in = new Intent(getApplicationContext(),	ObjetoActivity.class);
					
					//Titulo.
					String TVtitulo = ((TextView) view.findViewById(R.id.TVtitulo)).getText().toString();
					//Url_foto.
					String TVurl_foto = ((TextView) view.findViewById(R.id.TVurl_foto)).getText().toString();
					//Descripcion.
					String TVdescripcion = ((TextView) view.findViewById(R.id.TVdescripcion)).getText().toString();
					//Url_video.
					String TVurl_video = ((TextView) view.findViewById(R.id.TVurl_video)).getText().toString();
					
					Objeto objeto = new Objeto(TVtitulo, TVdescripcion, TVurl_foto, TVurl_video);
					in.putExtra("objeto", objeto);
					
					//Comenzar la actividad.
					startActivity(in);
				}
			}
		});

	}

}
