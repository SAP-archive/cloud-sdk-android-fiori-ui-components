package com.sap.cloud.mobile.fiori.demo.onboarding;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.android.gms.vision.barcode.Barcode;
import com.sap.cloud.mobile.fiori.demo.DemoApplication;
import com.sap.cloud.mobile.onboarding.qrcodereader.google.BarcodeValidationException;
import com.sap.cloud.mobile.onboarding.qrcodereader.google.QRCodeReaderActionHandler;

/**
 * Demo action handler for QR code reader activity.
 */
public class QRCodeReaderActionHandlerImpl implements QRCodeReaderActionHandler {
    final private static String TAG = "QRCodeReaderAHandler"; //abbreviated

    /**
     * Validates all non-null barcodes after a user defined delay. If the validation is
     * interrupted the result is false.
     *
     * @param fragment the caller fragment of the QR code reader activity
     * @param barcode  the barcode which will be validated
     * @return true if the barcode is not null and the validation is not interrupted
     */
    @Override
    public void validateBarcode(@NonNull Fragment fragment, @Nullable Barcode barcode)
            throws BarcodeValidationException, InterruptedException {
        Log.i(TAG, "validating barcode: ".concat(barcode == null ? "null" : barcode.displayValue));
        ((DemoApplication) fragment.getActivity().getApplication()).delay();
        if (barcode == null) {
            Log.i(TAG, "barcode is null");
            throw new BarcodeValidationException("Null barcode");
        }
    }
}
