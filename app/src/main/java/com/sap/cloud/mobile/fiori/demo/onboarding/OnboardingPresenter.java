package com.sap.cloud.mobile.fiori.demo.onboarding;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by i036160 on 2017. 11. 29..
 */
class OnboardingPresenter {
    private OnboardingState onboardingState;
    private OnboardingView view;

    OnboardingPresenter(OnboardingView view) {
        this.view = view;
    }

    void startScenario() {
        switch (onboardingState) {
            case NOT_REGISTERED:
                onboardingState = OnboardingState.IN_LAUNCHSCREEN;
                view.startLaunchScreen();
                break;
            case IN_EULA:
                onboardingState = OnboardingState.IN_EULA;
                view.startEULAScreen();
                break;
            case IN_QRCONFIRM:
                view.startQRConfirmScreen();
                break;
            case REG_WITH_PASSCODE:
                onboardingState = OnboardingState.IN_ENTER_PASSCODE;
                view.startEnterPasscode();
                break;
            case REG_WITHOUT_PASSCODE:
                onboardingState = OnboardingState.IN_CREATE_PASSCODE;
                view.startCreatePasscode();
                break;
            case REG_WITH_FINGERPRINT:
                onboardingState = OnboardingState.IN_CREATE_FINGERPRINT;
                view.startFingerprint();
                break;
            case TEST_ENABLE_FINGERPRINT:
                onboardingState = OnboardingState.IN_ENABLE_FINGERPRINT;
                view.startEnableFingerprint();
                break;
            case TEST_FINGERPRINT_ERROR:
                onboardingState = OnboardingState.IN_FINGERPRINT_ERROR;
                view.startFingerprintError();
                break;
            case IN_CREATE_PASSCODE:
            case IN_LAUNCHSCREEN:
            case IN_WELCOME_SCREEN:
            case IN_ENTER_PASSCODE:
            case IN_CREATE_FINGERPRINT:
            case IN_ENABLE_FINGERPRINT:
            case IN_FINGERPRINT_ERROR:
                break;
            case STARTED:
                break;
            case FINISHED:
            default:
                throw new IllegalStateException("Unknown onboarding state: " + onboardingState.toString());
        }
    }

    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == OnboardingActivity.WELCOME_SCREEN) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null && data.getBooleanExtra("DEMO", false)) {
                    this.setOnboardingState(OnboardingPresenter.OnboardingState.STARTED);
                    view.startAfterOnboarding();
                } else {
                    this.setOnboardingState(OnboardingPresenter.OnboardingState.REG_WITHOUT_PASSCODE);
                    this.startScenario();
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                this.setOnboardingState(OnboardingPresenter.OnboardingState.FINISHED);
                view.stopOnboarding(resultCode);
            }
        } else if (requestCode == OnboardingActivity.SET_PASSCODE) {
            if (resultCode == Activity.RESULT_OK) {
                this.setOnboardingState(OnboardingPresenter.OnboardingState.STARTED);
                view.startAfterOnboarding();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                this.setOnboardingState(OnboardingPresenter.OnboardingState.NOT_REGISTERED);
                this.startScenario();
            }else if(resultCode == com.sap.cloud.mobile.onboarding.passcode.SetPasscodeActivity.POLICY_CANCELLED) {
                view.stopOnboarding(resultCode);
            }

        } else if (requestCode == OnboardingActivity.FINGERPRINT_CREATE) {

            this.setOnboardingState(OnboardingPresenter.OnboardingState.STARTED);
            view.startAfterOnboarding();
        } else if (requestCode == OnboardingActivity.EULA) {
            if (resultCode == Activity.RESULT_OK) {
                this.setOnboardingState(OnboardingPresenter.OnboardingState.STARTED);
                view.startAfterOnboarding();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                this.setOnboardingState(OnboardingPresenter.OnboardingState.NOT_REGISTERED);
                this.startScenario();
            }
        } else if (requestCode == OnboardingActivity.LAUNCH_SCREEN) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null && data.getBooleanExtra("DEMO", false)) {
                    this.setOnboardingState(OnboardingPresenter.OnboardingState.STARTED);
                    view.startAfterOnboarding();
                } else {
                    this.setOnboardingState(OnboardingPresenter.OnboardingState.REG_WITHOUT_PASSCODE);
                    this.startScenario();
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                view.stopOnboarding(resultCode);
            }
        } else if (requestCode == OnboardingActivity.ENTER_PASSCODE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null && data.getBooleanExtra("RESET_PASSWORD", false)) {
                    this.setOnboardingState(OnboardingPresenter.OnboardingState.REG_WITHOUT_PASSCODE);
                    this.startScenario();
                } else {
                    this.setOnboardingState(OnboardingPresenter.OnboardingState.STARTED);
                    view.startAfterOnboarding();
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                this.setOnboardingState(OnboardingPresenter.OnboardingState.FINISHED);
                view.stopOnboarding(resultCode);
            }
        } else if (requestCode == OnboardingActivity.FINGERPRINT_ENTER) {
            if (resultCode == Activity.RESULT_OK) {
                this.setOnboardingState(OnboardingPresenter.OnboardingState.STARTED);
                view.startAfterOnboarding();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                this.setOnboardingState(OnboardingPresenter.OnboardingState.FINISHED);
                view.stopOnboarding(resultCode);
            }
        }
    }

    enum OnboardingState {
        NOT_REGISTERED,
        REG_WITHOUT_PASSCODE,
        REG_WITH_PASSCODE,
        STARTED,
        FINISHED,
        IN_WELCOME_SCREEN,
        IN_ENTER_PASSCODE,
        IN_CREATE_PASSCODE,
        IN_EULA,
        IN_QRCONFIRM,
        IN_LAUNCHSCREEN,
        IN_CREATE_FINGERPRINT,
        REG_WITH_FINGERPRINT,
        TEST_FINGERPRINT_ERROR,
        IN_FINGERPRINT_ERROR,
        TEST_ENABLE_FINGERPRINT,
        IN_ENABLE_FINGERPRINT,
    }

    OnboardingState getOnboardingState() {
        return onboardingState;
    }

    void setOnboardingState(OnboardingState onboardingState) {
        this.onboardingState = onboardingState;
    }

    interface OnboardingView {
        public void stopOnboarding(int resultCode);
        public void startCreatePasscode();
        public void startEnterPasscode();
        public void startEULAScreen();
        public void startQRConfirmScreen();
        public void startLaunchScreen();
        public void startFingerprint();
        public void startEnableFingerprint();
        public void startFingerprintError();
        public void startAfterOnboarding();
    }
}
