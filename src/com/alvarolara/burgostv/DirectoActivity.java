package com.alvarolara.burgostv;

import com.alvarolara.burgostv.utiles.Utilidades;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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

public class DirectoActivity extends Activity implements OnCompletionListener {

	/**
	 * Videoview para reproducir el video.
	 */
	private VideoView video;
	
	/**
	 * Controlador multimedia.
	 */
	private MediaController ctlr;
	
	/**
	 * ImageView para el play.
	 */
	private ImageView play;
	
	/**
	 * Manejador para actualizar el seekbar.
	 */
	private Handler mHandler = new Handler();

	
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
		

		// Recoger la URl a través del intent.
		Intent in = getIntent();
		String path = in.getStringExtra(Utilidades.KEY_URL_STREAMING);

		video = (VideoView) findViewById(R.id.video);
		
		//path =  "rtsp://188.40.15.22/webcam/webcam1";
		path = "rtsp://188.40.15.22:1935/programado/burgosTV";
		//path = "rtsp://218.204.223.237:554/live/1/66251FC11353191F/e7ooqwcfbqjoo80j.sdp";
		//path = "rtsp://v5.cache1.c.youtube.com/CjYLENy73wIaLQnhycnrJQ8qmRMYESARFEIJbXYtZ29vZ2xlSARSBXdhdGNoYPj_hYjnq6uUTQw=/0/0/0/video.3gp";
		//path = "rtsp://184.72.239.149/vod/mp4:BigBuckBunny_175k.mov";
		Log.i("url directo: ",convierteaRSTP(path));
		//video.setVideoURI(Uri.parse(convierteaRSTP(path)));
		video.setVideoPath(convierteaRSTP(path));

		play = (ImageView) findViewById(R.id.play);

		video.setMediaController(ctlr);
		video.requestFocus();
		video.start();
		video.setOnCompletionListener(this);

		// Evento play/pause.
		video.setOnTouchListener(new View.OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {

				if (video.isPlaying()) {
					video.pause();
					// Mostrar el boton de play.
					play.setVisibility(View.VISIBLE);
				} else {
					video.start();
					// Ocultar boton de play.
					play.setVisibility(View.GONE);
				}

				return false;
			}
		});

	}

	/**
	 * Cuando acabe de reproducir, finaliza el Activity.
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
