package com.sap.cloud.mobile.fiori.demo.object;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import com.sap.cloud.mobile.fiori.demo.R;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A business object_placeholder for demo purposes
 */

public class BizObject {
    private static Lorem sLorem = LoremIpsum.getInstance();
    @NonNull
    private static Lorem sChineseLorem = new ChineseLorem();
    public enum LANGUAGE{
        ENGLISH,
        CHINSE
    }
    private int mId;
    private String mHeadline;
    private String mSubHeadline;
    private String mFootnote;
    private String mDescription;
    @DrawableRes
    private int mDetailImageResId;
    private String mDetailImageUri;
    private Priority mPriority;
    @DrawableRes
    private int mStatusId;
    private int mPendingTasks;
    private boolean mNeedsAttention;
    private boolean mProtected;
    private int mPrice;

    public String getDetailImageUri() {
        return mDetailImageUri;
    }

    public void setDetailImageUri(String detailImageUri) {
        mDetailImageUri = detailImageUri;
    }

    public BizObject(int id) {
        mId = id;
    }

    public int getId(){
        return mId;
    }

    public String getHeadline() {
        return mHeadline;
    }

    public void setHeadline(String headline) {
        mHeadline = headline;
    }

    public String getSubHeadline() {
        return mSubHeadline;
    }

    public void setSubHeadline(String subHeadline) {
        mSubHeadline = subHeadline;
    }

    public String getFootnote() {
        return mFootnote;
    }

    public void setFootnote(String footnote) {
        mFootnote = footnote;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public int getDetailImageResId() {
        return mDetailImageResId;
    }

    public void setDetailImageResId(int detailImageResId) {
        mDetailImageResId = detailImageResId;
    }

    public Priority getPriority() {
        return mPriority;
    }

    public void setPriority(Priority priority) {
        mPriority = priority;
    }

    public int getStatusId() {
        return mStatusId;
    }

    public void setStatusId(int statusId) {
        mStatusId = statusId;
    }

    public int getPendingTasks() {
        return mPendingTasks;
    }

    public void setPendingTasks(int pendingTasks) {
        mPendingTasks = pendingTasks;
    }

    public boolean isNeedsAttention() {
        return mNeedsAttention;
    }

    public void setNeedsAttention(boolean needsAttention) {
        mNeedsAttention = needsAttention;
    }

    public boolean isProtected() {
        return mProtected;
    }

    public void setProtected(boolean aProtected) {
        mProtected = aProtected;
    }

    public int getPrice() {
        return mPrice;
    }

    public void setPrice(int price) {
        mPrice = price;
    }

    @NonNull
    public static List<BizObject> createBizObjectsList(int numBizObjects) {
        return createBizObjectsList(numBizObjects, LANGUAGE.ENGLISH);
    }

    @NonNull
    public static List<BizObject> createBizObjectsList(int numBizObjects, LANGUAGE lang) {
        Lorem lorem;
        if (lang == LANGUAGE.CHINSE){
            lorem = sChineseLorem;
        }else{
            lorem = sLorem;
        }

        List<BizObject> BizObjects = new ArrayList<>();
        Random r = new Random();
        for (int i = 1; i <= numBizObjects; i++) {
            BizObject obj = new BizObject(i);
            obj.setHeadline(i + " " + cap(lorem.getWords(2, 8)));
            obj.setSubHeadline(cap(lorem.getWords(2,10)));
            obj.setFootnote(cap(lorem.getWords(2,10)));
            obj.setDescription(cap(lorem.getWords(4,26)));
            int mod = i % 7;
            if (mod == 0){
                int mod4 = i % 4;
                switch (mod4){
                    case 0:
                        obj.setDetailImageResId(R.drawable.ic_android_color_24dp);
                        break;
                    case 1:
                        obj.setDetailImageResId(R.drawable.ic_directions_bike_color_24dp);
                        break;
                    case 2:
                        obj.setDetailImageResId(R.drawable.ic_directions_car_color_24dp);
                        break;
                    default:
                        obj.setDetailImageResId(R.drawable.ic_flight_color_24dp);
                        break;
                }
            }else if (mod == 4){
                int size = r.nextInt(20)/2 + 40 + 1;
                //obj.setDetailImageResId(R.drawable.ic_android_color_24dp);
                obj.setDetailImageResId(0);
                obj.setDetailImageUri("https://picsum.photos/"+size+"/"+size+"/");
            }else if (mod == 5){
                obj.setDetailImageResId(0);
            }else{
                obj.setDetailImageUri("https://api.adorable.io/avatars/60/"+i+"@adorable.png");
                obj.setDetailImageResId(0);
            }

            mod = i % 3;
            if (mod == 0){
                obj.setPriority(Priority.HIGH);
            }else if (mod == 1){
                obj.setPriority(Priority.NORM);
            }else{
                obj.setPriority(Priority.LOW);
            }

            mod = (int)Math.round(Math.random()*3);
            if (mod == 0){
                obj.setStatusId(R.drawable.ic_error_black_24dp);
            }else if (mod == 1){
                obj.setStatusId(R.drawable.ic_warning_black_24dp);
            }else{
                obj.setStatusId(R.drawable.ic_check_circle_black_24dp);
            }

            obj.setPendingTasks(r.nextInt(250));
            obj.setNeedsAttention(r.nextBoolean());
            obj.setProtected(r.nextBoolean());

            obj.setPrice(r.nextInt(100000));

            BizObjects.add(obj);
        }

        return BizObjects;
    }

    private static String cap(String input){
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    /**
     * Returns a string representation of the object_placeholder. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object_placeholder. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p>
     * The {@code toString} method for class {@code Object}
     * returns a string consisting of the name of the class of which the
     * object_placeholder is an instance, the at-sign character `{@code @}', and
     * the unsigned hexadecimal representation of the hash code of the
     * object_placeholder. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object_placeholder.
     */
    @NonNull
    @Override
    public String toString() {
        return getHeadline() + ", " + getSubHeadline();
    }
}
