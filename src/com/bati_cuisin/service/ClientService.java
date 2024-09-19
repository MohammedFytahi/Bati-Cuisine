package com.bati_cuisin.service;

import com.bati_cuisin.model.Client;
import com.bati_cuisin.repository.ClientRepository;

import java.sql.SQLException;

public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository){

        this.clientRepository = clientRepository;
    }

    public void addClient(Client client) throws SQLException {
        clientRepository.createClient(client);
    }
}
