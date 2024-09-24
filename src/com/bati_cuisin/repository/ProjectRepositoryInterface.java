package com.bati_cuisin.repository;


import com.bati_cuisin.model.Project;

import java.sql.SQLException;
import java.util.Optional;


public interface ProjectRepositoryInterface {
    void ajouterProjet(Project project);



        Optional<Project> trouverProjetParId(int idProjet);

    void updateCoutTotal(int idProjet, double coutTotal) throws SQLException;
}
