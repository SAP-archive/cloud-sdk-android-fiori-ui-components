package com.sap.cloud.mobile.fiori.demo.formcell;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sap.cloud.mobile.fiori.demo.R;


public class CountryView extends ConstraintLayout {
    public View view;
    public TextView mname, mpop, sportView;
    public ImageView mflag;

    public CountryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CountryView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        view = inflate(context, R.layout.itemview, this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 125);
        view.setLayoutParams(params);
        mname = view.findViewById(R.id.countryName);
        mpop = view.findViewById(R.id.pop);
        mflag = view.findViewById(R.id.flagimage);
        sportView = view.findViewById(R.id.sport);
    }

    public void setFlag(int flagpic) {
        if (flagpic > 0) {
            mflag.setVisibility(VISIBLE);
            mflag.setImageResource(flagpic);
        } else {
            mflag.setVisibility(GONE);
        }
    }

    public int getCountryNameTextviewId() {
        return mname.getId();
    }

    @NonNull
    public String getCountryName() {
        return (String) mname.getText();
    }

    public void setSport(@NonNull String sport) {
        if (sport.length() == 0) {
            sportView.setVisibility(GONE);
        } else {
            sportView.setVisibility(VISIBLE);
            sportView.setText(sport);
        }
    }

    public void setCountryName(@NonNull String name) {
        if (name.length() == 0) {
            mname.setVisibility(GONE);
        } else {
            mname.setVisibility(VISIBLE);
            mname.setText(name);
        }
    }

    @NonNull
    public String getPopulation() {
        return mpop.getText().toString();
    }

    public void setPopulation(double pop) {
        mpop.setText(String.valueOf(pop));
    }

    public void getFlag() {
    }
}
