package com.sap.cloud.mobile.fiori.demo.object;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.thedeanda.lorem.Lorem;

import java.util.Random;

/**
 * Created by I063014 on 9/15/2017.
 */

public class ChineseLorem implements Lorem {
    private static final char MIN = '\u4E00';
    private static final char MAX = '\u9FCC';
    private static final char RANGE = 0x51CC;

    @Nullable
    @Override
    public String getCity() {
        return null;
    }

    @Nullable
    @Override
    public String getCountry() {
        return null;
    }

    @Nullable
    @Override
    public String getEmail() {
        return null;
    }

    @Nullable
    @Override
    public String getFirstName() {
        return null;
    }

    @Nullable
    @Override
    public String getFirstNameMale() {
        return null;
    }

    @Nullable
    @Override
    public String getFirstNameFemale() {
        return null;
    }

    @Nullable
    @Override
    public String getLastName() {
        return null;
    }

    @Nullable
    @Override
    public String getName() {
        return null;
    }

    @Nullable
    @Override
    public String getNameMale() {
        return null;
    }

    @Nullable
    @Override
    public String getNameFemale() {
        return null;
    }

    @Nullable
    @Override
    public String getTitle(int count) {
        return null;
    }

    @Nullable
    @Override
    public String getTitle(int min, int max) {
        return null;
    }

    @Nullable
    @Override
    public String getHtmlParagraphs(int min, int max) {
        return null;
    }

    @Nullable
    @Override
    public String getParagraphs(int min, int max) {
        return null;
    }

    @Nullable
    @Override
    public String getUrl() {
        return null;
    }

    @Nullable
    @Override
    public String getWords(int count) {
        return null;
    }

    @NonNull
    @Override
    public String getWords(int min, int max) {
        Random random = new Random();
        int count = random.nextInt(max-min) + min;
        StringBuilder sb = new StringBuilder(count);
        for(int i=0;i<=count;i++){
            char code = (char)(MIN+random.nextInt(RANGE));
            sb.append(new Character(code));
        }
        return sb.toString();
    }

    @Nullable
    @Override
    public String getPhone() {
        return null;
    }

    @Nullable
    @Override
    public String getStateAbbr() {
        return null;
    }

    @Nullable
    @Override
    public String getStateFull() {
        return null;
    }

    @Nullable
    @Override
    public String getZipCode() {
        return null;
    }
}
