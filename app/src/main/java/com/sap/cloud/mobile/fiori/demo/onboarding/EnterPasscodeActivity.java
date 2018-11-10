package com.sap.cloud.mobile.fiori.demo.onboarding;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by i036160 on 2017. 10. 30..
 */

public class EnterPasscodeActivity extends PasscodeActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            presenter.setOnboardingState(
                    (OnboardingPresenter.OnboardingState) savedInstanceState.getSerializable(
                            KEY_ONBOARDING_STATE));
        } else {
            presenter.setOnboardingState(OnboardingPresenter.OnboardingState.REG_WITH_PASSCODE);
        }
    }
}
