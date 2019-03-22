package com.sap.cloud.mobile.fiori.demo.onboarding;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;

import com.sap.cloud.mobile.onboarding.activation.ActivationSettings;
import com.sap.cloud.mobile.onboarding.eula.EULAScreenSettings;
import com.sap.cloud.mobile.onboarding.fingerprint.EnableFingerprintSettings;
import com.sap.cloud.mobile.onboarding.fingerprint.FingerprintErrorSettings;
import com.sap.cloud.mobile.onboarding.fingerprint.FingerprintSettings;
import com.sap.cloud.mobile.onboarding.launchscreen.LaunchScreenSettings;
import com.sap.cloud.mobile.onboarding.passcode.ConfirmPasscodeSettings;
import com.sap.cloud.mobile.onboarding.passcode.EnterPasscodeSettings;
import com.sap.cloud.mobile.onboarding.passcode.PasscodeInputMode;
import com.sap.cloud.mobile.onboarding.passcode.SetPasscodeSettings;
import com.sap.cloud.mobile.onboarding.qrcodereader.QRCodeConfirmSettings;
import com.sap.cloud.mobile.onboarding.qrcodereader.QRCodeReaderSettings;
import com.sap.cloud.mobile.onboarding.utility.OnboardingType;

public class OnboardingView implements OnboardingPresenter.OnboardingView {
    public static final String KEY_QRCODE_READ_ACTION_HANDLER = "qrcode_read_action_handler";
    private OnboardingActivity activity;

    OnboardingView(OnboardingActivity activity) {
        this.activity = activity;
    }

    final static String KEY_ACTIVATION_EMAIL_TITLE = "activation_email_title";
    final static String KEY_ACTIVATION_EMAIL_INSTRUCTION = "activation_email_instruction";
    final static String KEY_ACTIVATION_EMAIL_GRAPHIC_ELEMENT = "activation_email_graphic_element";
    final static String KEY_ACTIVATION_EMAIL_ADDRESS = "activation_email_address";
    final static String KEY_ACTIVATION_CHOOSE_TITLE = "activation_choose_title";
    final static String KEY_ACTIVATION_CHOOSE_INSTRUCTION = "activation_choose_instruction";
    final static String KEY_ACTIVATION_CHOOSE_PRIMARY_BUTTON_TITLE = "activation_choose_button1";
    final static String KEY_ACTIVATION_CHOOSE_SECONDARY_BUTTON_TITLE = "activation_choose_button2";
    final static String KEY_ACTIVATION_DISCOVERY_TITLE = "activation_discovery_title";
    final static String KEY_ACTIVATION_DISCOVERY_INSTRUCTION = "activation_discovery_instruction";
    final static String KEY_ACTIVATION_DISCOVERY_BUTTON_TITLE = "activation_discovery_button";
    final static String KEY_ACTIVATION_DISCOVERY_EMAIL_HINT = "activation_discovery_email_hint";
    final static String KEY_ACTIVATION_DISCOVERY_SERVICE = "activation_discovery_service";
    final static String KEY_ACTIVATION_BARCODE_TITLE = "activation_barcode_title";
    final static String KEY_ACTIVATION_BARCODE_INSTRUCTION = "activation_barcode_instruction";
    final static String KEY_ACTIVATION_BARCODE_BUTTON_TITLE = "activation_barcode_button";

    @Override
    public void stopOnboarding(int resultCode) {
        activity.setResult(resultCode);
        activity.finish();
    }

