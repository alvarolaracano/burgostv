package es.burgostv;

import es.burgostv.R;

import es.burgostv.clases.Objeto;
import es.burgostv.utiles.CargadorImagenes;
import es.burgostv.utiles.JustificaTexto;
import es.burgostv.utiles.Utilidades;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

/**
 * Visualiza un objeto y su descripcion.
 * @author Alvaro Lara Cano
 *
 */
public class ObjetoActivity extends Activity {

	/**
	 * Objeto auxiliar.
	 */
	Objeto objeto;
	
	
	/**
	 * Llamado cuando la Actividad es creada.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.objeto);
		
		
		//Alpha al play.
		((ImageView) findViewById(R.id.IVplay)).setAlpha(200);

		// Obtener el intent de ListaActivity.
		Intent in = getIntent();
		
		
		objeto = Utilidades.creaUrlObjeto(this, in);

		
		//Establecer el titulo.
		((TextView) findViewById(R.id.TVtitulo)).setText(objeto.getTitulo());
		
		//Establecer descripcion.
		((TextView) findViewById(R.id.TVdescripcion)).setText(objeto.getDescripcion());
		// Justificar la descripcion (como se vea, depende de cada pantalla).
		JustificaTexto.justifica(((TextView) findViewById(R.id.TVdescripcion)),	340f);
		
		
		//Añadimos la foto de la noticia.
		ImageView IVurl_foto = (ImageView) findViewById(R.id.IVurl_foto);
		
		// Cargar la nueva imagen a partir de la URL.
		CargadorImagenes cargadorImagenes = new CargadorImagenes(getApplicationContext());
		//Cargarla en tamano grande G.
		cargadorImagenes.muestraImagen(objeto.getUrl_foto(), IVurl_foto, "G");
					

		//Click listener.
		IVurl_foto.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// Comprobar la conexion a internet.
				if (Utilidades.hayInternet(ObjetoActivity.this, false)) {
					
					// Intent para videoactivity.
					Intent in = new Intent(getApplicationContext(),
							VideoActivity.class);
					in.putExtra(Utilidades.KEY_URL_VIDEO, objeto.getUrl_video());
					startActivity(in);
				}
			}
		});
			
		
			
	}
	
}
