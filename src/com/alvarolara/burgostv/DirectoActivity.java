package com.alvarolara.burgostv;

import com.alvarolara.burgostv.utiles.Utilidades;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

/**
 * Visualiza streaming e directo.
 * @author Alvaro Lara Cano
 *
 */
public class DirectoActivity extends Activity implements OnCompletionListener {

	/**
	 * Videoview para reproducir el video.
	 */
	private VideoView VVdirecto;
	
	/**
	 * Controlador multimedia.
	 */
	private MediaController controlador;
	
	/**
	 * ImageView para el play.
	 */
	private ImageView IVplay;
	

	
	/**
	 * Llamado cuando la Actividad es creada.
	 */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		setContentView(R.layout.directo);
		
		// No apagar la pantalla.
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		
		//Alpha al play.
		((ImageView) findViewById(R.id.IVplay)).setAlpha(200);
		

		// Recoger la URl a través del intent.
		Intent in = getIntent();
		String path = in.getStringExtra(Utilidades.KEY_URL_STREAMING);

		VVdirecto = (VideoView) findViewById(R.id.VVdirecto);
		
		//path =  "rtsp://188.40.15.22/webcam/webcam1";
		path = "rtsp://188.40.15.22:1935/programado/burgosTV";
		//path = "rtsp://218.204.223.237:554/live/1/66251FC11353191F/e7ooqwcfbqjoo80j.sdp";
		//path = "rtsp://v5.cache1.c.youtube.com/CjYLENy73wIaLQnhycnrJQ8qmRMYESARFEIJbXYtZ29vZ2xlSARSBXdhdGNoYPj_hYjnq6uUTQw=/0/0/0/video.3gp";
		//path = "rtsp://184.72.239.149/vod/mp4:BigBuckBunny_175k.mov";
		Log.i("url directo: ",convierteaRSTP(path));
		//video.setVideoURI(Uri.parse(convierteaRSTP(path)));
		VVdirecto.setVideoPath(convierteaRSTP(path));

		IVplay = (ImageView) findViewById(R.id.IVplay);

		VVdirecto.setMediaController(controlador);
		VVdirecto.requestFocus();
		
		VVdirecto.setOnErrorListener(new OnErrorListener() {
			
			public boolean onError(MediaPlayer mp, int what, int extra) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Error al reproducir el video.",Toast.LENGTH_LONG).show();
				finish();
				return false;
			}
		});

		VVdirecto.start();
		VVdirecto.setOnCompletionListener(this);

		// Evento play/pause.
		VVdirecto.setOnTouchListener(new View.OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {

				if (VVdirecto.isPlaying()) {
					VVdirecto.pause();
					// Mostrar el boton de play.
					IVplay.setVisibility(View.VISIBLE);
				} else {
					VVdirecto.start();
					// Ocultar boton de play.
					IVplay.setVisibility(View.GONE);
				}

				return false;
			}
		});
		
		//Desactivar el progressbar cargando cuando acabe de cargarse.
		VVdirecto.setOnPreparedListener(new OnPreparedListener() {
			
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

	
	/**
	 * Convierte una direccion RTMP a RTSP :1935.
	 * @param path
	 * @return
	 */
	private String convierteaRSTP(String path){
		//Reemplazar RTMP por RTSP.
		String pathRTSP = path.replace("rtmp", "rtsp");
		
		//añadir el puerto 1935.
		pathRTSP = pathRTSP.replace("22", "22:1935");
		return pathRTSP;
	}

}
