package com.tick42.quicksilver.repositories;

import com.tick42.quicksilver.models.Tag;
import com.tick42.quicksilver.repositories.base.TagRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TagRepositoryImpl implements TagRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public TagRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Tag create(Tag tag) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(tag);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return tag;
    }

    @Override
    public Tag update(Tag tag) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(tag);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return tag;
    }

    @Override
    public void delete(int id) {
        Tag tag = findById(id);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(tag);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Tag> findAll() {
        List<Tag> tags = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            tags = session
                    .createQuery("from Tag")
                    .list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return tags;
    }

    @Override
    public Tag findById(int id) {
        Tag tag = null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            tag = session.get(Tag.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return tag;
    }

    @Override
    public Tag findByName(String name) {
        Tag tag = null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            tag = (Tag) session
                    .createQuery("from Tag where name = :name")
                    .setParameter("name", name)
                    .uniqueResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return tag;
    }
}
