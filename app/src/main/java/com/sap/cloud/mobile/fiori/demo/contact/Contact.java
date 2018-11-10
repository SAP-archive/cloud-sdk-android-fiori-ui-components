package com.sap.cloud.mobile.fiori.demo.contact;

import android.support.annotation.NonNull;

import com.sap.cloud.mobile.fiori.demo.R;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Sample contact object
 */

public class Contact {
    private static Lorem sLorem = LoremIpsum.getInstance();

    private int mId;
    private int mImageResId;
    private String mName;
    private String mTitle;
    private String mAddress;
    private String mPhone;
    private String mEmail;
    private String mImageUri;

    public int getImageResId() {
        return mImageResId;
    }

    public void setImageResId(int imageResId) {
        mImageResId = imageResId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getImageUri() {
        return mImageUri;
    }

    public void setImageUri(String imageUri) {
        mImageUri = imageUri;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    @NonNull
    private static String[] mStreetTypes = {"Road", "Street", "Circle", "Place", "Boulevard", "Lane", "Avenue"};

    @NonNull
    public static List<Contact> createContactsList(int numContacts) {
        Lorem lorem = sLorem;

        List<Contact> Contacts = new ArrayList<>();

        Random r = new Random();
        for (int i = 1; i <= numContacts; i++) {
            Contact obj = new Contact();
            obj.setId(i);
            obj.setName(lorem.getName());
            //if (r.nextBoolean()) {
            obj.setTitle(lorem.getTitle(2, 8));
            //}
            String address = i + " " + cap(lorem.getWords(1, 5)) + " " + mStreetTypes[r.nextInt(mStreetTypes.length)] + System.getProperty("line.separator")
                    + lorem.getCity() + ", " + lorem.getStateAbbr();
            if (r.nextBoolean()){
                address += System.getProperty("line.separator") + lorem.getZipCode();
            }
            obj.setAddress(address);
            if (r.nextBoolean()) {
                obj.setEmail(lorem.getEmail());
            }
            obj.setPhone(lorem.getPhone());

            int mod = i % 7;
            if (mod == 0){
                obj.setImageResId(R.drawable.ic_person_black_24dp);
            }else if (mod == 4){
                int size = r.nextInt(20)/2 + 40;
                obj.setImageResId(0);
                obj.setImageUri("https://picsum.photos/"+size+"/"+size+"/");
            }else if (mod == 5){
                obj.setImageResId(0);
            }else{
                obj.setImageUri("https://api.adorable.io/avatars/58/"+i+"@adorable.png");
                obj.setImageResId(0);
            }

            Contacts.add(obj);
        }

        return Contacts;
    }

    private static String cap(String input){
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}
