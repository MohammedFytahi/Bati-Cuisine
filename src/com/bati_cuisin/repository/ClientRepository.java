package com.bati_cuisin.repository;

import com.bati_cuisin.database.DatabaseConnection;
import com.bati_cuisin.model.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientRepository implements ClientRepositoryInterface{
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

            // Execute the update
            pstmt.executeUpdate();

            // Retrieve the generated keys
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Return the generated ID
                } else {
                    throw new SQLException("Échec de l'obtention de l'ID du client.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Rethrow the exception after logging it
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
        return null; // Si aucun client n'est trouvé
    }



}
