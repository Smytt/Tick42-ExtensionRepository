package com.tick42.quicksilver.services;

import com.tick42.quicksilver.config.FileConfig;
import com.tick42.quicksilver.exceptions.FileStorageException;
import com.tick42.quicksilver.exceptions.MyFileNotFoundException;
import com.tick42.quicksilver.models.Extension;
import com.tick42.quicksilver.models.File;
import com.tick42.quicksilver.repositories.FileRepositoryImpl;
import com.tick42.quicksilver.repositories.base.ExtensionRepository;
import com.tick42.quicksilver.services.base.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl implements FileService {

    private final Path fileLocation;
    private final FileRepositoryImpl fileRepository;
    private final ExtensionsServiceImpl extensionsService;
    private final ExtensionRepository extensionRepository;

    @Autowired
    public FileServiceImpl(FileConfig fileConfig, FileRepositoryImpl fileRepository, ExtensionsServiceImpl extensionsService, ExtensionRepository extensionRepository) {
        this.fileRepository = fileRepository;
        this.extensionsService = extensionsService;
        this.extensionRepository = extensionRepository;
        this.fileLocation = Paths.get("./uploads")
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileLocation);
        } catch (Exception e) {
            throw new FileStorageException("Couldn't create directory");
        }
    }

    @Override
    public File storeFile(MultipartFile receivedFile, int extensionId) {

        String fileName = StringUtils.cleanPath(receivedFile.getOriginalFilename());
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(fileName)
                .toUriString();

        Extension extension = extensionsService.findById(extensionId);

        File file = new File();
        file.setName(fileName);
        file.setLocation(fileDownloadUri);
        file.setSize(receivedFile.getSize());
        file.setType(receivedFile.getContentType());
        extension.setFile(file);

        try {
            if(fileName.contains("..")) {
                throw new FileStorageException("Filename contains invalid path sequence");
            }

            Path targetLocation = this.fileLocation.resolve(fileName);
            Files.copy(receivedFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            extensionRepository.update(extension);
            return file;
        }
        catch (IOException e) {
            throw new FileStorageException("Couldn't store file");
        }
    }

    @Override
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
