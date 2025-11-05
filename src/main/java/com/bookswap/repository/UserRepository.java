package com.bookswap.repository;

import com.bookswap.dao.UserDao;
import com.bookswap.models.User;

public class UserRepository implements IUserRepository{
    
    private final UserDao userDao;

    public UserRepository() {
        this.userDao = new UserDao();
    }

    @Override
    public User findById(int id) {
        return userDao.findById(id);
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public void updateSenha(int id, String novaSenha) {
        userDao.updateSenha(id, novaSenha);
    }

    @Override
    public void delete(int id) {
        userDao.delete(id);
    }

}