package com.tick42.quicksilver.repositories.base;

import com.tick42.quicksilver.models.Extension;

import java.util.List;

public interface ExtensionRepository extends GenericRepository<Extension> {

    List<Extension> findFeatured();

    List<Extension> findAll();

    List<Extension> findAllByDate(String name, Integer page, Integer perPage);

    List<Extension> findAllByCommit(String name, Integer page, Integer perPage);

    List<Extension> findAllByName(String name, Integer page, Integer perPage);

    List<Extension> findAllByDownloads(String name, Integer page, Integer perPage);

    Long getTotalResults(String name);
}
