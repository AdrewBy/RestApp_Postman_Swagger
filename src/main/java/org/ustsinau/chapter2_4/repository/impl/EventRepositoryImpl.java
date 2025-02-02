package org.ustsinau.chapter2_4.repository.impl;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.ustsinau.chapter2_4.models.Event;
import org.ustsinau.chapter2_4.repository.EventRepository;
import org.ustsinau.chapter2_4.utils.HibernateUtil;

import java.util.List;

public class EventRepositoryImpl implements EventRepository {
    private Transaction transaction;

    @Override
    public Event create(Event event) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            try {
                session.merge(event);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw new RuntimeException("Error creating event", e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error opening session", e);
        }
        return event;
    }

    @Override
    public Event update(Event event) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(event);
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Error updating event", e);
        }
        return event;
    }

    @Override
    public void delete(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            try {
                Event event = session.get(Event.class, id);
                if (event != null) {
                    session.remove(event);
                    transaction.commit();
                } else {
                    System.out.println("Event not found with id: " + id);
                }
            } catch (Exception e) {
                transaction.rollback();
                throw new RuntimeException("Error deleting event", e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error opening session", e);
        }
    }

    @Override
    public Event getById(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            Event event = session.get(Event.class, id);
            if (event != null) {
                Hibernate.initialize(event.getUser()); // Инициализация ленивой коллекции
                if (event.getUser() != null) {
                    Hibernate.initialize(event.getUser().getEvents()); // Инициализация ленивой коллекции пользователя
                }
            }
            return event;

        }catch (Exception e){
            throw new RuntimeException("Error getting event by id", e);
        }
    }

    @Override
    public List<Event> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            String hql = "FROM Event order by id";
            Query<Event> query = session.createQuery(hql, Event.class);
            List<Event> events = query.list();

            return events;
        }catch (Exception e){
            throw new RuntimeException("Error retrieving all events", e);
        }
    }
}
