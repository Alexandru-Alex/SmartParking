package com.example.smartparking.Obiecte;

public class Plati {

    private String ora,pret,nrinmatriculare,nume_parcare,data,oraplata;

   public Plati(String data,String pret,String nrinmatriculare,String nume_parcare)
    {

        this.pret=pret+"RON";
        this.nrinmatriculare=nrinmatriculare;
        this.nume_parcare=nume_parcare;
        this.data=data;

    }
   public Plati(String ora,String nume_parcare,String pret,String nrinmatriculare,String data)
   {
       this.pret=pret;
       this.nrinmatriculare=nrinmatriculare;
       this.nume_parcare=nume_parcare;
       this.data=data;
       this.ora=ora;
   }

    public String getOraplata() {
        return oraplata;
    }

    public void setOraplata(String oraplata) {
        this.oraplata = oraplata;
    }

    public String getOra() {
        return ora;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }

    public String getPret() {
        return pret;
    }

    public void setPret(String pret) {
        this.pret = pret;
    }

    public String getNrinmatriculare() {
        return nrinmatriculare;
    }

    public void setNrinmatriculare(String nrinmatriculare) {
        this.nrinmatriculare = nrinmatriculare;
    }

    public void setData(String data)
    {
        this.data=data;
    }

    public String getData()
    {
        return data;
    }
    public String getNume_parcare() {
        return nume_parcare;
    }

    public void setNume_parcare(String nume_parcare) {
        this.nume_parcare = nume_parcare;
    }
}
