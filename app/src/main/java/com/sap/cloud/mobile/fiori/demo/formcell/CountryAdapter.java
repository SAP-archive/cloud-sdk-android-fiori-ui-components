package com.sap.cloud.mobile.fiori.demo.formcell;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sap.cloud.mobile.fiori.demo.R;

import java.util.List;


public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.MyViewHolder> {
    private List<Country> countryList;

    public CountryAdapter(List<Country> countryList) {
        this.countryList = countryList;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Country country = countryList.get(position);
        ConstraintSet cs = new ConstraintSet();
        ConstraintLayout cl = new ConstraintLayout(holder.countryView.getContext());
        cs.clone(cl);
        ConstraintLayout.LayoutParams cname = (ConstraintLayout.LayoutParams) holder.countryView.mname.getLayoutParams();
        cname.width = 0;
        holder.countryView.mname.setLayoutParams(cname);
        ConstraintLayout.LayoutParams cpop = (ConstraintLayout.LayoutParams) holder.countryView.mpop.getLayoutParams();
        cpop.width = 0;
        holder.countryView.mpop.setLayoutParams(cpop);
        cs.constrainDefaultWidth(holder.countryView.mname.getId(), ConstraintSet.MATCH_CONSTRAINT_WRAP);
        cs.constrainDefaultWidth(holder.countryView.mpop.getId(), ConstraintSet.MATCH_CONSTRAINT_WRAP);
        cs.applyTo(cl);
        holder.countryView.setCountryName(country.name);
        holder.countryView.setPopulation(country.population);
        holder.countryView.setFlag(country.flagpic);
        holder.countryView.setSport(country.sport);
       /* holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = recyclerView.indexOfChild(view);
                Toast toast = Toast.makeText(getApplicationContext(),"Clicked at pos "+pos, Toast
                .LENGTH_SHORT);
            }
        });
*/
    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = new CountryView(parent.getContext());
        return new MyViewHolder(view);
    }

    public class MyViewHolder extends ViewHolder {
        public CountryView countryView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            countryView = (CountryView) itemView;
        }
    }
}
