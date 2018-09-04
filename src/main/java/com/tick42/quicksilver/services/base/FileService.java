package com.tick42.quicksilver.services.base;

import com.tick42.quicksilver.models.File;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    public File storeFile(MultipartFile file, int extensionId, int userId);

    public Resource loadFileAsResource(String fileName);

    public File storeImage(MultipartFile receivedFile, int extensionId, int userId);
}
