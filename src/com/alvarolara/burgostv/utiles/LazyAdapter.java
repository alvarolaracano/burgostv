package com.alvarolara.burgostv.utiles;

import java.util.ArrayList;
import java.util.HashMap;

import com.alvarolara.burgostv.R;
import com.alvarolara.burgostv.async.CargaXML;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LazyAdapter extends BaseAdapter {
    
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    
    public LazyAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.lista_filas, null);

        TextView titulo = (TextView)vi.findViewById(R.id.titulo);
        TextView descripcion = (TextView)vi.findViewById(R.id.descripcion);
        TextView url_video = (TextView)vi.findViewById(R.id.url_video);
        ImageView url_foto = (ImageView)vi.findViewById(R.id.url_foto);
        TextView fecha = (TextView)vi.findViewById(R.id.fecha);
        //Url de la foto del textview Oculto.
        TextView url_foto_textview = (TextView)vi.findViewById(R.id.url_foto_textview);
        
        HashMap<String, String> objeto = new HashMap<String, String>();
        objeto = data.get(position);
        
        // Colocar los valores en el ListView.
        titulo.setText(objeto.get(CargaXML.KEY_TITULO));
        descripcion.setText(objeto.get(CargaXML.KEY_DESCRIPCION));
        url_video.setText(objeto.get(CargaXML.KEY_URL_VIDEO));
        fecha.setText(objeto.get(CargaXML.KEY_FECHA));
        
        //Cargar la URL para pasarla mediante un Intent.
        url_foto_textview.setText(objeto.get(CargaXML.KEY_URL_FOTO));
        
        //Dibujar la imagen.
        imageLoader.DisplayImage(objeto.get(CargaXML.KEY_URL_FOTO), url_foto, "P");
        return vi;
    }
}