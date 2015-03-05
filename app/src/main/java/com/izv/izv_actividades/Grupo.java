package com.izv.izv_actividades;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Aaron on 11/02/2015.
 */
public class Grupo {
    private String id,grupo;

    public Grupo(JSONObject object) throws JSONException {
        this.id = object.getString("id");
        this.grupo = object.getString("grupo");
    }

    public JSONObject getJson(){
        JSONObject objetoJSON = new JSONObject();
        try{
            objetoJSON.put("id", this.id); //Esto solo para el put
            objetoJSON.put("grupo", this.grupo);
            return objetoJSON;
        }catch(Exception e){
            return null;
        }
    }

    public Grupo(String id, String grupo) {
        this.id = id;
        this.grupo = grupo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    @Override
    public String toString() {
        return "Grupo{" +
                "id='" + id + '\'' +
                ", grupo='" + grupo + '\'' +
                '}';
    }
}
