package com.sap.cloud.mobile.fiori.demo.onboarding;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;

import com.sap.cloud.mobile.fiori.demo.R;

/**
 * A login screen that offers login via email/password.
 */
public class DummyExternalDevelopersActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy_external);

        findViewById(R.id.dummy_test_button).setOnClickListener(view1 -> {
            setResult(Activity.RESULT_OK);
            finish();
        });
    }
}

