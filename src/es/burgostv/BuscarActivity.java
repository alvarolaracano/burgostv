package es.burgostv;

import es.burgostv.R;

import es.burgostv.async.CargaXML;
import es.burgostv.utiles.Utilidades;

import android.app.Activity;
import android.content.pm.ActivityInfo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Busca objetos por peticion HTTP.
 * @author Alvaro Lara Cano
 *
 */
public class BuscarActivity extends Activity {

	/**
	 * Variable para la busqueda.
	 */
	private String busqueda = "";

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
		
		//Establecer layout segun telefono o tablet.
		if(getResources().getString(R.string.tipo_pantalla).equals("telefono")){
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			portrait = true;
			
		}else{
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			portrait = false;
		}

		setContentView(R.layout.buscar);

		// Foco en el edittext.
		EditText ETbuscar = (EditText) findViewById(R.id.ETbuscar);
		ETbuscar.setFocusable(true);
		ETbuscar.requestFocus();

		// Forzar el teclado a aparecer.
		this.getWindow()
				.setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);

		// Seleccionar por defecto el fecha y si cambia, desmarcar el otro.

		final RadioGroup RGordenar = (RadioGroup) findViewById(R.id.RGordenar);
		RGordenar.check(R.id.RGordenarFecha);

		// Obtener los radiobuttons.
		final RadioButton RGordenarFecha = (RadioButton) findViewById(R.id.RGordenarFecha);
		final RadioButton RGordenarPopularidad = (RadioButton) findViewById(R.id.RGordenarPopularidad);

		RGordenar
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						if (RGordenarFecha.isChecked()) {
							RGordenarFecha.setChecked(false);
							RGordenarPopularidad.setChecked(true);

						} else if (RGordenarPopularidad.isChecked()) {
							RGordenarPopularidad.setChecked(false);
							RGordenarFecha.setChecked(true);
						}
					}
				});

		// Eventlistener para el boton.
		ImageButton IBbuscar = (ImageButton) findViewById(R.id.IBbuscar);

		IBbuscar.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// Al hacer click en el boton.
				if (Utilidades.hayInternet(BuscarActivity.this, false)) {
					// Obtener el tipo para enviarlo al Intent.
					String tipo = "1";
					if (RGordenar.getCheckedRadioButtonId() == R.id.RGordenarFecha) {
						tipo = "1";
						Log.i("BuscarActivity", "ordenar por fecha");
					} else if (RGordenar.getCheckedRadioButtonId() == R.id.RGordenarPopularidad) {
						tipo = "2";
						Log.i("BuscarActivity", "ordenar por popularidad");
					}
	
					EditText ETbuscar = (EditText) findViewById(R.id.ETbuscar);
					busqueda = ETbuscar.getText().toString();
	
					if (busqueda.length() < 2) {
	
						// Mensaje de error
						Toast.makeText(BuscarActivity.this,
								"La búsqueda es demasido corta", Toast.LENGTH_LONG)
								.show();
	
					} else {
						// Reemplazar espacios en blanco por '+'.
						busqueda = busqueda.replace(' ', '+');
	
						// Formar la cadena de busqueda completa.
						String URL_BUSQUEDA = Utilidades.URL_BUSCAR + "?busqueda=" + busqueda
								+ "&tipo=" + tipo;
						if (Utilidades.hayInternet(BuscarActivity.this, false)) {
							new CargaXML(BuscarActivity.this, URL_BUSQUEDA, portrait).execute();
							Log.i("cadena de busqueda: ", URL_BUSQUEDA);
						}
	
					}
				}
			}
		});

	}

}