    /**
     * Starts a set passcode activity with intent configured based on the shared preferences.
     */
    @Override
    public void startCreatePasscode() {
        Intent i = new Intent();
        i.setComponent(new ComponentName(activity.getPackageName(), "com.sap.cloud.mobile.onboarding.passcode.SetPasscodeActivity"));
        SetPasscodeSettings settings;
        ConfirmPasscodeSettings confirmSettings;

        if(activity.isUITest){
            settings = new SetPasscodeSettings(activity.getIntent());
            confirmSettings = new ConfirmPasscodeSettings(activity.getIntent());
        }else{
            settings = new SetPasscodeSettings();
            confirmSettings = new ConfirmPasscodeSettings();

            String createPasscodeValidationActionHandler = activity.sharedPreferences.getString("passcode_create_validation_action_handler", null);
            if (createPasscodeValidationActionHandler != null && !createPasscodeValidationActionHandler.equals("")) {
                settings.setValidationActionHandler(createPasscodeValidationActionHandler);
            }

            String createPasscodeSkipButtonText = activity.sharedPreferences.getString("passcode_skip_button_text", null);
            if (createPasscodeSkipButtonText != null && !createPasscodeSkipButtonText.equals("")) {
                settings.setSkipButtonText(createPasscodeSkipButtonText);
            }

            String headlineLabel = activity.sharedPreferences.getString("passcode_create_title", null);
            if (headlineLabel != null && !headlineLabel.equals("")) {
                settings.setTitle(headlineLabel);
            }

            String createDoneButtonText = activity.sharedPreferences.getString("passcode_done_button_text", null);
            if (createDoneButtonText != null && !createDoneButtonText.equals("")) {
                settings.setDoneButtonText(createDoneButtonText);
            }

            String detailLabel = activity.sharedPreferences.getString("passcode_create_instruction_text", null);
            if (detailLabel != null && !detailLabel.equals("")) {
                settings.setInstructionText(detailLabel);
            }

            String lowercaseLabel = activity.sharedPreferences.getString("passcode_lowercase_label", null);
            if (lowercaseLabel != null && !lowercaseLabel.equals("")) {
                settings.setLowerCaseLabel(lowercaseLabel);
            }

            String uppercaseLabel = activity.sharedPreferences.getString("passcode_uppercase_label", null);
            if (uppercaseLabel != null && !uppercaseLabel.equals("")) {
                settings.setUpperCaseLabel(uppercaseLabel);
            }

            String digitLabel = activity.sharedPreferences.getString("passcode_digit_label", null);
            if (digitLabel != null && !digitLabel.equals("")) {
                settings.setDigitCaseLabel(digitLabel);
            }

            String symbolLabel = activity.sharedPreferences.getString("passcode_symbol_label", null);
            if (symbolLabel != null && !symbolLabel.equals("")) {
                settings.setSymbolCaseLabel(symbolLabel);
            }

            String nullPolicyAlertHeaderText = activity.sharedPreferences.getString("passcode_alert_dialog_null_policy_header_text", null);
            if (nullPolicyAlertHeaderText != null && !nullPolicyAlertHeaderText.equals("")) {
                settings.setNullPolicyAlertHeaderText(nullPolicyAlertHeaderText);
            }

            String nullPolicyAlertDescText = activity.sharedPreferences.getString("passcode_alert_dialog_null_policy_desc_text", null);
            if (nullPolicyAlertDescText != null && !nullPolicyAlertDescText.equals("")) {
                settings.setNullPolicyAlertDescriptionText(nullPolicyAlertDescText);
            }
            String nullPolicyAlertRetryText = activity.sharedPreferences.getString("passcode_alert_dialog_null_policy_retry_button_text", null);
            if (nullPolicyAlertRetryText != null && !nullPolicyAlertRetryText.equals("")) {
                settings.setNullPolicyAlertRetryButtonText(nullPolicyAlertRetryText);
            }
            String nullPolicyAlertCancelText = activity.sharedPreferences.getString("passcode_alert_dialog_null_policy_cancel_button_text", null);
            if (nullPolicyAlertCancelText != null && !nullPolicyAlertCancelText.equals("")) {
                settings.setNullPolicyAlertCancelButtonText(nullPolicyAlertCancelText);
            }

            String confirmTitleText = activity.sharedPreferences.getString("passcode_confirm_title", null);
            if (confirmTitleText != null && !confirmTitleText.equals("")) {
                confirmSettings.setTitle(confirmTitleText);
            }

            String confirmDoneButtonText = activity.sharedPreferences.getString("passcode_confirm_done_button_text", null);
            if (confirmDoneButtonText != null && !confirmDoneButtonText.equals("")) {
                confirmSettings.setDoneButtonText(confirmDoneButtonText);
            }

            String confirmInstructionText = activity.sharedPreferences.getString("passcode_confirm_instruction_text", null);
            if (confirmInstructionText != null && !confirmInstructionText.equals("")) {
                confirmSettings.setInstructionText(confirmInstructionText);
            }

            String confirmMisMatchText = activity.sharedPreferences.getString("passcode_confirm_mismatch_text", null);
            if (confirmMisMatchText != null && !confirmMisMatchText.equals("")) {
                confirmSettings.setConfirmMismatchText(confirmMisMatchText);
            }

            String confirmCancelButtonText = activity.sharedPreferences.getString("passcode_confirm_button_cancel_text", null);
            if (confirmCancelButtonText != null && !confirmCancelButtonText.equals("")) {
                confirmSettings.setCancelButtonText(confirmCancelButtonText);
            }
        }

        settings.saveToIntent(i);
        confirmSettings.saveToIntent(i);

        activity.startActivityForResult(i, OnboardingActivity.SET_PASSCODE);
    }

    /**
     * Starts an enter passcode activity with intent configured based on the shared preferences.
     */
    @Override
    public void startEnterPasscode() {
        Intent i = new Intent();
        i.setComponent(new ComponentName(activity.getPackageName(),
                "com.sap.cloud.mobile.onboarding.passcode.EnterPasscodeActivity"));
        EnterPasscodeSettings settings;

        if (activity.isUITest) {
            settings = new EnterPasscodeSettings(activity.getIntent());
        } else {
            settings = new EnterPasscodeSettings();

            boolean resetEnabled = activity.sharedPreferences.getBoolean(
                    "passcode_reset_enabled", true);
            settings.setResetEnabled(resetEnabled);

            boolean finalDisabled = activity.sharedPreferences.getBoolean(
                    "passcode_final_disabled", false);
            settings.setFinalDisabled(finalDisabled);

            String headlineLabel = activity.sharedPreferences.getString(
                    "passcode_enter_title", null);
            if (headlineLabel != null) {
                settings.setTitle(headlineLabel);
            }

            String detailLabel = activity.sharedPreferences.getString(
                    "passcode_enter_instruction_text", null);
            if (detailLabel != null) {
                settings.setInstructionText(detailLabel);
            }

            String changeHeadlineLabel = activity.sharedPreferences.getString(
                    "passcode_change_title", null);
            if (changeHeadlineLabel != null) {
                settings.setChangeTitle(changeHeadlineLabel);
            }

            String changeDetailLabel = activity.sharedPreferences.getString(
                    "passcode_change_instruction_text", null);
            if (changeDetailLabel != null) {
                settings.setChangeInstructionText(changeDetailLabel);
            }

            String enterButtonText = activity.sharedPreferences.getString(
                    "passcode_enter_done_button_text", null);
            if (enterButtonText != null) {
                settings.setDoneButtonText(enterButtonText);
            }

            String resetButtonText = activity.sharedPreferences.getString(
                    "passcode_enter_reset_button_text", null);
            if (resetButtonText != null) {
                settings.setResetButtonText(resetButtonText);
            }

            String skipButtonText = activity.sharedPreferences.getString(
                    "passcode_change_skip_button_text", null);
            if (skipButtonText != null) {
                settings.setCancelButtonText(skipButtonText);
            }

            String alertDialogHeaderText = activity.sharedPreferences.getString(
                    "passcode_alert_dialog_header_text", null);
            if (alertDialogHeaderText != null) {
                settings.setMaxAttemptsReachedMessage(alertDialogHeaderText);
            }

            String alertDialogBodyText = activity.sharedPreferences.getString(
                    "passcode_alert_dialog_body_text", null);
            if (alertDialogBodyText != null) {
                settings.setEnterCredentialsMessage(alertDialogBodyText);
            }

            String alertDialogButtonText = activity.sharedPreferences.getString(
                    "passcode_alert_dialog_button_text", null);
            if (alertDialogButtonText != null) {
                settings.setOkButtonString(alertDialogButtonText);
            }
        }

        settings.saveToIntent(i);
        activity.startActivityForResult(i, OnboardingActivity.ENTER_PASSCODE);
    }

