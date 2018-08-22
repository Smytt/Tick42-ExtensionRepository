package com.tick42.quicksilver.repositories;

import com.sun.org.apache.xalan.internal.lib.Extensions;
import com.tick42.quicksilver.models.Extension;
import com.tick42.quicksilver.repositories.base.ExtensionRepository;
import com.tick42.quicksilver.repositories.base.GenericRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ExtensionRepositoryImpl implements ExtensionRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public ExtensionRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Extension create(Extension extension) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(extension);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return extension;
    }

    @Override
    public Extension update(Extension extension) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(extension);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return extension;
    }

    @Override
    public void delete(int id) {
        Extension extension = findById(id);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(extension);
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
                    .createQuery("from Extension")
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
            extension = session.get(Extension.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return extension;
    }

    @Override
    public List<Extension> findByName(String searchQuery) {
        List<Extension> extensions = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            extensions = session
                    .createQuery("from Extension where name like :name")
                    .setParameter("name", "%" + searchQuery + "%")
                    .list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return extensions;
    }
    @Override
    public List<Extension> findTopMostDownloaded(int count){
        List<Extension> extensions = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            extensions = session
                    .createQuery("from Extension order by times_downloaded desc")
                    .setMaxResults(count)
                    .list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
            return extensions;
    }
    public List<Extension> findLatestUploads(int count){
        List<Extension> extensions = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            extensions = session
                    .createQuery("from Extension order by last_commit desc")
                    .setMaxResults(count)
                    .list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return extensions;
    }
}