package com.alvarolara.burgostv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.alvarolara.burgostv.async.CargaXML;
import com.alvarolara.burgostv.utiles.ImageLoader;
import com.alvarolara.burgostv.utiles.ParseadorXML;
import com.alvarolara.burgostv.utiles.TextViewJustify;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

public class UnObjetoActivity  extends Activity {
	
	// Claves xml del nodo.
	static final String KEY_TITULO = "titulo";
	static final String KEY_DESCRIPCION = "descripcion";
	static final String KEY_URL_FOTO = "url_foto";
	static final String KEY_URL_VIDEO = "url_video";
	
	/**
	 * Titulo del objeto.
	 */
	String titulo = "";
	
	/**
	 * Url_foto del objeto.
	 */
	String url_foto = "";
	
	/**
	 * Descripcion del objeto.
	 */
	String descripcion = "";
	
	/**
	 * Url_video del objeto.
	 */
	String url_video = "";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.lista_solo);
        
        // Obtener datos del intent.
        Intent in = getIntent();
        //Saber la procedencia del intent, recogiendo datos como el titulo.
        if(in.getSerializableExtra(KEY_TITULO) == null){
        	
        	String url = "http://www.burgostv.es/android/xml-url.php?tipo=";
        	
        	//Procede de una URL
        	Uri datos = in.getData();
        	
        	List<String> params = datos.getPathSegments();
        	
        	//Saber que parametro es el primero.
        	if(params.get(0).compareTo("noticias")==0){
        		//Si son noticias.
        		
        		url += "noticia&url="+params.get(4);
        		
        		cargaXml(url);
        		
        	}else if(params.get(0).compareTo("reportajes")==0){
        		//Si son noticias.
        		
        		url += "reportaje&url="+params.get(2);
        		
        		cargaXml(url);
        		
        	}else if(params.get(0).compareTo("mas-deporte")==0){
        		//Si son noticias.
        		
        		url += "mas_deporte&url="+params.get(4);
        		
        		cargaXml(url);
        		
        	}else if(params.get(0).compareTo("burgos-en-persona")==0){
        		//Si son noticias.
        		
        		url += "burgos_en_persona&url="+params.get(4);
        		
        		cargaXml(url);
        		
        	}else{
        		//Mostrar mensaje de error
        		Toast.makeText(getApplicationContext(), "La url no se puede abrir.", Toast.LENGTH_LONG).show();
        		
        		//Salir de la aplicacion.
        		finish();
        	}  	
        }else{
        
	        // Obtener los datos del intent anterior.
	        titulo = in.getStringExtra(KEY_TITULO);
	        url_foto = in.getStringExtra(KEY_URL_FOTO);
	
	        descripcion = in.getStringExtra(KEY_DESCRIPCION);
	        url_video = in.getStringExtra(KEY_URL_VIDEO);
	        
        }     
	        
	        TextView lblTitulo = (TextView) findViewById(R.id.titulo);
	        ImageView lblUrl_foto = (ImageView) findViewById(R.id.url_foto);
	        lblUrl_foto.setAlpha(200);
	        
	        TextView lblDescripcion = (TextView) findViewById(R.id.descripcion);
	        
	        
	        lblTitulo.setText(titulo);
	        
	        //Cargar la nueva imagen a partir de la URL.
	        ImageLoader imageLoader=new ImageLoader(getApplicationContext());
	        imageLoader.DisplayImage(url_foto, lblUrl_foto, "G");
	        
	        //Toast.makeText(getApplicationContext(), url_foto, Toast.LENGTH_LONG).show();
	        
	        lblDescripcion.setText(descripcion);
	        
	        //Justificar el texto (como se vea, depende de cada pantalla).
	        TextViewJustify.justifyText(((TextView)findViewById(R.id.descripcion)), 340f); 
	        
        
        
        ImageView imagen=(ImageView)findViewById(R.id.url_foto);
        
        imagen.setOnClickListener(new 	View.OnClickListener() {
        	
            public void onClick(View v) {
            	//Comprobar la conexion a internet.
            	if(!PrincipalActivity.hayInternet(UnObjetoActivity.this)){
		        	Toast.makeText(UnObjetoActivity.this, "Debe disponer de conexión a internet", Toast.LENGTH_LONG).show();
		        }else{
	                //Intent para videoactivity.
	                Intent in = new Intent(getApplicationContext(), VideoActivity.class);
	                in.putExtra(KEY_URL_VIDEO, url_video);
	                startActivity(in);
		        }
            }
        });

        
    }
	
	public void cargaXml(String url){
		
		ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();
 	    
 	    ParseadorXML parser = new ParseadorXML();
		
		String xml = parser.getXmlFromUrl(url);
 	    
 	    Document doc = parser.getElementoDom(xml);
 	    
 	    
 	    NodeList nl = doc.getElementsByTagName(CargaXML.KEY_ITEM);
 	    
 	    //Si la longitud del nodelist es 0 al buscar la clave 'item', 
 	    //lanzamos excepcion.
 	    
	 	   try{
		 	    if(nl.getLength()==0){
		 	    	throw new Exception();
		 	    }else{
		 	        Element e = (Element) nl.item(0);
		 	        // Añadir cada nodo hijo al hashmap con clave=> valor.
		 	        titulo = parser.getValor(e, KEY_TITULO);
		 	        descripcion =  parser.getValor(e, KEY_DESCRIPCION);
		 	        url_foto = parser.getValor(e, KEY_URL_FOTO);
		 	        url_video = parser.getValor(e, KEY_URL_VIDEO);
		 	    }
	 	  }catch(Exception e){
	 		 Toast.makeText(getApplicationContext(), "Error al abrir la URL.", Toast.LENGTH_LONG).show();
	    }
	}
	
}

