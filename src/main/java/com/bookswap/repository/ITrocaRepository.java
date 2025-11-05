package com.bookswap.repository;

import java.util.List;
import com.bookswap.models.Troca;

public interface ITrocaRepository {
    
    void save(Troca troca);
    void update(Troca troca);
    void delete(int idTroca);
    Troca findById(int idTroca);
    
    List<Troca> findAll();
    
    List<Troca> findTrocasByUserId(int idUsuario);
    List<Troca> findTrocasPendentes(int idUsuario);
}