    final private static String KEY_CREATE_RANDOM_PASSCODE = "create_random_passcode";
    final protected static String KEY_FINGERPRINT_KEY_ALGORITHM = "fingerprint_key_algorithm";
    final protected static String KEY_FINGERPRINT_BLOCK_MODE = "fingerprint_block_mode";
    final protected static String KEY_FINGERPRINT_ENCRYPTION_PADDING =
            "fingerprint_encryption_padding";
    final protected static String KEY_ENABLE_HEADLINE_LABEL = "enable_headline_label";
    final protected static String KEY_ENABLE_DETAIL_LABEL = "enable_detail_label";
    final protected static String KEY_ENABLE_BUTTON_TITLE = "enable_button_title";

    final protected static String KEY_ENABLE_ALERT_TITLE = "enable_alert_title";
    final protected static String KEY_ENABLE_ALERT_MESSAGE = "enable_alert_message";
    final protected static String KEY_ENABLE_ALERT_CANCEL_BUTTON_TEXT = "enable_alert_cancel_button_text";
    final protected static String KEY_ENABLE_ALERT_SETTINGS_BUTTON_TEXT = "enable_alert_settings_button_text";
    final protected static String KEY_SKIP_FOR_NOW_BUTTON_TITLE = "skip_for_now_button_title";

    final protected static String KEY_FINGERPRINT_HEADLINE_LABEL = "confirm_headline_label";
    final protected static String KEY_GENERAL_ACTION_HANDLER = "general_action_handler";
    final protected static String KEY_FINGERPRINT_DETAIL_LABEL = "confirm_detail_label";
    protected static final String KEY_FINGERPRINT_BUTTON_TITLE = "confirm_try_password_button";
    protected static final String KEY_FINGERPRINT_BUTTON_ENABLED =
            "confirm_try_password_button_enabled";
    final protected static String KEY_ERROR_HEADLINE_LABEL = "error_headline_label";
    final protected static String KEY_ERROR_DETAIL_LABEL = "error_detail_label";
    final protected static String KEY_ERROR_RESET_BUTTON = "error_reset_button";
    final protected static String KEY_ERROR_RESET_ENABLED = "error_reset_enabled";

    final private static String KEY_TEST_CREATE_RANDOM_PASSCODE = "test_create_random_passcode";
    final protected static String KEY_TEST_FINGERPRINT_KEY_ALGORITHM = "test_fingerprint_key_algorithm";
    final protected static String KEY_TEST_FINGERPRINT_BLOCK_MODE = "test_fingerprint_block_mode";
    final protected static String KEY_TEST_FINGERPRINT_ENCRYPTION_PADDING = "test_fingerprint_encryption_padding";

