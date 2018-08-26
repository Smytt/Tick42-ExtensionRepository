package com.tick42.quicksilver.repositories;

import com.tick42.quicksilver.models.GitHubModel;
import com.tick42.quicksilver.repositories.base.GenericRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class GitHubRepositoryImpl implements GenericRepository<GitHubModel> {

    private final SessionFactory sessionFactory;

    @Autowired
    public GitHubRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public GitHubModel create(GitHubModel model) {
        return null;
    }

    @Override
    public GitHubModel update(GitHubModel gitHubModel) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(gitHubModel);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return gitHubModel;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<GitHubModel> findAll() {
        List<GitHubModel> gitHubModels = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            gitHubModels = session
                    .createQuery("from GitHubModel")
                    .list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return gitHubModels;
    }

    @Override
    public GitHubModel findById(int id) {
        return null;
    }
}
