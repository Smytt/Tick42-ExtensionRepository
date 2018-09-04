package com.tick42.quicksilver.services;

import com.tick42.quicksilver.exceptions.ExtensionNotFoundException;
import com.tick42.quicksilver.exceptions.InvalidParameterException;
import com.tick42.quicksilver.exceptions.InvalidStateException;
import com.tick42.quicksilver.exceptions.UserNotFoundException;
import com.tick42.quicksilver.models.DTO.ExtensionDTO;
import com.tick42.quicksilver.models.DTO.PageDTO;
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
        Extension extension = new Extension(extensionSpec);
        extension.setIsPending(true);
        extension.setOwner(user);
        extension.setUploadDate(new Date());
        extension.setTags(tagService.generateTags(extensionSpec.getTags()));

        extension.setGithub(gitHubService.generateGitHub(extensionSpec.getGithub()));

        return new ExtensionDTO(extensionRepository.create(extension));
    }

    @Override
    public ExtensionDTO findById(int id) {
        Extension extension = extensionRepository.findById(id);
        if (extension != null) {
            ExtensionDTO extensionDTO = new ExtensionDTO(extension);
            return extensionDTO;
        }
        throw new ExtensionNotFoundException("Extension doesn't exist.");
    }

    @Override
    public ExtensionDTO update(int extensionsId, ExtensionSpec extensionSpec, int userId) {

        Extension extension = extensionRepository.findById(extensionsId);
        if (extension == null) {
            throw new ExtensionNotFoundException("Extension not found.");
        }

        String role = userRepository.findById(userId).getRole();
        if (userId == extension.getOwner().getId() || role.equals("ROLE_ADMIN")) {
            extensionRepository.update(extension);
        }

        extension.setName(extensionSpec.getName());
        extension.setVersion(extensionSpec.getVersion());
        extension.setDescription(extensionSpec.getDescription());
        extension.setGithub(gitHubService.generateGitHub(extensionSpec.getGithub()));
        extension.setTags(tagService.generateTags(extensionSpec.getTags()));

        return new ExtensionDTO(extension);
    }

    @Override
    public void delete(int id, int userId) {
        Extension extension = extensionRepository.findById(id);
        String role = userRepository.findById(userId).getRole();
        if (userId == extension.getOwner().getId() || role.equals("ROLE_ADMIN")) {
            extensionRepository.delete(id);
        }
    }


    @Override
    public PageDTO<ExtensionDTO> findAll(String name, String orderBy, Integer page, Integer perPage) {

        if (page < 1) {
            throw new InvalidParameterException("\"page\" should be a positive number.");
        }

        if (perPage < 1) {
            throw new InvalidParameterException("\"perPage\" should be a positive number.");
        }

        if (name == null) {
            name = "";
        }

        if (orderBy == null) {
            orderBy = "date";
        }

        if (page == null) {
            page = 1;
        }

        if (perPage == null || perPage < 1) {
            perPage = 10;
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
        return new ExtensionDTO(extensionRepository.update(extension));
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

    private List<ExtensionDTO> generateExtensionDTOList(List<Extension> extensions) {
        List<ExtensionDTO> extensionsDTO = extensions
                .stream()
                .map(ExtensionDTO::new)
                .collect(Collectors.toList());

        return extensionsDTO;
    }
}
