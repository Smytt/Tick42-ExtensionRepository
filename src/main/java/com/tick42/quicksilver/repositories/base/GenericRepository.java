package com.tick42.quicksilver.repositories.base;

import java.util.List;

public interface GenericRepository<T> {
    T create(T model);

    T update(T model);

    void delete(int id);

    List<T> findAll();

    T findById(int id);

}
