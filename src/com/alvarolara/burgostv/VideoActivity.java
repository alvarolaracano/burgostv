package com.alvarolara.burgostv;

import com.alvarolara.burgostv.utiles.Utilidades;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

/**
 * Visualiza un objeto descargado por HTTP.
 * @author Alvaro Lara Cano
 *
 */
public class VideoActivity extends Activity implements OnCompletionListener,
		SeekBar.OnSeekBarChangeListener {

	/**
	 * Videoview para visualizar el video.
	 */
	private VideoView VVvideo;
	
	/**
	 * Controlador multimedia.
	 */
	private MediaController controlador;
	
	/**
	 * Barra de progreso.
	 */
	private SeekBar SBbarraProgreso;
	
	/**
	 * TextView para tiempo transcurrido. 
	 */
	private TextView TVduracionActual;
	
	/**
	 * TextView para tiempo total.
	 */
	private TextView TVduracionTotal;
	
	/**
	 * ImageView play.
	 */
	private ImageView IVplay;
	
	/**
	 * Manejador para actualizar el seekbar.
	 */
	private Handler manejador = new Handler();
	
	/**
	 * Utilidades.
	 */
	private Utilidades utilidades;
	

	/**
	 * Llamado cuando la Actividad es creada.
	 */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		setContentView(R.layout.video);
		
		// No apagar la pantalla.
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		
		//Alpha al play.
		((ImageView) findViewById(R.id.IVplay)).setAlpha(200);

		// Instanciar Utilidades.
		utilidades = new Utilidades();

		// Recoger la URl a través del intent.
		Intent in = getIntent();
		String path = in.getStringExtra(Utilidades.KEY_URL_VIDEO);

		VVvideo = (VideoView) findViewById(R.id.VVvideo);
		VVvideo.setVideoPath(path);
		IVplay = (ImageView) findViewById(R.id.IVplay);

		// Duracion Actual y total.
		TVduracionActual = (TextView) findViewById(R.id.TVduracionActual);
		TVduracionTotal = (TextView) findViewById(R.id.TVduracionTotal);

		// Mi seekbar personalizado.
		SBbarraProgreso = (SeekBar) findViewById(R.id.SBbarraProgreso);
		SBbarraProgreso.setProgress(0);
		SBbarraProgreso.setMax(100);
		SBbarraProgreso.setOnSeekBarChangeListener(this);

		VVvideo.setMediaController(controlador);
		VVvideo.requestFocus();
		VVvideo.start();
		VVvideo.setOnCompletionListener(this);
		updateProgressBar();

		// Evento play/pause.
		VVvideo.setOnTouchListener(new View.OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {

				if (VVvideo.isPlaying()) {
					VVvideo.pause();
					// Mostrar el boton de play.
					IVplay.setVisibility(View.VISIBLE);
					updateProgressBar();
				} else {
					VVvideo.start();
					// Ocultar boton de play.
					IVplay.setVisibility(View.GONE);
					updateProgressBar();
				}

				return false;
			}
		});
		
		//Desactivar el progressbar cargando cuando acabe de cargarse.
		VVvideo.setOnPreparedListener(new OnPreparedListener() {
			
			public void onPrepared(MediaPlayer mp) {
				// TODO Auto-generated method stub
				((ProgressBar)findViewById(R.id.PBcargando)).setVisibility(View.GONE);
			}
		});

	}
	
	
	/**
	 * Cuando acaba de reproducir, finaliza el Activity.
	 */
	public void onCompletion(MediaPlayer mp) {
		finish();
	}

	
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		

	}

	public void onStartTrackingTouch(SeekBar seekBar) {
		manejador.removeCallbacks(mUpdateTimeTask);
	}

	/**
	 * Cuando el usuario acaba de desplazar el seek.
	 */
	public void onStopTrackingTouch(SeekBar seekBar) {

		manejador.removeCallbacks(mUpdateTimeTask);

		int duracionTotal = VVvideo.getDuration();
		int posicionActual = utilidades.progresoATimer(seekBar.getProgress(),
				duracionTotal);

		// Si la posicion del seek es > del 15 % del buffer, mantenemos donde
		// estaba.

		// posicionbuffer-15 para un rango.
		if (((posicionActual * 100) / duracionTotal) < SBbarraProgreso.getSecondaryProgress()) {
			// Movernos a la posicionActual.
			VVvideo.seekTo(posicionActual);
		}

		// Actualizar el timmer.
		updateProgressBar();
	}

	/**
	 * Actualizar la seek bar.
	 */
	public void updateProgressBar() {
		manejador.postDelayed(mUpdateTimeTask, 100);
	}

	/**
	 * Runnable la duracion actual/total.
	 */
	private Runnable mUpdateTimeTask = new Runnable() {
		public void run() {

			long duracionTotal = VVvideo.getDuration();
			long duracionActual = VVvideo.getCurrentPosition();

			// Mostar la duracion total.
			TVduracionTotal.setText("" + utilidades.milisegundosATimer(duracionTotal));

			// Mostar la duracion actual. Si no tiene un 00, añadir un espacio.
			if (utilidades.milisegundosATimer(duracionActual).toString().length() == 4) {
				TVduracionActual.setText(" " + utilidades.milisegundosATimer(duracionActual)
						+ " /");
			} else {
				TVduracionActual.setText("" + utilidades.milisegundosATimer(duracionActual)
						+ " /");
			}

			// Actualizar la barra de progreso.
			int progress = (int) (utilidades.porcentajeProgreso(duracionActual,
					duracionTotal));

			SBbarraProgreso.setProgress(progress);

			//Porcentaje descargado.
			int descargado = VVvideo.getBufferPercentage();

			SBbarraProgreso.setSecondaryProgress(descargado);

			// Ejecutar este Thread despues de 100ms.
			manejador.postDelayed(this, 100);
		}
	};
}
