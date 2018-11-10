package com.sap.cloud.mobile.fiori.demo.onboarding;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.sap.cloud.mobile.fiori.demo.DemoApplication;
import com.sap.cloud.mobile.onboarding.launchscreen.WelcomeScreenActionHandler;
import com.sap.cloud.mobile.onboarding.utility.ActivityResultActionHandler;

/**
 * Created by i036160 on 2017. 10. 26..
 */

public class WelcomeScreenActionHandlerImpl
        implements WelcomeScreenActionHandler,
        ActivityResultActionHandler {
    final private java.lang.Object SYNC = new java.lang.Object();
    private boolean responseAvailable = false;
    final private static String TAG = "WelcomeScreenAHandler"; //abbreviated

    @Override
    public void startStandardOnboarding(@NonNull Fragment fragment) throws InterruptedException {
        Log.i(TAG, "createCustomOnboarding");
        ((DemoApplication) fragment.getActivity().getApplication()).delay();
        Intent i = new Intent();
        i.setComponent(new ComponentName(
                fragment.getActivity().getPackageName(),
                "com.sap.cloud.mobile.fiori.demo.onboarding.DummyLoginActivity"));
        fragment.getActivity().startActivityForResult(i, 1000);
        synchronized (SYNC) {
            while (!responseAvailable) {
                SYNC.wait();
            }
        }
    }

    @Override
    public void startDemoMode(@NonNull Fragment fragment) {
        Log.i(TAG, "createDemoMode");
        Intent intent = new Intent();
        intent.putExtra("DEMO", true);
        try {
            ((DemoApplication) fragment.getActivity().getApplication()).delay();
            fragment.getActivity().setResult(Activity.RESULT_OK, intent);
            fragment.getActivity().finish();
        } catch (InterruptedException e) {

        }
    }

    @Override
    public void startOnboardingWithDiscoveryServiceEmail(
            @NonNull Fragment fragment, @NonNull String userEmail) {
        Log.i(TAG, "startOnboardingWithDiscoveryServiceEmail");
        Log.i(TAG, "email:" + userEmail);
        Intent intent = new Intent();

        try {
            ((DemoApplication) fragment.getActivity().getApplication()).delay();
            if (!userEmail.equals("invalid@test.test")) {
                fragment.getActivity().setResult(Activity.RESULT_OK, intent);
                fragment.getActivity().finish();
            }
        } catch (InterruptedException e) {

        }
    }

    @Override
    public boolean onActivityResult(@NonNull Fragment fragment, int requestCode, int resultCode, Intent data) {

        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                Intent intent = new Intent();
                intent.putExtra("DEMO", false);
                fragment.getActivity().setResult(Activity.RESULT_OK, intent);
                fragment.getActivity().finish();
                synchronized (SYNC) {
                    responseAvailable = true;
                    SYNC.notify();
                }
                return false;
            } else {
                synchronized (SYNC) {
                    responseAvailable = true;
                    SYNC.notify();
                }
                return true;
            }
        }
        return true;
    }

}
