package com.alvarolara.burgostv;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

/**
 * Redirecciona actividad cuando abre una URL.
 * @author Alvaro Lara Cano
 *
 */
public class RedireccionaActivity extends Activity{
	
	
	/**
	 * Llamado cuando la Actividad es creada.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		//Recoger el intent.
		Intent intent = getIntent();
		
		//Mirar si es telefono o tablet.
		if(getResources().getString(R.string.tipo_pantalla).equals("telefono")){
			Intent in = new Intent(getApplicationContext(), ObjetoActivity.class);
			//Enviarle la URL.
			in.putExtra("url", intent.getData().toString());
			startActivity(in);
		}else{
			Intent in = new Intent(getApplicationContext(), com.alvarolara.burgostv.fragment.ObjetoActivity.class);
			//Enviarle la URL.
			in.putExtra("url", intent.getData().toString());
			startActivity(in);
		}
	}

}
