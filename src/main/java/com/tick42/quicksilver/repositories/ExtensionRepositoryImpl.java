package com.tick42.quicksilver.repositories;

import com.tick42.quicksilver.models.Extension;
import com.tick42.quicksilver.models.Rating;
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
            extension.getTags().forEach(session::saveOrUpdate);
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
    public List<Extension> findFeatured() {
        List<Extension> extensions = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            extensions = session
                    .createQuery("from Extension where isFeatured = true and isPending = false and owner.isActive = true order by uploadDate desc")
                    .list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return extensions;
    }

    @Override
    public List<Extension> findPending() {
        List<Extension> extensions = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            extensions = session
                    .createQuery("from Extension where isPending = true")
                    .list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return extensions;
    }


    @Override
    public List<Extension> findAllByDate(String name, Integer page, Integer perPage) {
        List<Extension> extensions = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            extensions = session
                    .createQuery("from Extension where isPending = false and owner.isActive = true and name like :name order by uploadDate desc")
                    .setParameter("name", "%" + name + "%")
                    .setFirstResult((page - 1) * perPage)
                    .setMaxResults(perPage)
                    .list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return extensions;
    }

    @Override
    public List<Extension> findAllByCommit(String name, Integer page, Integer perPage) {
        List<Extension> extensions = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            extensions = session
                    .createQuery("from Extension where isPending = false and owner.isActive = true and name like :name order by github.lastCommit desc")
                    .setParameter("name", "%" + name + "%")
                    .setFirstResult((page - 1) * perPage)
                    .setMaxResults(perPage)
                    .list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return extensions;
    }

    @Override
    public List<Extension> findAllByName(String name, Integer page, Integer perPage) {
        List<Extension> extensions = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            extensions = session
                    .createQuery("from Extension where isPending = false and owner.isActive = true and name like :name order by name asc")
                    .setParameter("name", "%" + name + "%")
                    .setFirstResult((page - 1) * perPage)
                    .setMaxResults(perPage)
                    .list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return extensions;
    }

    @Override
    public List<Extension> findAllByDownloads(String name, Integer page, Integer perPage) {
        List<Extension> extensions = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            extensions = session
                    .createQuery("from Extension where isPending = false and owner.isActive = true and name like :name order by timesDownloaded desc")
                    .setParameter("name", "%" + name + "%")
                    .setFirstResult((page - 1) * perPage)
                    .setMaxResults(perPage)
                    .list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return extensions;
    }

    @Override
    public Long getTotalResults(String name) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Long total = (Long) session
                    .createQuery("select count(*) from Extension where isPending = false and owner.isActive = true and name like :name")
                    .setParameter("name", "%" + name + "%").uniqueResult();
            session.getTransaction().commit();
            return total;
        } catch (Exception e) {
            throw new RuntimeException("Couldn't get total results");
        }
    }

}