package es.burgostv;

import es.burgostv.R;

import es.burgostv.async.CargaXML;
import es.burgostv.utiles.ParseadorXML;
import es.burgostv.utiles.Utilidades;

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

/**
 * Menu principal con las diferentes opciones.
 * @author Alvaro Lara Cano
 *
 */
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

		setContentView(R.layout.principal);
		
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

			

			// Obtener la url del directo leyendo el xml.
			ParseadorXML parser = new ParseadorXML(Utilidades.URL_DIRECTO, Utilidades.KEY_ITEM_DIRECTO);

			// Recoger los valores del xml.
			streaming = parser.getValor(Utilidades.KEY_STREAMING);
			url_streaming = parser.getValor(Utilidades.KEY_URL_STREAMING);
			url_stream = parser.getValor(Utilidades.KEY_URL_STREAM);

			/**
			 * NOTICIAS.
			 */
			ImageButton IBnoticias = (ImageButton) findViewById(R.id.IBnoticias);

			IBnoticias.setOnClickListener(new OnClickListener() {

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
			ImageButton IBreportajes = (ImageButton) findViewById(R.id.IBreportajes);

			IBreportajes.setOnClickListener(new OnClickListener() {

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
			ImageButton IBmasdeporte = (ImageButton) findViewById(R.id.IBmasdeporte);

			IBmasdeporte.setOnClickListener(new OnClickListener() {

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
			ImageButton IBvideoencuentro = (ImageButton) findViewById(R.id.IBvideoencuentro);

			IBvideoencuentro.setOnClickListener(new OnClickListener() {

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
			ImageButton IBdirecto = (ImageButton) findViewById(R.id.IBdirecto);
			
			IBdirecto.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					if (streaming.contains("NO")) {
						// Pasarle al videoView la url del streaming.
						if (Utilidades.hayInternet(PrincipalActivity.this, false)) {
							Intent in = new Intent(getApplicationContext(),
									DirectoActivity.class);
							in.putExtra(Utilidades.KEY_URL_STREAMING, url_streaming
									+ url_stream);
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
			ImageButton IBbuscar = (ImageButton) findViewById(R.id.IBbuscar);

			IBbuscar.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					Intent in = new Intent(getApplicationContext(),
							BuscarActivity.class);
					startActivity(in);
				}

			});

		}
	}
	
}