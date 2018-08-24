package com.tick42.quicksilver.services.base;


import com.tick42.quicksilver.models.Extension;

import java.util.List;

public interface ExtensionService {

    Extension create(Extension model);

    Extension findById(int id);

    void delete(int id);

    List<Extension> findByName(String searchQuery);

    List<Extension> findAll();

    List<Extension> findTopMostDownloaded(int count);

    List<Extension> findMostRecentUploads(int count);

    List<Extension> findFeatured(int count);

    List<Extension> sortByUploadDate();

    List<Extension> sortByMostDownloads();

    List<Extension> sortByCommitDate();

    List<Extension> findByTag(String tagName);

    void approveExtension(int id);

    void changeFeaturedState(int id);

    void updateExtensionDetails();
}
