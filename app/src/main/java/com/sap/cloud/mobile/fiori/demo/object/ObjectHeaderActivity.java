package com.sap.cloud.mobile.fiori.demo.object;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.TypefaceSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.sap.cloud.mobile.fiori.demo.AbstractDemoActivity;
import com.sap.cloud.mobile.fiori.demo.R;
import com.sap.cloud.mobile.fiori.demo.common.DemoSetting;
import com.sap.cloud.mobile.fiori.demo.common.DemoSettingAdapter;
import com.sap.cloud.mobile.fiori.object.ObjectHeader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ObjectHeaderActivity extends AbstractDemoActivity {
    private static final String TAG = "BaseObjectHeaderActivity";
    private ObjectHeader mObjectHeader;
    @NonNull
    private ArrayList<DemoSetting> mSettings = new ArrayList<>();
    @Nullable
    private HashMap<Integer, Boolean> mStates = new HashMap<>();
    private static final String STATE_SETTINGS = "settings";

    @VisibleForTesting
    public static final int ID_ANALYTICS = 1;
    @VisibleForTesting
    public static final int ID_DESCRIPTION = 2;
    @VisibleForTesting
    public static final int ID_TAGS = 3;
    @VisibleForTesting
    public static final int ID_BODY = 4;
    @VisibleForTesting
    public static final int ID_FOOTNOTE = 5;
    @VisibleForTesting
    public static final int ID_IMAGE = 6;
    @VisibleForTesting
    public static final int ID_SUB_HEADLINE = 7;
    @VisibleForTesting
    public static final int ID_STATUS = 8;
    @VisibleForTesting
    public static final int ID_CHARACTER_IMAGE = 9;
    private LinearLayout mSettingsView;
    private DemoSettingAdapter mDemoSettingAdapter;

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.content_base_object_header_appbar);
        mObjectHeader = findViewById(R.id.objectHeader);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        myToolbar.setTitle(mObjectHeader.getSubheadline());
        setSupportActionBar(myToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (savedInstanceState != null) {
            mStates = (HashMap<Integer, Boolean>) savedInstanceState.getSerializable(
                    STATE_SETTINGS);
        } else {
            mStates = createDefaultStates();
        }
        createSettings();
        configureObjectHeader();
        //TODO replace with preference. see ProfileHeaderActivity
        //Here we're re-using the list adapter for the linear layout
        mDemoSettingAdapter = new DemoSettingAdapter(this, mSettings);
        mSettingsView = findViewById(R.id.linearLayout);
        for(int i=0;i<mSettings.size();i++){
            final View child = mDemoSettingAdapter.getView(i, null, mSettingsView);
            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(@NonNull View v) {
                    onListItemClick(v);
                }
            });
            mSettingsView.addView(child);
        }
    }

    private void toggleSetting(int position){
        View view = mSettingsView.getChildAt(position);
        onListItemClick(view);
    }

    private void onListItemClick(View view) {
        Switch switchView = view.findViewById(R.id.switch1);
        switchView.toggle();
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.objectheader_setting, menu);
        for (int i=0; i<mSettings.size();i++) {
            DemoSetting s = mSettings.get(i);
            int menuId = 0;
            switch(s.getId()){
                case ID_IMAGE:
                    menuId = R.id.menu_image;
                    break;
                case ID_CHARACTER_IMAGE:
                    menuId = R.id.menu_character_image;
                    break;
                case ID_SUB_HEADLINE:
                    menuId = R.id.menu_sub_headline;
                    break;
                case ID_ANALYTICS:
                    menuId = R.id.menu_analytics;
                    break;
                case ID_DESCRIPTION:
                    menuId = R.id.menu_description;
                    break;
                case ID_TAGS:
                    menuId = R.id.menu_tags;
                    break;
                case ID_BODY:
                    menuId = R.id.menu_body;
                    break;
                case ID_FOOTNOTE:
                    menuId = R.id.menu_footnote;
                    break;
                case ID_STATUS:
                    menuId = R.id.menu_status;
                default:
                    break;
            }
            if (menuId != 0){
                menu.findItem(menuId).setChecked(s.isChecked());
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle item selection
        int settingPosition = -1;
        switch (item.getItemId()) {
            case R.id.menu_image:
                settingPosition = getSettingPosition(ID_IMAGE);
                break;
            case R.id.menu_character_image:
                settingPosition = getSettingPosition(ID_CHARACTER_IMAGE);
                break;
            case R.id.menu_sub_headline:
                settingPosition = getSettingPosition(ID_SUB_HEADLINE);
                break;
            case R.id.menu_analytics:
                settingPosition = getSettingPosition(ID_ANALYTICS);
                break;
            case R.id.menu_description:
                settingPosition = getSettingPosition(ID_DESCRIPTION);
                break;
            case R.id.menu_tags:
                settingPosition = getSettingPosition(ID_TAGS);
                break;
            case R.id.menu_body:
                settingPosition = getSettingPosition(ID_BODY);
                break;
            case R.id.menu_footnote:
                settingPosition = getSettingPosition(ID_FOOTNOTE);
                break;
            case R.id.menu_status:
                settingPosition = getSettingPosition(ID_STATUS);
                break;
            default:
                CharSequence name = item.getTitle();
                Toast toast = Toast.makeText(this,
                        "Clicked: " + name,
                        Toast.LENGTH_SHORT);
                toast.show();
                return true;
        }
        boolean isChecked = !item.isChecked();
        item.setChecked(isChecked);
        toggleSetting(settingPosition);
        return true;
    }

    private int getSettingPosition(int id){
        for(int i=0;i<mSettings.size();i++){
            if (mSettings.get(i).getId() == id){
                return i;
            }
        }
        return -1;
    }

    @NonNull
    private HashMap<Integer, Boolean> createDefaultStates() {
        HashMap<Integer, Boolean> states = new HashMap<>();
        states.put(ID_IMAGE, true);
        states.put(ID_CHARACTER_IMAGE, true);
        states.put(ID_SUB_HEADLINE, true);
        states.put(ID_ANALYTICS, true);
        states.put(ID_DESCRIPTION, true);
        states.put(ID_TAGS, true);
        states.put(ID_BODY, true);
        states.put(ID_FOOTNOTE, true);
        states.put(ID_STATUS, true);
        return states;
    }

    private void configureDetailText(View rootView) {
        TextView textView = rootView.findViewById( R.id.object_header_detail_time);
        Spannable spannable = new SpannableString(getResources().getString(R.string.object_header_detail_time));
        //10h 59m
        //span should not be reused
        spannable.setSpan( new TypefaceSpan("f72_regular"), 2, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );
        spannable.setSpan( new TypefaceSpan("f72_regular"), 6, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );

        spannable.setSpan( new AbsoluteSizeSpan(24, true), 2, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );
        spannable.setSpan( new AbsoluteSizeSpan(24, true), 6, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );
        textView.setText(spannable, TextView.BufferType.SPANNABLE);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        for (DemoSetting s : mSettings) {
            mStates.put(s.getId(), s.isChecked());
        }
        outState.putSerializable(STATE_SETTINGS, mStates);
    }

    private void createSettings() {
        mSettings = new ArrayList<>();
        mSettings.add(createSetting(ID_IMAGE, R.string.object_header_setting_image,
                R.string.object_header_setting_image_desc));
        mSettings.add(createSetting(ID_CHARACTER_IMAGE, R.string.object_header_setting_character_image,
                R.string.object_header_setting_character_image_desc));
        mSettings.add(createSetting(ID_SUB_HEADLINE, R.string.object_header_setting_sub_headline,
                R.string.object_header_setting_sub_headline_desc));
        mSettings.add(createSetting(ID_ANALYTICS, R.string.object_header_setting_analytics,
                R.string.object_header_setting_analytics_desc));
        mSettings.add(createSetting(ID_DESCRIPTION, R.string.object_header_setting_description,
                R.string.object_header_setting_description_desc));
        mSettings.add(createSetting(ID_TAGS, R.string.object_header_setting_tags,
                R.string.object_header_setting_tags_desc));
        mSettings.add(createSetting(ID_BODY, R.string.object_header_setting_body,
                R.string.object_header_setting_body_desc));
        mSettings.add(createSetting(ID_FOOTNOTE, R.string.object_header_setting_footnote,
                R.string.object_header_setting_footnote_desc));
        mSettings.add(createSetting(ID_STATUS, R.string.object_header_setting_status,
                R.string.object_header_setting_status_desc));
    }


    @Nullable
    private DemoSetting createSetting(final int id, int nameResId, int descResId) {
        Boolean state = mStates.get(id);
        final DemoSetting s = new DemoSetting(id, nameResId,
                descResId, state,
                null);
        s.setListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                s.setChecked(isChecked);
                mStates.put(id, isChecked);
                //to recreate menu so that states are in sync
                invalidateOptionsMenu();
                configureObjectHeader(id, isChecked);
            }
        });
        return s;
    }

    protected void configureObjectHeader() {
        Iterator it = mStates.keySet().iterator();
        while (it.hasNext()) {
            Integer id = (Integer) it.next();
            configureObjectHeader(id, mStates.get(id));
        }
    }

    protected void configureObjectHeader(int id, boolean isChecked) {
        switch (id) {
            case ID_IMAGE:
                if (isChecked) {
                    mObjectHeader.setDetailImage(R.drawable.object_placeholder);
                } else {
                    mObjectHeader.setDetailImage(null);
                }
                break;
            case ID_CHARACTER_IMAGE:
                if (isChecked) {
                    mObjectHeader.setDetailImageCharacter("C");
                } else {
                    mObjectHeader.setDetailImageCharacter(null);
                }
                break;
            case ID_SUB_HEADLINE:
                if (isChecked) {
                    mObjectHeader.setSubheadline(R.string.object_header_subheadline);
                } else {
                    mObjectHeader.setSubheadline(null);
                }
                break;
            case ID_ANALYTICS:
                if (isChecked) {
                    if (mObjectHeader.getDetailView() == null) {
                        View analyticsView = View.inflate(ObjectHeaderActivity.this,
                                R.layout.content_object_header_detail, null);
                        configureDetailText(analyticsView);
                        mObjectHeader.setDetailView(analyticsView);
                    }else{
                        configureDetailText(mObjectHeader.getDetailView());
                    }
                } else {
                    mObjectHeader.setDetailView(null);
                }
                break;
            case ID_DESCRIPTION:
                if (isChecked) {
                    mObjectHeader.setDescription(R.string.object_header_description);
                } else {
                    mObjectHeader.setDescription(null);
                }
                break;
            case ID_TAGS:
                if (isChecked) {
                    mObjectHeader.setTag(R.string.object_header_tag1, 0);
                    mObjectHeader.setTag(R.string.object_header_tag2, 1);
                    mObjectHeader.setTag(R.string.object_header_tag3, 2);
                } else {
                    mObjectHeader.setTag(null, 2);
                    mObjectHeader.setTag(null, 1);
                    mObjectHeader.setTag(null, 0);
                }
                break;
            case ID_BODY:
                if (isChecked) {
                    mObjectHeader.setBody(R.string.object_header_body);
                } else {
                    mObjectHeader.setBody(null);
                }
                break;
            case ID_FOOTNOTE:
                if (isChecked) {
                    mObjectHeader.setFootnote(R.string.object_header_footnote);
                } else {
                    mObjectHeader.setFootnote(null);
                }
                break;
            case ID_STATUS:
                mObjectHeader.clearStatuses();
                if (isChecked) {
                    mObjectHeader.setStatus(R.drawable.ic_warning_black_24dp, 0, R.string.error);
                    mObjectHeader.setStatusColor(getResources().getColor(R.color.sap_ui_button_reject_border_color, getTheme()), 0);
                    mObjectHeader.setStatus(R.string.object_header_status, 1);
                    mObjectHeader.setStatusColor(getResources().getColor(R.color.sap_ui_button_reject_border_color, getTheme()), 1);
                }
                break;
            default:
                break;
        }
        mObjectHeader.invalidate();
        mObjectHeader.requestLayout();
    }

}
