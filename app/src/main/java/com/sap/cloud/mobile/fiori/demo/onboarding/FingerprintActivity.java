package com.sap.cloud.mobile.fiori.demo.onboarding;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.sap.cloud.mobile.fiori.demo.DemoApplication;
import com.sap.cloud.mobile.fiori.demo.R;

/**
 * Created by i036160 on 2017. 10. 30..
 */

public class FingerprintActivity extends OnboardingActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            presenter.setOnboardingState(
                    (OnboardingPresenter.OnboardingState) savedInstanceState.getSerializable(
                            KEY_ONBOARDING_STATE));
        } else {
            presenter.setOnboardingState(OnboardingPresenter.OnboardingState.REG_WITH_FINGERPRINT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FINGERPRINT_CREATE ||  requestCode == FINGERPRINT_ENTER) {
            if (resultCode == Activity.RESULT_OK) {
                TextView label = findViewById(R.id.passcode_label);
                label.setText("Passcode:>" + new String(
                        ((DemoApplication) getApplication()).getCurrentPasscode()) + "<");
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
