package com.sap.cloud.mobile.fiori.demo.contact;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.sap.cloud.mobile.fiori.contact.ContactCell;
import com.sap.cloud.mobile.fiori.contact.ProfileHeader;
import com.sap.cloud.mobile.fiori.demo.AbstractDemoActivity;
import com.sap.cloud.mobile.fiori.demo.R;

import java.util.Arrays;
import java.util.List;

/**
 * Demos ProfileHeader usage.
 */

public class ProfileHeaderActivity extends AbstractDemoActivity implements
        SharedPreferences.OnSharedPreferenceChangeListener {
    public static final String PREF_KEY_IMAGE = "pref_profile_header_image";
    public static final String PREF_KEY_NAME = "pref_profile_header_name";
    public static final String PREF_KEY_TITLE = "pref_profile_header_title";
    public static final String PREF_KEY_ACTIONS = "pref_profile_header_actions";
    public static final String PREF_KEY_ACTION = "pref_profile_header_action";
    public static final String PREF_KEY_DESCRIPTION = "pref_profile_header_description";
    public static final String PREF_VALUE_DESC_NONE = "NONE";
    public static final String PREF_VALUE_DESC_SHORT = "SHORT";
    public static final String PREF_VALUE_DESC_LONG = "LONG";
    public static final String PREF_VALUE_ACTIONS = "4";
    private static final String STATE_SETTINGS = "settings";

    public static final ContactCell.ContactAction[] ACTIONS =
            {ContactCell.ContactAction.CALL, ContactCell.ContactAction.EMAIL,
                    ContactCell.ContactAction.VIDEO_CALL, ContactCell.ContactAction.MESSAGE,
                    ContactCell.ContactAction.DETAIL};

    private ProfileHeader mProfileHeader;
    private ProfileHeaderSettingsFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_profile_header);
        mProfileHeader = findViewById(R.id.profileHeader);
        mProfileHeader.getDetailImage().setTint(Color.WHITE);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        myToolbar.setTitle(R.string.profile_header_title);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        init(savedInstanceState);

        // Display the fragment as the main content.
        if (savedInstanceState != null) {
            List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
            if (fragmentList != null && fragmentList.size()>0){
                //retrieve fragment which has restored states
                mFragment = (ProfileHeaderSettingsFragment) fragmentList.get(0);
            }
        }
        if (mFragment == null){
            mFragment = new ProfileHeaderSettingsFragment();
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, mFragment)
                .commit();

    }

    private void init(Bundle savedInstanceState){
        SharedPreferences sharedPreferences = android.support.v7.preference.PreferenceManager.getDefaultSharedPreferences(this);
        if (savedInstanceState == null){
            sharedPreferences.edit().clear().apply();
        }
        android.support.v7.preference.PreferenceManager.setDefaultValues(this, R.xml.pref_profile_header, true);
        onSharedPreferenceChanged(sharedPreferences, PREF_KEY_IMAGE);
        onSharedPreferenceChanged(sharedPreferences, PREF_KEY_NAME);
        onSharedPreferenceChanged(sharedPreferences, PREF_KEY_TITLE);
        onSharedPreferenceChanged(sharedPreferences, PREF_KEY_ACTIONS);
        onSharedPreferenceChanged(sharedPreferences, PREF_KEY_DESCRIPTION);
        onSharedPreferenceChanged(sharedPreferences, PREF_KEY_ACTION);
    }

    @Override
    public void onSharedPreferenceChanged(@NonNull SharedPreferences sharedPreferences, @NonNull String key) {
        if (key.equals(PREF_KEY_IMAGE)) {
            if (sharedPreferences.getBoolean(key, true)){
                mProfileHeader.setDetailImage(R.drawable.portrait);
            }else{
                mProfileHeader.setDetailImage(R.drawable.ic_account_circle_black_24dp);
                mProfileHeader.getDetailImage().setTint(Color.WHITE);
            }
            mProfileHeader.setDetailImageDescription(R.string.profile_header_detail_image_desc);
        }else if (key.equals(PREF_KEY_NAME)) {
            if (sharedPreferences.getBoolean(key, false)){
                mProfileHeader.setHeadline(R.string.profile_header_headline_long);
                mProfileHeader.setHeadlineLines(2);
            }else{
                mProfileHeader.setHeadline(R.string.profile_header_headline);
                mProfileHeader.setHeadlineLines(1);
            }
        }else if (key.equals(PREF_KEY_TITLE)) {
            if (sharedPreferences.getBoolean(key, true)){
                mProfileHeader.setSubheadline(R.string.profile_header_subheadline);
            }else{
                mProfileHeader.setSubheadline(null);
            }
        }else if (key.equals(PREF_KEY_ACTIONS)) {
            int count = Integer.parseInt(sharedPreferences.getString(key, PREF_VALUE_ACTIONS));
            mProfileHeader.setContactActions(Arrays.copyOf(ACTIONS, count));
            View.OnClickListener l = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mProfileHeader.getContext(), R.string.not_implemented, Toast.LENGTH_SHORT).show();
                }
            };
            if (count > 0) {
                mProfileHeader.getContactActionView(0).setOnClickListener(l);
                mProfileHeader.getContactActionView(1).setEnabled(false);
                mProfileHeader.getContactActionView(1).setOnClickListener(null);
            }
            if (count >2) {
                mProfileHeader.getContactActionView(2).setOnClickListener(l);
            }
            if (count>3) {
                mProfileHeader.getContactActionView(3).setOnClickListener(l);
            }
            if (count>4) {
                mProfileHeader.getContactActionView(4).setOnClickListener(l);
            }

        }else if (key.equals(PREF_KEY_DESCRIPTION)){
            String value = sharedPreferences.getString(key, PREF_VALUE_DESC_LONG);
            if (value.equals(PREF_VALUE_DESC_NONE)){
                mProfileHeader.setDescriptionWidthPercent(0);
                mProfileHeader.setDescription(null);
            }else if (value.equals(PREF_VALUE_DESC_SHORT)){
                mProfileHeader.setLines(1);
                mProfileHeader.setDescriptionWidthPercent(0.6f);
                mProfileHeader.setDescription(R.string.profile_header_description_one);
            }else if (value.equals(PREF_VALUE_DESC_LONG)){
                mProfileHeader.setLines(2);
                mProfileHeader.setDescriptionWidthPercent(0.6f);
                mProfileHeader.setDescription(R.string.profile_header_description);
            }
        }else if (key.equals(PREF_KEY_ACTION)){
            if (sharedPreferences.getBoolean(key, true)){
                ToggleButton button = (ToggleButton)getLayoutInflater().inflate(R.layout.profile_action_button, null);
                button.setTextOn(getResources().getString(R.string.pref_profile_header_action_button_on));
                button.setTextOff(getResources().getString(R.string.pref_profile_header_action_button_off));
                button.setText(getResources().getString(R.string.pref_profile_header_action_button_off));
                button.setContentDescription(getResources().getString(R.string.profile_header_action_desc));
                mProfileHeader.setProfileActionView(button);
            }else{
                mProfileHeader.setProfileActionView(null);
            }
        }


        mProfileHeader.invalidate();
        mProfileHeader.requestLayout();
    }

}
