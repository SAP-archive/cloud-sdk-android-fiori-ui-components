package com.sap.cloud.mobile.fiori.demo.onboarding;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.sap.cloud.mobile.fiori.demo.ApiDemos;
import com.sap.cloud.mobile.fiori.demo.R;

import java.util.List;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class OnboardingSettingsActivity extends AppCompatPreferenceActivity {

    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    @NonNull
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener =
            new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object value) {
                    String stringValue = value.toString();

                    if (preference instanceof ListPreference) {
                        // For list preferences, look up the correct display value in
                        // the preference's 'entries' list.
                        ListPreference listPreference = (ListPreference) preference;
                        int index = listPreference.findIndexOfValue(stringValue);

                        // Set the summary to reflect the new value.
                        preference.setSummary(
                                index >= 0
                                        ? listPreference.getEntries()[index]
                                        : null);

                    } else {
                        // For all other preferences, set the summary to the value's
                        // simple string representation.
                        preference.setSummary(stringValue);
                    }
                    return true;
                }
            };

    /**
     * Helper method to determine if the device has an extra-large screen. For
     * example, 10" tablets are extra-large.
     */
    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    /**
     * Binds a preference's summary to its value. More specifically, when the
     * preference's value is changed, its summary (line of text below the
     * preference title) is updated to reflect the value. The summary is also
     * immediately updated upon calling this method. The exact display format is
     * dependent on the type of preference.
     *
     * @see #sBindPreferenceSummaryToValueListener
     */
    private static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onMenuItemSelected(int featureId, @NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (!super.onMenuItemSelected(featureId, item)) {
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                upIntent.putExtra(ApiDemos.COM_SAP_CLOUD_MOBILE_FIORI_DEMO_PATH, "title_onboarding_test");
                NavUtils.navigateUpTo(this, upIntent);
            }
            return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.pref_headers, target);
    }

    /**
     * This fragment shows general preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    protected boolean isValidFragment(String fragmentName) {
        return PreferenceFragment.class.getName().equals(fragmentName)
                || GeneralPreferenceFragment.class.getName().equals(fragmentName)
                || PasscodePreferenceFragment.class.getName().equals(fragmentName)
                || PasscodePolicyPreferenceFragment.class.getName().equals(fragmentName)
                || FingerprintPreferenceFragment.class.getName().equals(fragmentName)
                || FingerprintUIPreferenceFragment.class.getName().equals(fragmentName)
                || QRCodeConfirmPreferenceFragment.class.getName().equals(fragmentName)
                || QRCodeReadPreferenceFragment.class.getName().equals(fragmentName)
                || EULAScreenPreferenceFragment.class.getName().equals(fragmentName)
                || LaunchScreenPreferenceFragment.class.getName().equals(fragmentName)
                || ActivationEmailLinkPreferenceFragment.class.getName().equals(fragmentName)
                || ActivationChooseAnOptionPreferenceFragment.class.getName().equals(fragmentName)
                || ActivationDiscoveryServicePreferenceFragment.class.getName().equals(fragmentName)
                || ActivationBarcodePreferenceFragment.class.getName().equals(fragmentName);
    }

    /**
     * This fragment shows general preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class GeneralPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);
            setHasOptionsMenu(true);

            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(findPreference("delay"));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(findPreference("current_passcode"));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(findPreference("general_action_handler"));
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), OnboardingSettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This fragment shows passcode preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class PasscodePreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_passcode_process);
            setHasOptionsMenu(true);
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(findPreference("passcode_done_button_text"));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(findPreference("passcode_skip_button_text"));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(findPreference("passcode_create_title"));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(findPreference("passcode_create_instruction_text"));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(findPreference("passcode_create_validation_action_handler"));
            SwitchPreference resetEnabled = (SwitchPreference) findPreference("passcode_reset");
            resetEnabled.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference arg0, @NonNull Object resetEnabled_in) {
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    boolean isEnabled = ((Boolean) resetEnabled_in).booleanValue();
                    SharedPreferences.Editor e = sharedPreferences.edit();
                    e.putBoolean("passcode_reset_enabled", isEnabled);
                    e.commit();
                    return true;
                }
            });

            SwitchPreference finalDisabled = (SwitchPreference) findPreference("passcode_final_disabled_pref");
            finalDisabled.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, @NonNull Object finalDisabled) {
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    boolean isEnabled = ((Boolean) finalDisabled).booleanValue();
                    SharedPreferences.Editor e = sharedPreferences.edit();
                    e.putBoolean("passcode_final_disabled", isEnabled);
                    e.commit();
                    return true;
                }
            });


            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(findPreference("passcode_lowercase_label"));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(findPreference("passcode_uppercase_label"));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(findPreference("passcode_digit_label"));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(findPreference("passcode_symbol_label"));

            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(findPreference("passcode_enter_title"));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(findPreference("passcode_enter_reset_button_text"));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(findPreference("passcode_enter_done_button_text"));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(findPreference("passcode_enter_instruction_text"));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(findPreference("passcode_error_message_text"));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(findPreference("passcode_alert_dialog_header_text"));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(findPreference("passcode_alert_dialog_body_text"));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(findPreference("passcode_alert_dialog_button_text"));


            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(findPreference("passcode_confirm_title"));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(findPreference("passcode_confirm_done_button_text"));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(findPreference("passcode_confirm_instruction_text"));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(findPreference("passcode_confirm_mismatch_text"));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(findPreference("passcode_confirm_button_cancel_text"));

            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(findPreference("passcode_change_skip_button_text"));

            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(findPreference("passcode_alert_dialog_null_policy_header_text"));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(findPreference("passcode_alert_dialog_null_policy_desc_text"));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(findPreference("passcode_alert_dialog_null_policy_retry_button_text"));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(findPreference("passcode_alert_dialog_null_policy_cancel_button_text"));
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), OnboardingSettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

    }

    /**
     * This fragment shows passcode policy preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class PasscodePolicyPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_passcode_policy);
            setHasOptionsMenu(true);
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference("minLength"));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference("minUnique"));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference("retryLimit"));
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), OnboardingSettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This fragment shows confirm fingerprint preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class FingerprintPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_fingerprint);
            setHasOptionsMenu(true);

        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), OnboardingSettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This fragment shows confirm fingerprint UI preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class FingerprintUIPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_fingerprint_ui);
            setHasOptionsMenu(true);
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_ENABLE_HEADLINE_LABEL));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_ENABLE_DETAIL_LABEL));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_ENABLE_BUTTON_TITLE));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_FINGERPRINT_HEADLINE_LABEL));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_FINGERPRINT_DETAIL_LABEL));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_ERROR_HEADLINE_LABEL));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_ERROR_DETAIL_LABEL));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_ERROR_RESET_BUTTON));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference("confirm_try_password_button"));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference("enable_alert_title"));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference("enable_alert_message"));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference("enable_alert_cancel_button_text"));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference("enable_alert_settings_button_text"));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference("skip_for_now_button_title"));

        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), OnboardingSettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This fragment shows QR code reader preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class QRCodeConfirmPreferenceFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_qrcode_confirm);
            setHasOptionsMenu(true);
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_QRCODE_CONFIRM_HEADLINE));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_QRCODE_CONFIRM_DETAIL));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_QRCODE_CONFIRM_CONTINUE));
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), OnboardingSettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This fragment shows QR code reader preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class QRCodeReadPreferenceFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_qrcode_read);
            setHasOptionsMenu(true);
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_QRCODE_READ_INVALID_TITLE));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_QRCODE_READ_INVALID_MESSAGE));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_QRCODE_READ_INVALID_CONTINUE_BUTTON));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_QRCODE_READ_BARCODE_FORMAT));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_QRCODE_READ_ACTION_HANDLER));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_QRCODE_READ_CONFIRM_ACTIVITY));

        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), OnboardingSettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This fragment shows QR code reader preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class EULAScreenPreferenceFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_eula_screen);
            setHasOptionsMenu(true);
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_EULA_HEADLINE));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_EULA_BUTTON_CONFIRM));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_EULA_BUTTON_REJECT));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_EULA_URL));

        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), OnboardingSettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This fragment shows Launch screen preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class LaunchScreenPreferenceFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_launchscreen);
            setHasOptionsMenu(true);
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_LAUNCHSCREEN_HEADLINE));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_LAUNCHSCREEN_ANIMATION_INTERVAL));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_LAUNCHSCREEN_IMAGES));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_LAUNCHSCREEN_TITLES));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_LAUNCHSCREEN_DESCRIPTIONS));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_LAUNCHSCREEN_BUTTON_PRIMARY));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_LAUNCHSCREEN_BUTTON_DEMO));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_LAUNCHSCREEN_URL_TERMSOFSERVICE));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_LAUNCHSCREEN_URL_PRIVACY));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_LAUNCHSCREEN_TYPE));
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), OnboardingSettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This fragment shows Activation screen (email link) preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class ActivationEmailLinkPreferenceFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_activation_email);
            setHasOptionsMenu(true);
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_ACTIVATION_EMAIL_TITLE));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_ACTIVATION_EMAIL_INSTRUCTION));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_ACTIVATION_EMAIL_GRAPHIC_ELEMENT));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_ACTIVATION_EMAIL_ADDRESS));
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), OnboardingSettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This fragment shows Activation screen (choose an option) preferences only.
     * It is used when the activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class ActivationChooseAnOptionPreferenceFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_activation_choose_an_option);
            setHasOptionsMenu(true);
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_ACTIVATION_CHOOSE_TITLE));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_ACTIVATION_CHOOSE_INSTRUCTION));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_ACTIVATION_CHOOSE_PRIMARY_BUTTON_TITLE));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_ACTIVATION_CHOOSE_SECONDARY_BUTTON_TITLE));
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), OnboardingSettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This fragment shows Activation screen (choose an option) preferences only.
     * It is used when the activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class ActivationDiscoveryServicePreferenceFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_activation_discovery_service);
            setHasOptionsMenu(true);
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_ACTIVATION_DISCOVERY_TITLE));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_ACTIVATION_DISCOVERY_INSTRUCTION));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_ACTIVATION_DISCOVERY_BUTTON_TITLE));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_ACTIVATION_DISCOVERY_EMAIL_HINT));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_ACTIVATION_DISCOVERY_SERVICE));
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), OnboardingSettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This fragment shows Activation screen (choose an option) preferences only.
     * It is used when the activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class ActivationBarcodePreferenceFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_activation_barcode);
            setHasOptionsMenu(true);
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_ACTIVATION_BARCODE_TITLE));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_ACTIVATION_BARCODE_INSTRUCTION));
            ((OnboardingSettingsActivity) getActivity()).bindPreferenceSummaryToValue(
                    findPreference(OnboardingView.KEY_ACTIVATION_BARCODE_BUTTON_TITLE));
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), OnboardingSettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }
}
