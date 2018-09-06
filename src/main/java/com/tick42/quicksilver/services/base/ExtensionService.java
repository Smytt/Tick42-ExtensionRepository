package com.tick42.quicksilver.services.base;


import com.tick42.quicksilver.models.DTO.ExtensionDTO;
import com.tick42.quicksilver.models.DTO.PageDTO;
import com.tick42.quicksilver.models.Extension;
import com.tick42.quicksilver.models.Spec.ExtensionSpec;
import com.tick42.quicksilver.models.User;

import java.util.List;

public interface ExtensionService {

    ExtensionDTO create(ExtensionSpec model, int id);

    ExtensionDTO findById(int id, User user);

    ExtensionDTO update(int extensionId, ExtensionSpec extension, int userId);

    void delete(int id, int userId);

    PageDTO<ExtensionDTO> findAll(String name, String orderBy, Integer page, Integer perPage);

    List<ExtensionDTO> findFeatured();

    ExtensionDTO setPublishedState(int id, String newState);

    ExtensionDTO setFeaturedState(int id, String newState);

    List<ExtensionDTO> findPending();

    List<ExtensionDTO> generateExtensionDTOList(List<Extension> extensions);

    ExtensionDTO fetchGitHub(int extensionId, int userId);

    ExtensionDTO increaseDownloadCount(int id, User user);

    int rate(int extensionId, int rating, int userId);

    int userRatingForExtension(int extensionId, int userId);
}
