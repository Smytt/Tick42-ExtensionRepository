package com.tick42.quicksilver.repositories;

import com.tick42.quicksilver.models.File;
import com.tick42.quicksilver.repositories.base.FileRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FileRepositoryImpl implements FileRepository {

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
}
