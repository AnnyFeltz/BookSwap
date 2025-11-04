package com.bookswap.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bookswap.database.DatabaseConnection; // Assumindo que você tem essa classe
import com.bookswap.models.Livro;
import com.bookswap.models.subModels.LivroStatus;
// import com.bookswap.repository.ILivroRepository;

public class LivroDao{
    
    private static final String TABLE_NAME = "livros_bs";

    private static final String SELECT_ALL_FIELDS = 
        "idLivro, idUsuario, titulo, autor, condicao_estado, preco_creditos, foto_capa, status_livro";
    
    private static final String INSERT_FIELDS = 
        "idUsuario, titulo, autor, condicao_estado, preco_creditos, foto_capa, status_livro";

    private Livro mapLivro(ResultSet rs) throws SQLException {
        Livro livro = new Livro();
        livro.setId(rs.getInt("id"));
        livro.setIdUsuario(rs.getInt("idUsuario"));
        livro.setTitulo(rs.getString("titulo"));
        livro.setAutor(rs.getString("autor"));
        livro.setCondicaoEstado(rs.getString("condicao_estado"));
        livro.setPrecoCreditos(rs.getDouble("preco_creditos"));
        livro.setFotoCapa(rs.getString("foto_capa"));
        
        livro.setStatusLivro(LivroStatus.valueOf(rs.getString("status_livro").toUpperCase()));
        
        return livro;
    }

    // @Override
    public void save(Livro livro) {
        String sql = "INSERT INTO " + TABLE_NAME + " (" + INSERT_FIELDS + ") VALUES (?,?,?,?,?,?,?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int i = 1;
            stmt.setInt(i++, livro.getIdUsuario());
            stmt.setString(i++, livro.getTitulo());
            stmt.setString(i++, livro.getAutor());
            stmt.setString(i++, livro.getCondicaoEstado());
            stmt.setDouble(i++, livro.getPrecoCreditos());
            stmt.setString(i++, livro.getFotoCapa());
            
            // Mapeamento do ENUM
            stmt.setString(i++, livro.getStatusLivro().name()); 
            
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // @Override
    public Livro findById(int id) {
        String sql = "SELECT " + SELECT_ALL_FIELDS + " FROM " + TABLE_NAME + " WHERE idLivro = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapLivro(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // @Override
    public List<Livro> findByUserId(int idUsuario) {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT " + SELECT_ALL_FIELDS + " FROM " + TABLE_NAME + " WHERE idUsuario = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                livros.add(mapLivro(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livros;
    }

    // @Override
    public List<Livro> findByTitulo(String titulo) {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT " + SELECT_ALL_FIELDS + " FROM " + TABLE_NAME + " WHERE titulo LIKE ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + titulo + "%"); 
            
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                livros.add(mapLivro(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livros;
    }

    // @Override
    public List<Livro> findAllAvailable() {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT " + SELECT_ALL_FIELDS + " FROM " + TABLE_NAME + " WHERE status_livro = ?"; 
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, LivroStatus.DISPONIVEL.name()); 
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                livros.add(mapLivro(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livros;
    }

    // @Override
    public void update(Livro livro) {
        String sql = "UPDATE " + TABLE_NAME + " SET titulo = ?, autor = ?, condicao_estado = ?, preco_creditos = ?, foto_capa = ?, status_livro = ? WHERE idLivro = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            int i = 1;
            stmt.setString(i++, livro.getTitulo());
            stmt.setString(i++, livro.getAutor());
            stmt.setString(i++, livro.getCondicaoEstado());
            stmt.setDouble(i++, livro.getPrecoCreditos());
            stmt.setString(i++, livro.getFotoCapa());
            stmt.setString(i++, livro.getStatusLivro().name());
            
            stmt.setInt(i++, livro.getId()); 
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // @Override
    public void delete(int id) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE idLivro = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}