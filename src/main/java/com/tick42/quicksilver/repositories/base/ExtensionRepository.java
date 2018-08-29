package com.tick42.quicksilver.repositories.base;

import com.tick42.quicksilver.models.Extension;

import java.util.List;

public interface ExtensionRepository extends GenericRepository<Extension> {
    List<Extension> findByName(String searchQuery);

    List<Extension> findFeatured();

    List<Extension> findAll();

    List<Extension> findAllByDate(Integer page, Integer perPage);

    List<Extension> findAllByCommit(Integer page, Integer perPage);

    List<Extension> findAllByName(Integer page, Integer perPage);

    List<Extension> findAllByDownloads(Integer page, Integer perPage);
}
