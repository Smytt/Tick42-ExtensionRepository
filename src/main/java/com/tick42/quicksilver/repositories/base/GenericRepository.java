package com.tick42.quicksilver.repositories.base;

import com.tick42.quicksilver.models.Extension;

import java.util.List;

public interface GenericRepository<T> {
    void create(T model);

    void update(T model);

    void delete(int id);

    List<T> findAll();

    T findById(int id);

}