    /**
     * Starts a fingerprint activity with intent configured based on the shared preferences.
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void startFingerprint() {
        int requestCode;
        Intent i = new Intent();
        i.setComponent(new ComponentName(activity.getPackageName(), "com.sap.cloud.mobile.onboarding.fingerprint.FingerprintActivity"));

        EnableFingerprintSettings enableSettings;
        FingerprintSettings fingerprintSettings;
        FingerprintErrorSettings fingerprintErrorSettings;

        if (activity.isUITest) {
            boolean isCreateScenario = activity.getIntent().getBooleanExtra(KEY_TEST_CREATE_RANDOM_PASSCODE, false);
            if(isCreateScenario){
                KeyHandler.passcodeInputMode = PasscodeInputMode.CREATE;
                requestCode = OnboardingActivity.FINGERPRINT_CREATE;
            }else{
                KeyHandler.passcodeInputMode = PasscodeInputMode.MATCH;
                requestCode = OnboardingActivity.FINGERPRINT_ENTER;
            }

            String fingerprintKeyAlgorythm = activity.getIntent().getStringExtra(KEY_TEST_FINGERPRINT_KEY_ALGORITHM);
            KeyHandler.algorithm = fingerprintKeyAlgorythm;

            String fingerprintBlockMode = activity.getIntent().getStringExtra(KEY_TEST_FINGERPRINT_BLOCK_MODE);
            KeyHandler.blockMode = fingerprintBlockMode;

            String fingerprintEncryptionPadding = activity.getIntent().getStringExtra(KEY_TEST_FINGERPRINT_ENCRYPTION_PADDING);
            KeyHandler.encryptionPadding = fingerprintEncryptionPadding;

            enableSettings = new EnableFingerprintSettings(activity.getIntent());
            fingerprintSettings = new FingerprintSettings(activity.getIntent());
            fingerprintErrorSettings = new FingerprintErrorSettings(activity.getIntent());
        } else {

            boolean isCreateScenario = activity.sharedPreferences.getBoolean(
                    KEY_CREATE_RANDOM_PASSCODE, false);
            if (isCreateScenario) {
                KeyHandler.passcodeInputMode = PasscodeInputMode.CREATE;
                requestCode = OnboardingActivity.FINGERPRINT_CREATE;
            } else {
                KeyHandler.passcodeInputMode = PasscodeInputMode.MATCH;
                requestCode = OnboardingActivity.FINGERPRINT_ENTER;
            }

            String fingerprintKeyAlgorithm = activity.sharedPreferences.getString(
                    KEY_FINGERPRINT_KEY_ALGORITHM, KeyProperties.KEY_ALGORITHM_AES);
            KeyHandler.algorithm = fingerprintKeyAlgorithm;

            String fingerprintBlockMode = activity.sharedPreferences.getString(
                    KEY_FINGERPRINT_BLOCK_MODE, KeyProperties.BLOCK_MODE_CBC);
            KeyHandler.blockMode = fingerprintBlockMode;

            String fingerprintEncryptionPadding = activity.sharedPreferences.getString(
                    KEY_FINGERPRINT_ENCRYPTION_PADDING, KeyProperties.ENCRYPTION_PADDING_PKCS7);
            KeyHandler.encryptionPadding = fingerprintEncryptionPadding;

            enableSettings = new EnableFingerprintSettings();
                String fingerprintEnableHeadlineLabel = activity.sharedPreferences.getString(
                        KEY_ENABLE_HEADLINE_LABEL, null);
                if (fingerprintEnableHeadlineLabel != null) {
                    enableSettings.setEnableHeadlineLabel(fingerprintEnableHeadlineLabel);
                }

                String fingerprintEnableDetailLabel = activity.sharedPreferences.getString(
                        KEY_ENABLE_DETAIL_LABEL, null);
                if (fingerprintEnableDetailLabel != null) {
                    enableSettings.setEnableDetailLabel(fingerprintEnableDetailLabel);
                }

                String fingerprintEnableButtonTitle = activity.sharedPreferences.getString(
                        KEY_ENABLE_BUTTON_TITLE, null);
                if (fingerprintEnableButtonTitle != null) {
                    enableSettings.setEnableButtonTitle(fingerprintEnableButtonTitle);
                }

                String fingerprintEnableAlertTitle = activity.sharedPreferences.getString(
                        KEY_ENABLE_ALERT_TITLE, null);
                if (fingerprintEnableAlertTitle != null) {
                    enableSettings.setAlertTitleText(fingerprintEnableAlertTitle);
                }

                String fingerprintEnableAlertMessage = activity.sharedPreferences.getString(
                        KEY_ENABLE_ALERT_MESSAGE, null);
                if (fingerprintEnableAlertMessage != null) {
                    enableSettings.setAlertMessageText(fingerprintEnableAlertMessage);
                }

                String fingerprintEnableAlertCancelText = activity.sharedPreferences.getString(
                        KEY_ENABLE_ALERT_CANCEL_BUTTON_TEXT, null);
                if (fingerprintEnableAlertCancelText != null) {
                    enableSettings.setAlertCancelButtonText(fingerprintEnableAlertCancelText);
                }

                String fingerprintEnableAlertSettingsText = activity.sharedPreferences.getString(
                        KEY_ENABLE_ALERT_SETTINGS_BUTTON_TEXT, null);
                if (fingerprintEnableAlertSettingsText != null) {
                    enableSettings.setAlertSettingsButtonText(fingerprintEnableAlertSettingsText);
                }

                String fingerprintEnableSkipButtonext = activity.sharedPreferences.getString(
                        KEY_SKIP_FOR_NOW_BUTTON_TITLE, null);
                if (fingerprintEnableSkipButtonext != null) {
                    enableSettings.setSkipForNowButtonTitle(fingerprintEnableSkipButtonext);
                }

            fingerprintSettings = new FingerprintSettings();
                String fingerprintHeadlineLabel = activity.sharedPreferences.getString(
                        KEY_FINGERPRINT_HEADLINE_LABEL, null);
                if (fingerprintHeadlineLabel != null) {
                    fingerprintSettings.setHeadlineLabel(fingerprintHeadlineLabel);
                }

            String fingerprintActionHandler = activity.sharedPreferences.getString(
                    KEY_GENERAL_ACTION_HANDLER, null);
            if (fingerprintActionHandler != null  && !fingerprintActionHandler.equals("")) {
                fingerprintActionHandler = FingerprintActionHandlerImpl.class.getName();
                fingerprintSettings.setActionHandler(fingerprintActionHandler);
            }

                String fingerprintFallbackButtonTitle = activity.sharedPreferences.getString(KEY_FINGERPRINT_BUTTON_TITLE
                        , null);
                if (fingerprintFallbackButtonTitle != null) {
                    fingerprintSettings.setFallbackButtonTitle(fingerprintFallbackButtonTitle);
                }

                boolean fallbackEnabled = activity.sharedPreferences.getBoolean(KEY_FINGERPRINT_BUTTON_ENABLED
                        , false);
                fingerprintSettings.setFallbackButtonEnabled(fallbackEnabled);

                String fingerprintDetailLabel = activity.sharedPreferences.getString(
                        KEY_FINGERPRINT_DETAIL_LABEL, null);
                if (fingerprintDetailLabel != null) {
                    fingerprintSettings.setDetailLabel(fingerprintDetailLabel);
                }

            fingerprintErrorSettings = new FingerprintErrorSettings();
                String fingerprintErrorHeadlineLabel = activity.sharedPreferences.getString(
                        KEY_ERROR_HEADLINE_LABEL, null);
                if (fingerprintErrorHeadlineLabel != null) {
                    fingerprintErrorSettings.setFingerprintErrorHeadlineLabel(
                            fingerprintErrorHeadlineLabel);
                }

                String fingerprintErrorDetailLabel = activity.sharedPreferences.getString(
                        KEY_ERROR_DETAIL_LABEL, null);
                if (fingerprintErrorDetailLabel != null) {
                    fingerprintErrorSettings.setFingerprintErrorDetailLabel(fingerprintErrorDetailLabel);
                }

                String fingerprintErrorResetButton = activity.sharedPreferences.getString(
                        KEY_ERROR_RESET_BUTTON, null);
                if (fingerprintErrorResetButton != null) {
                    fingerprintErrorSettings.setFingerprintErrorResetButtonTitle(
                            fingerprintErrorResetButton);
                }
                boolean resetEnabled = activity.sharedPreferences.getBoolean(
                        KEY_ERROR_RESET_ENABLED, false);
                fingerprintErrorSettings.setFingerprintErrorResetEnabled(resetEnabled);
        }

        enableSettings.saveToIntent(i);
        fingerprintSettings.saveToIntent(i);
        fingerprintErrorSettings.saveToIntent(i);

        activity.startActivityForResult(i, requestCode);
    }

    /**
     * Starts an enable fingerprint activity with intent configured based on the shared preferences.
     */
    @Override
    public void startEnableFingerprint() {
        int requestCode = OnboardingActivity.FINGERPRINT_ENABLE;
        Intent i = new Intent();
        i.setComponent(new ComponentName(activity.getPackageName(), "com.sap.cloud.mobile.onboarding.fingerprint.EnableFingerprintActivity"));

        EnableFingerprintSettings enableSettings;

        if (activity.isUITest) {
            enableSettings = new EnableFingerprintSettings(activity.getIntent());
        } else {
            enableSettings = new EnableFingerprintSettings();
            String fingerprintEnableHeadlineLabel = activity.sharedPreferences.getString(
                    KEY_ENABLE_HEADLINE_LABEL, null);
            if (fingerprintEnableHeadlineLabel != null) {
                enableSettings.setEnableHeadlineLabel(fingerprintEnableHeadlineLabel);
            }

            String fingerprintEnableDetailLabel = activity.sharedPreferences.getString(
                    KEY_ENABLE_DETAIL_LABEL, null);
            if (fingerprintEnableDetailLabel != null) {
                enableSettings.setEnableDetailLabel(fingerprintEnableDetailLabel);
            }

            String fingerprintEnableButtonTitle = activity.sharedPreferences.getString(
                    KEY_ENABLE_BUTTON_TITLE, null);
            if (fingerprintEnableButtonTitle != null) {
                enableSettings.setEnableButtonTitle(fingerprintEnableButtonTitle);
            }

            String fingerprintEnableAlertTitle = activity.sharedPreferences.getString(
                    KEY_ENABLE_ALERT_TITLE, null);
            if (fingerprintEnableAlertTitle != null) {
                enableSettings.setAlertTitleText(fingerprintEnableAlertTitle);
            }

            String fingerprintEnableAlertMessage = activity.sharedPreferences.getString(
                    KEY_ENABLE_ALERT_MESSAGE, null);
            if (fingerprintEnableAlertMessage != null) {
                enableSettings.setAlertMessageText(fingerprintEnableAlertMessage);
            }

            String fingerprintEnableAlertCancelText = activity.sharedPreferences.getString(
                    KEY_ENABLE_ALERT_CANCEL_BUTTON_TEXT, null);
            if (fingerprintEnableAlertCancelText != null) {
                enableSettings.setAlertCancelButtonText(fingerprintEnableAlertCancelText);
            }

            String fingerprintEnableAlertSettingsText = activity.sharedPreferences.getString(
                    KEY_ENABLE_ALERT_SETTINGS_BUTTON_TEXT, null);
            if (fingerprintEnableAlertSettingsText != null) {
                enableSettings.setAlertSettingsButtonText(fingerprintEnableAlertSettingsText);
            }

            String fingerprintEnableSkipButtonext = activity.sharedPreferences.getString(
                    KEY_SKIP_FOR_NOW_BUTTON_TITLE, null);
            if (fingerprintEnableSkipButtonext != null) {
                enableSettings.setSkipForNowButtonTitle(fingerprintEnableSkipButtonext);
            }
        }

        enableSettings.saveToIntent(i);

        activity.startActivityForResult(i, requestCode);
    }

