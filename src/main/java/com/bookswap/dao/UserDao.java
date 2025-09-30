package com.bookswap.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bookswap.database.DatabaseConnection;
import com.bookswap.models.Role;
import com.bookswap.models.User;

public class UserDao {
    
    public User findById(int id) {
        String sql = "";

        try (Connection conn = DatabaseConnection.getConnection(); 
        PreparedStatement stmt = conn.prepareStatement(sql)){

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public User findByEmail(String email) {
        String sql = "";

        try (Connection conn = DatabaseConnection.getConnection(); 
        PreparedStatement stmt = conn.prepareStatement(sql)){

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void save(User user) {
        String sql = "INSERT INTO users_bs (nome, email, senha, role) VALUES (?,?,?,?)";

        try (Connection conn = DatabaseConnection.getConnection(); 
        PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, user.getNome());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getSenha());
            stmt.setString(4, user.getRole().name());

            stmt.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void update(User user) {
        String sql = "";

        try (Connection conn = DatabaseConnection.getConnection(); 
        PreparedStatement stmt = conn.prepareStatement(sql)){

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "";

        try (Connection conn = DatabaseConnection.getConnection(); 
        PreparedStatement stmt = conn.prepareStatement(sql)){

        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
