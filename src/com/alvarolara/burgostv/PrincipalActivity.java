package com.alvarolara.burgostv;

import com.alvarolara.burgostv.R;
import com.alvarolara.burgostv.async.CargaXML;
import com.alvarolara.burgostv.utiles.ParseadorXML;
import com.alvarolara.burgostv.utiles.Utilidades;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

public class PrincipalActivity extends Activity {
	

	/**
	 * Variables para almacenar los datos obtenidos en el XML.
	 */
	String streaming = "";
	String url_streaming = "";
	String url_stream = "";
	
	/**
	 * Si la pantalla esta en landscape o portrait.
	 */
	private boolean portrait;


	/**
	 * Llamado cuando la Actividad es creada.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Si no hay internet, salimos de la aplicación.
		if (Utilidades.hayInternet(this, true)) {
			
			//Establecer layout segun telefono o tablet.
			if(getResources().getString(R.string.tipo_pantalla).equals("telefono")){
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
				portrait = true;
				
			}else{
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
				portrait = false;
			}

			setContentView(R.layout.principal);

			// Obtener la url del directo leyendo el xml.
			ParseadorXML parser = new ParseadorXML(Utilidades.URL_DIRECTO, Utilidades.KEY_ITEM_DIRECTO);

			// Recoger los valores del xml.
			streaming = parser.getValor(Utilidades.KEY_STREAMING);
			url_streaming = parser.getValor(Utilidades.KEY_URL_STREAMING);
			url_stream = parser.getValor(Utilidades.KEY_URL_STREAM);

			/**
			 * NOTICIAS.
			 */
			ImageButton botonnoticias = (ImageButton) findViewById(R.id.botonnoticias);

			botonnoticias.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					// Al hacer click en el boton. Llamar a CargaXML y que esta
					// llame a ListaActivity.
					if (Utilidades.hayInternet(PrincipalActivity.this, false)) {
						
						new CargaXML(PrincipalActivity.this, Utilidades.URL_NOTICIAS, portrait)
								.execute();
					}

				}
			});

			/**
			 * REPORTAJES.
			 */
			ImageButton botonreportajes = (ImageButton) findViewById(R.id.botonreportajes);

			botonreportajes.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					// Al hacer click en el boton. Llamar a CargaXML y que esta
					// llame a ListaActivity.
					if (Utilidades.hayInternet(PrincipalActivity.this, false)) {
						new CargaXML(PrincipalActivity.this, Utilidades.URL_REPORTAJES, portrait)
								.execute();
					}

				}
			});

			/**
			 * MAS DEPORTE.
			 */
			ImageButton botonmasdeporte = (ImageButton) findViewById(R.id.botonmasdeporte);

			botonmasdeporte.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					// Al hacer click en el boton. Llamar a CargaXML y que esta
					// llame a ListaActivity.
					if (Utilidades.hayInternet(PrincipalActivity.this, false)) {
						new CargaXML(PrincipalActivity.this, Utilidades.URL_MAS_DEPORTE, portrait)
								.execute();
					}

				}
			});

			/**
			 * VIDEOENCUENTRO.
			 */
			ImageButton botonvideoencuentro = (ImageButton) findViewById(R.id.botonvideoencuentro);

			botonvideoencuentro.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					// Al hacer click en el boton. Llamar a CargaXML y que esta
					// llame a ListaActivity.
					if (Utilidades.hayInternet(PrincipalActivity.this, false)) {
						new CargaXML(PrincipalActivity.this, Utilidades.URL_VIDEOENCUENTRO, portrait)
								.execute();
					}

				}
			});

			/**
			 * DIRECTO.
			 */
			ImageButton botondirecto = (ImageButton) findViewById(R.id.botondirecto);

			botondirecto.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					if (streaming.contains("NO")) {
						// Pasarle al videoView la url del streaming.
						if (Utilidades.hayInternet(PrincipalActivity.this, false)) {
							Intent in = new Intent(getApplicationContext(),
									DirectoActivity.class);
							in.putExtra(Utilidades.KEY_URL_STREAMING, url_streaming
									+ url_stream);
							Log.i("video: ->", url_streaming + url_stream);
							startActivity(in);
						}
					} else {
						// Toast mostrando que no hay emisión actualmente.
						Toast.makeText(PrincipalActivity.this,
								"No hay emisiones en este momento.",
								Toast.LENGTH_LONG).show();
					}

				}

			});

			/**
			 * BUSCAR.
			 */
			ImageButton botonbuscar = (ImageButton) findViewById(R.id.botonbuscar);

			botonbuscar.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					Intent in = new Intent(getApplicationContext(),
							BuscarActivity.class);
					startActivity(in);
				}

			});

		}
	}
	
}