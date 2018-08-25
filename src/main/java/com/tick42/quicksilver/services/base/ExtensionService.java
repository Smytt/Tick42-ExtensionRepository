package com.tick42.quicksilver.services.base;


import com.tick42.quicksilver.models.DTO.ExtensionDTO;
import com.tick42.quicksilver.models.Spec.ExtensionSpec;

import java.util.List;

public interface ExtensionService {

    ExtensionDTO create(ExtensionSpec model);

    ExtensionDTO findById(int id);

    void delete(int id);

    List<ExtensionDTO> findByName(String searchQuery);

    List<ExtensionDTO> findAll();

    List<ExtensionDTO> findTopMostDownloaded(int count);

    List<ExtensionDTO> findMostRecentUploads(int count);

    List<ExtensionDTO> findFeatured(int count);
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
