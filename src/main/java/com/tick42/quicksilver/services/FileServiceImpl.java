package com.tick42.quicksilver.services;

import com.tick42.quicksilver.exceptions.*;
import com.tick42.quicksilver.models.Extension;
import com.tick42.quicksilver.models.File;
import com.tick42.quicksilver.models.User;
import com.tick42.quicksilver.repositories.FileRepositoryImpl;
import com.tick42.quicksilver.repositories.base.ExtensionRepository;
import com.tick42.quicksilver.repositories.base.UserRepository;
import com.tick42.quicksilver.services.base.FileService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
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
    private final ExtensionServiceImpl extensionsService;
    private final ExtensionRepository extensionRepository;
    private final UserRepository userRepository;

    @Autowired
    public FileServiceImpl(FileRepositoryImpl fileRepository, ExtensionServiceImpl extensionsService, ExtensionRepository extensionRepository, UserRepository userRepository) {
        this.fileRepository = fileRepository;
        this.extensionsService = extensionsService;
        this.extensionRepository = extensionRepository;
        this.userRepository = userRepository;
        this.fileLocation = Paths.get("./uploads")
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileLocation);
        } catch (Exception e) {
            throw new FileStorageException("Couldn't create directory");
        }
    }

    @Override
    public File storeFile(MultipartFile receivedFile, int extensionId, int userId) {

        Extension extension = extensionRepository.findById(extensionId);
        if (extension == null) {
            throw new ExtensionNotFoundException("Extension not found.");
        }

        User user = userRepository.findById(userId);
        if (user == null) {
            throw new ExtensionNotFoundException("User not found.");
        }

        if (user.getId() != extension.getOwner().getId() && !user.getRole().equals("ROLE_ADMIN")) {
            throw new UnauthorizedExtensionModificationException("You are not authorized to add files to this extension.");
        }

        File file = generateFile(receivedFile, "file", extensionId);

        try {
            saveFile(file, receivedFile);

            extension.setFile(file);
            extensionRepository.update(extension);
            return file;

        } catch (IOException e) {
            throw new FileStorageException("Couldn't store file");
        }
    }


    @Override
    public File storeImage(MultipartFile receivedFile, int extensionId, int userId) {

        Extension extension = extensionRepository.findById(extensionId);
        if (extension == null) {
            throw new ExtensionNotFoundException("Extension not found.");
        }

        User user = userRepository.findById(userId);
        if (user == null) {
            throw new ExtensionNotFoundException("User not found.");
        }

        if (user.getId() != extension.getOwner().getId() && !user.getRole().equals("ROLE_ADMIN")) {
            throw new UnauthorizedExtensionModificationException("You are not authorized to add images to this extension.");
        }

        File image = generateFile(receivedFile, "image", extensionId);

        try {
            if (!image.getType().startsWith("image/")) {
                throw new FileFormatException("File should be of type IMAGE.");
            }

            saveFile(image, receivedFile);

            extension.setImage(image);
            extensionRepository.update(extension);
            return image;

        } catch (IOException e) {
            throw new FileStorageException("Couldn't store image.");
        }
    }

    private void saveFile(File image, MultipartFile receivedFile) throws IOException {
        Path targetLocation = this.fileLocation.resolve(image.getName());
        Files.copy(receivedFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found");
            }
        } catch (MalformedURLException e) {
            throw new FileNotFoundException("File not found " + e);
        }
    }

    private File generateFile(MultipartFile receivedFile, String type, int extensionId) {
        String fileType = FilenameUtils.getExtension(receivedFile.getOriginalFilename());
        String fileName = extensionId + "_" + type + "." + fileType;
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/download/")
                .path(fileName)
                .toUriString();
        File file = new File();
        file.setName(fileName);
        file.setLocation(fileDownloadUri);
        file.setSize(receivedFile.getSize());
        file.setType(receivedFile.getContentType());
        return file;
    }
}
