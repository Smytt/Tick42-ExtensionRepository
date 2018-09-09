package com.tick42.quicksilver.repositories;

import com.tick42.quicksilver.models.Rating;
import com.tick42.quicksilver.models.Settings;
import com.tick42.quicksilver.repositories.base.SettingsRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SettingsRepositoryImpl implements SettingsRepository {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Settings get() {
        Settings settings = null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            settings = session.get(Settings.class, 1);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return settings;
    }

    @Override
    public Settings set(Settings settings) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(settings);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return settings;
    }
}
