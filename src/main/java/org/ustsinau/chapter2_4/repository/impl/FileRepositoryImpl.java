package org.ustsinau.chapter2_4.repository.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.ustsinau.chapter2_4.models.File;
import org.ustsinau.chapter2_4.repository.FileRepository;
import org.ustsinau.chapter2_4.utils.HibernateUtil;

import java.util.List;

public class FileRepositoryImpl implements FileRepository {
    private Transaction transaction;

    @Override
    public File create(File file) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            try {
                session.persist(file);
                transaction.commit();
                return file;
            } catch (Exception e) {
                transaction.rollback();
                throw new RuntimeException("Error creating file", e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error opening session", e);
        }
    }

    @Override
    public File update(File file) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(file);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException("Error updating file", e);
        }
        return file;
    }

    @Override
    public void delete(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            try {
                File file = session.get(File.class, id);
                if (file != null) {
                    session.remove(file);
                    transaction.commit();
                } else {
                    System.out.println("File not found with id: " + id);
                }
            } catch (Exception e) {
                transaction.rollback();
                throw new RuntimeException("Error deleting file", e);
            }
        }catch (Exception e){
            throw new RuntimeException("Error opening session", e);
        }
    }

    @Override
    public File getById(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            File file = session.get(File.class, id);
            return file;
        }catch (Exception e){
            throw new RuntimeException("Error getting file by id", e);
        }
    }

    @Override
    public List<File> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<File> query = session.createQuery("FROM File ", File.class);
            List<File> files = query.list();
            return files;
        }catch (Exception e){
            throw new RuntimeException("Error retrieving all posts", e);
        }
    }
}
