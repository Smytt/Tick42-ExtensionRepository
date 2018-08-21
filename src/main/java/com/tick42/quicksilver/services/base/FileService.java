package com.tick42.quicksilver.services.base;

import com.tick42.quicksilver.models.File;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    public File storeFile(MultipartFile file);
    public Resource loadFileAsResource(String fileName);
}
