package com.izv.izv_actividades;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Aaron on 11/02/2015.
 */
public class Actividad implements Parcelable {
    private String id,idprofesor,tipo,fechai,fechaf,lugari,lugarf,descripcion,alumno;

    public Actividad(JSONObject object) throws JSONException {
        this.id = object.getString("id");
        this.idprofesor = object.getString("idprofesor");
        this.tipo = object.getString("tipo");
        this.fechai = object.getString("fechai");
        this.fechaf = object.getString("fechaf");
        this.lugari = object.getString("lugari");
        this.lugarf = object.getString("lugarf");
        this.descripcion = object.getString("descripcion");
        this.alumno = object.getString("alumno");

    }

    public JSONObject getJson(){
        JSONObject objetoJSON = new JSONObject();
        try{
            objetoJSON.put("id", this.id); //Esto solo para el put
            objetoJSON.put("idprofesor", this.idprofesor);
            objetoJSON.put("tipo", this.tipo);
            objetoJSON.put("fechai", this.fechai);
            objetoJSON.put("fechaf", this.fechaf);
            objetoJSON.put("lugari", this.lugari);
            objetoJSON.put("lugarf", this.lugarf);
            objetoJSON.put("descripcion",this.descripcion);
            objetoJSON.put("alumno", this.alumno);
            return objetoJSON;
        }catch(Exception e){
            return null;
        }
    }

    public Actividad(String id, String idprofesor, String tipo, String fechai, String fechaf, String lugari, String lugarf, String descripcion) {
        this.id = id;
        this.idprofesor = idprofesor;
        this.tipo = tipo;
        this.fechai = fechai;
        this.fechaf = fechaf;
        this.lugari = lugari;
        this.lugarf = lugarf;
        this.descripcion = descripcion;
        this.alumno = "aaron";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdprofesor() {
        return idprofesor;
    }

    public void setIdprofesor(String idprofesor) {
        this.idprofesor = idprofesor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFechai() {
        return fechai;
    }

    public void setFechai(String fechai) {
        this.fechai = fechai;
    }

    public String getFechaf() {
        return fechaf;
    }

    public void setFechaf(String fechaf) {
        this.fechaf = fechaf;
    }

    public String getLugari() {
        return lugari;
    }

    public void setLugari(String lugari) {
        this.lugari = lugari;
    }

    public String getLugarf() {
        return lugarf;
    }

    public void setLugarf(String lugarf) {
        this.lugarf = lugarf;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAlumno() {
        return alumno;
    }


    @Override
    public String toString() {
        return "Actividad{" +
                "id='" + id + '\'' +
                ", idprofesor='" + idprofesor + '\'' +
                ", tipo='" + tipo + '\'' +
                ", fechai='" + fechai + '\'' +
                ", fechaf='" + fechaf + '\'' +
                ", lugari='" + lugari + '\'' +
                ", lugarf='" + lugarf + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(idprofesor);
        dest.writeString(tipo);
        dest.writeString(fechai);
        dest.writeString(fechaf);
        dest.writeString(lugari);
        dest.writeString(lugarf);
        dest.writeString(descripcion);
        dest.writeString(alumno);
    }

    public Actividad(Parcel parcel){
        this.id = parcel.readString();
        this.idprofesor = parcel.readString();
        this.tipo = parcel.readString();
        this.fechai = parcel.readString();
        this.fechaf = parcel.readString();
        this.lugari = parcel.readString();
        this.lugarf = parcel.readString();
        this.descripcion = parcel.readString();
        this.alumno = parcel.readString();
    }

    public static final Creator<Actividad> CREATOR =
            new Creator<Actividad>() {
                public Actividad createFromParcel(Parcel parcel) {
                    return new Actividad(parcel);
                }

                public Actividad[] newArray(int size) {
                    return new Actividad[size];
                }
            };
}
