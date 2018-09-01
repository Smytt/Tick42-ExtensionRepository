package com.tick42.quicksilver.services;

import com.tick42.quicksilver.models.DTO.ExtensionDTO;
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
public class ExtensionsServiceImpl implements ExtensionService {

    private final ExtensionRepository extensionRepository;
    private final TagService tagService;
    private final GitHubService gitHubService;
    private JwtValidator validator;
    private UserRepository userRepository;

    @Autowired
    public ExtensionsServiceImpl(ExtensionRepository extensionRepository, TagService tagService,
                                 GitHubService gitHubService, JwtValidator jwtValidator, UserRepository userRepository) {
        this.extensionRepository = extensionRepository;
        this.tagService = tagService;
        this.gitHubService = gitHubService;
        this.validator = jwtValidator;
        this.userRepository = userRepository;
    }

    @Override
    public ExtensionDTO create(ExtensionSpec extensionSpec) {

        Extension extension = new Extension(extensionSpec);

        User mockUser = new User();
        mockUser.setId(3);

        extension.setIsPending(true);
        extension.setOwner(mockUser);
        extension.setUploadDate(new Date());
        extension.setGithub(gitHubService.generateGitHub(extensionSpec.getGithub()));
        extension.setTags(tagService.generateTags(extensionSpec.getTags()));

        return new ExtensionDTO(extensionRepository.create(extension));
    }

    @Override
    public ExtensionDTO findById(int id) {
        Extension extension = extensionRepository.findById(id);
        ExtensionDTO extensionDTO = new ExtensionDTO(extension);
        return extensionDTO;
    }

    @Override
    public void delete(int id) {
        extensionRepository.delete(id);
    }

    @Override
    public List<ExtensionDTO> findByName(String name) {
        List<Extension> extensions = extensionRepository.findByName(name);
        return createDTO(extensions);
    }


    @Override
    public List<ExtensionDTO> findAll(String orderBy, Integer page, Integer perPage) {
        List<Extension> extensions = new ArrayList<>();
        switch (orderBy) {
            case "date":
                extensions = extensionRepository.findAllByDate(page, perPage); break;
            case "commits":
                extensions = extensionRepository.findAllByCommit(page, perPage); break;
            case "name":
                extensions = extensionRepository.findAllByName(page, perPage); break;
            case "downloads":
                extensions = extensionRepository.findAllByDownloads(page, perPage); break;
            default:
                extensions = extensionRepository.findAllByDate(page, perPage); break;
        }
        return createDTO(extensions);
    }

    @Override
    public List<ExtensionDTO> findFeatured() {
        List<Extension> extensions = extensionRepository.findFeatured();
        return createDTO(extensions);
    }

    @Override
    public List<ExtensionDTO> findByTag(String tagName) {
        List<Extension> extensions = tagService.findByName(tagName).getExtensions();
        return createDTO(extensions);
    }

    @Override
    public void approveExtension(int id) {
        Extension extension = extensionRepository.findById(id);
        extension.setIsPending(false);
        extensionRepository.update(extension);
    }

    @Override
    public void changeFeaturedState(int id, String state) {
        Extension extension = extensionRepository.findById(id);
        if (extension.getIsFeatured()) {
            extension.setIsFeatured(false);
            extensionRepository.update(extension);
        } else {
            extension.setIsFeatured(true);
            extensionRepository.update(extension);
        }
    }

    @Override
    public List<ExtensionDTO> findUserExtensions(int id) {
        User user = userRepository.findById(id);
        List<Extension> extensions = user.getExtensions();
        return createDTO(extensions);
    }

    private List<ExtensionDTO> createDTO(List<Extension> extensions) {
        List<ExtensionDTO> extensionsDTO = extensions
                .stream()
                .map(ExtensionDTO::new)
                .collect(Collectors.toList());

        return extensionsDTO;
    }
}
