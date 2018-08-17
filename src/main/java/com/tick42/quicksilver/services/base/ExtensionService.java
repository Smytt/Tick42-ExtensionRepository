package com.tick42.quicksilver.services.base;


import com.tick42.quicksilver.models.Extension;

public interface ExtensionService {

    void create(Extension model);

    Extension findById(int id);

    void delete(int id);
}
