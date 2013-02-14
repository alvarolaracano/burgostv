package com.alvarolara.burgostv;

import com.alvarolara.burgostv.R;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;

/**
 * Splash con el logo de la aplicacion.
 * @author Alvaro Lara Cano
 *
 */
public class SplashActivity extends Activity {

	/**
	 * Valores para el timmer.
	 */
	private boolean activo = true;
	private int tiempo = 2000;

	/**
	 * Llamado cuando se crea la actividad.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		//Establecer layout segun telefono o tablet.
		if(getResources().getString(R.string.tipo_pantalla).equals("telefono")){
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}else{
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}

		setContentView(R.layout.splash);

		// Tread que controla el tiempo de la pantalla.
		Thread splashTread = new Thread() {
			@Override
			public void run() {
				try {
					int waited = 0;
					while (activo && (waited < tiempo)) {
						sleep(100);
						if (activo) {
							waited += 100;
						}
					}
				} catch (InterruptedException e) {
					// do nothing
				} finally {
					finish();
					// Comenzar el activity PrincipalActivity.
					Intent in = new Intent(getApplicationContext(),
							PrincipalActivity.class);
					startActivity(in);
				}
			}
		};

		// Comenzar el Thread.
		splashTread.start();
	}

	/**
	 * Aunque toques la pantalla, que siga mostrando el splash.
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			activo = true;
		}
		return true;
	}

}