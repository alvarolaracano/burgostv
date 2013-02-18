package com.alvarolara.burgostv.clases;

import java.io.Serializable;

/**
 * Objeto generico para mapear los recibidos por HTTP.
 * Noticia, Reportaje, Magazine...
 * @author Alvaro Lara Cano
 *
 */
public class Objeto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Titulo.
	 */
	private String titulo;
	
	/**
	 * Descripcion.
	 */
	private String descripcion;
	
	/**
	 * Url_foto.
	 */
	private String url_foto;
	
	/**
	 * Url_video.
	 */
	private String url_video;
	
	/**
	 * Fecha.
	 */
	private String fecha;
	
	
	/**
	 * COnstructor vacio.
	 */
	public Objeto(){
		
	}
	
	
	/**
	 * Constructor sin fecha.
	 * @param titulo
	 * @param descripcion
	 * @param url_foto
	 * @param url_video
	 */
	public Objeto(String titulo, String descripcion, String url_foto, String url_video){
		this.titulo =  titulo;
		this.descripcion =  descripcion;
		this.url_foto = url_foto;
		this.url_video = url_video;
	}
	
	/**
	 * Constructor con fecha.
	 * @param titulo
	 * @param descripcion
	 * @param url_foto
	 * @param url_video
	 * @param fecha
	 */
	public Objeto(String titulo, String descripcion, String url_foto, String url_video, String fecha){
		this.titulo =  titulo;
		this.descripcion =  descripcion;
		this.url_foto = url_foto;
		this.url_video = url_video;
		this.fecha = fecha;
	}
	
	
	/**
	 * Metodo toString.
	 */
	@Override
	public String toString() {
		return "Objeto [titulo=" + titulo + ", descripcion=" + descripcion
				+ ", url_foto=" + url_foto + ", url_video=" + url_video
				+ ", fecha=" + fecha + "]";
	}


	/**
	 * Devuelve el titulo.
	 * @return titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * Establece el titulo.
	 * @param titulo
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * Devuelve la descripcion.
	 * @return
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Establece la descripcion.
	 * @param descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Devuelve la url_foto.
	 * @return url_foto
	 */
	public String getUrl_foto() {
		return url_foto;
	}

	/**
	 * Establece la url_foto.
	 * @param url_foto
	 */
	public void setUrl_foto(String url_foto) {
		this.url_foto = url_foto;
	}

	/**
	 * Devuelve la url_video.
	 * @return url_video
	 */
	public String getUrl_video() {
		return url_video;
	}

	/**
	 * Establece la url_video.
	 * @param url_video
	 */
	public void setUrl_video(String url_video) {
		this.url_video = url_video;
	}
	
	/**
	 * Devuelve la fecha.
	 * @return fecha
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * Establece la fecha.
	 * @param fecha
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
}
