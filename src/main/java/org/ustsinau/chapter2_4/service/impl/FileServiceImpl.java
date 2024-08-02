package org.ustsinau.chapter2_4.service.impl;

import org.ustsinau.chapter2_4.models.File;
import org.ustsinau.chapter2_4.repository.FileRepository;
import org.ustsinau.chapter2_4.repository.impl.FileRepositoryImpl;
import org.ustsinau.chapter2_4.service.FileService;

import java.util.List;

public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;

    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public FileServiceImpl() {
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
}
