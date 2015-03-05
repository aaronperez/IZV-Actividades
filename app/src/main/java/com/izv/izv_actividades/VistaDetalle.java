package com.izv.izv_actividades;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class VistaDetalle extends Fragment {

    private TextView tvDescripcion,tvFechaS,tvFechaL,tvHoraS,tvHoraL,tvProfesor,tvGrupo,tvLugarS,tvLugarL, tvLFinDetalle;
    View v;

    public VistaDetalle() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_vista_detalle, container, false);
        tvDescripcion=(TextView)v.findViewById(R.id.tvDetalleDescripcion);
        tvFechaS=(TextView)v.findViewById(R.id.tvFechaSalidaDetalle);
        tvFechaL=(TextView)v.findViewById(R.id.tvFechaLlegadaDet);
        tvHoraS=(TextView)v.findViewById(R.id.tvHoraSalidaDet);
        tvHoraL=(TextView)v.findViewById(R.id.tvHoraLlegadaDet);
        tvProfesor=(TextView)v.findViewById(R.id.tvProfesorDet);
        tvGrupo=(TextView)v.findViewById(R.id.tvGrupoDet);
        tvLugarS=(TextView)v.findViewById(R.id.tvLugarDetS);
        tvLugarL=(TextView)v.findViewById(R.id.tvLugarDetL);
        tvLFinDetalle=(TextView)v.findViewById(R.id.tvLabelFinDet);
        return v;

    }

    public void inicia(String s){
        int x=Integer.parseInt(s);
        Actividad a= Main.act.get(x);
        if(a.getTipo().equals("extraescolar")){
            tvDescripcion.setBackgroundResource(R.drawable.backextra);
            tvFechaL.setText(getFecha(a.getFechaf()));
        }else{
            tvDescripcion.setBackgroundResource(R.drawable.backcompl);
            tvFechaL.setVisibility(View.INVISIBLE);
            tvLFinDetalle.setVisibility(View.INVISIBLE);
        }
        tvDescripcion.setText(a.getDescripcion());
        tvFechaS.setText(getFecha(a.getFechai()));
        tvHoraS.setText(getHora(a.getFechai()));
        tvHoraL.setText(getHora(a.getFechaf()));
        tvProfesor.setText(Main.nombreProfesor(x));
        //tvGrupo.setText(Main.nombreGrupo(x));
        tvLugarS.setText(a.getLugari());
        tvLugarL.setText(a.getLugarf());
    }

    private String getFecha(String f){
        String fecha = f.substring(0, f.indexOf(" "));
        fecha=fecha.substring(fecha.length()-2,fecha.length())+"/"+
                fecha.substring(fecha.indexOf("-")+1,fecha.indexOf("-",fecha.indexOf("-")+1))+"/"+
                fecha.substring(0,4);
        return fecha;
    }

    private String getHora(String f){
        String hora = f.substring(f.indexOf(" "), f.length()-3);
        return hora;
    }

}
