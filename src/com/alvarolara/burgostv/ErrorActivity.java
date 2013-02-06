package com.alvarolara.burgostv;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class ErrorActivity extends Activity {

	/**
	 * Llamado cuando la Actividad es creada.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.error);

	}

}
