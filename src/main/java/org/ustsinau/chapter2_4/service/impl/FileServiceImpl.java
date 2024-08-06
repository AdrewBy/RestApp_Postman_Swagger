package org.ustsinau.chapter2_4.service.impl;

import jakarta.servlet.annotation.MultipartConfig;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.ustsinau.chapter2_4.models.Event;
import org.ustsinau.chapter2_4.models.File;
import org.ustsinau.chapter2_4.models.User;
import org.ustsinau.chapter2_4.repository.FileRepository;
import org.ustsinau.chapter2_4.repository.impl.FileRepositoryImpl;
import org.ustsinau.chapter2_4.service.EventService;
import org.ustsinau.chapter2_4.service.FileService;
import org.ustsinau.chapter2_4.service.UserService;
import org.ustsinau.chapter2_4.utils.GetFileNameUtil;
import org.ustsinau.chapter2_4.utils.HibernateUtil;

import java.io.InputStream;
import java.util.List;

@MultipartConfig
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    private final UserService userService;
    private final EventService eventService;

    // Конструктор, который инициализирует все поля
    public FileServiceImpl(UserService userService, EventService eventService, FileRepository fileRepository) {
        this.userService = userService;
        this.eventService = eventService;
        this.fileRepository = fileRepository;
    }

    // Конструктор по умолчанию, если вам нужен, но он будет создавать репозиторий без userService и eventService
    public FileServiceImpl() {
        this.fileRepository = new FileRepositoryImpl();
        this.userService = new UserServiceImpl(); // или null, если не инициализировано
        this.eventService = new EventServiceImpl(); // или null, если не инициализировано
    }

    @Override
    public File getById(Integer id) {
        return fileRepository.getById(id);
    }

    @Override
    public File create(File file) {
        return fileRepository.create(file);
    }

    @Override
    public File update(File file) {
        return fileRepository.update(file);
    }

    @Override
    public void deleteById(Integer id) {
        fileRepository.delete(id);
    }

    @Override
    public List<File> getAll() {
        return fileRepository.getAll();
    }
    @Override
    public File uploadFile(InputStream inputStream, Integer userId) {

        String fileName = GetFileNameUtil.getFileName(inputStream);

        File file = new File();
        file.setFileName(fileName);
        file.setFilePath("C:\\Soft\\TesT\\" + fileName);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                // Получаем пользователя из сессии
                User user = session.get(User.class, userId);
                Event event = new Event();
                event.setUser(user);
                event.setFile(file);

                // Сохраняем событие
                session.save(event);

                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw new RuntimeException("Error creating event", e);
            }
        }
        return file;
    }
}
