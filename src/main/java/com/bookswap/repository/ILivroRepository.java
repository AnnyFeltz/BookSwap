package com.bookswap.repository;

import java.util.List;

import com.bookswap.models.Livro;

public interface ILivroRepository {
    Livro findById(int id);
    List<Livro> findByIdUsuario(int idUsuario);
    List<Livro> findByTitulo(String titulo);
    List<Livro> findAllAvailable();
    List<Livro> findAvailableByUserId(int idUsuario);
    void save(Livro livro);
    void update(Livro livro);
    void delete(int id);
}
