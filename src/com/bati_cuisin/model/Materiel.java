    package com.bati_cuisin.model;

    public class Materiel extends Composant {
        private double coutUnitaire;
        private double quantite;
        private double coutTransport;
        private double coefficientQualite;

        public Materiel(String nom, double tauxTVA, double coutUnitaire, double quantite, double coutTransport, double coefficientQualite) {
            super(nom, tauxTVA);
            this.coutUnitaire = coutUnitaire;
            this.quantite = quantite;
            this.coutTransport = coutTransport;
            this.coefficientQualite = coefficientQualite;
        }

        // Getters et setters pour les attributs spécifiques
        public double getCoutUnitaire() {
            return coutUnitaire;
        }

        public void setCoutUnitaire(double coutUnitaire) {
            this.coutUnitaire = coutUnitaire;
        }

        public double getQuantite() {
            return quantite;
        }

        public void setQuantite(double quantite) {
            this.quantite = quantite;
        }

        public double getCoutTransport() {
            return coutTransport;
        }

        public void setCoutTransport(double coutTransport) {
            this.coutTransport = coutTransport;
        }

        public double getCoefficientQualite() {
            return coefficientQualite;
        }

        public void setCoefficientQualite(double coefficientQualite) {
            this.coefficientQualite = coefficientQualite;
        }
    }

