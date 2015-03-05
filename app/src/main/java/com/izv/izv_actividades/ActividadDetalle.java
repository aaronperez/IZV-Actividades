package com.izv.izv_actividades;

import android.app.Activity;
import android.os.Bundle;
import android.view.Display;
import android.view.Surface;


public class ActividadDetalle extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        if(display.getRotation()== Surface.ROTATION_0){
            setContentView(R.layout.activity_actividad_detalle);
            String s=getIntent().getExtras().getString("id");
            final VistaDetalle fdetalle=(VistaDetalle)getFragmentManager().findFragmentById(R.id.fragmentoactdet);
            fdetalle.inicia(s);
        }else{
            finish();
        }
    }
}