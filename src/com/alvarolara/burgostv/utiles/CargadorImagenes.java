package com.alvarolara.burgostv.utiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.alvarolara.burgostv.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Carga las imagenes.
 * @author Alvaro Lara Cano
 *
 */
public class CargadorImagenes {

	/**
	 * Caché de memoria.
	 */
	private CacheMemoria cacheMemoria = new CacheMemoria();
	
	/**
	 * Cache de Archivo.
	 */
	private CacheArchivo cacheArchivo;
	
	/**
	 * Coleccion de ImageViews.
	 */
	private Map<ImageView, String> imageViews = Collections
			.synchronizedMap(new WeakHashMap<ImageView, String>());
	
	/**
	 * Servicio Ejecutor.
	 */
	private ExecutorService executorService;

	/**
	 * Tamano de escalado (mejor si es potencia de 2).
	 */
	private int TAMANO;
	
	

	/**
	 * Constructor del cargador de imagenes.
	 * @param contexto
	 */
	public CargadorImagenes(Context contexto) {
		cacheArchivo = new CacheArchivo(contexto);
		executorService = Executors.newFixedThreadPool(5);
	}

	int IVnoticia ;

	/**
	 * Muestra la imagen.
	 * @param url
	 * @param imageView
	 * @param tamano
	 */
	public void muestraImagen(String url, ImageView imageView, String tamano) {

		if (tamano.contains("P")) {
			// tamaño pequeño.
			TAMANO = 70;
			IVnoticia = R.drawable.noticia;
		} else {
			// tamaño grande.
			TAMANO = 300;
			IVnoticia = R.drawable.noticia_grande;
		}

		imageViews.put(imageView, url);
		Bitmap bitmap = cacheMemoria.get(url);
		if (bitmap != null)
			imageView.setImageBitmap(bitmap);
		else {
			encolaFoto(url, imageView);
			imageView.setImageResource(IVnoticia);
		}
	}

	
	/**
	 * Encola la codificacion de la foto.
	 * @param url
	 * @param imageView
	 */
	private void encolaFoto(String url, ImageView imageView) {
		FotoACargar foto = new FotoACargar(url, imageView);
		executorService.submit(new CargadorFotos(foto));
	}

	
	/**
	 * Obtiene el bitmap o de cache o de internet.
	 * @param url
	 * @return
	 */
	private Bitmap getBitmap(String url) {
		File archivo = cacheArchivo.getFile(url);

		// Si ya esta en cache.
		Bitmap b = decodificaArchivo(archivo);
		if (b != null)
			return b;

		// Obtenerla de internet.
		try {
			Bitmap bitmap = null;
			URL urlImagen = new URL(url);
			HttpURLConnection conexion = (HttpURLConnection) urlImagen
					.openConnection();
			conexion.setConnectTimeout(30000);
			conexion.setReadTimeout(30000);
			conexion.setInstanceFollowRedirects(true);
			InputStream is = conexion.getInputStream();
			OutputStream os = new FileOutputStream(archivo);
			copiaStream(is, os);
			os.close();
			bitmap = decodificaArchivo(archivo);
			/*if(bitmap==null)
				bitmap = getBitmap(Utilidades.PROCESANDO);*/
			return bitmap;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	
	/**
	 * Decodifica la imagen y la escala para reducir el
	 * consumo de memoria.
	 * @param f
	 * @return
	 */
	private Bitmap decodificaArchivo(File archivo) {
		try {
			// Decodifica el tamano de la imagen.
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(archivo), null, o);

			int anchoTemporal = o.outWidth, altoTemporal = o.outHeight;
			int escala = 1;
			while (true) {
				if (anchoTemporal / 2 < TAMANO || altoTemporal / 2 < TAMANO)
					break;
				anchoTemporal /= 2;
				altoTemporal /= 2;
				escala *= 2;
			}

			// Decodifica con inSampleSize.
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = escala;
			return BitmapFactory.decodeStream(new FileInputStream(archivo), null, o2);
		} catch (FileNotFoundException e) {
		}
		return null;
	}


	/**
	 * Fotos para cargarlas.
	 * @author 
	 *
	 */
	private class FotoACargar {
		public String url;
		public ImageView imageView;

		public FotoACargar(String u, ImageView i) {
			url = u;
			imageView = i;
		}
	}

	
	/**
	 * Carga FotoACargar.
	 * @author 
	 *
	 */
	class CargadorFotos implements Runnable {
		FotoACargar fotoAcargar;

		/**
		 * Constructor de CargadorFotos.
		 * @param fotoAcargar
		 */
		CargadorFotos(FotoACargar fotoAcargar) {
			this.fotoAcargar = fotoAcargar;
		}

		/**
		 * Ejecuta la clase.
		 */
		public void run() {
			if (imageViewReusado(fotoAcargar))
				return;
			Bitmap bmp = getBitmap(fotoAcargar.url);
			/*if(bmp==null)
				bmp = getBitmap(Utilidades.PROCESANDO);*/
			cacheMemoria.put(fotoAcargar.url, bmp);
			if (imageViewReusado(fotoAcargar))
				return;
			MostradorBitmap mostrador = new MostradorBitmap(bmp, fotoAcargar);
			Activity a = (Activity) fotoAcargar.imageView.getContext();
			a.runOnUiThread(mostrador);
		}
	}

	
	/**
	 * Encuentra un imageView.
	 * @param fotoACargar
	 * @return
	 */
	boolean imageViewReusado(FotoACargar fotoACargar) {
		String tag = imageViews.get(fotoACargar.imageView);
		if (tag == null || !tag.equals(fotoACargar.url))
			return true;
		return false;
	}
	
	
	/**
	 * Copia el InputStream al OutputStream.
	 * @param is
	 * @param os
	 */
	public static void copiaStream(InputStream is, OutputStream os) {
		final int tamano_buffer = 1024;
		try {
			byte[] bytes = new byte[tamano_buffer];
			for (;;) {
				int contador = is.read(bytes, 0, tamano_buffer);
				if (contador == -1)
					break;
				os.write(bytes, 0, contador);
			}
		} catch (Exception ex) {
		}
	}

	
	/**
	 * Se usa para mostrar el Bitmap en el thread de la UI.
	 * @author 
	 *
	 */
	class MostradorBitmap implements Runnable {
		Bitmap bitmap;
		FotoACargar fotoACargar;

		/**
		 * Constructor de MostradorBitmap.
		 * @param bitmap
		 * @param fotoACargar
		 */
		public MostradorBitmap(Bitmap bitmap, FotoACargar fotoACargar) {
			this.bitmap = bitmap;
			this.fotoACargar = fotoACargar;
		}

		/**
		 * Ejecuta la clase.
		 */
		public void run() {
			if (imageViewReusado(fotoACargar))
				return;
			if (bitmap != null)
				fotoACargar.imageView.setImageBitmap(bitmap);
			else
				fotoACargar.imageView.setImageResource(IVnoticia);
		}
	}

	/**
	 * Limpiar toda la cache.
	 */
	public void clearCache() {
		cacheMemoria.limpiaCacheMemoria();
		cacheArchivo.limpiaCacheArchivo();
	}
	

}
