package com.example.smartparking.Obiecte;

import java.util.ArrayList;

public class Program {


    private ArrayList<String> zi=new ArrayList<>();
    private ArrayList<String> ore=new ArrayList<>();
    private int id;


    public Program(int id)
    { this.id=id;
    };


    public ArrayList<String> getZi() {
        return zi;
    }

    public void setZi(String zi) {
        this.zi.add(zi);
    }

    public ArrayList<String> getOre() {
        return ore;
    }

    public void setOre(String ore) {
        this.ore.add(ore);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
