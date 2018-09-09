package com.tick42.quicksilver.repositories.base;

import com.tick42.quicksilver.models.Settings;

public interface SettingsRepository {
    Settings get();

    Settings set(Settings settings);
}
