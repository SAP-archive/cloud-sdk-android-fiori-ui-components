package com.sap.cloud.mobile.fiori.demo.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.sap.cloud.mobile.fiori.demo.ApiDemos;
import com.sap.cloud.mobile.fiori.demo.R;

/**
 * After onboarding finishes, this screen will come up.
 */
public class AfterOnboardingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_onboarding);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent onboardingListIntent = new Intent();
                onboardingListIntent.setClass(this, ApiDemos.class);
                onboardingListIntent.putExtra(ApiDemos.COM_SAP_CLOUD_MOBILE_FIORI_DEMO_PATH, "title_onboarding_test");
                onboardingListIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(onboardingListIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

