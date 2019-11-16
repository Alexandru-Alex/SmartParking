package com.example.smartparking.Obiecte;



public class Parcare {
    private String nume;
    private String poza;
    private int id,id_pret,id_program,id_dotari,capacitate,ocupate;
    private float lat,lon,nota;



    Parcare()
    {


    };

    public float getNota() {
        return nota;
    }
    public void setNota(float nota) {
        this.nota = nota;
    }
    public float getLat() {
        return lat;
    }
    public void setLat(float lat) {
        this.lat = lat;
    }
    public float getLon() {
        return lon;
    }
    public void setLon(float lon) {
        this.lon = lon;
    }
    public String getNume() {
        return nume;
    }
    public void setNume(String nume) {
        this.nume = nume;
    }
    public int getCapacitate()
    {
        return capacitate;
    }
    public int getOcupate()
    {
        return ocupate;
    }
    public String getPoza() {
        return poza;
    }
    public void setPoza(String poza) {
        this.poza = poza;
    }
    public int getid()
    {
        return id;
    }
    public void setid(int id)
    {
        this.id=id;
    }
    public int getId_pret() {
        return id_pret;
    }
    public int getId_program() {
        return id_program;
    }
    public void setId_program(int id_program) {
        this.id_program = id_program;
    }
    public void setId_pret(int id_pret) {
        this.id_pret = id_pret;
    }
    public int getId_dotari() {
        return id_dotari;
    }
    public void setId_dotari(int id_dotari) {
        this.id_dotari = id_dotari;
    }
}

