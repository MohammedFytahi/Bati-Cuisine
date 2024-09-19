package com.bati_cuisin.model;

public class Client {
    private int id;
    private String nom;
    private String adresse;
    private String telephone;
    private  boolean est_professionnel;


    public Client(String nom, String adresse, String telephone, boolean est_professionnel){
        this.nom = nom;
        this.adresse= adresse;
        this.telephone = telephone;
        this.est_professionnel = false;
    }

    //constructor with id
    public Client(int id,String nom, String adresse, String telephone, boolean est_professionnel){
        this.id= id;
        this.nom = nom;
        this.adresse= adresse;
        this.telephone = telephone;
        this.est_professionnel = est_professionnel;
    }

    public Client(String nom, String adresse, String telephone) {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setEst_professionnel(boolean est_professionnel) {
        this.est_professionnel = est_professionnel;
    }

    public String getNom() {
        return nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public boolean isEst_professionnel() {
        return est_professionnel;
    }
}
