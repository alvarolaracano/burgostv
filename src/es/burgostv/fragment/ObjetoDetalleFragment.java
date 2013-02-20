package es.burgostv.fragment;

import es.burgostv.R;

import es.burgostv.VideoActivity;
import es.burgostv.clases.Objeto;
import es.burgostv.utiles.CargadorImagenes;
import es.burgostv.utiles.Utilidades;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * Fragment para ver un objeto seleccionado de la lista.
 * @author Alvaro Lara Cano
 * 
 */
public class ObjetoDetalleFragment extends Fragment {
	
	/**
	 * Cuando se crea la actividad.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	/**
	 * Cuando se crea la actividad.
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	/**
	 * Al crearse la vista.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.objeto, container, false);
		return view;
	}

	public void setObjeto(final Activity actividad, final Objeto objeto){
		
		//Establemos el titulo.
		((TextView) getView().findViewById(R.id.TVtitulo)).setText(objeto.getTitulo());
		
		// Cargar la nueva imagen a partir de la URL.
		CargadorImagenes cargadorImagenes = new CargadorImagenes(getActivity().getApplicationContext());
		//Cargarla en tamano grande G.
		cargadorImagenes.muestraImagen(objeto.getUrl_foto(), (ImageView) getView().findViewById(R.id.IVurl_foto), "G");
		
		//Establecemos la descripcion.
		((TextView) getView().findViewById(R.id.TVdescripcion)).setText(objeto.getDescripcion());
		
		//Evento en el reproductor.
		ImageView lblUrl_foto = (ImageView) getView().findViewById(R.id.IVurl_foto);
		
		
		lblUrl_foto.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// Comprobar la conexion a internet.
				if (Utilidades.hayInternet(actividad, false)) {
					
					// Intent para videoactivity.
					Intent in = new Intent(getActivity().getApplicationContext(),
							VideoActivity.class);
					in.putExtra(Utilidades.KEY_URL_VIDEO, objeto.getUrl_video());
					startActivity(in);
				}
			}
		});
	}
	
}
