package com.bati_cuisin.model;

public abstract class Composant {
    protected String nom;
    protected int id_composant;
    protected double tauxTVA;
 protected  int id_projet;

    public Composant(String nom, double tauxTVA, int id_projet) {
        this.nom = nom;
        this.tauxTVA = tauxTVA;
        this.id_projet = id_projet;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getTauxTVA() {
        return tauxTVA;
    }

    public void setTauxTVA(double tauxTVA) {
        this.tauxTVA = tauxTVA;
    }

    public void setId_projet(int id_projet) {
        this.id_projet = id_projet;
    }

    public int getId_projet() {
        return id_projet;
    }

    public void setId_composant(int id_composant) {
        this.id_composant = id_composant;
    }

    public int getId_composant() {
        return id_composant;
    }
}
