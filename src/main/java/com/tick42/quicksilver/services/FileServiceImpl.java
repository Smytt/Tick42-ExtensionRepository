package com.tick42.quicksilver.services;

import com.tick42.quicksilver.config.FileConfig;
import com.tick42.quicksilver.exceptions.FileStorageException;
import com.tick42.quicksilver.exceptions.MyFileNotFoundException;
import com.tick42.quicksilver.services.base.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.jnlp.FileSaveService;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl implements FileService {
    private final Path fileLocation;

    @Autowired
    public FileServiceImpl(FileConfig fileConfig) {
        this.fileLocation = Paths.get(fileConfig.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileLocation);
        } catch (Exception e) {
            throw new FileStorageException("Couldn't create directory");
        }
    }

    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if(fileName.contains("..")) {
                throw new FileStorageException("Filename contains invalid path sequence");
            }

            Path targetLocation = this.fileLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        }
        catch (IOException e) {
            throw new FileStorageException("Couldn't store file");
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            }
            else  {
                throw new MyFileNotFoundException("File not found");
            }
        }
        catch (MalformedURLException e) {
            throw new MyFileNotFoundException("File not found " + e);
        }
    }
}
