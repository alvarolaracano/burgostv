package com.alvarolara.burgostv;

import com.alvarolara.burgostv.async.CargaXML;
import com.alvarolara.burgostv.utiles.Utilidades;

import android.app.Activity;

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

public class BuscarActivity extends Activity {

	/**
	 * Variable para la busqueda.
	 */
	private String busqueda = "";

	
	/**
	 * Llamado cuando la Actividad es creada.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.buscar);

		// Foco en el edittext.
		EditText editTextBuscar = (EditText) findViewById(R.id.editTextBuscar);
		editTextBuscar.setFocusable(true);
		editTextBuscar.requestFocus();

		// Forzar el teclado a aparecer.
		this.getWindow()
				.setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);

		// Seleccionar por defecto el fecha y si cambia, desmarcar el otro.

		final RadioGroup grupoOrdenar = (RadioGroup) findViewById(R.id.grupoOrdenar);
		grupoOrdenar.check(R.id.radioFecha1);

		// Obtener los radiobuttons.
		final RadioButton radioFecha1 = (RadioButton) findViewById(R.id.radioFecha1);
		final RadioButton radioPopularidad2 = (RadioButton) findViewById(R.id.radioPopularidad2);

		grupoOrdenar
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						if (radioFecha1.isChecked()) {
							radioFecha1.setChecked(false);
							radioPopularidad2.setChecked(true);

						} else if (radioPopularidad2.isChecked()) {
							radioPopularidad2.setChecked(false);
							radioFecha1.setChecked(true);
						}
					}
				});

		// Eventlistener para el boton.
		ImageButton botonBuscar = (ImageButton) findViewById(R.id.botonbuscar);

		botonBuscar.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// Al hacer click en el boton.
				if (Utilidades.hayInternet(BuscarActivity.this, false)) {
					// Obtener el tipo para enviarlo al Intent.
					String tipo = "1";
					if (grupoOrdenar.getCheckedRadioButtonId() == R.id.radioFecha1) {
						tipo = "1";
						// Log.i("checkbox1", "tipo=1");
					} else if (grupoOrdenar.getCheckedRadioButtonId() == R.id.radioPopularidad2) {
						tipo = "2";
						// Log.i("checkbox2", "tipo=2");
					}
	
					EditText editTextBuscar = (EditText) findViewById(R.id.editTextBuscar);
					busqueda = editTextBuscar.getText().toString();
	
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
	
						new CargaXML(BuscarActivity.this, URL_BUSQUEDA).execute();
						Log.i("cadena de busqueda: ", URL_BUSQUEDA);
	
					}
				}
			}
		});

	}

}
