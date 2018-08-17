package com.tick42.quicksilver.repositories;

import com.tick42.quicksilver.models.Extension;
import com.tick42.quicksilver.repositories.base.GenericRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ExtensionRepositoryImpl implements GenericRepository<Extension> {

    private final SessionFactory sessionFactory;

    @Autowired
    public ExtensionRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Extension model) {

    }

    @Override
    public void update(int id, Extension model) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Extension> findAll() {
        return null;
    }

    @Override
    public Extension findById(int id) {
        return null;
    }
}
