package com.tick42.quicksilver.repositories.base;

import java.util.List;

public interface GenericRepository<T> {
    void create(T model);

    void update(T model);

    void delete(int id);

    List<T> findAll();

    T findById(int id);
}