    /**
     * Starts a fingerprint error activity with intent configured based on the shared preferences.
     */
    @Override
    public void startFingerprintError() {
        int requestCode = OnboardingActivity.FINGERPRINT_ERROR;
        Intent i = new Intent();
        i.setComponent(new ComponentName(activity.getPackageName(), "com.sap.cloud.mobile.onboarding.fingerprint.FingerprintErrorActivity"));

        FingerprintErrorSettings fingerprintErrorSettings;

        if (activity.isUITest) {
            fingerprintErrorSettings = new FingerprintErrorSettings(activity.getIntent());
        } else {
            fingerprintErrorSettings = new FingerprintErrorSettings();
            String fingerprintErrorHeadlineLabel = activity.sharedPreferences.getString(
                    KEY_ERROR_HEADLINE_LABEL, null);
            if (fingerprintErrorHeadlineLabel != null) {
                fingerprintErrorSettings.setFingerprintErrorHeadlineLabel(
                        fingerprintErrorHeadlineLabel);
            }

            String fingerprintErrorDetailLabel = activity.sharedPreferences.getString(
                    KEY_ERROR_DETAIL_LABEL, null);
            if (fingerprintErrorDetailLabel != null) {
                fingerprintErrorSettings.setFingerprintErrorDetailLabel(fingerprintErrorDetailLabel);
            }

            String fingerprintErrorResetButton = activity.sharedPreferences.getString(
                    KEY_ERROR_RESET_BUTTON, null);
            if (fingerprintErrorResetButton != null) {
                fingerprintErrorSettings.setFingerprintErrorResetButtonTitle(
                        fingerprintErrorResetButton);
            }
            boolean resetEnabled = activity.sharedPreferences.getBoolean(
                    KEY_ERROR_RESET_ENABLED, false);
            fingerprintErrorSettings.setFingerprintErrorResetEnabled(resetEnabled);
        }

        fingerprintErrorSettings.saveToIntent(i);

        activity.startActivityForResult(i, requestCode);
    }

    final static protected String KEY_EULA_HEADLINE = "eula_screen_headline";
    final static protected String KEY_EULA_BUTTON_CONFIRM = "eula_screen_button_confirm";
    final static protected String KEY_EULA_BUTTON_REJECT = "eula_screen_button_reject";
    final static protected String KEY_EULA_URL = "eula_screen_url";

    /**
     * Starts an EULA screen activity with intent configured based on the shared preferences.
     */
    @Override
    public void startEULAScreen() {


        Intent i = new Intent();
        i.setComponent(new ComponentName(activity.getPackageName(),
                "com.sap.cloud.mobile.onboarding.eula.EULAScreenActivity"));
        EULAScreenSettings eulaScreenSettings;

        if (activity.isUITest) {
            eulaScreenSettings = new EULAScreenSettings(activity.getIntent());
        } else {
            eulaScreenSettings = new EULAScreenSettings();

            String titleString = activity.sharedPreferences.getString(KEY_EULA_HEADLINE, null);
            if (titleString != null && !titleString.equals("")) {
                eulaScreenSettings.setEULATitle(titleString);
            }

            String eulaConfirmButton = activity.sharedPreferences.getString(KEY_EULA_BUTTON_CONFIRM, null);
            if (eulaConfirmButton != null && !eulaConfirmButton.equals("")) {
                eulaScreenSettings.setEULAButtonConfirm(eulaConfirmButton);
            }

            String eulaRejectButton = activity.sharedPreferences.getString(KEY_EULA_BUTTON_REJECT, null);
            if (eulaRejectButton != null && !eulaRejectButton.equals("")) {
                eulaScreenSettings.setEULAButtonReject(eulaRejectButton);
            }

            String eulaUrl = activity.sharedPreferences.getString(KEY_EULA_URL, null);
            if (eulaUrl != null && !eulaUrl.equals("")) {
                eulaScreenSettings.setEULAUrl(eulaUrl);
            }
        }

        eulaScreenSettings.saveToIntent(i);
        activity.startActivityForResult(i, OnboardingActivity.EULA);
    }

