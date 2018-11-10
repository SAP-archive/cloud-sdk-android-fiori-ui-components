package com.sap.cloud.mobile.fiori.demo.onboarding;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Base64;
import android.util.Log;

import com.sap.cloud.mobile.fiori.demo.DemoApplication;
import com.sap.cloud.mobile.onboarding.fingerprint.FingerprintException;
import com.sap.cloud.mobile.onboarding.passcode.PasscodeInputMode;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/**
 * Created by i321396 on 08/03/2018.
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class KeyHandler {

    private static final String KEY_NAME = "SapCloudKey";
    private static final String IV_KEY = "IVKey";
    private final KeyStore keyStore;
    private final KeyGenerator keyGenerator;
    @NonNull
    private final KeyGenParameterSpec.Builder algorithmParameterSpecBuilder;
    Cipher cipher;
    private SecretKey secretKey;
    static String algorithm;
    static String blockMode;
    static String encryptionPadding;
    @Nullable
    private String IV;
    private SharedPreferences prefs;
    IvParameterSpec ivParameterSpec;
    private SecureRandom secureRandom;
    static PasscodeInputMode passcodeInputMode;


    KeyHandler() throws KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException {
        algorithmParameterSpecBuilder = new KeyGenParameterSpec.Builder(KEY_NAME,
                KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT);

        keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyGenerator = KeyGenerator.getInstance(algorithm, "AndroidKeyStore");

        prefs = DemoApplication.getAppContext().getSharedPreferences(
                KEY_NAME, Context.MODE_PRIVATE);

        secureRandom = new SecureRandom();
    }

    void getInstanceOfCipher()
            throws NoSuchPaddingException, NoSuchAlgorithmException {
        cipher = Cipher.getInstance(algorithm + "/"
                + blockMode + "/"
                + encryptionPadding);
    }

    void initKeyGenerator()
            throws InvalidAlgorithmParameterException {
        keyGenerator.init(algorithmParameterSpecBuilder
                .setBlockModes(blockMode)
                .setUserAuthenticationRequired(true)
                .setEncryptionPaddings(encryptionPadding)
                .build());
    }

    void loadKeyStore() throws CertificateException, NoSuchAlgorithmException, IOException {
        keyStore.load(null);
    }

    void initCipherInEncryptMode() throws InvalidKeyException {
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
    }

    void generateKey() {
        secretKey = keyGenerator.generateKey();
    }

    private void setIvParameterSpec() {
        ivParameterSpec = new IvParameterSpec(decodeValue(IV));
    }

    byte[] decodeValue(String value) {
        return Base64.decode(value, Base64.URL_SAFE | Base64.NO_WRAP);
    }

    byte[] encryptValue(byte[] value) throws BadPaddingException, IllegalBlockSizeException {
        return cipher.doFinal(value);
    }

    byte[] decryptValue(byte[] value) throws BadPaddingException, IllegalBlockSizeException {
        return cipher.doFinal(value);
    }

    byte[] encodeValue(byte[] value) {
        return Base64.encode(value, Base64.URL_SAFE | Base64.NO_WRAP);
    }

    byte[] getIVFromCipher() {
        return cipher.getIV();

    }

    void setSecretKey() throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException {
        secretKey = (SecretKey) keyStore.getKey(KEY_NAME, null);
    }

    void saveIVToPref() throws IOException {
        IV = new String(encodeValue(getIVFromCipher()),"UTF-8");
        saveValueToPref(IV_KEY,IV);
    }

    private void saveValueToPref(String key, String value) {
       prefs.edit().putString(key, value).commit();
    }

    @Nullable
    private String getPasscodeFromPref(String key) throws FingerprintException {
        String result = prefs.getString(key, null);
        if (result == null) {
            throw new FingerprintException("value with key >" + key + "< is null");
        } else {
            return result;
        }
    }

   void loadIVFromPref() throws FingerprintException {
        IV = getPasscodeFromPref(IV_KEY);
        setIvParameterSpec();
    }

    void initCipherInDecryptMode() throws InvalidAlgorithmParameterException, InvalidKeyException {
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
    }

    @Nullable
    char[] generatePasscodeAndInvokeCallback(boolean encryption) throws UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException, FingerprintException {
        char passcode[] = null;
        if (encryption) {
            byte[] randomPasscode = generateRandomPasscode();
            byte[] encyptPasscode = encryptValue(randomPasscode);
            passcode = convertByteArrayToCharArray(randomPasscode);

            savePasscodeToPref(encodeValue(encyptPasscode), KEY_NAME);

        } else {
            byte[] decodedPasscode = decodeValue(getPasscodeFromPref(KEY_NAME));
            byte[] encryptPasscode = decryptValue(decodedPasscode);
            passcode = convertByteArrayToCharArray(encryptPasscode);
        }
        return passcode;
    }

    @NonNull
    byte[] generateRandomPasscode() throws UnsupportedEncodingException {
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder(20);
        for (int i = 0; i < 20; i++)
            sb.append(AB.charAt(secureRandom.nextInt(AB.length())));
        return sb.toString().getBytes("UTF-8");
    }


    @NonNull
    char[] convertByteArrayToCharArray(@NonNull byte[] array){
        char[] result = new char[array.length];
        for (int i = 0; i < result.length; i++){
            result[i] = (char) array[i];
        }
        return result;
    }


    void savePasscodeToPref(@NonNull byte[] encodedPasscode, String key) {
        String passcode = null;
        try {
            passcode = new String(encodedPasscode, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e("Keyhandler",e.getLocalizedMessage());
        }
        saveValueToPref(key,passcode);
    }

    public Cipher getCipher() {
        return cipher;
    }

    public void setCipher(Cipher cipher) {
        this.cipher = cipher;
    }


}
