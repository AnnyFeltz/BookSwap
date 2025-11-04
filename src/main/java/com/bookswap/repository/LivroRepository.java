package com.bookswap.repository; 

import com.bookswap.dao.LivroDao;
import com.bookswap.models.Livro;

public class LivroRepository implements ILivroRepository {
    
    private final LivroDao livroDao;

    public LivroRepository() {
        this.livroDao = new LivroDao();
    }

    @Override
    public Livro findById(int id) {
        return livroDao.findById(id);
    }

    @Override
    public Livro findByTitulo(String email) {
        return livroDao.findByTitulo(email);
    }

    @Override
    public void save(Livro livro) {
        livroDao.save(Livro);
    }

    @Override
    public void update(Livro livro) {
        livroDao.update(Livro);
    }

    @Override
    public void delete(int id) {
        livroDao.delete(id);
    }
}
