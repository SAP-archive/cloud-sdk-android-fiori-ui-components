package com.sap.cloud.mobile.fiori.demo.onboarding;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.sap.cloud.mobile.fiori.demo.DemoApplication;
import com.sap.cloud.mobile.onboarding.utility.ActivityResultActionHandler;

public class LaunchScreenActionHandlerImpl
        extends com.sap.cloud.mobile.onboarding.launchscreen.WelcomeScreenActionHandlerImpl
        implements ActivityResultActionHandler {
    final private Object SYNC = new Object();
    private boolean responseAvailable = false;
    final private static String TAG = "WelcomeScreenAHandler"; //abbreviated

    @Override
    public void startStandardOnboarding(@NonNull Fragment fragment) throws InterruptedException {
        Log.i(TAG, "createCustomOnboarding");
        //((DemoApplication) fragment.getActivity().getApplication()).delay();
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
    public void startDemoMode(@NonNull Fragment fragment) throws InterruptedException {
        Log.i(TAG, "createDemoMode");
        Intent intent = new Intent();
        intent.putExtra("DEMO", true);
        ((DemoApplication) fragment.getActivity().getApplication()).delay();
        fragment.getActivity().setResult(Activity.RESULT_OK, intent);
        fragment.getActivity().finish();
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
