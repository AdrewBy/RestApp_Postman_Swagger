package org.ustsinau.chapter2_4.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.InputStream;
import java.util.Properties;

public class HibernateUtil {

    private static final String PROPERTIES_FILE = "hibernate.properties";
    private static SessionFactory sessionFactory;

    private HibernateUtil() {}

    private static synchronized void buildSessionFactory() {
        if (sessionFactory == null) {
            try {
                // Создаем конфигурацию
                Configuration configuration = new Configuration();

                // Загружаем свойства из файла
                Properties properties = new Properties();
                try (InputStream input = HibernateUtil.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
                    if (input == null) {
                        System.err.println("Sorry, unable to find " + PROPERTIES_FILE);
                        throw new RuntimeException("Hibernate properties file not found");
                    }
                    properties.load(input);
                } catch (Exception e) {
                    System.err.println("Failed to load " + PROPERTIES_FILE + " file");
                    e.printStackTrace();
                    throw new RuntimeException("Failed to load " + PROPERTIES_FILE, e);
                }

                // Применяем свойства к конфигурации
                configuration.setProperties(properties);

                // Добавляем сущности
                configuration.addAnnotatedClass(org.ustsinau.chapter2_4.models.User.class);
                configuration.addAnnotatedClass(org.ustsinau.chapter2_4.models.Event.class);
                configuration.addAnnotatedClass(org.ustsinau.chapter2_4.models.File.class);

                // Создаем и настраиваем ServiceRegistry
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .build();

                // Создаем и инициализируем SessionFactory
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Throwable ex) {
                System.err.println("Initial SessionFactory creation failed." + ex);
                throw new ExceptionInInitializerError(ex);
            }
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            buildSessionFactory();
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            // Закрываем кэш и соединения
            sessionFactory.close();
        }
    }
}
