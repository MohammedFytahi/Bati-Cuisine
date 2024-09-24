package com.bati_cuisin.repository.interfaces;


import com.bati_cuisin.model.Project;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public interface ProjectRepositoryInterface {
    void ajouterProjet(Project project);



        Optional<Project> trouverProjetParId(int idProjet);

    void updateCoutTotal(int idProjet, double coutTotal) throws SQLException;

    List<Project> findAll();

    void updateProjectState(int projectId, Project.EtatProjet newState);
}
