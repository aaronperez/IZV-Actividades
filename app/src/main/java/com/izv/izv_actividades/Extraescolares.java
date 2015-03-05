package com.izv.izv_actividades;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
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

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class Extraescolares extends Fragment {

    private Spinner spP, spG;
    private static TextView horaS,horaL,fechaS,fechaL, descripcion, lugarS, lugarL;
    private View vista;

    public Extraescolares() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista=inflater.inflate(R.layout.fragment_extraescolares, container, false);
        iniciarComponentes();
        iniciarSpinner();
        return vista;
    }

    /*  Método para iniciar Spinner y escucharlo  */
    public void iniciarSpinner(){
        //Spinner Profesores
        String[] prof=new String[Main.profesores.size()];
        for(int x=0;x<Main.profesores.size();x++){
            prof[x]=Main.profesores.get(x).getId()+" "+Main.profesores.get(x).getNombre()+" "+Main.profesores.get(x).getApellidos();
        }
        ArrayAdapter<String> spinnerArrayAdapterP = new ArrayAdapter<String>(vista.getContext(), android.R.layout.simple_spinner_item, prof);
        spP.setAdapter(spinnerArrayAdapterP);
        //Spinner Grupos
        String[] grup=new String[Main.grupos.size()];
        for(int x=0;x<Main.grupos.size();x++){
            grup[x]=Main.grupos.get(x).getGrupo();
        }
        ArrayAdapter<String> spinnerArrayAdapterG = new ArrayAdapter<String>(vista.getContext(), android.R.layout.simple_spinner_item, grup);
        spG.setAdapter(spinnerArrayAdapterG);
    }

    public void iniciarComponentes(){
        spP =(Spinner)vista.findViewById(R.id.spinnerExtraProfesores);
        spG =(Spinner)vista.findViewById(R.id.spinnerExtraGrupo);
        descripcion=(TextView)vista.findViewById(R.id.etExtraDescripcion);
        horaS=(TextView)vista.findViewById(R.id.tvExtraHoraS);
        horaL=(TextView)vista.findViewById(R.id.tvExtraHoraL);
        fechaS=(TextView)vista.findViewById(R.id.tvExtraFechaS);
        fechaL=(TextView)vista.findViewById(R.id.tvExtraFechaL);
        lugarS=(TextView)vista.findViewById(R.id.etExtraLugarS);
        lugarL=(TextView)vista.findViewById(R.id.etExtraLugarL);
        botones();
    }

    public void botones() {
        //boton aceptar
        Button button = (Button)vista.findViewById(R.id.bExtraAceptar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregar(vista);
            }
        });
        //boton horaS
        ImageButton bHS = (ImageButton)vista.findViewById(R.id.ibExtraHoraS);
        bHS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarHoraS(v);
            }
        });
        //boton horaL
        ImageButton bHL = (ImageButton)vista.findViewById(R.id.ibExtraHoraL);
        bHL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarHoraL(v);
            }
        });
        //boton fechaS
        ImageButton bFS = (ImageButton) vista.findViewById(R.id.ibExtraFechaS);
        bFS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarFechaS(v);
            }
        });
        //boton fechaL
        ImageButton bFL = (ImageButton) vista.findViewById(R.id.ibExtraFechaL);
        bFL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarFechaL(v);
            }
        });
    }

    public void agregar(View v){
        Actividad a;
        String idProf=spP.getSelectedItem().toString();
        a=new Actividad("0",idProf.substring(0,idProf.indexOf(" ")),"extraescolar",
                Main.formatoFecha(fechaS.getText().toString())+" "+horaS.getText().toString(),
                Main.formatoFecha(fechaL.getText().toString())+" "+horaL.getText().toString(),
                lugarS.getText().toString(),lugarL.getText().toString(),
                descripcion.getText().toString());
        Log.v("a", a.toString());
        Intent i = new Intent();
        Bundle bundle= new Bundle();
        bundle.putParcelable("actividad", a);
        i.putExtras(bundle);
        ((Activity)v.getContext()).setResult(Activity.RESULT_OK, i);
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

    //Fecha llegada
    public static class DatePickerFragmentL extends DialogFragment implements DatePickerDialog.OnDateSetListener {

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
            fechaL.setText(fecha);
        }
    }

    public void agregarHoraS (View v){
        DialogFragment newFragment = new TimePickerFragmentS();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void agregarHoraL (View v){
        DialogFragment newFragment = new TimePickerFragmentL();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void agregarFechaS (View v){
        DialogFragment newFragment = new DatePickerFragmentS();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void agregarFechaL (View v){
        DialogFragment newFragment = new DatePickerFragmentL();
        newFragment.show(getFragmentManager(), "datePicker");
    }


}
