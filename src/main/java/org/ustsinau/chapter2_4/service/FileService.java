package org.ustsinau.chapter2_4.service;

import org.ustsinau.chapter2_4.models.File;

import java.io.InputStream;

public interface FileService extends GenericService<File, Integer> {
    File uploadFile(InputStream inputStream, Integer userId);
}
