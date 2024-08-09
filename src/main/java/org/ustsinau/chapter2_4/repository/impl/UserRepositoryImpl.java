package org.ustsinau.chapter2_4.repository.impl;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.ustsinau.chapter2_4.models.User;
import org.ustsinau.chapter2_4.repository.UserRepository;
import org.ustsinau.chapter2_4.utils.HibernateUtil;

import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private Transaction transaction;

    @Override
    public User create(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            try {
                session.persist(user);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw new RuntimeException("Error creating user", e);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error opening session", e);
        }
        return user;
    }

    @Override
    public User update(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            throw new RuntimeException("Error updating user", e);
        }
        return user;
    }

    @Override
    public void delete(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            try {
                User user = session.get(User.class, id);
                if (user != null) {
                    session.remove(user);
                    transaction.commit();
                } else {
                    System.out.println("User not found with id: " + id);
                }
            } catch (HibernateException e) {
                transaction.rollback();
                throw new RuntimeException("Error deleting user", e);
            }
        }catch (HibernateException e){
            throw new RuntimeException("Error opening session",e);
        }

    }

    @Override
    public User getById(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            User user =session.get(User.class, id);
      //     Hibernate.initialize(user.getEvents());
            return  user;
        }catch (HibernateException e){
            throw new RuntimeException("Error getting user by id", e);
        }
    }

    @Override
    public List<User> getAll() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()){

         //   String hql = "FROM User u left JOIN FETCH u.events ORDER BY u.id";
            String hql = "FROM User u ORDER BY u.id";
            Query<User> query = session.createQuery(hql, User.class);
            List<User> users = query.list();
            return users;
        }catch (HibernateException e){
            throw new RuntimeException("Error retrieving all posts", e);
        }

    }
}
