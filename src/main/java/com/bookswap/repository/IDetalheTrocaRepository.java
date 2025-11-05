package com.bookswap.repository;

import java.util.List;
import com.bookswap.models.DetalheTroca;

public interface IDetalheTrocaRepository {
    
    void save(DetalheTroca detalheTroca);
    void update(DetalheTroca detalheTroca);
    DetalheTroca findById(int idDetalhe);
    
    List<DetalheTroca> findByTrocaId(int idTroca);
    
    DetalheTroca findByLivroAndTrocaId(int idTroca, int idLivro);
    
    void delete(int idDetalhe);
}