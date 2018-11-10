package com.sap.cloud.mobile.fiori.demo.onboarding;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;

import com.sap.cloud.mobile.fiori.demo.DemoApplication;
import com.sap.cloud.mobile.onboarding.fingerprint.FingerprintActionHandler;
import com.sap.cloud.mobile.onboarding.fingerprint.FingerprintException;
import com.sap.cloud.mobile.onboarding.passcode.PasscodeInputMode;
import com.sap.cloud.mobile.onboarding.utility.ActivityResultActionHandler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


/**
 * Created by i321396 on 08/03/2018.
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerprintActionHandlerImpl implements FingerprintActionHandler,ActivityResultActionHandler {
    KeyHandler keyHandler;
    private boolean responseAvailable;
    final private java.lang.Object SYNC = new java.lang.Object();

    @Override
    public Cipher getCipher(@NonNull Fragment fragment) throws InterruptedException, FingerprintException {
        try {
            ((DemoApplication) fragment.getActivity().getApplication()).delay();
            keyHandler = new KeyHandler();
            keyHandler.getInstanceOfCipher();
            keyHandler.initKeyGenerator();
            keyHandler.loadKeyStore();
            if (KeyHandler.passcodeInputMode == PasscodeInputMode.CREATE) {
                keyHandler.generateKey();
                keyHandler.initCipherInEncryptMode();
                keyHandler.saveIVToPref();
            } else {
                keyHandler.loadIVFromPref();
                keyHandler.setSecretKey();
                keyHandler.initCipherInDecryptMode();
            }

            return keyHandler.cipher;
        } catch (KeyStoreException e) {
            throw new FingerprintException(e);
        } catch (NoSuchProviderException e) {
            throw new FingerprintException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new FingerprintException(e);
        } catch (NoSuchPaddingException e) {
            throw new FingerprintException(e);
        } catch (InvalidKeyException e) {
            throw new FingerprintException(e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new FingerprintException(e);
        } catch (CertificateException e) {
            throw new FingerprintException(e);
        } catch (IOException e) {
            throw new FingerprintException(e);
        } catch (UnrecoverableKeyException e) {
            throw new FingerprintException(e);
        }
    }

    @Override
    public void startDone(@NonNull Fragment fragment, Cipher cipher) throws InterruptedException, FingerprintException {
        try {
            ((DemoApplication) fragment.getActivity().getApplication()).delay();
            if (KeyHandler.passcodeInputMode == PasscodeInputMode.CREATE) {
                keyHandler = new KeyHandler();
                keyHandler.setCipher(cipher);
                char []passcode = keyHandler.generatePasscodeAndInvokeCallback(true);
                ((DemoApplication) fragment.getActivity().getApplication()).setCurrentPasscode(passcode);
            }
            else {
                keyHandler = new KeyHandler();
                keyHandler.setCipher(cipher);
                keyHandler.generatePasscodeAndInvokeCallback(false);
            }
        } catch (UnsupportedEncodingException e) {
            throw new FingerprintException(e);
        } catch (BadPaddingException e) {
            throw new FingerprintException(e);
        } catch (IllegalBlockSizeException e) {
            throw new FingerprintException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new FingerprintException(e);
        } catch (KeyStoreException e) {
            throw new FingerprintException(e);
        } catch (NoSuchProviderException e) {
            throw new FingerprintException(e);
        }
    }
    @Override
    public void fallback(@NonNull Fragment fragment) throws InterruptedException {
        ((DemoApplication) fragment.getActivity().getApplication()).delay();
        Intent intent = new Intent(fragment.getContext(),
                com.sap.cloud.mobile.onboarding.passcode.EnterPasscodeActivity.class);
        fragment.getActivity().startActivityForResult(intent,OnboardingActivity.ENTER_PASSCODE);
        synchronized (SYNC) {
            while (!responseAvailable) {
                SYNC.wait();
            }
        }
    }

    @Override
    public void shouldResetPasscode(@NonNull Fragment fragment) throws InterruptedException {
        ((DemoApplication) fragment.getActivity().getApplication()).delay();
    }

    @Override
    public boolean onActivityResult(@NonNull Fragment fragment, int requestCode, int resultCode, Intent data) {
        if (requestCode == OnboardingActivity.ENTER_PASSCODE) {
            if (resultCode == Activity.RESULT_OK) {
                fragment.getActivity().setResult(Activity.RESULT_OK, data);
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
