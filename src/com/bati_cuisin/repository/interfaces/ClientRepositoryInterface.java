package com.bati_cuisin.repository.interfaces;

import com.bati_cuisin.model.Client;

import java.sql.SQLException;
import java.util.List;

public interface ClientRepositoryInterface {
    int createClient(Client client) throws SQLException;
    Client trouverParNom(String nom) throws SQLException;
    List<Client> findClientsByProjectId(int projectId);
}
