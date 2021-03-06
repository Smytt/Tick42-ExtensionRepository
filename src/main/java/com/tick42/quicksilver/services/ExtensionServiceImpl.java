package com.tick42.quicksilver.services;

import com.tick42.quicksilver.exceptions.*;
import com.tick42.quicksilver.models.DTO.ExtensionDTO;
import com.tick42.quicksilver.models.DTO.PageDTO;
import com.tick42.quicksilver.models.GitHubModel;
import com.tick42.quicksilver.models.Rating;
import com.tick42.quicksilver.models.Spec.ExtensionSpec;
import com.tick42.quicksilver.models.Extension;
import com.tick42.quicksilver.models.User;
import com.tick42.quicksilver.repositories.base.ExtensionRepository;
import com.tick42.quicksilver.repositories.base.UserRepository;
import com.tick42.quicksilver.security.JwtValidator;
import com.tick42.quicksilver.services.base.ExtensionService;
import com.tick42.quicksilver.services.base.GitHubService;
import com.tick42.quicksilver.services.base.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExtensionServiceImpl implements ExtensionService {

    private final ExtensionRepository extensionRepository;
    private final TagService tagService;
    private final GitHubService gitHubService;
    private JwtValidator validator;
    private UserRepository userRepository;

    @Autowired
    public ExtensionServiceImpl(ExtensionRepository extensionRepository, TagService tagService,
                                GitHubService gitHubService, JwtValidator jwtValidator, UserRepository userRepository) {
        this.extensionRepository = extensionRepository;
        this.tagService = tagService;
        this.gitHubService = gitHubService;
        this.validator = jwtValidator;
        this.userRepository = userRepository;
    }

    @Override
    public ExtensionDTO create(ExtensionSpec extensionSpec, int id) {

        User user = userRepository.findById(id);
        if (user == null) {
            throw new UserNotFoundException("User not found.");
        }

        Extension extension = new Extension(extensionSpec);
        extension.setIsPending(true);
        extension.setOwner(user);
        extension.setTimesDownloaded(0);
        extension.setUploadDate(new Date());
        extension.setTags(tagService.generateTags(extensionSpec.getTags()));
        extension.setGithub(gitHubService.generateGitHub(extensionSpec.getGithub()));

        return new ExtensionDTO(extensionRepository.create(extension));
    }

    @Override
    public ExtensionDTO findById(int id, User user) {
        Extension extension = extensionRepository.findById(id);

        checkRequestingUserVsRequestedExtension(extension, user);

        return new ExtensionDTO(extension);
    }

    @Override
    public ExtensionDTO update(int extensionsId, ExtensionSpec extensionSpec, int userId) {

        Extension extension = extensionRepository.findById(extensionsId);
        if (extension == null) {
            throw new ExtensionNotFoundException("Extension not found.");
        }

        User user = userRepository.findById(userId);
        if (user == null) {
            throw new UserNotFoundException("User not found.");
        }

        if (user.getId() != extension.getOwner().getId() && !user.getRole().equals("ROLE_ADMIN")) {
            throw new UnauthorizedExtensionModificationException("You are not authorized to edit this extension.");
        }

        extension.setName(extensionSpec.getName());
        extension.setVersion(extensionSpec.getVersion());
        extension.setDescription(extensionSpec.getDescription());

        GitHubModel oldGitHub = extension.getGithub();
        GitHubModel newGitHub = gitHubService.generateGitHub(extensionSpec.getGithub());
        oldGitHub.setLink(newGitHub.getLink());
        oldGitHub.setUser(newGitHub.getUser());
        oldGitHub.setRepo(newGitHub.getRepo());
        gitHubService.setRemoteDetails(oldGitHub);

        extension.setTags(tagService.generateTags(extensionSpec.getTags()));

        extensionRepository.update(extension);
        return new ExtensionDTO(extension);
    }

    @Override
    public void delete(int extensionsId, int userId) {
        Extension extension = extensionRepository.findById(extensionsId);
        if (extension == null) {
            throw new ExtensionNotFoundException("Extension not found.");
        }

        User user = userRepository.findById(userId);
        if (user == null) {
            throw new UserNotFoundException("User not found.");
        }

        if (user.getId() != extension.getOwner().getId() && !user.getRole().equals("ROLE_ADMIN")) {
            throw new UnauthorizedExtensionModificationException("You are not authorized to delete this extension.");
        }

        extensionRepository.delete(extension);
    }


    @Override
    public PageDTO<ExtensionDTO> findAll(String name, String orderBy, Integer page, Integer perPage) {

        if (page == null) {
            page = 1;
        }

        if (perPage == null) {
            perPage = 10;
        }

        if (name == null) {
            name = "";
        }

        if (orderBy == null) {
            orderBy = "date";
        }

        if (page < 1) {
            throw new InvalidParameterException("\"page\" should be a positive number.");
        }

        if (perPage < 1) {
            throw new InvalidParameterException("\"perPage\" should be a positive number.");
        }

        Long totalResults = extensionRepository.getTotalResults(name);
        int totalPages = (int) Math.ceil(totalResults * 1.0 / perPage);

        if (page > totalPages && totalResults != 0) {
            throw new InvalidParameterException("Page" + totalPages + " is the last page. Page " + page + " is invalid.");
        }

        List<Extension> extensions = new ArrayList<>();
        switch (orderBy) {
            case "date":
                extensions = extensionRepository.findAllByDate(name, page, perPage);
                break;
            case "commits":
                extensions = extensionRepository.findAllByCommit(name, page, perPage);
                break;
            case "name":
                extensions = extensionRepository.findAllByName(name, page, perPage);
                break;
            case "downloads":
                extensions = extensionRepository.findAllByDownloads(name, page, perPage);
                break;
            default:
                throw new InvalidParameterException("\"" + orderBy + "\" is not a valid parameter. Use \"date\", \"commits\", \"name\" or \"downloads\".");
        }

        List<ExtensionDTO> extensionDTOS = generateExtensionDTOList(extensions);
        return new PageDTO<>(extensionDTOS, page, totalPages, totalResults);
    }

    @Override
    public List<ExtensionDTO> findFeatured() {
        List<Extension> extensions = extensionRepository.findFeatured();
        return generateExtensionDTOList(extensions);
    }

    @Override
    public ExtensionDTO setPublishedState(int id, String state) {
        Extension extension = extensionRepository.findById(id);
        if (extension == null) {
            throw new ExtensionNotFoundException("Extension not found.");
        }

        switch (state) {
            case "publish":
                extension.setIsPending(false);
                break;
            case "unpublish":
                extension.setIsPending(true);
                break;
            default:
                throw new InvalidStateException("\"" + state + "\" is not a valid extension state. Use \"publish\" or \"unpublish\".");
        }

        extensionRepository.update(extension);
        return new ExtensionDTO(extension);
    }

    @Override
    public ExtensionDTO setFeaturedState(int id, String state) {
        Extension extension = extensionRepository.findById(id);
        if (extension == null) {
            throw new ExtensionNotFoundException("Extension not found.");
        }

        switch (state) {
            case "feature":
                extension.setIsFeatured(true);
                break;
            case "unfeature":
                extension.setIsFeatured(false);
                break;
            default:
                throw new InvalidStateException("\"" + state + "\" is not a valid featured state. Use \"feature\" or \"unfeature\".");
        }

        extensionRepository.update(extension);
        return new ExtensionDTO(extension);
    }

    @Override
    public List<ExtensionDTO> findPending() {
        return generateExtensionDTOList(extensionRepository.findPending());
    }

    @Override
    public List<ExtensionDTO> generateExtensionDTOList(List<Extension> extensions) {
        List<ExtensionDTO> extensionsDTO = extensions
                .stream()
                .map(ExtensionDTO::new)
                .collect(Collectors.toList());

        return extensionsDTO;
    }

    @Override
    public ExtensionDTO fetchGitHub(int extensionId, int userId) {
        Extension extension = extensionRepository.findById(extensionId);
        if (extension == null) {
            throw new ExtensionNotFoundException("Extension not found.");
        }

        User user = userRepository.findById(userId);
        if (user == null) {
            throw new UserNotFoundException("User not found.");
        }

        if (!user.getRole().equals("ROLE_ADMIN")) {
            throw new UnauthorizedExtensionModificationException("You are not authorized to trgigger a github refresh.");
        }

        gitHubService.setRemoteDetails(extension.getGithub());

        extensionRepository.update(extension);

        return new ExtensionDTO(extension);
    }

    @Override
    public ExtensionDTO increaseDownloadCount(int id) {
        Extension extension = extensionRepository.findById(id);

        if(extension == null || extension.getIsPending() || !extension.getOwner().getIsActive()) {
            throw new ExtensionUnavailableException("Download count won't increase - the extension is unavailable");
        }

        extension.setTimesDownloaded(extension.getTimesDownloaded() + 1);

        return new ExtensionDTO(extensionRepository.update(extension));
    }

    private void checkRequestingUserVsRequestedExtension(Extension extension, User user) {
        if (extension == null) {
            throw new ExtensionNotFoundException("Extension doesn't exist.");
        }

        if (!extension.getOwner().getIsActive() &&
                ((user == null) || (!user.getRole().equals("ROLE_ADMIN")))) {
            throw new ExtensionUnavailableException("Extension is unavailable.");
        }

        if (extension.getIsPending() &&
                ((user == null) ||
                        (extension.getOwner().getId() != user.getId()) &&
                                (!user.getRole().equals("ROLE_ADMIN")))) {
            throw new ExtensionUnavailableException("Extension is unavailable.");
        }
    }

}
