package com.izv.izv_actividades;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;


public class Main extends Activity {

    private final int ACTIVIDAD = 1;
    private final static String URLBASE = "http://ieszv.x10.bz/restful/api/";
    private ListView lv;
    private Adaptador ad;
    public static ArrayList <Actividad> act;
    public static ArrayList <Grupo> grupos;
    public static ArrayList <Profesor> profesores;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayUseLogoEnabled(true);
        int sw = this.getResources().getConfiguration().smallestScreenWidthDp;
        if(sw<600){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else{
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        initListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_agregar) {
            Intent intent = new Intent(this, AgregarAct.class);
            startActivityForResult(intent, ACTIVIDAD);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /* Desplegar menú contextual*/
    @Override
    public void onCreateContextMenu(ContextMenu main, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(main, v, menuInfo);
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menu_contextual, main);
    }

    /* Al seleccionar elemento del menú contextual */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id=item.getItemId();
        AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        index= info.position;
        if (id == R.id.action_eliminar) {
            AlertDialog.Builder alert= new AlertDialog.Builder(this);
            alert.setTitle(R.string.eliminarA);
            LayoutInflater inflater= LayoutInflater.from(this);
            final View vista = inflater.inflate(R.layout.detalle, null);
            TextView titulo=(TextView)vista.findViewById(R.id.tvTitulo);
            TextView profesor=(TextView)vista.findViewById(R.id.tvProfesor);
            TextView fecha=(TextView)vista.findViewById(R.id.tvFecha);
            RelativeLayout fondo=(RelativeLayout)vista.findViewById(R.id.rlFondo);
            titulo.setText(act.get(index).getDescripcion());
            if(act.get(index).getTipo().equals("complementaria")){
                fondo.setBackgroundResource(R.drawable.backcompl);
            }else{
                fondo.setBackgroundResource(R.drawable.backextra);
            }
            profesor.setText(nombreProfesor(index));
            String f=act.get(index).getFechai();
            fecha.setText(f.substring(0, f.indexOf(" ")));
            alert.setView(vista);
            alert.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    eliminarActividad(act.get(index).getId(),index);
                }
            });
            alert.setNegativeButton(R.string.cancelar,new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    tostada(R.string.mensajeCancelar);
                    dialog.dismiss();
                }
            });
            alert.show();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    /* *******  Obtenemos el nombre del profesor de su ID ***** */
    public static String nombreProfesor(int position){
        String nombre="";
        for(int c=0; c<profesores.size();c++){
            if(profesores.get(c).getId().equals(position+"")){
                nombre=profesores.get(c).getNombre()+" "+profesores.get(c).getApellidos();
                break;
            }
        }
        return nombre;
    }

    /* ********* Obtenemos formato fecha ********** */
    public static String formatoFecha(String f){
        String d,m,y, fecha;
        d=f.substring(0,f.indexOf("/"));
        m=f.substring(f.indexOf("/")+1,f.indexOf("/",f.indexOf("/")+1));
        y=f.substring(f.length()-4,f.length());
        fecha=y+"-"+m+"-"+d;
        return fecha;
    }

    /* *******  Obtenemos el nombre del grupo por su ID ***** */
    /*public static String nombreGrupo(int position){
        String nombre="";
        for(int c=0; c<grupos.size();c++){
            if(grupos.get(c).getId().equals(act.get(position).getId())){
                nombre=profesores.get(c).getNombre()+" "+profesores.get(c).getApellidos();
                break;
            }
        }
        return nombre;
    }*/

    /* **      Iniciando componentes      ** */
    private void initListView(){
        act = new ArrayList<>();
        profesores= new ArrayList<>();
        grupos = new ArrayList<>();
        cargarActividades();
        lv = (ListView) findViewById(R.id.lvActividades);
        ad = new Adaptador(this, R.layout.detalle, act);
        lv.setAdapter(ad);
        registerForContextMenu(lv);
        final VistaDetalle fdetalle=(VistaDetalle)getFragmentManager().findFragmentById(R.id.fragmentodetalle);
        final boolean horizontal=fdetalle!=null && fdetalle.isInLayout();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicion, long id) {
                if(horizontal){
                    fdetalle.inicia(id+"");
                }
                else{
                    Intent intent=new Intent(Main.this, ActividadDetalle.class);
                    intent.putExtra("id", id+"");
                    startActivity(intent);
                }
            }
        });
    }

    /* Mostramos un mensaje flotante a partir de un recurso string*/
    public void tostada(int s){
        Toast.makeText(this, getText(s), Toast.LENGTH_SHORT).show();
    }

    /* ******************* Activities ******************************************* */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case ACTIVIDAD:
                    Actividad a=data.getParcelableExtra("actividad");
                    act.add(a);
                    agregarActividad(a);
                    break;
            }
            ad.notifyDataSetChanged();
        }
    }

    /* **                         Metodos JSON                                   ** */
    /* -----------------------cargar actividades ---------------------------------- */
    private void cargarActividades() {
        String[] peticiones = new String[3];
        peticiones[0] = "actividad/aaron";
        peticiones[1] = "profesor";
        peticiones[2] = "grupo";
        GetRestful get = new GetRestful();
        get.execute(peticiones);
    }

    private class GetRestful extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... params) {
            String[] r = new String[params.length];
            int contador = 0;
            for (String s : params) {
                r[contador] = ClienteRestFul.get(URLBASE + s);
                contador++;
            }
            return r;
        }

        @Override
        protected void onPostExecute(String[] r) {
            super.onPostExecute(r);
            JSONTokener token = new JSONTokener(r[0]);
            try {
                JSONArray array = new JSONArray(token);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject objeto = array.getJSONObject(i);
                    Actividad a = new Actividad(objeto);
                    act.add(a);
                }
                ad.notifyDataSetChanged();
            } catch (JSONException e) {}
            token = new JSONTokener(r[1]);
            try {
                JSONArray array = new JSONArray(token);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject objeto = array.getJSONObject(i);
                    Profesor p = new Profesor(objeto);
                    profesores.add(p);
                }
                ad.notifyDataSetChanged();
            } catch (JSONException e) {}
            token = new JSONTokener(r[2]);
            try {
                JSONArray array = new JSONArray(token);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject objeto = array.getJSONObject(i);
                    Grupo g = new Grupo(objeto);
                    grupos.add(g);
                }
                ad.notifyDataSetChanged();
            } catch (JSONException e) {}
        }

    }

    /* **********************Guardar actividad ********************************** */
    private void agregarActividad(Actividad a) {
        PostRestful post = new PostRestful();
        ParametrosPost pp = new ParametrosPost();
        pp.url = URLBASE+"actividad";
        pp.json = a.getJson();
        post.execute(pp);
    }

    static class ParametrosPost{
        String url;
        JSONObject json;
    }

    private class PostRestful extends AsyncTask<ParametrosPost, Void, String> {

        @Override
        protected String doInBackground(ParametrosPost[] params) {
            String r = ClienteRestFul.post(params[0].url, params[0].json);

            return r;
        }

        @Override
        protected void onPostExecute(String r) {
            super.onPostExecute(r);
            Toast.makeText(Main.this, r, Toast.LENGTH_SHORT).show();
            JSONTokener token = new JSONTokener(r);
            try {
                JSONObject objeto = new JSONObject(token);
                ad.notifyDataSetChanged();
            } catch (JSONException e) {

            }
        }
    }

    /* *****************************Borrar actividad ***************************** */
    private void eliminarActividad(String id,int index){
        DeleteRestfull del = new DeleteRestfull(index);
        del.execute("actividad/"+id);
    }

    private class DeleteRestfull extends AsyncTask<String, Void, String> {

        private int index;

        public DeleteRestfull(int index){
            this.index = index;
        }

        @Override
        protected String doInBackground(String... id) {
            String r = ClienteRestFul.delete(URLBASE + id[0]);
            return r;
        }

        @Override
        protected void onPostExecute(String r) {
            super.onPostExecute(r);
            Toast.makeText(Main.this, r, Toast.LENGTH_SHORT).show();
            act.remove(index);
            ad.notifyDataSetChanged();
        }
    }

}
