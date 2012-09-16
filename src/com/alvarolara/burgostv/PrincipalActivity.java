package com.alvarolara.burgostv;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.alvarolara.burgostv.R;
import com.alvarolara.burgostv.async.CargaXML;
import com.alvarolara.burgostv.utiles.ParseadorXML;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class PrincipalActivity extends Activity {
	 
	 /**
	  * Constantes para las URLS.
	  * 
	  */
	 static final String URL_NOTICIAS = "http://www.burgostv.es/android/xml-noticias.php";
	 static final String URL_REPORTAJES = "http://www.burgostv.es/android/xml-reportajes.php";
	 static final String URL_MAS_DEPORTE = "http://www.burgostv.es/android/xml-mas-deporte.php";
	 static final String URL_VIDEOENCUENTRO = "http://www.burgostv.es/android/xml-burgos-en-persona.php";
	 static final String URL_DIRECTO = "http://www.burgostv.es/android/xml-directo.php";
	 
	/**
	 * Variables para almacenar los datos obtenidos en el XML.
	 */
	 String xml = "";
	String streaming = "";
	String url_streaming = "";
	String url_stream = "";
	
	 /**
	 * Definiciones para el XML.
	 */
	public static final String KEY_ITEM = "directo";
	public static final String KEY_STREAMING = "streaming";
	public static final String KEY_URL_STREAMING = "url_streaming";
	public static final String KEY_URL_STREAM = "url_stream";
	
    /**
     * Llamado cuando la Actividad es creada.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Si no hay internet, salimos de la aplicación.
        if(!hayInternet(this)){
        	Toast.makeText(this, "Debe disponer de conexión a internet", Toast.LENGTH_LONG).show();
        	finish();
        }else{
        
	        setContentView(R.layout.principal);
	
	        
	       //Obtener la url del directo leyendo el xml.
		   ParseadorXML parser = new ParseadorXML();
		   xml = parser.getXmlFromUrl(URL_DIRECTO);
		   Document doc = parser.getElementoDom(xml);
		   NodeList nl = doc.getElementsByTagName(KEY_ITEM);
	 	   Element e = (Element) nl.item(0);
	       
	 	   //Recoger los valores del xml.
	       streaming = parser.getValor(e, KEY_STREAMING);
	       url_streaming = parser.getValor(e, KEY_URL_STREAMING);
	       url_stream = parser.getValor(e, KEY_URL_STREAM);
	        
	        
	        /**
	         * NOTICIAS.
	         */
	        ImageButton botonnoticias = (ImageButton) findViewById(R.id.botonnoticias);
	        
	        botonnoticias.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// Al hacer click en el boton. Llamar a CargaXML y que esta llame a NoticiasActivity.
					
					new CargaXML(PrincipalActivity.this, URL_NOTICIAS).execute();
					
					
				}
			});
	        
	        
	        /**
	         * REPORTAJES.
	         */
	        ImageButton botonreportajes = (ImageButton) findViewById(R.id.botonreportajes);
	        
	        botonreportajes.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// Al hacer click en el boton. Llamar a CargaXML y que esta llame a NoticiasActivity.
					
					new CargaXML(PrincipalActivity.this, URL_REPORTAJES).execute();
					
					
				}
			});
	        
	        /**
	         * MAS DEPORTE.
	         */
	        ImageButton botonmasdeporte = (ImageButton) findViewById(R.id.botonmasdeporte);
	        
	        botonmasdeporte.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// Al hacer click en el boton. Llamar a CargaXML y que esta llame a NoticiasActivity.
					
					new CargaXML(PrincipalActivity.this, URL_MAS_DEPORTE).execute();
					
					
				}
			});
	        
	        /**
	         * VIDEOENCUENTRO.
	         */
	        ImageButton botonvideoencuentro = (ImageButton) findViewById(R.id.botonvideoencuentro);
	        
	        botonvideoencuentro.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// Al hacer click en el boton. Llamar a CargaXML y que esta llame a NoticiasActivity.
					
					new CargaXML(PrincipalActivity.this, URL_VIDEOENCUENTRO).execute();
					
					
				}
			});
	        
	        
	        /**
	         * DIRECTO.
	         */
	        ImageButton botondirecto = (ImageButton) findViewById(R.id.botondirecto);
	        
	        botondirecto.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					if(streaming.contains("SI")){
			        	//Pasarle al videoView la url del streaming.
						Intent in = new Intent(getApplicationContext(), DirectoActivity.class);
		                in.putExtra(KEY_URL_STREAMING, url_streaming+url_stream);
		                Log.i("video: ->", url_streaming+url_stream);
		                startActivity(in);
			        }else{
			        	//Toast mostrando que no hay emisión actualmente.
			        	Toast.makeText(PrincipalActivity.this, "No hay emisiones en este momento.", Toast.LENGTH_LONG).show();
			        }
					
				}
	       
	        });
	        
	        
	        /**
	         * BUSCAR.
	         */
	        ImageButton botonbuscar = (ImageButton) findViewById(R.id.botonbuscar);
	        
	        botonbuscar.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					Intent in = new Intent(getApplicationContext(), BuscarActivity.class);
					 startActivity(in);
				}
	       
	        });
        
        }
    }
    
    /**
     * Funcion que comprueba si hay internet o no.
     * @param a
     */
    public static boolean hayInternet(Activity a) {
    	boolean hayWifi = false;
    	boolean hayMobile = false;

    	ConnectivityManager cm = (ConnectivityManager) a.getSystemService(Context.CONNECTIVITY_SERVICE);
    	NetworkInfo[] netInfo = cm.getAllNetworkInfo();
    	
    	for (NetworkInfo ni : netInfo) {
	    	if (ni.getTypeName().equalsIgnoreCase("wifi"))
	    	if (ni.isConnected()){
	    		hayWifi = true;
	    	}
	    	if (ni.getTypeName().equalsIgnoreCase("mobile"))
	    	if (ni.isConnected()){
	    		hayMobile = true;
	    	}
    	}
    	
    	//Si no hay wifi o no hay conexión de red, cerramos la aplicación.
    	if(hayWifi==false && hayMobile==false){
    		
    		return false;
    	}
    	return true;
	}
}