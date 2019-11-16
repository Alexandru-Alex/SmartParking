package com.example.smartparking.Obiecte;

import java.util.ArrayList;

public class Dotari {



    private int id;
    private ArrayList<String> dotare=new ArrayList<>();

    public Dotari(int id)
    { this.id=id;
    }

    public ArrayList<String> getDotare() {
        return dotare;
    }

    public void setDotare(String dotare) {
        this.dotare.add(dotare);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
