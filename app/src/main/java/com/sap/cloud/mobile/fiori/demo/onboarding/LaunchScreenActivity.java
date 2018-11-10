package com.sap.cloud.mobile.fiori.demo.onboarding;

import android.os.Bundle;
import android.support.annotation.Nullable;

public class LaunchScreenActivity extends OnboardingActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            presenter.setOnboardingState(
                    (OnboardingPresenter.OnboardingState) savedInstanceState.getSerializable(
                            KEY_ONBOARDING_STATE));
        } else {
            presenter.setOnboardingState(OnboardingPresenter.OnboardingState.NOT_REGISTERED);
        }
    }
}
