package com.pkrok.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageServiceUserPhoto {
    String storeFile(MultipartFile file);

    Resource loadFile(String fileName);
}
