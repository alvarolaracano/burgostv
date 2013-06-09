package es.burgostv;

import es.burgostv.R;

import es.burgostv.utiles.Utilidades;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
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
		
		//path = "rtsp://188.40.15.22:554/programado/burgosTV";
		//path = "rtsp://212-125.livestream.com:8080/livestreamiphone/4148639_2131179_64430285_1_600@32134";
		//path = "rtsp://212-125.livestream.com:8080/livestreamiphone/3891754_2079350_785fdd4e_1_198@111080";
		path= "rtsp://188.40.15.22:554/webcam/webcam2";
		
		Toast.makeText(DirectoActivity.this, path, Toast.LENGTH_LONG).show();
		
		Log.i("DirectoActivity","url directo: " + convierteaRSTP(path));
		//video.setVideoURI(Uri.parse(convierteaRSTP(path)));
		VVdirecto.setVideoURI(Uri.parse(path));
		//VVdirecto.setVideoPath(convierteaRSTP(path));

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
		
		//Evento para saber si esta cargando el buffer.
		//VVdirecto.seton

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
		pathRTSP = pathRTSP.replace("22", "22:554");
		return pathRTSP;
	}
	
	
	/**
	 * VideoView para directo que redimensiona video
	 * para que ocupe toda la pantalla.
	 * @author Alvaro Lara Cano
	 *
	 */
	public class VideoViewDirecto extends VideoView {

	    private int mForceHeight = 0;
	    private int mForceWidth = 0;
	    
	    public VideoViewDirecto(Context context) {
	        super(context);
	    }

	    public VideoViewDirecto(Context context, AttributeSet attrs) {
	        this(context, attrs, 0);
	    }

	    public VideoViewDirecto(Context context, AttributeSet attrs, int defStyle) {
	        super(context, attrs, defStyle);
	    }

	    public void setDimensions(int w, int h) {
	        this.mForceHeight = h;
	        this.mForceWidth = w;

	    }

	    @Override
	    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	        setMeasuredDimension(mForceWidth, mForceHeight);
	    }
	}

}
