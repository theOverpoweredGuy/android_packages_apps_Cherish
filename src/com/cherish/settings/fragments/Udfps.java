/*
 * Copyright (C) 2019-2022 The CherishOS Projects
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cherish.settings.fragments;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.hardware.fingerprint.FingerprintManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;

import androidx.preference.ListPreference;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreference;

import com.android.internal.logging.nano.MetricsProto;
import com.android.internal.util.cherish.CherishUtils;
import com.android.internal.util.cherish.udfps.UdfpsUtils;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexable;

@SearchIndexable(forTarget = SearchIndexable.ALL & ~SearchIndexable.ARC)
public class Udfps extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {

    private static final String UDFPS_ICON_PICKER = "udfps_icon_picker";
    private static final String UDFPS_ANIM_PREVIEW = "udfps_recognizing_animation_preview";
        
    private Preference mUdfpsIconPicker;
    private Preference mUdfpsAnimPreview;

    private static final String UDFPS_CATEGORY = "udfps_category";

    private PreferenceCategory mUdfpsCategory;


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.cherish_settings_udfps);

        ContentResolver resolver = getActivity().getContentResolver();
        final PreferenceScreen prefSet = getPreferenceScreen();
        Resources resources = getResources();

        mUdfpsCategory = findPreference(UDFPS_CATEGORY);
    if (!UdfpsUtils.hasUdfpsSupport(getContext())) {
        prefSet.removePreference(mUdfpsCategory);
    }

        final boolean udfpsResPkgInstalled = CherishUtils.isPackageInstalled(getContext(),
                "com.cherish.udfps.resources");
        mUdfpsIconPicker = findPreference(UDFPS_ICON_PICKER);
        mUdfpsAnimPreview = findPreference(UDFPS_ANIM_PREVIEW);
        if (!udfpsResPkgInstalled) {
            prefSet.removePreference(mUdfpsIconPicker);
            prefSet.removePreference(mUdfpsAnimPreview);
        }

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        ContentResolver resolver = getActivity().getContentResolver();
        return false;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.CHERISH_SETTINGS;
    }

    /**
     * For Search.
     */
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.cherish_settings_udfps);
}
