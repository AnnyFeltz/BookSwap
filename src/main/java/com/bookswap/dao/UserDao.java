package com.bookswap.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.bookswap.database.DatabaseConnection;
import com.bookswap.models.PenaltyStatus;
import com.bookswap.models.User;
import com.bookswap.models.UserRole;

public class UserDao {
    
    private static final String SELECT_ALL_FIELDS = 
        "id, nome, email, senha, data_registro, perfil, creditos_disponiveis, status_penalidade, motivo_penalidade, data_inicio_penalidade, data_fim_penalidade, foto_perfil_url";
    private static final String INSERT_FIELDS = 
        "nome, email, senha, data_registro, perfil, creditos_disponiveis, status_penalidade, motivo_penalidade, data_inicio_penalidade, data_fim_penalidade, foto_perfil_url";


    private User mapUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setNome(rs.getString("nome"));
        user.setEmail(rs.getString("email"));
        user.setSenha(rs.getString("senha"));
        
        Timestamp tsDataReg = rs.getTimestamp("data_registro");
        user.setDataRegistro(tsDataReg != null ? tsDataReg.toLocalDateTime() : null);
        
        user.setPerfil(UserRole.valueOf(rs.getString("perfil"))); 
        user.setCreditosDisponiveis(rs.getInt("creditos_disponiveis"));
        
        user.setStatusPenalidade(PenaltyStatus.valueOf(rs.getString("status_penalidade")));
        user.setMotivoPenalidade(rs.getString("motivo_penalidade"));
        
        Timestamp tsInicio = rs.getTimestamp("data_inicio_penalidade");
        user.setDataInicioPenalidade(tsInicio != null ? tsInicio.toLocalDateTime() : null);
        
        Timestamp tsFim = rs.getTimestamp("data_fim_penalidade");
        user.setDataFimPenalidade(tsFim != null ? tsFim.toLocalDateTime() : null);
        
        user.setFotoPerfilUrl(rs.getString("foto_perfil_url")); 
        
        return user;
    }


    public User findById(int id) {
        String sql = "SELECT " + SELECT_ALL_FIELDS + " FROM users_bs WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapUser(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User findByEmail(String email) {
        String sql = "SELECT " + SELECT_ALL_FIELDS + " FROM users_bs WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapUser(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void save(User user) {
        String sql = "INSERT INTO users_bs (" + INSERT_FIELDS + ") VALUES (?,?,?,?,?,?,?,?,?,?,?)"; // 11 ?

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int i = 1;
            stmt.setString(i++, user.getNome());
            stmt.setString(i++, user.getEmail());
            stmt.setString(i++, user.getSenha());
            
            stmt.setTimestamp(i++, user.getDataRegistro() != null ? Timestamp.valueOf(user.getDataRegistro()) : null);
            stmt.setString(i++, user.getPerfil().name());
            stmt.setInt(i++, user.getCreditosDisponiveis());
            stmt.setString(i++, user.getStatusPenalidade().name());
            stmt.setString(i++, user.getMotivoPenalidade());
            stmt.setTimestamp(i++, user.getDataInicioPenalidade() != null ? Timestamp.valueOf(user.getDataInicioPenalidade()) : null);
            stmt.setTimestamp(i++, user.getDataFimPenalidade() != null ? Timestamp.valueOf(user.getDataFimPenalidade()) : null);
            
            stmt.setString(i++, user.getFotoPerfilUrl()); 
            
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(User user) {
        String sql = "UPDATE users_bs SET nome = ?, email = ?, perfil = ?, creditos_disponiveis = ?, " +
                     "status_penalidade = ?, motivo_penalidade = ?, data_inicio_penalidade = ?, data_fim_penalidade = ?, " +
                     "foto_perfil_url = ? " + 
                     "WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            int i = 1;
            stmt.setString(i++, user.getNome());
            stmt.setString(i++, user.getEmail());
            
            stmt.setString(i++, user.getPerfil().name());
            stmt.setInt(i++, user.getCreditosDisponiveis());
            stmt.setString(i++, user.getStatusPenalidade().name());
            stmt.setString(i++, user.getMotivoPenalidade());
            stmt.setTimestamp(i++, user.getDataInicioPenalidade() != null ? Timestamp.valueOf(user.getDataInicioPenalidade()) : null);
            stmt.setTimestamp(i++, user.getDataFimPenalidade() != null ? Timestamp.valueOf(user.getDataFimPenalidade()) : null);
            
            stmt.setString(i++, user.getFotoPerfilUrl()); 
            
            stmt.setInt(i++, user.getId()); 
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM users_bs WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}