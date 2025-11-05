package com.bookswap.repository;

import com.bookswap.models.Credito;

public interface ICreditoRepository {
    Credito findByIdUsuario(int idUsuario);
    void save(Credito credito);
    void update(Credito credito);
    void delete(int idUsuario);
}
