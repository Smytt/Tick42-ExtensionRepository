package com.tick42.quicksilver.services.base;


import com.tick42.quicksilver.models.DTO.ExtensionDTO;
import com.tick42.quicksilver.models.DTO.PageDTO;
import com.tick42.quicksilver.models.Spec.ExtensionSpec;

import java.util.List;

public interface ExtensionService {

    ExtensionDTO create(ExtensionSpec model, int id);

    ExtensionDTO findById(int id);

    ExtensionDTO update(int extensionId, ExtensionSpec extension, int userId);

    void delete(int id, int userId);

    PageDTO<ExtensionDTO> findAll(String name, String orderBy, Integer page, Integer perPage);

    List<ExtensionDTO> findFeatured();

    ExtensionDTO setPublishedState(int id, String newState);

    ExtensionDTO setFeaturedState(int id, String newState);

    List<ExtensionDTO> findPending();
}
