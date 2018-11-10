package com.sap.cloud.mobile.fiori.demo.onboarding;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import com.sap.cloud.mobile.fiori.demo.DemoApplication;
import com.sap.cloud.mobile.onboarding.passcode.PasscodeActionHandler;
import com.sap.cloud.mobile.onboarding.passcode.PasscodeInputMode;
import com.sap.cloud.mobile.onboarding.passcode.PasscodePolicy;
import com.sap.cloud.mobile.onboarding.passcode.PasscodeValidationException;
import com.sap.cloud.mobile.onboarding.passcode.PasscodeValidationFailedToMatchException;
import com.sap.cloud.mobile.onboarding.utility.ActivityResultActionHandler;

/**
 * Created by i036160 on 2017. 10. 27..
 */

public class PasscodeActionHandlerImpl implements PasscodeActionHandler, ActivityResultActionHandler {
    final private static String TAG = "PasscodeAHandlerImpl"; //abbreviated
    final private Object SYNC = new Object();
    private boolean responseAvailable = false;
    @Nullable
    private String responseForGetPolicy = null;
    public static final int EXTERNAL_ACTIVITY_RESULT_CODE = 777;

    @Override
    public void shouldTryPasscode(char[] passcode, @NonNull PasscodeInputMode mode, @NonNull Fragment fragment) throws PasscodeValidationException, InterruptedException {
        Log.i(TAG, "shouldTryPasscode");
        ((DemoApplication) fragment.getActivity().getApplication()).delay();

        switch (mode) {
                case CREATE:
                case CHANGE:
                    ((DemoApplication) fragment.getActivity().getApplication()).setCurrentPasscode(passcode);
                    break;
                case MATCH:
                case MATCHFORCHANGE:
                    if (!((DemoApplication) fragment.getActivity().getApplication()).checkPasscode(passcode)) {
                        int limit = ((DemoApplication) fragment.getActivity().getApplication()).getPolicy().getRetryLimit();
                        int attempts = ((DemoApplication) fragment.getActivity().getApplication()).getAttempts();
                        ((DemoApplication) fragment.getActivity().getApplication()).setAttempts(++attempts);
                        int remaining = limit - attempts;
                        throw new PasscodeValidationFailedToMatchException("Passcode match", remaining, null);
                    } else {
                        ((DemoApplication) fragment.getActivity().getApplication()).setAttempts(0);
                    }
                    break;
                default:
                    throw new Error("Unknown input mode");
            }
    }

    @Override
    public void shouldResetPasscode(@NonNull Fragment fragment) throws InterruptedException {
        Log.i(TAG, "shouldResetPasscode");
        Intent intent = new Intent();
        intent.putExtra("RESET_PASSWORD", true);
        ((DemoApplication) fragment.getActivity().getApplication()).delay();
        ((DemoApplication) fragment.getActivity().getApplication()).setCurrentPasscode(null);
        fragment.getActivity().setResult(Activity.RESULT_OK, intent);
        fragment.getActivity().finish();
        ((DemoApplication) fragment.getActivity().getApplication()).setAttempts(0);
    }

    @Override
    public void didSkipPasscodeSetup(@NonNull Fragment fragment) throws InterruptedException {
        Log.i(TAG, "didSkipPasscodeSetup");
        Intent intent = new Intent();

        // Apply delay to test
        ((DemoApplication) fragment.getActivity().getApplication()).delay();

        // Simulate external Activity call
        if (((DemoApplication) fragment.getActivity().getApplication()).isActionHandlerExternalTestActivityCallFlag()) {
            Intent i = new Intent(fragment.getActivity(), DummyExternalDevelopersActivity.class);
            fragment.getActivity().startActivityForResult(i, EXTERNAL_ACTIVITY_RESULT_CODE);
            synchronized (SYNC) {
                while (!responseAvailable) {
                    SYNC.wait();
                }
            }
        }

        fragment.getActivity().setResult(Activity.RESULT_OK, intent);
        fragment.getActivity().finish();
    }

    /**
     * Starts retrieving the passcode policy.
     *
     * @param fragment the enclosing fragment invoking this handler, must be non-null
     * @return the passcode policy
     */
    @Override
    public PasscodePolicy getPasscodePolicy(@NonNull Fragment fragment) throws InterruptedException {
        Log.i(TAG, "getPasscodePolicy");

        // Apply delay to test
        ((DemoApplication) fragment.getActivity().getApplication()).delay();

        // Simulate external Activity call
        if (((DemoApplication) fragment.getActivity().getApplication()).isActionHandlerExternalTestActivityCallFlag()) {
            Intent i = new Intent(fragment.getActivity(), DummyExternalDevelopersActivity.class);
            fragment.getActivity().startActivityForResult(i, EXTERNAL_ACTIVITY_RESULT_CODE);
            synchronized (SYNC) {
                while (!responseAvailable) {
                    SYNC.wait();
                }
            }
        }

        PasscodePolicy policy = ((DemoApplication) fragment.getActivity().getApplication()).getPolicy();
        if (((DemoApplication) fragment.getActivity().getApplication()).isNullPolicyEnabled()) {
            return  null;
        } else {
            if ("DIGITS".equals(responseForGetPolicy)) {
                policy.setIsDigitsOnly(true);
            }
            return policy;
        }
    }

    @Override
    public boolean onActivityResult(Fragment fragment, int requestCode, int resultCode, Intent data) {
        if (requestCode == EXTERNAL_ACTIVITY_RESULT_CODE) {
            responseForGetPolicy = resultCode == Activity.RESULT_OK ? "DIGITS" : "DEFAULT";
            synchronized (SYNC) {
                responseAvailable = true;
                SYNC.notify();
            }
            return true;
        }
        return true;
    }
}