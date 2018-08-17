package com.tick42.quicksilver.repositories;

import com.tick42.quicksilver.repositories.base.GenericRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositoryImpl<T> implements GenericRepository<T> {
    private final SessionFactory sessionFactory;

    @Autowired
    public RepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void create(T model) {

    }

    @Override
    public void update(int id, T model) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<T> findAll() {
        return null;
    }

    @Override
    public T findById(int id) {
        T model = null;
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            model = session.get(Class<T>, id);
            session.getTransaction().commit();
        }
    }
}