    @Override
    public void startQRConfirmScreen() {
        Intent i = new Intent();
        i.setComponent(new ComponentName(activity.getPackageName(),
                "com.sap.cloud.mobile.onboarding.qrcodereader.QRCodeConfirmActivity"));
        QRCodeConfirmSettings confirmScreenSettings;

        if (activity.isUITest) {
            confirmScreenSettings = new QRCodeConfirmSettings(activity.getIntent());
        } else {
            confirmScreenSettings = new QRCodeConfirmSettings();
        }

        confirmScreenSettings.setActionHandler("com.sap.cloud.mobile.fiori.demo.onboarding.WelcomeScreenActionHandlerImpl");

        confirmScreenSettings.saveToIntent(i);
        activity.startActivityForResult(i, OnboardingActivity.QRCONFIRM);
    }

    /**
     * Start the activity that represents the user's app
     */
    @Override
    public void startAfterOnboarding() {
        activity.startActivity(new Intent(activity.getApplicationContext(),
                com.sap.cloud.mobile.fiori.demo.onboarding.AfterOnboardingActivity.class));
    }

    final static protected String KEY_LAUNCHSCREEN_TYPE = "welcome_screen_type";
    final static protected String KEY_LAUNCHSCREEN_DEMO_MODE = "demo";

    final protected static String KEY_LAUNCHSCREEN_HEADLINE = "launchscreen_headline";
    final protected static String KEY_LAUNCHSCREEN_ANIMATION_INTERVAL =
            "launchscreen_animation_interval";
    final protected static String KEY_LAUNCHSCREEN_IMAGES = "launchscreen_images";
    final protected static String KEY_LAUNCHSCREEN_TITLES = "launchscreen_titles";
    final protected static String KEY_LAUNCHSCREEN_DESCRIPTIONS = "launchscreen_descriptions";
    final protected static String KEY_LAUNCHSCREEN_BUTTON_PRIMARY = "launchscreen_button_primary";
    final protected static String KEY_LAUNCHSCREEN_BUTTON_DEMO = "launchscreen_button_demo";
    final protected static String KEY_LAUNCHSCREEN_URL_TERMSOFSERVICE =
            "launchscreen_url_termsofservice";
    final protected static String KEY_LAUNCHSCREEN_URL_PRIVACY = "launchscreen_url_privacy";

    final protected static String KEY_QRCODE_READ_INVALID_TITLE = "qrcode_read_invalidQR_title";
    final protected static String KEY_QRCODE_READ_INVALID_MESSAGE = "qrcode_read_invalidQR_message";
    final protected static String KEY_QRCODE_READ_INVALID_CONTINUE_BUTTON = "qrcode_read_invalidQR_continue";
    final protected static String KEY_QRCODE_READ_AUTOFOCUS = "qrcode_read_autofocus";
    final protected static String KEY_QRCODE_READ_SKIP_CONFIRM = "qrcode_read_skip_confirm";
    final protected static String KEY_QRCODE_READ_CONFIRM_ACTIVITY = "qrcode_read_confirm_activity";
    final protected static String KEY_QRCODE_READ_BARCODE_FORMAT = "barcode_format";
    private static int BARCODE_FORMAT_QR = 256;
    final protected static String KEY_WAIT_FOR_BARCODE_DETECTOR_IN_SEC = "wait_for_barcode_detector";
    private static int WAIT_FOR_BARCODE_DETECTOR_IN_SEC = 20;

    protected final static String KEY_QRCODE_CONFIRM_HEADLINE = "qrcode_confirm_headline";
    protected final static String KEY_QRCODE_CONFIRM_DETAIL = "qrcode_confirm_detail";
    protected final static String KEY_QRCODE_CONFIRM_CONTINUE = "qrcode_confirm_continue";


