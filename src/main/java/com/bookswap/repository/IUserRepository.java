package com.bookswap.repository;

import com.bookswap.models.User;

public interface IUserRepository {
    User findById(int id);
    User findByEmail(String email);
    void save(User user);
    void update(User user);
    void delete(int id);
}
