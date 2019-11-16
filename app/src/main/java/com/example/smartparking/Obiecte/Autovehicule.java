package com.example.smartparking.Obiecte;

import com.example.smartparking.BdComunica;

import java.util.ArrayList;

public class Autovehicule {

    private ArrayList<String> nrinmatriculare=new ArrayList<>();
  public  Autovehicule()
    {


    };

    public ArrayList<String> getNrinmatriculare() {
        return nrinmatriculare;
    }

    public void setNrinmatriculare(String nrinmatriculare) {
        this.nrinmatriculare.add(nrinmatriculare);
    }
    public int getSize()
    {
        return nrinmatriculare.size();
    }

}
