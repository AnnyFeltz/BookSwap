package com.bookswap.repository;

import com.bookswap.models.Livro;

public class ILivroRepository {
    Livro findById(int id);
    Livro findByTitulo(String titulo);
    void save(Livro livro);
    void update(Livro livro);
    void delete(int id);
}