    /**
     * Starts a Launch screen activity with intent configured based on the shared preferences.
     */
    @Override
    public void startLaunchScreen() {
        Intent i = new Intent();
        i.setComponent(new ComponentName(activity.getPackageName(),
                "com.sap.cloud.mobile.onboarding.launchscreen.LaunchScreenActivity"));
        LaunchScreenSettings settings;
        ActivationSettings activationSettings;
        QRCodeReaderSettings qrCodeReaderSettings;
        QRCodeConfirmSettings qrCodeConfirmSettings;

        if (activity.isUITest) {
            settings = new LaunchScreenSettings(activity.getIntent());
            activationSettings = new ActivationSettings(activity.getIntent());
            qrCodeReaderSettings = new QRCodeReaderSettings(activity.getIntent());
            qrCodeConfirmSettings = new QRCodeConfirmSettings(activity.getIntent());

        } else {
            settings = new LaunchScreenSettings();
            String type = activity.sharedPreferences.getString(KEY_LAUNCHSCREEN_TYPE, OnboardingType.ACTIVATION_MAIL_ONBOARDING.toString());
            settings.setWelcomeScreenType(OnboardingType.valueOf(type));

            Boolean demo = activity.sharedPreferences.getBoolean(
                    KEY_LAUNCHSCREEN_DEMO_MODE, true);
            settings.setDemoAvailable(demo);

            String generalActionHandler = activity.sharedPreferences.getString(KEY_GENERAL_ACTION_HANDLER, null);
            if (generalActionHandler != null && !generalActionHandler.equals("")) {
                generalActionHandler = WelcomeScreenActionHandlerImpl.class.getName();
                settings.setActionHandler(generalActionHandler);
            }

            String headlineLabel = activity.sharedPreferences.getString(KEY_LAUNCHSCREEN_HEADLINE, null);
            if (headlineLabel != null) {
                settings.setLaunchScreenHeadline(headlineLabel);
            }

            String anim = activity.sharedPreferences.getString(KEY_LAUNCHSCREEN_ANIMATION_INTERVAL, null);
            if (anim != null && anim.length() > 0) {
                try {
                    int animationInterval = Integer.parseInt(anim);
                    settings.setLaunchScreenAnimationInterval(animationInterval);
                } catch (NumberFormatException e) {
                    //ignore
                }
            }

            String imagesString = activity.sharedPreferences.getString(KEY_LAUNCHSCREEN_IMAGES, null);
            if (imagesString != null) {
                String[] imagesStringArray = imagesString.split(";");
                int[] images = new int[imagesStringArray.length];
                for (int c = 0; c < imagesStringArray.length; c++) {
                    images[c] = activity.getResources().getIdentifier(imagesStringArray[c],
                            "drawable", activity.getPackageName());
                }
                settings.setLaunchScreenImages(images);
            }

            String titlesString = activity.sharedPreferences.getString(KEY_LAUNCHSCREEN_TITLES, null);
            if (titlesString != null) {
                String[] titles = titlesString.split(";");
                settings.setLaunchScreenTitles(titles);
            }

            String descriptionsString = activity.sharedPreferences.getString(KEY_LAUNCHSCREEN_DESCRIPTIONS, null);
            if (descriptionsString != null) {
                String[] descriptions = descriptionsString.split(";");
                settings.setLaunchScreenDescriptions(descriptions);
            }

            String primaryButtonLabel = activity.sharedPreferences.getString(KEY_LAUNCHSCREEN_BUTTON_PRIMARY, null);
            if (primaryButtonLabel != null) {
                settings.setLaunchScreenPrimaryButton(primaryButtonLabel);
            }

            String demoButtonLabel = activity.sharedPreferences.getString(KEY_LAUNCHSCREEN_BUTTON_DEMO, null);
            if (demoButtonLabel != null) {
                settings.setLaunchScreenDemoButton(demoButtonLabel);
            }

            String urlTermsOfService = activity.sharedPreferences.getString(KEY_LAUNCHSCREEN_URL_TERMSOFSERVICE, null);
            if (urlTermsOfService != null && urlTermsOfService.length() > 0) {
                settings.setLaunchScreenUrlTermsOfService(urlTermsOfService);
            }

            String urlPrivacy = activity.sharedPreferences.getString(KEY_LAUNCHSCREEN_URL_PRIVACY, null);
            if (urlPrivacy != null && urlPrivacy.length() > 0) {
                settings.setLaunchScreenUrlPrivacy(urlPrivacy);
            }

            activationSettings = new ActivationSettings();
            String titleString;
            String instruction;
            String imageDescr;
            String email;
            String startButton;
            switch (settings.getWelcomeScreenType()) {
                case ACTIVATION_MAIL_ONBOARDING:
                    titleString = activity.sharedPreferences.getString(KEY_ACTIVATION_EMAIL_TITLE, null);
                    if (titleString != null && !titleString.equals("")) {
                        activationSettings.setActivationTitle(titleString);
                    }
                    instruction = activity.sharedPreferences.getString(KEY_ACTIVATION_EMAIL_INSTRUCTION, null);
                    if (instruction != null && !instruction.equals("")) {
                        activationSettings.setActivationInstruction(instruction);
                    }
                    imageDescr = activity.sharedPreferences.getString(KEY_ACTIVATION_EMAIL_GRAPHIC_ELEMENT, null);
                    if (imageDescr != null) {
                        int image = activity.getResources().getIdentifier(imageDescr,
                                "drawable", activity.getPackageName());
                        activationSettings.setActivationImage(image);
                    }
                    email = activity.sharedPreferences.getString(KEY_ACTIVATION_EMAIL_ADDRESS, null);
                    if (email != null && !email.equals("")) {
                        activationSettings.setActivationEmail(email);
                    }
                    break;
                case BARCODE_OR_DISCOVERY_SERVICE_ONBOARDING:
                    titleString = activity.sharedPreferences.getString(KEY_ACTIVATION_CHOOSE_TITLE, null);
                    if (titleString != null && !titleString.equals("")) {
                        activationSettings.setActivationTitle(titleString);
                    }
                    instruction = activity.sharedPreferences.getString(KEY_ACTIVATION_CHOOSE_INSTRUCTION, null);
                    if (instruction != null && !instruction.equals("")) {
                        activationSettings.setActivationInstruction(instruction);
                    }
                    titleString = activity.sharedPreferences.getString(KEY_ACTIVATION_DISCOVERY_TITLE, null);
                    if (titleString != null && !titleString.equals("")) {
                        activationSettings.setActivationDiscoveryTitle(titleString);
                    }
                    instruction = activity.sharedPreferences.getString(KEY_ACTIVATION_DISCOVERY_INSTRUCTION, null);
                    if (instruction != null && !instruction.equals("")) {
                        activationSettings.setActivationDiscoveryInstruction(instruction);
                    }
                    String nextButton = activity.sharedPreferences.getString(KEY_ACTIVATION_CHOOSE_PRIMARY_BUTTON_TITLE,
                            null);
                    if (nextButton != null && !nextButton.equals("")) {
                        activationSettings.setActivationNextTitle(nextButton);
                    }
                    String scanButton = activity.sharedPreferences.getString(KEY_ACTIVATION_CHOOSE_SECONDARY_BUTTON_TITLE,
                            null);
                    if (scanButton != null && !scanButton.equals("")) {
                        activationSettings.setActivationScanTitle(scanButton);
                    }
                    break;
                case DISCOVERY_SERVICE_ONBOARDING:
                    titleString = activity.sharedPreferences.getString(KEY_ACTIVATION_DISCOVERY_TITLE, null);
                    if (titleString != null && !titleString.equals("")) {
                        activationSettings.setActivationTitle(titleString);
                    }
                    instruction = activity.sharedPreferences.getString(KEY_ACTIVATION_DISCOVERY_INSTRUCTION, null);
                    if (instruction != null && !instruction.equals("")) {
                        activationSettings.setActivationInstruction(instruction);
                    }
                    startButton = activity.sharedPreferences.getString(KEY_ACTIVATION_DISCOVERY_BUTTON_TITLE,
                            null);
                    if (startButton != null && !startButton.equals("")) {
                        activationSettings.setActivationStartTitle(startButton);
                    }
                    String emailHint = activity.sharedPreferences.getString(KEY_ACTIVATION_DISCOVERY_EMAIL_HINT,
                            null);
                    if (emailHint != null && !emailHint.equals("")) {
                        activationSettings.setActivationEmailHint(emailHint);
                    }
                    String discoveryService = activity.sharedPreferences.getString(KEY_ACTIVATION_DISCOVERY_SERVICE,
                            null);
                    if (discoveryService != null && !discoveryService.equals("")) {
                        activationSettings.setActivationDiscoveryService(discoveryService);
                    }
                    break;
                case BARCODE_ONBOARDING:
                    titleString = activity.sharedPreferences.getString(KEY_ACTIVATION_BARCODE_TITLE, null);
                    if (titleString != null && !titleString.equals("")) {
                        activationSettings.setActivationTitle(titleString);
                    }
                    instruction = activity.sharedPreferences.getString(KEY_ACTIVATION_BARCODE_INSTRUCTION, null);
                    if (instruction != null && !instruction.equals("")) {
                        activationSettings.setActivationInstruction(instruction);
                    }
                    startButton = activity.sharedPreferences.getString(KEY_ACTIVATION_BARCODE_BUTTON_TITLE,
                            null);
                    if (startButton != null && !startButton.equals("")) {
                        activationSettings.setActivationStartTitle(startButton);
                    }
                    break;
            }

            qrCodeReaderSettings = new QRCodeReaderSettings();

            String qrCodeActionHandler = activity.sharedPreferences.getString(KEY_QRCODE_READ_ACTION_HANDLER, null);
            if (qrCodeActionHandler != null) {
                qrCodeReaderSettings.setQrCodeReaderActionHandler(qrCodeActionHandler);
            }

            String qrCodeConfirmActivity = activity.sharedPreferences.getString(KEY_QRCODE_READ_CONFIRM_ACTIVITY, null);
            if (qrCodeConfirmActivity != null && !qrCodeConfirmActivity.equals("")) {
                qrCodeReaderSettings.setQrCodeConfirmActivity(qrCodeConfirmActivity);
            }

            String invalidTitle = activity.sharedPreferences.getString(KEY_QRCODE_READ_INVALID_TITLE, null);
            if (invalidTitle != null) {
                qrCodeReaderSettings.setInvalidQRTitle(invalidTitle);
            }

            String invalidMessage = activity.sharedPreferences.getString(KEY_QRCODE_READ_INVALID_MESSAGE, null);
            if (invalidMessage != null) {
                qrCodeReaderSettings.setInvalidQRMessage(invalidMessage);
            }

            String invalidContinueButton = activity.sharedPreferences.getString(KEY_QRCODE_READ_INVALID_CONTINUE_BUTTON, null);
            if (invalidContinueButton != null) {
                qrCodeReaderSettings.setOkButtonString(invalidContinueButton);
            }

            Boolean useAutoFocus = activity.sharedPreferences.getBoolean(KEY_QRCODE_READ_AUTOFOCUS,true);
                qrCodeReaderSettings.setAutoFocus(useAutoFocus);

            Boolean skipConfirm = activity.sharedPreferences.getBoolean(KEY_QRCODE_READ_SKIP_CONFIRM,false);
            qrCodeReaderSettings.setSkipConfirmScreen(skipConfirm);

            String barcodeFormat = activity.sharedPreferences.getString(KEY_QRCODE_READ_BARCODE_FORMAT, null);
            qrCodeReaderSettings.setBarcodeFormat(barcodeFormat == null ? BARCODE_FORMAT_QR : Integer.parseInt(barcodeFormat));

            String waitForBarcodeDetectorInSec = activity.sharedPreferences.getString(KEY_WAIT_FOR_BARCODE_DETECTOR_IN_SEC, null);
            qrCodeReaderSettings.setBarcodeDetectorBuilderTimeout(waitForBarcodeDetectorInSec == null ?
                    WAIT_FOR_BARCODE_DETECTOR_IN_SEC : Integer.parseInt(waitForBarcodeDetectorInSec));

            qrCodeConfirmSettings = new QRCodeConfirmSettings();
            String confirmHeadline = activity.sharedPreferences.getString(KEY_QRCODE_CONFIRM_HEADLINE, null);
            if (confirmHeadline != null && !confirmHeadline.equals("")) {
                qrCodeConfirmSettings.setQrCodeConfirmHeadline(confirmHeadline);
            }

            String confirmDetail = activity.sharedPreferences.getString(KEY_QRCODE_CONFIRM_DETAIL, null);
            if (confirmDetail != null && !confirmDetail.equals("")) {
                qrCodeConfirmSettings.setQrCodeConfirmDetail(confirmDetail);
            }

            String confirmContinue = activity.sharedPreferences.getString(KEY_QRCODE_CONFIRM_CONTINUE, null);
            if (confirmContinue != null && !confirmContinue.equals("")) {
                qrCodeConfirmSettings.setQrCodeConfirmContinueTitle(confirmContinue);
            }
        }

        settings.saveToIntent(i);
        activationSettings.saveToIntent(i);
        qrCodeReaderSettings.saveToIntent(i);
        qrCodeConfirmSettings.saveToIntent(i);

        activity.startActivityForResult(i, OnboardingActivity.LAUNCH_SCREEN);
    }
}
