package com.sap.cloud.mobile.fiori.demo.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 *
 */

public class QRConfirmDemoActivity extends OnboardingActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            presenter.setOnboardingState(
                    (OnboardingPresenter.OnboardingState) savedInstanceState.getSerializable(
                            KEY_ONBOARDING_STATE));
        } else {
            presenter.setOnboardingState(OnboardingPresenter.OnboardingState.IN_QRCONFIRM);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }
}
