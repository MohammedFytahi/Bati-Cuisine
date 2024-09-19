package com.bati_cuisin.repository;

import com.bati_cuisin.database.DatabaseConnection;
import com.bati_cuisin.model.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
    public void createClient(Client client) throws SQLException {
        String sql = "INSERT INTO client (nom, adresse, telephone, est_professionnel) Values(?,?,?,?)";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1,client.getNom());
            pstmt.setString(2, client.getAdresse());
            pstmt.setString(3,client.getTelephone());
            pstmt.setBoolean(4, client.isEst_professionnel());
            pstmt.executeUpdate();
        }
    }


}
