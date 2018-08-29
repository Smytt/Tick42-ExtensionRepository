package com.tick42.quicksilver.repositories;

import com.tick42.quicksilver.models.Extension;
import com.tick42.quicksilver.repositories.base.ExtensionRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Extension extension = session.get(Extension.class, id);
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
                    .createQuery("from Extension where isPending = false order by uploadDate desc")
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
    public List<Extension> findFeatured() {
        List<Extension> extensions = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            extensions = session
                    .createQuery("from Extension where is_featured = 1")
                    .list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return extensions;
    }


    @Override
    public List<Extension> findAllByDate(Integer page, Integer perPage) {
        List<Extension> extensions = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            extensions = session
                    .createQuery("from Extension where isPending = false order by uploadDate desc")
                    .setFirstResult((page - 1) * perPage)
                    .setMaxResults(page * perPage)
                    .list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return extensions;
    }

    @Override
    public List<Extension> findAllByCommit(Integer page, Integer perPage) {
        List<Extension> extensions = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            extensions = session
                    .createQuery("from Extension where isPending = false order by github.lastCommit desc")
                    .setFirstResult((page - 1) * perPage)
                    .setMaxResults(page * perPage)
                    .list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return extensions;
    }

    @Override
    public List<Extension> findAllByName(Integer page, Integer perPage) {
        List<Extension> extensions = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            extensions = session
                    .createQuery("from Extension where isPending = false order by name asc")
                    .setFirstResult((page - 1) * perPage)
                    .setMaxResults(page * perPage)
                    .list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return extensions;
    }

    @Override
    public List<Extension> findAllByDownloads(Integer page, Integer perPage) {
        List<Extension> extensions = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            extensions = session
                    .createQuery("from Extension where isPending = false order by timesDownloaded desc")
                    .setFirstResult((page - 1) * perPage)
                    .setMaxResults(page * perPage)
                    .list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return extensions;
    }
}