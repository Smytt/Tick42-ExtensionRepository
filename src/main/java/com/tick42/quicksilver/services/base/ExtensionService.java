package com.tick42.quicksilver.services.base;


import com.tick42.quicksilver.models.DTO.ExtensionDTO;
import com.tick42.quicksilver.models.DTO.PageDTO;
import com.tick42.quicksilver.models.Spec.ExtensionSpec;

import java.util.List;

public interface ExtensionService {

    ExtensionDTO create(ExtensionSpec model, int id);

    ExtensionDTO findById(int id);

    void delete(int id);

    PageDTO<ExtensionDTO> findAll(String name, String orderBy, Integer page, Integer perPage);

    List<ExtensionDTO> findFeatured();

    void approveExtension(int id);

    void changeFeaturedState(int id, String newState);

    List<ExtensionDTO> findPending();
}
