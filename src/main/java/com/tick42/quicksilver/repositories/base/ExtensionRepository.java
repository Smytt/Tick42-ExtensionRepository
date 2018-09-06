package com.tick42.quicksilver.repositories.base;

import com.tick42.quicksilver.models.Extension;
import com.tick42.quicksilver.models.Rating;

import java.util.List;

public interface ExtensionRepository {

    Extension findById(int id);

    Extension create(Extension extension);

    Extension update(Extension extension);

    void delete(int id);

    List<Extension> findAll();

    List<Extension> findAllByDate(String name, Integer page, Integer perPage);

    List<Extension> findAllByCommit(String name, Integer page, Integer perPage);

    List<Extension> findAllByName(String name, Integer page, Integer perPage);

    List<Extension> findAllByDownloads(String name, Integer page, Integer perPage);

    Long getTotalResults(String name);

    List<Extension> findFeatured();

    List<Extension> findPending();
}
