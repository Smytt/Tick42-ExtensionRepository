package com.tick42.quicksilver.services.base;


import com.tick42.quicksilver.models.Extension;

import java.util.List;

public interface ExtensionService {

    Extension create(Extension model);

    Extension findById(int id);

    void delete(int id);

    List<Extension> findByName(String searchQuery);

    List<Extension> findAll();
}
