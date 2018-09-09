package com.tick42.quicksilver.repositories;

import com.tick42.quicksilver.models.Rating;
import com.tick42.quicksilver.repositories.base.RatingRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class RatingRepositoryImpl implements RatingRepository {

    private final SessionFactory sessionFactory;

    public RatingRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void rate(Rating rate) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(rate);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateRating(Rating rate) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(rate);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public int findExtensionRatingByUser(int extensionId, int userId) {
        int rating = 0;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            rating = (int) session
                    .createQuery("Select rating from Rating where user = :user and extension = :extension")
                    .setParameter("user", userId)
                    .setParameter("extension", extensionId)
                    .uniqueResult();
            session.getTransaction().commit();
        } catch (Exception e) {
//            System.out.println(e.getMessage());
        }
        return rating;

    }
}
