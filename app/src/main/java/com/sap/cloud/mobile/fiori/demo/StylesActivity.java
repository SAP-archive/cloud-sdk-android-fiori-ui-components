package com.sap.cloud.mobile.fiori.demo;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Dimension;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.sap.cloud.mobile.fiori.common.Utility;

public class StylesActivity extends AbstractDemoActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiori_type_system);
    }

    private void testLineSpacing(){
        printLineSpacing(R.id.textViewH1, R.dimen.h1_line_height);
        printLineSpacing(R.id.textViewH2, R.dimen.h2_line_height);
        printLineSpacing(R.id.textViewH3, R.dimen.h3_line_height);
        printLineSpacing(R.id.textViewH3L, R.dimen.h3_line_height);
        printLineSpacing(R.id.textViewH4, R.dimen.h4_line_height);
        printLineSpacing(R.id.textViewH4L, R.dimen.h4_line_height);
        printLineSpacing(R.id.textViewH5, R.dimen.h5_line_height);
        printLineSpacing(R.id.textViewH6, R.dimen.h6_line_height);
        printLineSpacing(R.id.textViewSubtitle1, R.dimen.subtitle1_line_height);
        printLineSpacing(R.id.textViewSubtitle2, R.dimen.subtitle2_line_height);
        printLineSpacing(R.id.textViewSubtitle3, R.dimen.subtitle3_line_height);
        printLineSpacing(R.id.textViewBody1, R.dimen.body1_line_height);
        printLineSpacing(R.id.textViewBody2, R.dimen.body2_line_height);
        printLineSpacing(R.id.textViewBUTTON, R.dimen.button_default_line_height);
        printLineSpacing(R.id.textViewCaption, R.dimen.caption_line_height);
        printLineSpacing(R.id.textViewOVERLINE, R.dimen.overline_line_height);
    }

    private static float dpToPx(@Dimension int dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return (dp * metrics.density);
    }

    private static float pxToDp(@Dimension float px) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return (px/metrics.density);
    }

    public void printLineSpacing(int id, int heightId){
        TextView view = findViewById(id);
        int lineHeight = (int)getResources().getDimension(heightId);
        float add = Utility.getSpacingAdd(view.getPaint(), lineHeight);
        System.out.println("LineSpacingExtra: " + view.getText() + ": " + pxToDp(add));
        float multi = Utility.getSpacingMultiplier(view.getPaint(), lineHeight);
        System.out.println("LineSpacingMultiplier: " + view.getText() + ": " + pxToDp(multi));
    }

}
