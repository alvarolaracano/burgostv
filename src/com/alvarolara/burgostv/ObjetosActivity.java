package com.alvarolara.burgostv;

import java.util.ArrayList;
import java.util.HashMap;

import com.alvarolara.burgostv.async.CargaXML;
import com.alvarolara.burgostv.utiles.LazyAdapter;
import com.alvarolara.burgostv.R;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class ObjetosActivity extends ListActivity {
	
	
	ArrayList<HashMap<String, String>> menuItems;
	
	
	ListView list;
    LazyAdapter adapter;
	
	/**
	 * Se llama cuando la actividad es creada.
	 */
    @SuppressWarnings("unchecked")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.lista);
        
        
        //Recoger el hashmap mediante un intent.
        
       menuItems = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("xml");
       
        
        list=(ListView)findViewById(android.R.id.list);
		
		// Obtener el adaptador y parsear todo el XML.
        adapter=new LazyAdapter(this, menuItems);        
        list.setAdapter(adapter);

        
        
        list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				//Comprobar la conexion a internet.
				if(!PrincipalActivity.hayInternet(ObjetosActivity.this)){
		        	Toast.makeText(ObjetosActivity.this, "Debe disponer de conexión a internet", Toast.LENGTH_LONG).show();
		        }else{
				
					// Obtener valores de los objetos.
					String titulo = ((TextView) view.findViewById(R.id.titulo)).getText().toString();
					String descripcion = ((TextView) view.findViewById(R.id.descripcion)).getText().toString();
					String fecha = ((TextView) view.findViewById(R.id.fecha)).getText().toString();
					
					String url_foto = ((TextView) view.findViewById(R.id.url_foto_textview)).getText().toString();
					String url_video = ((TextView) view.findViewById(R.id.url_video)).getText().toString();
					
					// Nuevo intent de texto.
					Intent in = new Intent(getApplicationContext(), UnObjetoActivity.class);
					in.putExtra(CargaXML.KEY_TITULO, titulo);
					in.putExtra(CargaXML.KEY_URL_FOTO, url_foto);
					in.putExtra(CargaXML.KEY_DESCRIPCION, descripcion);
					in.putExtra(CargaXML.KEY_URL_VIDEO, url_video);
					startActivity(in);
		        }
			}
		});

    }
    
    
}
