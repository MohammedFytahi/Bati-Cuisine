package com.bati_cuisin.model;

public abstract class Composant {
    protected String nom;
    protected double tauxTVA;
 protected  int id_projet;

    public Composant(String nom, double tauxTVA, int id_projet) {
        this.nom = nom;
        this.tauxTVA = tauxTVA;
        this.id_projet = id_projet;
    }

    // Getters et setters pour les attributs communs
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


    public int getId_projet() {
        return id_projet;
    }
}
