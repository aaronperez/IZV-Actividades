package com.izv.izv_actividades;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Aaron on 11/02/2015.
 */
public class Profesor {
    private String id,nombre,apellidos,departamento;

    public Profesor(JSONObject object) throws JSONException {
        this.id = object.getString("id");
        this.nombre = object.getString("nombre");
        this.apellidos = object.getString("apellidos");
        this.departamento = object.getString("departamento");
    }

    public JSONObject getJson(){
        JSONObject objetoJSON = new JSONObject();
        try{
            objetoJSON.put("id", this.id); //Esto solo para el put
            objetoJSON.put("nombre", this.nombre);
            objetoJSON.put("apellidos", this.apellidos);
            objetoJSON.put("departamento", this.departamento);
            return objetoJSON;
        }catch(Exception e){
            return null;
        }
    }

    public Profesor(String id, String nombre, String apellidos, String departamento) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.departamento = departamento;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    @Override
    public String toString() {
        return "Profesor{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", departamento='" + departamento + '\'' +
                '}';
    }
}
