package com.bookswap.repository;

import java.util.List;

import com.bookswap.dao.TrocaDao;
import com.bookswap.models.Troca;

public class TrocaRepository implements ITrocaRepository {

    private final TrocaDao trocaDao;

    public TrocaRepository() {
        this.trocaDao = new TrocaDao();
    }

    @Override
    public void save(Troca troca) {
        trocaDao.save(troca);
    }

    @Override
    public void update(Troca troca) {
        trocaDao.update(troca);
    }

    @Override
    public void delete(int idTroca) {
        trocaDao.delete(idTroca);
    }

    @Override
    public Troca findById(int idTroca) {
        return trocaDao.findById(idTroca);
    }

    @Override
    public List<Troca> findAll() {
        return trocaDao.findAll();
    }

    @Override
    public List<Troca> findTrocasByUserId(int idUsuario) {
        return trocaDao.findTrocasByUserId(idUsuario);
    }

    @Override
    public List<Troca> findTrocasPendentes(int idUsuario) {
        return trocaDao.findTrocasPendentes(idUsuario);
    }

    public List<Troca> findPendentesParaUsuario(int idUsuario) {
        return trocaDao.findPendentesParaUsuario(idUsuario);
    }

    public List<Troca> findHistoricoTrocas(int idUsuario) {
        return trocaDao.findHistoricoTrocas(idUsuario);
    }

}
