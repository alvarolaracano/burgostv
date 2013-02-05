package com.alvarolara.burgostv;

import com.alvarolara.burgostv.utiles.Utilidades;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoActivity extends Activity implements OnCompletionListener,
		SeekBar.OnSeekBarChangeListener {

	// private static final String path
	// ="http://www.burgostv.es/noticias/2012/julio/25-07-12_2-SD.mp4";
	private VideoView video;
	private MediaController ctlr;
	private SeekBar seek;
	private TextView actual;
	private TextView total;
	private ImageView play;
	static final String KEY_URL_VIDEO = "url_video";

	// Manejador para actualizar el seekbar.
	private Handler mHandler = new Handler();
	private Utilidades utils;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		setContentView(R.layout.video);
		// No apagar la pantalla.
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		// Instanciar Utilidades.
		utils = new Utilidades();

		// Recoger la URl a través del intent.
		Intent in = getIntent();
		String path = in.getStringExtra(KEY_URL_VIDEO);

		video = (VideoView) findViewById(R.id.video);
		video.setVideoPath(path);

		// Controles de reproducción antiguos.
		/*
		 * ctlr = new MediaController(this); ctlr.setMediaPlayer(video);
		 */
		play = (ImageView) findViewById(R.id.play);

		// duracion Actual y total.
		actual = (TextView) findViewById(R.id.duracionActual);
		total = (TextView) findViewById(R.id.duracionTotal);

		// Mi seekbar
		seek = (SeekBar) findViewById(R.id.seekBar);
		seek.setProgress(0);
		seek.setMax(100);
		seek.setOnSeekBarChangeListener(this);

		video.setMediaController(ctlr);
		video.requestFocus();
		video.start();
		video.setOnCompletionListener(this);
		updateProgressBar();

		// Evento play/pause.
		video.setOnTouchListener(new View.OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {

				if (video.isPlaying()) {
					video.pause();
					// Toast.makeText(v.getContext(), "pausa",
					// Toast.LENGTH_LONG).show();
					// Mostrar el boton de play.
					play.setVisibility(View.VISIBLE);
					updateProgressBar();
				} else {
					video.start();
					// Toast.makeText(v.getContext(), "play",
					// Toast.LENGTH_LONG).show();
					// Ocultar boton de play.
					play.setVisibility(View.GONE);
					updateProgressBar();
				}

				return false;
			}
		});

	}

	public void onCompletion(MediaPlayer mp) {
		// Cuando acabe de reproducir, finaliza el Activity.
		finish();
	}

	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// Actualizar el video con la posicion del seek.

	}

	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		mHandler.removeCallbacks(mUpdateTimeTask);
	}

	/**
	 * Cuando el usuario acaba de desplazar el seek.
	 */
	public void onStopTrackingTouch(SeekBar seekBar) {

		mHandler.removeCallbacks(mUpdateTimeTask);

		int duracionTotal = video.getDuration();
		int posicionActual = utils.progresoATimer(seekBar.getProgress(),
				duracionTotal);

		int posicionBuffer = seek.getSecondaryProgress();

		// Si la posicion del seek es > del 15 % del buffer, mantenemos donde
		// estaba.

		int posicion = (posicionActual * 100) / duracionTotal;

		// Toast.makeText(getApplicationContext(),
		// "posicion: "+posicion+" => buffer "+posicionBuffer,
		// Toast.LENGTH_LONG).show();

		// posicionbuffer-15 para un rango.
		if (posicion < posicionBuffer) {
			// Movernos a la posicionActual.

			// Toast.makeText(getApplicationContext(), "SI mueve posicion",
			// Toast.LENGTH_LONG).show();
			video.seekTo(posicionActual);

		} else {
			// Toast.makeText(getApplicationContext(), "NO mueve posicion",
			// Toast.LENGTH_LONG).show();
		}

		// Actualizar el timmer.
		updateProgressBar();
	}

	/**
	 * Actualizar la seek bar.
	 */
	public void updateProgressBar() {
		mHandler.postDelayed(mUpdateTimeTask, 100);
	}

	private Runnable mUpdateTimeTask = new Runnable() {
		public void run() {

			long duracionTotal = video.getDuration();
			long duracionActual = video.getCurrentPosition();

			// Mostar el tiempo total.
			total.setText("" + utils.milisegundosATimer(duracionTotal));

			// Mostar el tiempo actual. Si no tiene un 00, añadir un espacio.
			if (utils.milisegundosATimer(duracionActual).toString().length() == 4) {
				actual.setText(" " + utils.milisegundosATimer(duracionActual)
						+ " /");
			} else {
				actual.setText("" + utils.milisegundosATimer(duracionActual)
						+ " /");
			}

			// Updating progress bar
			int progress = (int) (utils.porcentajeProgreso(duracionActual,
					duracionTotal));

			seek.setProgress(progress);

			int descargado = video.getBufferPercentage();

			seek.setSecondaryProgress(descargado);

			// Ejecutar este Thread despues de 100ms.
			mHandler.postDelayed(this, 100);
		}
	};
}
