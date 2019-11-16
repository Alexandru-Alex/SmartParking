package com.example.smartparking.Obiecte;
import java.util.ArrayList;

public class Preturi {



    private ArrayList<String> timp=new ArrayList<>();
    private ArrayList<Integer> pret=new ArrayList<>();
    private int id;


    public Preturi(int id)
    {
this.id=id;
    };


    public ArrayList<String> getTimp() {

        return timp;
    }

    public void setTimp(String timp) {
        this.timp.add(timp);
    }

    public ArrayList<Integer> getPret() {
        return pret;
    }

    public void setPret(int pret) {
        this.pret.add(pret);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
