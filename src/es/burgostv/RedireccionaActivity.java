package es.burgostv;

import es.burgostv.R;

import es.burgostv.async.CargaXML;
import es.burgostv.utiles.Utilidades;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class RedireccionaActivity extends Activity{

	/**
	 * Llamado cuando la Actividad es creada.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		Intent intent = getIntent();
		
		if (Utilidades.hayInternet(this, true)) {
			if(getResources().getString(R.string.tipo_pantalla).equals("telefono")){
				if(intent.getData()!=null){
					//Recibe datos de URL.
					
					//Nuevo intent para cargar el objeto.
					Intent in = new Intent(getApplicationContext(), ObjetoActivity.class);
					//Enviarle la URL.
					in.putExtra("url", intent.getData().toString());
					in.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
					startActivity(in);
					finish();
				}
			}else{
				if(intent.getData()!=null){
					//Recibe datos de URL.
					
					Intent in = new Intent(getApplicationContext(), es.burgostv.fragment.ObjetoActivity.class);
					String datos = intent.getData().toString();
					//Enviarle la URL.
					in.putExtra("url", datos);
					//Cargar el XML segun sea noticia etc.
					String carga = null;
					if(datos.contains("noticias")){
						carga = Utilidades.URL_NOTICIAS;
					}else if(datos.contains("reportajes")){
						carga = Utilidades.URL_REPORTAJES;
					}else if(datos.contains("mas-deporte")){
						carga = Utilidades.URL_MAS_DEPORTE;
					}else if(datos.contains("burgos-en-persona")){
						carga = Utilidades.URL_VIDEOENCUENTRO;
					}
					CargaXML xml = new CargaXML(carga);
					in.putExtra("xml", xml.parsea());
					in.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
					startActivity(in);
					finish();
				}
			}
		}
	}
}
