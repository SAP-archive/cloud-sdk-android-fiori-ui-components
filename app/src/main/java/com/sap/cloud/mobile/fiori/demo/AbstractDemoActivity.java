package com.sap.cloud.mobile.fiori.demo;

import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Super class of demo activities.
 * By extending this class, the activity title will be taken care of.
 */

public abstract class AbstractDemoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CharSequence label = null;
        try {
            label = getPackageManager().getActivityInfo(getComponentName(), 0).nonLocalizedLabel;
            if (label != null) {
                String sLabel = label.toString();
                int i = sLabel.lastIndexOf('/');
                int id = getResources().getIdentifier(sLabel.substring(i + 1), "string",
                        getPackageName());
                if (id != 0) {
                    setTitle(getResources().getString(id));
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Returns whether the current device is tablet
     */
    public boolean isTablet() {
        Configuration config = this.getResources().getConfiguration();
        //only show description for tablet
        return (config.smallestScreenWidthDp >= 600);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();  return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
