package com.sap.cloud.mobile.fiori.demo.onboarding;

import android.util.Log;

import com.sap.cloud.mobile.fiori.demo.R;
import com.sap.cloud.mobile.onboarding.qrcodereader.QRCodeConfirmActivity;

public class CustomQRCodeConfirmActivity extends QRCodeConfirmActivity {
    final private static String TAG = "CustomQRCodeConfirmA";
    @Override
    public void setContentView(int layoutResID) {
        Log.i(TAG, "Custom content");
        if (layoutResID == com.sap.cloud.mobile.onboarding.R.layout.activity_qrcode_confirm) {
            super.setContentView(R.layout.activity_qrcode_custom_confirm);
        } else {
            super.setContentView(layoutResID);
        }
    }
}
