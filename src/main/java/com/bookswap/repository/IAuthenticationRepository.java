package com.bookswap.repository;

import io.javalin.http.Context;

public interface  IAuthenticationRepository {
    void register(Context ctx);
    void login(Context ctx);
    void logout(Context ctx);
}
