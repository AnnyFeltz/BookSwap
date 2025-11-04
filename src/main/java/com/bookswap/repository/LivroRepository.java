package com.bookswap.repository; 

import java.util.List;

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
    public List<Livro> findByIdUsuario(int idUsuario) {
        return livroDao.findByIdUsuario(idUsuario);
    }

    @Override
    public List<Livro> findByTitulo(String titulo) {
        return livroDao.findByTitulo(titulo);
    }

    @Override
    public void save(Livro livro) {
        livroDao.save(livro);
    }

    @Override
    public void update(Livro livro) {
        livroDao.update(livro);
    }

    @Override
    public void delete(int id) {
        livroDao.delete(id);
    }
}
