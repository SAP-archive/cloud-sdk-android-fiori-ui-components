package com.sap.cloud.mobile.fiori.demo;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.sap.cloud.mobile.onboarding.passcode.PasscodePolicy;
import com.squareup.leakcanary.LeakCanary;

import java.util.Arrays;

/**
 * Demo application. Installs LeakCanary to detect memory leaks.
 */

public class DemoApplication extends Application {
    final private static String TAG = "DemoApplication";
    private static final String DEFAULT_PASSCODE = "1234";
    @NonNull
    private Delay delay = Delay.NONE;
    private boolean actionHandlerExternalTestActivityCallFlag;
    final private PasscodePolicy policy = new PasscodePolicy();
    @Nullable
    private char[] currentPasscode;
    private static Context context;
    private int attempts;
    protected CharSequence charSequence;
    private boolean nullPolicyEnabled;

    @Override
    public void onCreate() {
        super.onCreate();

        DemoApplication.context = getApplicationContext();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        //we don't want to request permission during testing, which is required for API 21/22.
        //see https://github.com/square/leakcanary/issues/285
        if (Build.VERSION.SDK_INT >= 23) {
            LeakCanary.install(this);
        }
        // Normal app init code...

        this.policy.setRetryLimit(3);
        currentPasscode = DEFAULT_PASSCODE.toCharArray();
        attempts = 0;
    }
    public void setCurrentPasscode(@Nullable char[] passCode) {
        Log.i(TAG, (passCode == null) ? "reset current passcode" :
                "set current passcode:" + new String(passCode));
        this.currentPasscode = passCode;
        attempts = 0;
    }

    @Nullable
    public char[] getCurrentPasscode() {
        return this.currentPasscode;
    }
    public void setDelay(String delayString) {
        Log.i(TAG, "setDelay: " + delayString);
        switch (Integer.parseInt(delayString)) {
            case 0:
                delay = Delay.NONE;
                break;
            case 1:
                delay = Delay.ONESEC;
                break;
            case 2:
                delay = Delay.TWOSEC;
                break;
            case 3:
                delay = Delay.TENSEC;
                break;
            case 4:
                delay = Delay.ONEMIN;
                break;
            case 5:
                delay = Delay.NEVER;
                break;
            default:
                delay = Delay.NONE;
                break;
        }
    }
    public void delay() throws InterruptedException {
        Log.i(TAG, "Delay: " + this.delay.toString());
        switch (this.delay) {
            case NONE:
                break;
            case ONESEC:
                Thread.sleep(1000);
                break;
            case TWOSEC:
                Thread.sleep(2000);
                break;
            case TENSEC:
                Thread.sleep(10000);
                break;
            case ONEMIN:
                Thread.sleep(60000);
                break;
            case NEVER:
                while (true) {
                    Thread.sleep(60000);
                }
            default:
                break;
        }

    }

    public void setActionHandlerExternalTestActivityCallFlag(boolean actionHandlerExternalTestActivityCallFlag) {
        this.actionHandlerExternalTestActivityCallFlag = actionHandlerExternalTestActivityCallFlag;
    }

    public boolean isActionHandlerExternalTestActivityCallFlag() {
        return actionHandlerExternalTestActivityCallFlag;
    }

    public boolean checkPasscode(@NonNull char[] passcode) {
        Log.i(TAG, "Passcode: " + new String(passcode));
        Log.i(TAG, "Expected Passcode: " + new String(this.currentPasscode));
        return Arrays.equals(passcode, this.currentPasscode);
    }

    @NonNull
    public PasscodePolicy getPolicy() {
        Log.i(TAG, this.policy.toString());
        return this.policy;
    }

    public enum Delay {
        NONE, ONESEC, TWOSEC, TENSEC, ONEMIN, NEVER
    }

    public static Context getAppContext() {
        return DemoApplication.context;
    }
    public int getAttempts() { return attempts; }

    public void setAttempts(int attempts) { this.attempts = attempts; }

    public boolean isNullPolicyEnabled() {
        return nullPolicyEnabled;
    }

    public void setNullPolicyEnabled(boolean nullPolicyEnabled) {
        this.nullPolicyEnabled = nullPolicyEnabled;
    }
}
