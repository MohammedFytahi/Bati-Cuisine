package com.bati_cuisin.repository.interfaces;

import com.bati_cuisin.model.Devis;

import java.time.LocalDate;
import java.util.List;

public interface DevisRepositoryInterface {


    void insererDevis(int idProjet, double montantEstime, LocalDate dateEmission, LocalDate dateValidite, boolean accepte);



}
