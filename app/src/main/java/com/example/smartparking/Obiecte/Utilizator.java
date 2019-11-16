package com.example.smartparking.Obiecte;

public class Utilizator {

    public String nume;
    public String prenume;
    public String username;
    public String parola;
    public String email;
    public String id;
    private String codnumeric;


    public Utilizator()
    {

    }

    public Utilizator(String id, String nume, String prenume, String username, String parola, String email)
    {

        this.id=id;
        this.nume=nume;
        this.prenume=prenume;
        this.username=username;
        this.parola=parola;
        this.email=email;

    }

    public String getId()
    {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getUsername() {
        return username;
    }

    public String getParola() {
        return parola;
    }

    public String getEmail() {
        return email;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodnumeric() { return codnumeric; }

    public void setCodnumeric(String codnumeric) { this.codnumeric = codnumeric; }
}
