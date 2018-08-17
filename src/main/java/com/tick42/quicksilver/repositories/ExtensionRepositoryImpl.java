package com.tick42.quicksilver.repositories;

import com.tick42.quicksilver.models.Extension;
import com.tick42.quicksilver.repositories.base.GenericRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
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
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(model);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(int id, Extension model) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(model);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query query = session
                    .createQuery("DELETE FROM Employee WHERE id = :id")
                    .setParameter("id", id);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Extension> findAll() {
        List<Extension> extensions = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            extensions = session
                    .createQuery("from Extensions")
                    .list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return extensions;

    }

    @Override
    public Extension findById(int id) {
        Extension extension = null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            extension = (Extension) session
                    .createQuery("from Extensions where id = :id")
                    .setParameter("id", id)
                    .list().get(0);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return extension;
    }
}