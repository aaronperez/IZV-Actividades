package com.izv.izv_actividades;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class Complementarias extends Fragment {

    private Spinner spP, spG;
    private static TextView horaS,horaL,fechaS, lugarS, descripcion;

    public Complementarias() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_complementarias, container, false);
        iniciarComponentes(v);
        iniciarSpinner(v);
        return v;
    }

    /*  Método para iniciar Spinner y escucharlo  */
    private void iniciarSpinner(View v){
        //Spinner Profesores
        String[] prof=new String[Main.profesores.size()];
        for(int x=0;x<Main.profesores.size();x++){
            prof[x]=Main.profesores.get(x).getId()+" "+Main.profesores.get(x).getNombre()+" "+Main.profesores.get(x).getApellidos();
        }
        ArrayAdapter<String> spinnerArrayAdapterP = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_item, prof);
        spP.setAdapter(spinnerArrayAdapterP);
        //Spinner Grupos
        String[] grup=new String[Main.grupos.size()];
        for(int x=0;x<Main.grupos.size();x++){
            grup[x]=Main.grupos.get(x).getGrupo();
        }
        ArrayAdapter<String> spinnerArrayAdapterG = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_item, grup);
        spG.setAdapter(spinnerArrayAdapterG);
    }

    private void iniciarComponentes(View v){
        spP =(Spinner)v.findViewById(R.id.spinnerCompleProfesores);
        spG =(Spinner)v.findViewById(R.id.spinnerCompleGrupo);
        descripcion=(TextView)v.findViewById(R.id.etCompleDescripcion);
        horaS=(TextView)v.findViewById(R.id.tvCompleHoraS);
        horaL=(TextView)v.findViewById(R.id.tvCompleHoraL);
        fechaS=(TextView)v.findViewById(R.id.tvCompleFechaL);
        lugarS=(TextView)v.findViewById(R.id.etCompleLugarL);
        botones(v);
    }

    private void botones(View v){
        //boton aceptar
        Button button = (Button)v.findViewById(R.id.bCompleAceptar);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                agregar(v);
            }
        });
        //boton horaS
        ImageButton bHS = (ImageButton)v.findViewById(R.id.ibCompleHoraS);
        bHS.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                agregarHoraS();
            }
        });
        //boton horaL
        ImageButton bHL = (ImageButton)v.findViewById(R.id.ibCompleHoraL);
        bHL.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                agregarHoraL();
            }
        });
        //boton fechaS
        ImageButton bFS = (ImageButton)v.findViewById(R.id.ibCompleFechaS);
        bFS.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                agregarFechaS();
            }
        });
    }



    public void agregar(View v){
        Actividad a;
        String idProf=spP.getSelectedItem().toString();
        a=new Actividad("0",idProf.substring(0,idProf.indexOf(" ")),"complementaria",
                Main.formatoFecha(fechaS.getText().toString())+" "+horaS.getText().toString(),
                Main.formatoFecha(fechaS.getText().toString())+" "+horaL.getText().toString(),
                lugarS.getText().toString(),lugarS.getText().toString(),
                descripcion.getText().toString());
        Log.v("a", a.toString());
        Intent i = new Intent();
        Bundle bundle= new Bundle();
        bundle.putParcelable("actividad", a);
        i.putExtras(bundle);
        ((Activity)v.getContext()).setResult(Activity.RESULT_OK,i);
        ((Activity)v.getContext()).finish();
    }

    //Tiempo llegada
    public static class TimePickerFragmentL extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String tiempo;
            String hour=""+hourOfDay, min=""+minute;
            if(hourOfDay<10){
                hour=0+hour;
            }
            if(minute<10){
                min=0+min;
            }
            tiempo = hour+":"+min;
            horaL.setText(tiempo);
        }
    }

    //Tiempo salida
    public static class TimePickerFragmentS extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String tiempo;
            String hour=""+hourOfDay, min=""+minute;
            if(hourOfDay<10){
                hour=0+hour;
            }
            if(minute<10){
                min=0+min;
            }
            tiempo = hour+":"+min;
            horaS.setText(tiempo);
        }
    }

    //Fecha salida
    public static class DatePickerFragmentS extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            int mon=month+1;
            String fecha,y=""+year,m=""+mon,d=""+day;
            if(mon<10){
                m=0+m;
            }
            if(day<10){
                d=0+d;
            }
            fecha = d+"/"+m+"/"+String.valueOf(year);
            fechaS.setText(fecha);
        }
    }

    public void agregarHoraS (){
        DialogFragment newFragment = new TimePickerFragmentS();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void agregarHoraL (){
        DialogFragment newFragment = new TimePickerFragmentL();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void agregarFechaS (){
        DialogFragment newFragment = new DatePickerFragmentS();
        newFragment.show(getFragmentManager(), "datePicker");
    }

}
