package com.bati_cuisin.repository.implementation;

import com.bati_cuisin.database.DatabaseConnection;
import com.bati_cuisin.model.Client;
import com.bati_cuisin.repository.interfaces.ClientRepositoryInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientRepository implements ClientRepositoryInterface {
    private Connection connection;
    public ClientRepository(){
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    @Override
    public int createClient(Client client) throws SQLException {
        String sql = "INSERT INTO client (nom, adresse, telephone, est_professionnel) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, client.getNom());
            pstmt.setString(2, client.getAdresse());
            pstmt.setString(3, client.getTelephone());
            pstmt.setBoolean(4, client.isEst_professionnel());


            pstmt.executeUpdate();


            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Échec de l'obtention de l'ID du client.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }


    @Override
    public Client trouverParNom(String nom) throws SQLException {
        String query = "SELECT * FROM client WHERE nom = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nom);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id_client");
                    String adresse = resultSet.getString("adresse");
                    String telephone = resultSet.getString("telephone");
                    boolean estProfessionnel = resultSet.getBoolean("est_professionnel");

                    return new Client(id, nom, adresse, telephone, estProfessionnel);
                }
            }
        }
        return null;
    }

    @Override
    public List<Client> findClientsByProjectId(int projectId) {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT c.id_client, c.nom, c.adresse, c.telephone, c.est_professionnel " +
                "FROM client c " +
                "JOIN projet p ON c.id_client = p.id_client " + // Adjust according to your foreign key relation
                "WHERE p.id_projet = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, projectId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id_client");
                String nom = resultSet.getString("nom");
                String adresse = resultSet.getString("adresse");
                String telephone = resultSet.getString("telephone");
                boolean estProfessionnel = resultSet.getBoolean("est_professionnel");

                Client client = new Client(id, nom, adresse, telephone, estProfessionnel);
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }


}
