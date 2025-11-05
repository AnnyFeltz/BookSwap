package com.bookswap.repository;

import java.util.List;

import com.bookswap.dao.DetalheTrocaDao;
import com.bookswap.models.DetalheTroca;

public class DetalheTrocaRepository implements IDetalheTrocaRepository {
    
    private final DetalheTrocaDao detalheTrocaDao;

    public DetalheTrocaRepository() {
        this.detalheTrocaDao = new DetalheTrocaDao();
    }

    @Override
    public void save(DetalheTroca detalheTroca) {
        detalheTrocaDao.save(detalheTroca);
    }

    @Override
    public void update(DetalheTroca detalheTroca) {
        detalheTrocaDao.update(detalheTroca);
    }

    @Override
    public DetalheTroca findById(int idDetalhe) {
        return detalheTrocaDao.findById(idDetalhe);
    }

    @Override
    public List<DetalheTroca> findByTrocaId(int idTroca) {
        return detalheTrocaDao.findByTrocaId(idTroca);
    }

    @Override
    public DetalheTroca findByLivroAndTrocaId(int idTroca, int idLivro) {
        return detalheTrocaDao.findByLivroAndTrocaId(idTroca, idLivro);
    }

    @Override
    public void delete(int idDetalhe) {
        detalheTrocaDao.delete(idDetalhe);
    }
}