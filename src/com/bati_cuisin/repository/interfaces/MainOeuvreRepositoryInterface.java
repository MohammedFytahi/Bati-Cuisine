package com.bati_cuisin.repository.interfaces;

import com.bati_cuisin.model.MainOeuvre;

import java.util.List;
import java.util.Optional;

public interface MainOeuvreRepositoryInterface {


    void ajouterMainOeuvre(MainOeuvre mainOeuvre);
    Optional<List<MainOeuvre>> findMainOeuvreByProjectId(int idProjet);


}
