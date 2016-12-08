package com.itheima.mvplayer.ui.activity;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itheima.mvplayer.R;

public class SettingsActivity extends BaseActivity {
    public static final String TAG = "SettingsActivity";

    @Override
    public int getLayoutResID() {
        return R.layout.activity_settings;
    }

    public static class SettingsFragment extends PreferenceFragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            addPreferencesFromResource(R.xml.settings_prefs);
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }
}
