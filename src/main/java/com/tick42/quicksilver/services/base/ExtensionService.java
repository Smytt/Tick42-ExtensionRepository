package com.tick42.quicksilver.services.base;


import com.tick42.quicksilver.models.DTO.ExtensionDTO;
import com.tick42.quicksilver.models.DTO.PageDTO;
import com.tick42.quicksilver.models.Spec.ExtensionSpec;

import java.util.List;

public interface ExtensionService {

    ExtensionDTO create(ExtensionSpec model);

    ExtensionDTO findById(int id);

    void delete(int id);

    List<ExtensionDTO> findByName(String searchQuery);

    PageDTO<ExtensionDTO> findAll(String name, String orderBy, Integer page, Integer perPage);

    List<ExtensionDTO> findFeatured();

    List<ExtensionDTO> findUserExtensions(int id);
//
//    List<Extension> sortByUploadDate();
//
//    List<Extension> sortByMostDownloads();
//
//    List<Extension> sortByCommitDate();

    List<ExtensionDTO> findByTag(String tagName);

    void approveExtension(int id);

    void changeFeaturedState(int id, String newState);
}
