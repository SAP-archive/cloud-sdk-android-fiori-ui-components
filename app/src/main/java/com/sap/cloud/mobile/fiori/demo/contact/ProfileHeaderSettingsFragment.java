package com.sap.cloud.mobile.fiori.demo.contact;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sap.cloud.mobile.fiori.demo.R;

/**
 * Preferences for profile header activity
 */

public class ProfileHeaderSettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (savedInstanceState == null){
            //reset default values before loading it into UI
            sharedPreferences.edit().clear().apply();
        }
        PreferenceManager.setDefaultValues(getActivity(), R.xml.pref_profile_header, true);
        // Load the preferences from an XML resource
        setPreferencesFromResource(R.xml.pref_profile_header, rootKey);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //necessary when it's inside nested scroll view
        final RecyclerView lv = (RecyclerView) view.findViewById(android.support.v7.preference.R.id.recycler_view);
        if (lv != null)
            ViewCompat.setNestedScrollingEnabled(lv, true);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof SharedPreferences.OnSharedPreferenceChangeListener){
            getPreferenceScreen().getSharedPreferences()
                    .registerOnSharedPreferenceChangeListener((SharedPreferences.OnSharedPreferenceChangeListener)getActivity());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getActivity() instanceof SharedPreferences.OnSharedPreferenceChangeListener){
            getPreferenceScreen().getSharedPreferences()
                    .unregisterOnSharedPreferenceChangeListener((SharedPreferences.OnSharedPreferenceChangeListener)getActivity());
        }
    }
}
