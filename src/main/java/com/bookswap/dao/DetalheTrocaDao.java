package com.bookswap.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bookswap.database.DatabaseConnection; 
import com.bookswap.models.DetalheTroca;
import com.bookswap.models.subModels.LivroStatus;
import com.bookswap.repository.IDetalheTrocaRepository;

public class DetalheTrocaDao implements IDetalheTrocaRepository {
    
    private static final String TABLE_NAME = "detalhes_troca_bs";
    private static final String FIELDS = "idDetalhe_troca, idTroca, idLivro_ofertado, credito_ofertados, status_livro";
    private static final String INSERT_FIELDS = "idTroca, idLivro_ofertado, credito_ofertados, status_livro";

    private DetalheTroca mapDetalheTroca(ResultSet rs) throws SQLException {
        DetalheTroca detalhe = new DetalheTroca();
        detalhe.setId(rs.getInt("idDetalhe_troca"));
        detalhe.setIdTroca(rs.getInt("idTroca"));
        
        detalhe.setIdLivroOfertado(rs.getInt("idLivro_ofertado")); 
        
        detalhe.setCreditosOfertados(rs.getDouble("credito_ofertados"));
        detalhe.setStatusLivro(LivroStatus.valueOf(rs.getString("status_livro")));
        return detalhe;
    }

    @Override
    public void save(DetalheTroca detalheTroca) {
        String sql = "INSERT INTO " + TABLE_NAME + " (" + INSERT_FIELDS + ") VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            int i = 1;
            stmt.setInt(i++, detalheTroca.getIdTroca());
            
            if (detalheTroca.getIdLivroOfertado() > 0) {
                 stmt.setInt(i++, detalheTroca.getIdLivroOfertado());
            } else {
                 stmt.setNull(i++, java.sql.Types.INTEGER);
            }
            
            stmt.setDouble(i++, detalheTroca.getCreditosOfertados());
            stmt.setString(i++, detalheTroca.getStatusLivro().name());
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    detalheTroca.setId(rs.getInt(1));
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(DetalheTroca detalheTroca) {
        String sql = "UPDATE " + TABLE_NAME + " SET idTroca = ?, idLivro_ofertado = ?, credito_ofertados = ?, status_livro = ? WHERE idDetalhe_troca = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            int i = 1;
            stmt.setInt(i++, detalheTroca.getIdTroca());
            
            if (detalheTroca.getIdLivroOfertado() > 0) {
                 stmt.setInt(i++, detalheTroca.getIdLivroOfertado());
            } else {
                 stmt.setNull(i++, java.sql.Types.INTEGER);
            }
            
            stmt.setDouble(i++, detalheTroca.getCreditosOfertados());
            stmt.setString(i++, detalheTroca.getStatusLivro().name());
            
            stmt.setInt(i++, detalheTroca.getId()); 
            
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public DetalheTroca findById(int idDetalhe) {
        String sql = "SELECT " + FIELDS + " FROM " + TABLE_NAME + " WHERE idDetalhe_troca = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idDetalhe);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapDetalheTroca(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DetalheTroca> findByTrocaId(int idTroca) {
        List<DetalheTroca> detalhes = new ArrayList<>();
        String sql = "SELECT " + FIELDS + " FROM " + TABLE_NAME + " WHERE idTroca = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idTroca);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                detalhes.add(mapDetalheTroca(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detalhes;
    }

    @Override
    public DetalheTroca findByLivroAndTrocaId(int idTroca, int idLivro) {
        String sql = "SELECT " + FIELDS + " FROM " + TABLE_NAME + " WHERE idTroca = ? AND idLivro_ofertado = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idTroca);
            stmt.setInt(2, idLivro);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapDetalheTroca(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(int idDetalhe) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE idDetalhe_troca = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idDetalhe);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}