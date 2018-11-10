package com.sap.cloud.mobile.fiori.demo.onboarding;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.sap.cloud.mobile.fiori.demo.AbstractDemoActivity;
import com.sap.cloud.mobile.fiori.demo.DemoApplication;
import com.sap.cloud.mobile.fiori.demo.R;
import com.sap.cloud.mobile.onboarding.passcode.PasscodePolicy;

public abstract class OnboardingActivity extends AbstractDemoActivity {
    final public static String UI_TEST_KEY = "__UI_TEST__";
    final private static String TAG = "OnboardingActivity";
    final protected static int WELCOME_SCREEN = 100;
    final protected static int SET_PASSCODE = 200;
    final protected static int ENTER_PASSCODE = 250;
    final protected static int FINGERPRINT_CREATE = 400;
    final protected static int FINGERPRINT_ENTER = 500;
    final protected static int FINGERPRINT_ENABLE = 501;
    final protected static int FINGERPRINT_ERROR = 502;
    final protected static int EULA = 300;
    final protected static int QRCONFIRM = 330;
    final protected static int LAUNCH_SCREEN = 600;
    final private static String NULL_POLICY_ENABLED = "nullPolicyEnabled";
    final private static String SKIP_ENABLED = "skipEnabled";
    final private static String MIN_LENGTH = "minLength";
    final private static String MIN_UNIQUE = "minUnique";
    final private static String HAS_DIGIT = "hasDigit";
    final private static String HAS_UPPER = "hasUpper";
    final private static String HAS_LOWER = "hasLower";
    final private static String HAS_SPECIAL = "hasSpecial";
    final private static String IS_DIGITS_ONLY = "isDigitsOnly";
    final private static String RETRY_LIMIT = "retryLimit";

    final protected static String KEY_ONBOARDING_STATE = "onboarding_state";
    protected OnboardingPresenter presenter;

    boolean isUITest = false;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        isUITest = getIntent().getBooleanExtra(UI_TEST_KEY, false);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(
                this);
        presenter = new OnboardingPresenter(new OnboardingView(this));
        ((DemoApplication) this.getApplication()).setDelay(sharedPreferences.getString(
                "delay", "0"));
        ((DemoApplication) this.getApplication()).setActionHandlerExternalTestActivityCallFlag(sharedPreferences.getBoolean(
                "action_handler_external_test_activity_call_flag", false));
        if (sharedPreferences.getBoolean("force_current_passcode", false) || ((DemoApplication) this.getApplication()).getCurrentPasscode() == null) {
            ((DemoApplication) this.getApplication()).setCurrentPasscode(sharedPreferences.getString(
                    "current_passcode", "1234").toCharArray());
            sharedPreferences.edit().remove("force_current_passcode");
            sharedPreferences.edit().apply();
        }
        if (sharedPreferences.contains(NULL_POLICY_ENABLED)) {
            ((DemoApplication) this.getApplication()).setNullPolicyEnabled(sharedPreferences.getBoolean(NULL_POLICY_ENABLED, false));
        }
        if (sharedPreferences.contains(SKIP_ENABLED)) {
            ((DemoApplication) this.getApplication()).getPolicy().setSkipEnabled(sharedPreferences.getBoolean(SKIP_ENABLED, false));
        }
        if (sharedPreferences.contains(HAS_DIGIT)) {
            ((DemoApplication) this.getApplication()).getPolicy().setHasDigit(sharedPreferences.getBoolean(HAS_DIGIT, false));
        }
        if (sharedPreferences.contains(HAS_LOWER)) {
            ((DemoApplication) this.getApplication()).getPolicy().setHasLower(sharedPreferences.getBoolean(HAS_LOWER, false));
        }
        if (sharedPreferences.contains(HAS_UPPER)) {
            ((DemoApplication) this.getApplication()).getPolicy().setHasUpper(sharedPreferences.getBoolean(HAS_UPPER, false));
        }
        if (sharedPreferences.contains(HAS_SPECIAL)) {
            ((DemoApplication) this.getApplication()).getPolicy().setHasSpecial(sharedPreferences.getBoolean(HAS_SPECIAL, false));
        }
        if (sharedPreferences.contains(IS_DIGITS_ONLY)) {
            ((DemoApplication) this.getApplication()).getPolicy().setIsDigitsOnly(sharedPreferences.getBoolean(IS_DIGITS_ONLY, false));
        }
        if (sharedPreferences.contains(MIN_LENGTH)) {
            ((DemoApplication) this.getApplication()).getPolicy().setMinLength(Integer.parseInt(
                    sharedPreferences.getString(MIN_LENGTH, "4")));
        }
        if (sharedPreferences.contains(MIN_UNIQUE)) {
            ((DemoApplication) this.getApplication()).getPolicy().setMinUniqueChars(Integer.parseInt(
                    sharedPreferences.getString(MIN_UNIQUE, String.valueOf(PasscodePolicy.PASSCODE_POLICY_NO_LIMIT))));
        }
        if (sharedPreferences.contains(RETRY_LIMIT)) {
            ((DemoApplication) this.getApplication()).getPolicy().setRetryLimit(Integer.parseInt(
                    sharedPreferences.getString(RETRY_LIMIT, "3")));
        }

        setContentView(R.layout.activity_onboarding);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.startScenario();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
        outState.putSerializable(KEY_ONBOARDING_STATE, this.presenter.getOnboardingState());
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        Log.i(TAG, "onActivityResult");
        Log.d(TAG, Integer.toString(requestCode));
        Log.d(TAG, Integer.toString(resultCode));
        presenter.onActivityResult(requestCode, resultCode, data);
    }

}
