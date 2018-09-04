package com.tick42.quicksilver.controllers;

import com.tick42.quicksilver.exceptions.ExtensionNotFoundException;
import com.tick42.quicksilver.exceptions.FileFormatException;
import com.tick42.quicksilver.exceptions.FileStorageException;
import com.tick42.quicksilver.exceptions.UnauthorizedExtensionModificationException;
import com.tick42.quicksilver.models.File;
import com.tick42.quicksilver.security.JwtValidator;
import com.tick42.quicksilver.services.base.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping(value = "/api")
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);


    private final FileService fileService;
    private JwtValidator validator;

    @Autowired
    public FileController(FileService fileService, JwtValidator validator) {
        this.fileService = fileService;
        this.validator = validator;
    }

    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    @PostMapping("/auth/upload/file/{id}")
    @ResponseBody
    public File uploadFile(@RequestParam("file") MultipartFile receivedFile, @PathVariable(name = "id") int extensionId, HttpServletRequest request) {

        int userId = validator.getUserIdFromToken(request);
        return fileService.storeFile(receivedFile, extensionId, userId);
    }

    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    @PostMapping("/auth/upload/image/{id}")
    public File uploadImage(@RequestParam("image") MultipartFile receivedImage, @PathVariable(name = "id") int extensionId, HttpServletRequest request) {
        int userId = validator.getUserIdFromToken(request);
        return fileService.storeImage(receivedImage, extensionId, userId);
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = fileService.loadFileAsResource(fileName);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            logger.info("Could not get file type");
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
                        resource.getFilename() + "\"")
                .body(resource);
    }

    @ExceptionHandler
    ResponseEntity handleFileFormatException(FileFormatException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler
    ResponseEntity handleFileStorageException(FileStorageException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler
    ResponseEntity handleExtensionNotFoundException(ExtensionNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler
    ResponseEntity handleUnauthorizedExtensionModificationException(UnauthorizedExtensionModificationException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(e.getMessage());
    }



}
