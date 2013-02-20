package es.burgostv.utiles;

import java.io.File;
import android.content.Context;

/**
 * Crea la cahe de archivo.
 * @author Alvaro Lara Cano
 *
 */
public class CacheArchivo {

	/**
	 * Fichero para el cache.
	 */
	private File directorioCache;

	/**
	 * 
	 * @param context
	 */
	public CacheArchivo(Context context) {
		// Encontrar el directorio para almacenar las imagenes cacheadas.
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
			directorioCache = new File(
					android.os.Environment.getExternalStorageDirectory(),
					Utilidades.DIRECTORIO_APLICACION);
		else
			directorioCache = context.getCacheDir();
		if (!directorioCache.exists())
			directorioCache.mkdirs();
	}

	
	/**
	 * Asigna un fichero a la URL basandose en hashcode.
	 * @param url
	 * @return
	 */
	public File getFile(String url) {
		// Identificar las imagenes por un hashcode. 
		String nombreFichero = String.valueOf(url.hashCode());
		// Otra posible solucion a lo anterior podria ser.
		// String nombreFichero = URLEncoder.encode(url);
		File archivo = new File(directorioCache, nombreFichero);
		return archivo;

	}

	/**
	 * Limpia la cache.
	 */
	public void limpiaCacheArchivo() {
		File[] archivos = directorioCache.listFiles();
		if (archivos == null)
			return;
		for (File a : archivos)
			a.delete();
	}

}