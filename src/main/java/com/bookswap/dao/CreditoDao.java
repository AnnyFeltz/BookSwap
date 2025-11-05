package com.bookswap.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bookswap.database.DatabaseConnection; 
import com.bookswap.models.Credito;
import com.bookswap.repository.ICreditoRepository;

public class CreditoDao implements ICreditoRepository {
    
    private static final String TABLE_NAME = "creditos_bs";
    private static final String INSERT_FIELDS = "idUsuario, saldo_atual";

    private Credito mapCredito(ResultSet rs) throws SQLException {
        Credito credito = new Credito();
        credito.setIdUsuario(rs.getInt("idUsuario"));
        credito.setSaldoAtual(rs.getDouble("saldo_atual"));
        return credito;
    }

    @Override
    public Credito findByIdUsuario(int idUsuario) {
        String sql = "SELECT " + INSERT_FIELDS + " FROM " + TABLE_NAME + " WHERE idUsuario = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapCredito(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(Credito credito) {
        String sql = "INSERT INTO " + TABLE_NAME + " (" + INSERT_FIELDS + ") VALUES (?,?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int i = 1;
            stmt.setInt(i++, credito.getIdUsuario());
            stmt.setDouble(i++, credito.getSaldoAtual());
            
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Credito credito) {
        String sql = "UPDATE " + TABLE_NAME + " SET saldo_atual = ? WHERE idUsuario = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            int i = 1;
            stmt.setDouble(i++, credito.getSaldoAtual());
            stmt.setInt(i++, credito.getIdUsuario()); 
            
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int idUsuario) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE idUsuario = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idUsuario);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}