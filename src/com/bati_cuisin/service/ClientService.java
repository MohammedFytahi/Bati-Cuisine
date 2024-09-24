package com.bati_cuisin.service;
import com.bati_cuisin.model.Client;
import com.bati_cuisin.repository.implementation.ClientRepository;

import java.sql.SQLException;
import java.util.HashMap;

public class ClientService {

    private final ClientRepository clientRepository;
    private final HashMap<String, Client> clientCache;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
        this.clientCache = new HashMap<>();
    }

    public int addClient(Client client) throws SQLException {
        int clientId = clientRepository.createClient(client);
        clientCache.put(client.getNom(), client);
        return clientId;
    }

    public Client trouverClientParNom(String nom) throws SQLException {

        if (clientCache.containsKey(nom)) {
            System.out.println("Client trouvé dans le cache.");
            return clientCache.get(nom);
        }


        Client client = clientRepository.trouverParNom(nom);
        if (client != null) {
            clientCache.put(nom, client);
        }

        return client;
    }


    public void clearCache() {
        clientCache.clear();
    }
}