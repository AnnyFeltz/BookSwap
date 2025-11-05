package com.bookswap.repository;

import com.bookswap.dao.CreditoDao;
import com.bookswap.models.Credito;

public class CreditoRepository implements ICreditoRepository {
    private final CreditoDao creditoDao;

    public CreditoRepository() {
        this.creditoDao = new CreditoDao();
    }

    @Override
    public Credito findByIdUsuario(int idUsuario) {
        return creditoDao.findByIdUsuario(idUsuario);
    }

    @Override
    public void save(Credito credito) {
        creditoDao.save(credito);
    }

    @Override
    public void update(Credito credito) {
        creditoDao.update(credito);
    }

    @Override
    public void delete(int idUsuario) {
        creditoDao.delete(idUsuario);
    }

}
