package com.bati_cuisin.model;

public abstract class Composant {
    protected String nom;
    protected double tauxTVA;

    public Composant(String nom, double tauxTVA) {
        this.nom = nom;
        this.tauxTVA = tauxTVA;
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
}
