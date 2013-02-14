package com.alvarolara.burgostv.utiles;

import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import android.graphics.Bitmap;

/**
 * Crea la cache de memoria.
 * @author Alvaro Lara Cano
 *
 */
public class CacheMemoria {
	
	/**
	 * Mapa de Bitmaps para la memoria.
	 */
	private Map<String, SoftReference<Bitmap>> cache = Collections
			.synchronizedMap(new HashMap<String, SoftReference<Bitmap>>());

	/**
	 * Devuelve el Bitmap segun la id.
	 * @param id
	 * @return
	 */
	public Bitmap get(String id) {
		if (!cache.containsKey(id))
			return null;
		SoftReference<Bitmap> ref = cache.get(id);
		return ref.get();
	}

	
	/**
	 * Pone en la cache la id y el bitmap.
	 * @param id
	 * @param bitmap
	 */
	public void put(String id, Bitmap bitmap) {
		cache.put(id, new SoftReference<Bitmap>(bitmap));
	}

	
	/**
	 * Limpia la cache.
	 */
	public void limpiaCacheMemoria() {
		cache.clear();
	}
}