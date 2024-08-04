package org.ustsinau.chapter2_4.service.impl;

import jakarta.servlet.annotation.MultipartConfig;
import org.ustsinau.chapter2_4.models.Event;
import org.ustsinau.chapter2_4.models.File;
import org.ustsinau.chapter2_4.models.User;
import org.ustsinau.chapter2_4.repository.FileRepository;
import org.ustsinau.chapter2_4.repository.impl.FileRepositoryImpl;
import org.ustsinau.chapter2_4.service.EventService;
import org.ustsinau.chapter2_4.service.FileService;
import org.ustsinau.chapter2_4.service.UserService;
import org.ustsinau.chapter2_4.utils.GetFileNameUtil;

import java.io.InputStream;
import java.util.List;

@MultipartConfig
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    private  UserService userService;
    private  EventService eventService;

    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public FileServiceImpl() {
        this.fileRepository = new FileRepositoryImpl();
    }

    public FileServiceImpl(UserService userService, EventService eventService, FileRepository fileRepository) {
        this.fileRepository = new FileRepositoryImpl();
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
        User user = userService.getById(userId);
        Event event = new Event();
        event.setUser(user);
        event.setFile(file);
        eventService.create(event);
        return file;
    }
}
