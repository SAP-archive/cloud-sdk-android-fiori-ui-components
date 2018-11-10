package com.sap.cloud.mobile.fiori.demo.onboarding;

import android.support.v4.app.Fragment;

import com.sap.cloud.mobile.onboarding.passcode.PasscodeValidationActionHandler;
import com.sap.cloud.mobile.onboarding.passcode.PasscodeValidationException;
import com.sap.cloud.mobile.onboarding.passcode.PasscodeValidationFailedToMeetPolicy;

/**
 * Created by i036160 on 2017. 10. 30..
 */

public class PasscodeValidationActionHandlerImpl implements PasscodeValidationActionHandler {
    @Override
    public void validate(Fragment fragment, char[] passcode) throws PasscodeValidationException, InterruptedException {
        Thread.sleep(5000);
        if (String.valueOf(passcode).equals("12345678")) {
            throw new PasscodeValidationFailedToMeetPolicy("Passcode is not complex enough", null);
        }
    }
}
