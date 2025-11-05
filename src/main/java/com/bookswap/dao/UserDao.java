package com.bookswap.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

import com.bookswap.database.DatabaseConnection;
import com.bookswap.models.User;
import com.bookswap.models.subModels.UserRole;
import com.bookswap.models.subModels.UserStatus;
import com.bookswap.repository.IUserRepository;

public class UserDao implements IUserRepository {
    
    private static final String TABLE_NAME = "usuarios_bs";
    
    private static final String SELECT_ALL_FIELDS = 
        "idUsuario, nome, email, senha, data_registro, role, status, foto_perfil, localizacao";
        
    private static final String INSERT_FIELDS = 
        "nome, email, senha, role, status, foto_perfil, localizacao, data_registro";

    private User mapUser(ResultSet rs) throws SQLException {
        User user = new User();
        
        user.setId(rs.getInt("idUsuario")); 

        user.setNome(rs.getString("nome"));
        user.setEmail(rs.getString("email"));
        user.setSenha(rs.getString("senha"));
        
        Timestamp tsDataReg = rs.getTimestamp("data_registro");
        user.setDataRegistro(tsDataReg != null ? tsDataReg.toLocalDateTime() : null);
        
        user.setRole(UserRole.valueOf(rs.getString("role").toUpperCase()));
        user.setStatus(UserStatus.valueOf(rs.getString("status").toUpperCase()));
        
        user.setFotoPerfil(rs.getString("foto_perfil")); 
        user.setLocalizacao(rs.getString("localizacao"));
        
        return user;
    }

    @Override
    public User findById(int id) {
        String sql = "SELECT " + SELECT_ALL_FIELDS + " FROM " + TABLE_NAME + " WHERE idUsuario = ?";
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

    @Override
    public User findByEmail(String email) {
        String sql = "SELECT " + SELECT_ALL_FIELDS + " FROM " + TABLE_NAME + " WHERE email = ?";
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
    
    @Override
    public void save(User user) {
        String sql = "INSERT INTO " + TABLE_NAME + " (" + INSERT_FIELDS + ") VALUES (?,?,?,?,?,?,?,?)"; 

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            int i = 1;
            stmt.setString(i++, user.getNome());
            stmt.setString(i++, user.getEmail());
            stmt.setString(i++, user.getSenha());
            
            stmt.setString(i++, user.getRole().name()); 
            stmt.setString(i++, user.getStatus().name());
            
            // Tratamento de Nulos
            stmt.setObject(i++, user.getFotoPerfil(), Types.VARCHAR); 
            stmt.setObject(i++, user.getLocalizacao(), Types.VARCHAR); 

            stmt.setTimestamp(i++, Timestamp.valueOf(user.getDataRegistro())); 
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setId(rs.getInt(1)); 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE " + TABLE_NAME + " SET nome = ?, email = ?, role = ?, status = ?, foto_perfil = ?, localizacao = ? WHERE idUsuario = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            int i = 1;
            stmt.setString(i++, user.getNome());
            stmt.setString(i++, user.getEmail());
            
            stmt.setString(i++, user.getRole().name());
            stmt.setString(i++, user.getStatus().name());
            
            // Tratamento de Nulos
            stmt.setObject(i++, user.getFotoPerfil(), Types.VARCHAR); 
            stmt.setObject(i++, user.getLocalizacao(), Types.VARCHAR); 
            
            stmt.setInt(i++, user.getId()); 
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void updateSenha(int id, String novaSenha) {
        String sql = "UPDATE " + TABLE_NAME + " SET senha = ? WHERE idUsuario = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, novaSenha); 
            
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE idUsuario = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}