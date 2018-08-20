package com.tick42.quicksilver.repositories.base;

import com.tick42.quicksilver.models.Extension;

import java.util.List;

public interface GenericRepository<T> {
    T create(T model);

    T update(T model);

    void delete(int id);

    List<T> findAll();

    T findById(int id);

}
