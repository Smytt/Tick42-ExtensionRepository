package com.tick42.quicksilver.repositories;

import com.tick42.quicksilver.models.File;
import com.tick42.quicksilver.repositories.base.GenericRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FileRepositoryImpl implements GenericRepository<File> {

    private final SessionFactory sessionFactory;

    @Autowired
    public FileRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public File create(File file) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(file);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return file;
    }

    @Override
    public File update(File file) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<File> findAll() {
        return null;
    }

    @Override
    public File findById(int id) {
        return null;
    }
}
