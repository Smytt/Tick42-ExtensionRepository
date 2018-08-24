package com.tick42.quicksilver.repositories;

import com.tick42.quicksilver.models.GitHub;
import com.tick42.quicksilver.repositories.base.GenericRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class GitHubRepositoryImpl implements GenericRepository<GitHub> {

    private final SessionFactory sessionFactory;

    @Autowired
    public GitHubRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public GitHub create(GitHub model) {
        return null;
    }

    @Override
    public GitHub update(GitHub gitHub) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(gitHub);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return gitHub;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<GitHub> findAll() {
        List<GitHub> gitHubs = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            gitHubs = session
                    .createQuery("from GitHub")
                    .list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return gitHubs;
    }

    @Override
    public GitHub findById(int id) {
        return null;
    }
}
