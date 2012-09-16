package com.alvarolara.burgostv;

import com.alvarolara.burgostv.utiles.Utilities;

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

public class DirectoActivity extends Activity implements OnCompletionListener{

	private VideoView video;
	private MediaController ctlr;
	private SeekBar seek;
	private TextView actual;
	private TextView total;
	private ImageView play;
	static final String KEY_URL_STREAMING = "url_streaming";
	
	//Manejador para actualizar el seekbar.
	private Handler mHandler = new Handler();
	private Utilities utils;
	
	
	@Override
	public void onCreate(Bundle icicle) {
	        super.onCreate(icicle);
	        getWindow().setFormat(PixelFormat.TRANSLUCENT);
	        setContentView(R.layout.directo);
	        //No apagar la pantalla.
	        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	        
	        //Instanciar Utils.
	        utils = new Utilities();
	        
	        //Recoger la URl a través del intent.
	        Intent in = getIntent();
	        String path = in.getStringExtra(KEY_URL_STREAMING);
	        
	        video = (VideoView) findViewById(R.id.video);
	        video.setVideoPath(path);
	        

	        play = (ImageView) findViewById(R.id.play);
	        
	        
	        video.setMediaController(ctlr);
	        video.requestFocus();
	        video.start();
	        video.setOnCompletionListener(this);
	        
	        // Evento play/pause.
	        video.setOnTouchListener(new View.OnTouchListener() {
				
				public boolean onTouch(View v, MotionEvent event) {
					 
					 if(video.isPlaying()){
						 video.pause();
						 //Mostrar el boton de play.
						 play.setVisibility(View.VISIBLE);
					 }else{
						 video.start();
						 //Ocultar boton de play.
						 play.setVisibility(View.GONE);
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

}

