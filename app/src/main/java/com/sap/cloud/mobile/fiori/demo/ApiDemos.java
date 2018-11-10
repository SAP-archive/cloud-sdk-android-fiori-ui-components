/*
 * Modifications Copyright © 2016 - 2017 SAP SE or an SAP affiliate company. All rights reserved.
 * Copyright (C) 2007 The Android Open Source Project
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

package com.sap.cloud.mobile.fiori.demo;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.sap.cloud.mobile.fiori.demo.onboarding.OnboardingSettingsActivity;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.LAYOUT_DIRECTION_RTL;

/**
 * An activity that lists demo activities by "label"s declared in AndroidManifest.xml.
 * Labels can have multiple levels separated by "/".
 * Modified from the Android ApiDemos class to add description for demo activities and categories.
 * Now the segments of the "label" become keys that will be used to look up the localizable
 * label value. The description key to be looked up will be &lt;label&gt;_desc.
 *
 */
public class ApiDemos extends AppCompatActivity{
    public static final String COM_SAP_CLOUD_MOBILE_FIORI_DEMO_PATH =
            "com.sap.cloud.mobile.fiori.demo.Path";
    public static final String CATEGORY_SAMPLE_CODE = "sap.fiori.intent.category.SAMPLE_CODE";
    /**
     * This field should be made private, so it is hidden from the SDK.
     * {@hide}
     */
    protected ListAdapter mAdapter;
    /**
     * This field should be made private, so it is hidden from the SDK.
     * {@hide}
     */
    protected ListView mList;
    private boolean isOnboarding;

    @NonNull
    private Handler mHandler = new Handler();
    private boolean mFinishedStart = false;

