package com.tick42.quicksilver.repositories.base;

import com.tick42.quicksilver.models.Settings;

import java.util.List;

public interface SettingsRepository {
    Settings update(Settings settings);

    Settings findById(int id);
}
