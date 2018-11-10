package com.sap.cloud.mobile.fiori.demo.object;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sap.cloud.mobile.fiori.object.ObjectCell;
import com.sap.cloud.mobile.fiori.demo.R;

/**
 * Created by I063014 on 9/18/2017.
 */

@BindingMethods({
        @BindingMethod(type = ObjectCell.class, attribute = "onActionClick", method = "setSecondaryActionOnClickListener"),
})
public class ObjectCellBindingAdapter {

    @BindingAdapter("contentDescription")
    public static void setContentDescription(@NonNull ImageView view, int iconId) {
        int statusDescId = android.R.string.ok;

        if (iconId == R.drawable.ic_error_black_24dp){
            statusDescId = R.string.error;
        }else if (iconId == R.drawable.ic_warning_black_24dp){
            statusDescId = R.string.warning;
        }
        view.setContentDescription(view.getResources().getString(statusDescId));
    }


    @BindingAdapter("src")
    public static void setImageSrc(ImageView view, int resId) {
        view.setImageResource(resId);
        if (resId == R.drawable.ic_error_black_24dp){
            view.getDrawable().setTint(BaseObjectCellActivity.sSapUiNegativeText);
        }
    }

    @BindingAdapter("detailImage")
    public static void setImageUrl(ObjectCell cell, BizObject obj) {
        Context context = cell.getContext();

        if (obj.getDetailImageResId() != 0) {
            cell.setDetailImageCharacter(null);
            cell.setDetailImage(obj.getDetailImageResId());
            cell.setDetailImageDescription(R.string.avatar);
        } else if (obj.getDetailImageUri() != null) {
            RequestOptions cropOptions = new RequestOptions().placeholder(
                    R.drawable.rectangle);
            cell.setDetailImageCharacter(null);
            Glide.with(context).load(obj.getDetailImageUri()).apply(cropOptions).into(
                    cell.prepareDetailImageView());
            cell.setDetailImageDescription(R.string.avatar);
        } else {
            cell.setDetailImage(null);
            cell.setDetailImageCharacter(obj.getSubHeadline().substring(0, 1));
        }
    }

    @BindingAdapter("android:text")
    public static void setText(TextView view, Priority priority) {
        view.setText(priority.toString());
        if (priority == Priority.HIGH){
            view.setTextColor(BaseObjectCellActivity.sSapUiNegativeText);
        }
    }

    public static void showInfo(View view, BizObject obj){
        Toast toast = Toast.makeText(view.getContext(),
                "Item: " + obj.getHeadline() + " is clicked.",
                Toast.LENGTH_SHORT);
        toast.show();
    }
}