    @NonNull
    private Runnable mRequestFocus = new Runnable() {
        public void run() {
            mList.focusableViewAvailable(mList);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String path = intent.getStringExtra(COM_SAP_CLOUD_MOBILE_FIORI_DEMO_PATH);

        if (path == null) {
            path = "";
        }

        setContentView(R.layout.list_activity);
        mList = (ListView)findViewById(R.id.list);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        boolean canGoBack = false;
        if (!TextUtils.isEmpty(path)){
            canGoBack = true;
            String currentLevelKey = path;
            int i = path.lastIndexOf('/');
            if (i>0){
                currentLevelKey = path.substring(i+1);
            }
            String packageName = getPackageName();
            Resources resources = getResources();
            int labelId = resources.getIdentifier(currentLevelKey, "string", packageName);
            if (labelId != 0){
                String label = resources.getString(labelId);
                myToolbar.setTitle(resources.getString(R.string.demo_app_name) + ": " + label);
                if (label.equals("Onboarding") ){
                    isOnboarding = true;
                }else{
                    isOnboarding = false;
                }
            }
        }
        setSupportActionBar(myToolbar);
        if (canGoBack){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        Configuration config = getResources().getConfiguration();
        int resId = R.layout.list_item;
        if (config.getLayoutDirection() ==  LAYOUT_DIRECTION_RTL){
            resId = R.layout.list_item_rtl;
        }
        setListAdapter(new SimpleAdapter(this, getData(path),
                resId, new String[] { "title", "description" },
                new int[] { android.R.id.text1, android.R.id.text2 }));
        mList.setTextFilterEnabled(true);

    }

    @NonNull
    protected List<Map<String, Object>> getData(@NonNull String prefix) {
        List<Map<String, Object>> myData = new ArrayList<Map<String, Object>>();

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(CATEGORY_SAMPLE_CODE);

        PackageManager pm = getPackageManager();
        List<ResolveInfo> list = pm.queryIntentActivities(mainIntent, 0);

        if (null == list)
            return myData;

        String[] prefixPath;
        String prefixWithSlash = prefix;
        
        if (prefix.equals("")) {
            prefixPath = null;
        } else {
            prefixPath = prefix.split("/");
            prefixWithSlash = prefix + "/";
        }
        
        int len = list.size();
        
        Map<String, Boolean> entries = new HashMap<String, Boolean>();
        Resources resources = getResources();
        String packageName = getPackageName();
        for (int i = 0; i < len; i++) {
            ResolveInfo info = list.get(i);
            CharSequence labelSeq = info.loadLabel(pm);
            String label = labelSeq != null
                    ? labelSeq.toString()
                    : info.activityInfo.name;
            
            if (prefixWithSlash.length() == 0 || label.startsWith(prefixWithSlash)) {
                
                String[] labelPath = label.split("/");

                String nextLabelKey = prefixPath == null ? labelPath[0] : labelPath[prefixPath.length];
                int labelId = resources.getIdentifier(nextLabelKey, "string", packageName);
                int descId = resources.getIdentifier(nextLabelKey + "_desc", "string", packageName);
                String nextLabel = nextLabelKey;
                String desc = "";
                if (labelId == 0){
                    Log.w("ApiDemos", "Resource not found: " + nextLabelKey);
                }else{
                    nextLabel = resources.getString(labelId);
                }

                if (labelId == 0){
                    Log.w("ApiDemos", "Resource not found: " + nextLabelKey + "_desc");
                }else{
                    desc = resources.getString(descId);
                }

                if ((prefixPath != null ? prefixPath.length : 0) == labelPath.length - 1) {
                    addItem(myData, nextLabel, desc, activityIntent(
                            info.activityInfo.applicationInfo.packageName,
                            info.activityInfo.name));
                } else {
                    if (entries.get(nextLabelKey) == null) {
                        addItem(myData, nextLabel, desc, browseIntent(prefix.equals("") ? nextLabelKey : prefix + "/" + nextLabelKey));
                        entries.put(nextLabelKey, true);
                    }
                }
            }
        }

        Collections.sort(myData, sDisplayNameComparator);
        
        return myData;
    }

    private final static Comparator<Map<String, Object>> sDisplayNameComparator =
        new Comparator<Map<String, Object>>() {
        private final Collator collator = Collator.getInstance();

        public int compare(Map<String, Object> map1, Map<String, Object> map2) {
            return collator.compare(map1.get("title"), map2.get("title"));
        }
    };

    @NonNull
    protected Intent activityIntent(@NonNull String pkg, @NonNull String componentName) {
        Intent result = new Intent();
        result.setClassName(pkg, componentName);
        return result;
    }
    
    @NonNull
    protected Intent browseIntent(String path) {
        Intent result = new Intent();
        result.setClass(this, ApiDemos.class);
        result.putExtra(COM_SAP_CLOUD_MOBILE_FIORI_DEMO_PATH, path);
        return result;
    }

    protected void addItem(@NonNull List<Map<String, Object>> data, String name, String desc, Intent intent) {
        Map<String, Object> temp = new HashMap<String, Object>();
        temp.put("title", name);
        temp.put("description", desc);
        temp.put("intent", intent);
        data.add(temp);
    }

    @SuppressWarnings("unchecked")
    protected void onListItemClick(@NonNull ListView l, View v, int position, long id) {
        Map<String, Object> map = (Map<String, Object>)l.getItemAtPosition(position);

        Intent intent = new Intent((Intent) map.get("intent"));
        intent.addCategory(CATEGORY_SAMPLE_CODE);
        intent.putExtras(getIntent());
        startActivity(intent);
    }

    //from ListActivity
    /**
     * @see Activity#onDestroy()
     */
    @Override
    protected void onDestroy() {
        mHandler.removeCallbacks(mRequestFocus);
        super.onDestroy();
    }

    /**
     * Updates the screen state (current list and other views) when the
     * content changes.
     *
     * @see Activity#onContentChanged()
     */
    @Override
    public void onContentChanged() {
        super.onContentChanged();
        mList = (ListView)findViewById(R.id.list);
        mList.setOnItemClickListener(mOnClickListener);
        if (mFinishedStart) {
            setListAdapter(mAdapter);
        }
        mHandler.post(mRequestFocus);
        mFinishedStart = true;
    }

    /**
     * Provide the cursor for the list view.
     */
    public void setListAdapter(ListAdapter adapter) {
        synchronized (this) {
            mAdapter = adapter;
            mList.setAdapter(adapter);
        }
    }

    @NonNull
    private AdapterView.OnItemClickListener mOnClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View v, int position, long id)
        {
            onListItemClick((ListView)parent, v, position, id);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isOnboarding) {
            getMenuInflater().inflate(R.menu.onboarding_setting, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings_icon) {
            Intent modifySettings = new Intent(ApiDemos.this,
                    OnboardingSettingsActivity.class);
            startActivity(modifySettings);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
