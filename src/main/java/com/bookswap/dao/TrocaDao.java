package com.bookswap.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bookswap.database.DatabaseConnection;
import com.bookswap.models.Troca;
import com.bookswap.models.subModels.TrocaStatus;
import com.bookswap.repository.ITrocaRepository;

public class TrocaDao implements ITrocaRepository {

    private static final String TABLE_NAME = "trocas_bs";
    private static final String FIELDS = "idTroca, idUsuario_ofertando, idUsuario_recebendo, data_inicio, data_limite_transacao, status_troca";

    private Troca mapTroca(ResultSet rs) throws SQLException {
        Troca troca = new Troca();
        troca.setId(rs.getInt("idTroca"));
        troca.setIdUsuarioOfertando(rs.getInt("idUsuario_ofertando"));
        troca.setIdUsuarioRecebendo(rs.getInt("idUsuario_recebendo"));
        troca.setDataInicio(rs.getDate("data_inicio").toLocalDate());

        Date dataLimiteSql = rs.getDate("data_limite_transacao");
        if (dataLimiteSql != null) {
            troca.setDataLimite(dataLimiteSql.toLocalDate());
        }

        troca.setStatusTroca(TrocaStatus.valueOf(rs.getString("status_troca")));
        return troca;
    }

    @Override
    public void save(Troca troca) {
        String sql = "INSERT INTO " + TABLE_NAME + " (idUsuario_ofertando, idUsuario_recebendo, data_inicio, status_troca) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            int i = 1;
            stmt.setInt(i++, troca.getIdUsuarioOfertando());
            stmt.setInt(i++, troca.getIdUsuarioRecebendo());
            stmt.setDate(i++, Date.valueOf(troca.getDataInicio()));
            stmt.setString(i++, troca.getStatusTroca().name());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    troca.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Troca troca) {
        String sql = "UPDATE " + TABLE_NAME + " SET idUsuario_ofertando = ?, idUsuario_recebendo = ?, data_limite_transacao = ?, status_troca = ? WHERE idTroca = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            int i = 1;
            stmt.setInt(i++, troca.getIdUsuarioOfertando());
            stmt.setInt(i++, troca.getIdUsuarioRecebendo());

            if (troca.getDataLimite() != null) {
                stmt.setDate(i++, Date.valueOf(troca.getDataLimite()));
            } else {
                stmt.setNull(i++, java.sql.Types.DATE);
            }

            stmt.setString(i++, troca.getStatusTroca().name());
            stmt.setInt(i++, troca.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Troca findById(int idTroca) {
        String sql = "SELECT " + FIELDS + " FROM " + TABLE_NAME + " WHERE idTroca = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idTroca);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapTroca(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Troca> findAll() {
        List<Troca> trocas = new ArrayList<>();
        String sql = "SELECT " + FIELDS + " FROM " + TABLE_NAME + " ORDER BY data_inicio DESC";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                trocas.add(mapTroca(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trocas;
    }

    @Override
    public List<Troca> findTrocasByUserId(int idUsuario) {
        List<Troca> trocas = new ArrayList<>();
        String sql = "SELECT " + FIELDS + " FROM " + TABLE_NAME + " WHERE idUsuario_ofertando = ? OR idUsuario_recebendo = ? ORDER BY data_inicio DESC";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idUsuario);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                trocas.add(mapTroca(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trocas;
    }

    @Override
    public List<Troca> findTrocasPendentes(int idUsuario) {
        List<Troca> trocas = new ArrayList<>();
        String sql = "SELECT " + FIELDS + " FROM " + TABLE_NAME
                + " WHERE (idUsuario_ofertando = ? OR idUsuario_recebendo = ?) AND status_troca = ? ORDER BY data_inicio ASC";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idUsuario);
            stmt.setString(3, TrocaStatus.PENDENTE.name());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                trocas.add(mapTroca(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trocas;
    }

    @Override
    public void delete(int idTroca) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE idTroca = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idTroca);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Troca> findPendentesParaUsuario(int idUsuario) {
        List<Troca> trocas = new ArrayList<>();
        String sql = "SELECT t.*, "
                + "uo.nome AS nomeOfertante, "
                + "ur.nome AS nomeReceptor "
                + "FROM trocas_bs t "
                + "JOIN usuarios_bs uo ON uo.idUsuario = t.idUsuario_ofertando "
                + "JOIN usuarios_bs ur ON ur.idUsuario = t.idUsuario_recebendo "
                + "WHERE (t.idUsuario_ofertando = ? OR t.idUsuario_recebendo = ?) "
                + "AND t.status_troca = ? "
                + "ORDER BY t.data_inicio ASC";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idUsuario);
            stmt.setString(3, TrocaStatus.PENDENTE.name());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Troca troca = mapTroca(rs);
                trocas.add(troca);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trocas;
    }

    public List<Troca> findHistoricoTrocas(int idUsuario) {
        List<Troca> trocas = new ArrayList<>();
        String sql = "SELECT t.*, "
                + "uo.nome AS nomeOfertante, "
                + "ur.nome AS nomeReceptor "
                + "FROM trocas_bs t "
                + "JOIN usuarios_bs uo ON uo.idUsuario = t.idUsuario_ofertando "
                + "JOIN usuarios_bs ur ON ur.idUsuario = t.idUsuario_recebendo "
                + "WHERE (t.idUsuario_recebendo = ? OR t.idUsuario_ofertando = ?) "
                + "AND t.status_troca IN ('ACEITA', 'CONCLUIDA', 'CANCELADA') "
                + "ORDER BY t.data_inicio DESC";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idUsuario);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Troca troca = mapTroca(rs);
                trocas.add(troca);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trocas;
    }

}
