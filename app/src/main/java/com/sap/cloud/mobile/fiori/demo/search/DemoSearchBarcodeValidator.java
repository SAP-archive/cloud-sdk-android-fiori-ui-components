package com.sap.cloud.mobile.fiori.demo.search;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.android.gms.vision.barcode.Barcode;
import com.sap.cloud.mobile.fiori.demo.DemoApplication;
import com.sap.cloud.mobile.onboarding.qrcodereader.google.BarcodeValidationException;
import com.sap.cloud.mobile.onboarding.qrcodereader.google.QRCodeReaderActionHandler;

public class DemoSearchBarcodeValidator implements QRCodeReaderActionHandler {
    private static final String TAG = DemoSearchBarcodeValidator.class.getCanonicalName();
    private static Barcode mBarcode;

    @Override
    public void validateBarcode(Fragment fragment, Barcode barcode) throws BarcodeValidationException, InterruptedException {
        Log.i(TAG, "validating barcode: ".concat(barcode == null ? "null" : barcode.displayValue));
        ((DemoApplication) fragment.getActivity().getApplication()).delay();
        if (barcode == null) {
            Log.i(TAG, "barcode is null");
            throw new BarcodeValidationException("Null barcode");
        } else {
            mBarcode = barcode;
        }
    }

    public static Barcode getBarcode() {
        return mBarcode;
    }
}
