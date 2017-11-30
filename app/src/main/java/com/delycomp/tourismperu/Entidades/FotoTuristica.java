package com.delycomp.tourismperu.Entidades;

/**
 * Created by CarlosDavid on 11/08/2017.
 */

public class FotoTuristica {
    String cod_foto;
    String name_foto;
    String cod_lugar;
    String ruta;

    public String getCod_foto() {
        return cod_foto;
    }

    public void setCod_foto(String cod_foto) {
        this.cod_foto = cod_foto;
    }

    public String getName_foto() {
        return name_foto;
    }

    public void setName_foto(String name_foto) {
        this.name_foto = name_foto;
    }

    public String getCod_lugar() {
        return cod_lugar;
    }

    public void setCod_lugar(String cod_lugar) {
        this.cod_lugar = cod_lugar;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public FotoTuristica(String cod_foto, String name_foto, String cod_lugar, String ruta) {

        this.cod_foto = cod_foto;
        this.name_foto = name_foto;
        this.cod_lugar = cod_lugar;
        this.ruta = ruta;
    }
}
