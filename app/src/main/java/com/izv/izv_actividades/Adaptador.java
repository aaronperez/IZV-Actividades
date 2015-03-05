package com.izv.izv_actividades;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Aaron on 11/02/2015.
 */
public class Adaptador extends ArrayAdapter<Actividad> {

    private Context contexto;
    private ArrayList<Actividad> lista;
    private int recurso;
    private static LayoutInflater i;

    static class ViewHolder{
        public TextView tvTitulo, tvFecha, tvProfesor;
        public RelativeLayout rlFondo;
        public int posicion;
    }

    public Adaptador(Context context, int resource, ArrayList <Actividad> objects) {
        super(context, resource, objects);
        this.contexto = context;
        this.lista = objects;
        this.recurso = resource;
        this.i = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if(convertView == null){
            convertView = i.inflate(recurso, null);
            vh = new ViewHolder();
            vh.tvTitulo = (TextView) convertView.findViewById(R.id.tvTitulo);
            vh.tvProfesor = (TextView) convertView.findViewById(R.id.tvProfesor);
            vh.tvFecha = (TextView) convertView.findViewById(R.id.tvFecha);
            vh.rlFondo = (RelativeLayout) convertView.findViewById(R.id.rlFondo);
            convertView.setTag(vh);
        }
        else{
            vh=(ViewHolder) convertView.getTag();
        }
        vh.posicion=position;
        if(lista.get(position).getTipo().equals("complementaria")){
            vh.rlFondo.setBackgroundResource(R.drawable.backcompl);
        }else{
            vh.rlFondo.setBackgroundResource(R.drawable.backextra);
        }
        vh.tvTitulo.setText(lista.get(position).getDescripcion().toUpperCase());
        int idProf=Integer.parseInt(Main.act.get(position).getIdprofesor());
        String nombre = Main.nombreProfesor(idProf);
        Log.v("idProf",idProf+" "+nombre);
        vh.tvProfesor.setText(nombre);
        String fecha=lista.get(position).getFechai();
        fecha=fecha.substring(0, fecha.indexOf(" "));
        fecha=fecha.substring(fecha.length()-2,fecha.length())+"/"+
                fecha.substring(fecha.indexOf("-")+1,fecha.indexOf("-",fecha.indexOf("-")+1))+"/"+
                fecha.substring(0,4);
        vh.tvFecha.setText(fecha);
        return convertView;
    }

}
