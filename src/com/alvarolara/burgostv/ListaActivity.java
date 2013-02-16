package com.alvarolara.burgostv;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import com.alvarolara.burgostv.clases.Objeto;
import com.alvarolara.burgostv.utiles.AdaptadorLista;
import com.alvarolara.burgostv.utiles.Utilidades;
import com.alvarolara.burgostv.R;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
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
					in.putExtra(Utilidades.KEY_TITULO, TVtitulo);
					
					//Url_foto.
					String TVurl_foto = ((TextView) view.findViewById(R.id.TVurl_foto)).getText().toString();
					in.putExtra(Utilidades.KEY_URL_FOTO, TVurl_foto);
					
					//Descripcion.
					String TVdescripcion = ((TextView) view.findViewById(R.id.TVdescripcion)).getText().toString();
					in.putExtra(Utilidades.KEY_DESCRIPCION, TVdescripcion);
					
					//Url_video.
					String TVurl_video = ((TextView) view.findViewById(R.id.TVurl_video)).getText().toString();
					in.putExtra(Utilidades.KEY_URL_VIDEO, TVurl_video);
					
					
					
					
					//Imagencorrecta.
					String TVimagenCorrecta = ((TextView) view.findViewById(R.id.TVimagenCorrecta)).getText().toString();
					in.putExtra("cargando", "SI");
					
					
					//Comenzar la actividad.
					startActivity(in);
				}
			}
		});

	}
	
	public boolean comparaDrawable(Drawable d1, Drawable d2){
	    try{
	        Bitmap bitmap1 = ((BitmapDrawable)d1).getBitmap();
	        ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
	        bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, stream1);
	        stream1.flush();
	        byte[] bitmapdata1 = stream1.toByteArray();
	        stream1.close();

	        Bitmap bitmap2 = ((BitmapDrawable)d2).getBitmap();
	        ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
	        bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, stream2);
	        stream2.flush();
	        byte[] bitmapdata2 = stream2.toByteArray();
	        stream2.close();

	        return bitmapdata1.equals(bitmapdata2);
	    }
	    catch (Exception e) {
	        // TODO: handle exception
	    }
	    return false;
	}

}
