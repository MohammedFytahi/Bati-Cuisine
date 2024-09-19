package com.bati_cuisin.repository;

import com.bati_cuisin.model.Client;

import java.sql.SQLException;

public interface ClientRepositoryInterface {
    void createClient(Client client) throws SQLException;
}
