package com.delycomp.tourismperu.Entidades;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by CarlosDavid on 15/07/2017.
 */

public class LugarTuristico implements Parcelable {
    String cod_lugar;
    String name_lugar;
    double latitud;
    double longitud;
    String tipo_lugar;
    String telefono;
    String desc_es;
    String desc_en;
    String tarifa;
    String depar;
    String valoracion;
    String horario;

    public LugarTuristico() {
    }

    public LugarTuristico(String cod_lugar, String name_lugar, double latitud, double longitud, String tipo_lugar, String telefono, String desc_es, String desc_en, String tarifa, String depar, String valoracion, String horario) {

        this.cod_lugar = cod_lugar;
        this.name_lugar = name_lugar;
        this.latitud = latitud;
        this.longitud = longitud;
        this.tipo_lugar = tipo_lugar;
        this.telefono = telefono;
        this.desc_es = desc_es;
        this.desc_en = desc_en;
        this.tarifa = tarifa;
        this.depar = depar;
        this.valoracion = valoracion;
        this.horario = horario;
    }

    public LugarTuristico(String cod_lugar) {
        this.cod_lugar = cod_lugar;
    }

    public String getCod_lugar() {
        return cod_lugar;
    }

    public void setCod_lugar(String cod_lugar) {
        this.cod_lugar = cod_lugar;
    }

    public String getName_lugar() {
        return name_lugar;
    }

    public void setName_lugar(String name_lugar) {
        this.name_lugar = name_lugar;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getTipo_lugar() {
        return tipo_lugar;
    }

    public void setTipo_lugar(String tipo_lugar) {
        this.tipo_lugar = tipo_lugar;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDesc_es() {
        return desc_es;
    }

    public void setDesc_es(String desc_es) {
        this.desc_es = desc_es;
    }

    public String getDesc_en() {
        return desc_en;
    }

    public void setDesc_en(String desc_en) {
        this.desc_en = desc_en;
    }

    public String getTarifa() {
        return tarifa;
    }

    public void setTarifa(String tarifa) {
        this.tarifa = tarifa;
    }

    public String getDepar() {
        return depar;
    }

    public void setDepar(String depar) {
        this.depar = depar;
    }

    public String getValoracion() {
        return valoracion;
    }

    public void setValoracion(String valoracion) {
        this.valoracion = valoracion;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    protected LugarTuristico(Parcel in) {
        cod_lugar = in.readString();
        name_lugar = in.readString();
        latitud = in.readDouble();
        longitud = in.readDouble();
        tipo_lugar = in.readString();
        telefono = in.readString();
        desc_es = in.readString();
        desc_en = in.readString();
        tarifa = in.readString();
        depar = in.readString();
        valoracion = in.readString();
        horario = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cod_lugar);
        dest.writeString(name_lugar);
        dest.writeDouble(latitud);
        dest.writeDouble(longitud);
        dest.writeString(tipo_lugar);
        dest.writeString(telefono);
        dest.writeString(desc_es);
        dest.writeString(desc_en);
        dest.writeString(tarifa);
        dest.writeString(depar);
        dest.writeString(valoracion);
        dest.writeString(horario);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<LugarTuristico> CREATOR = new Parcelable.Creator<LugarTuristico>() {
        @Override
        public LugarTuristico createFromParcel(Parcel in) {
            return new LugarTuristico(in);
        }

        @Override
        public LugarTuristico[] newArray(int size) {
            return new LugarTuristico[size];
        }
    };
}