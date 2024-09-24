package com.bati_cuisin.service;

import com.bati_cuisin.model.Materiel;
import com.bati_cuisin.repository.interfaces.MaterielRepositoryInterface;

public class MaterielService {
    private MaterielRepositoryInterface materielRepository;

    public MaterielService(MaterielRepositoryInterface materielRepository) {
        this.materielRepository = materielRepository;
    }

    public void ajouterMateriel(String nom, double tauxTVA, double coutUnitaire, double quantite, double coutTransport, double coefficientQualite, int id_projet) {
        Materiel materiel = new Materiel(nom, tauxTVA, coutUnitaire, quantite, coutTransport, coefficientQualite,id_projet);
        materielRepository.ajouterMateriel(materiel);
    }


}
