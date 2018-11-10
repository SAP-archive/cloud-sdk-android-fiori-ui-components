package com.sap.cloud.mobile.fiori.demo.formcell;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.sap.cloud.mobile.fiori.demo.AbstractDemoActivity;
import com.sap.cloud.mobile.fiori.demo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FilterDemoMainActivity extends AbstractDemoActivity implements FilterOptionSelectedCallBack {
    private CountryAdapter countryAdapter;
    private ProgressBar busyIndicator;
    private RecyclerView recyclerView;
    private RefreshTask task;
    private List<Country> countryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filterdemo_activity_main);
        Toolbar MyToolbar = findViewById(R.id.my_toolbar);

        setSupportActionBar(MyToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        recyclerView = findViewById(R.id.recycle_view);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        countryList = createCountryList();
        countryAdapter = new CountryAdapter(countryList);
        recyclerView.setAdapter(countryAdapter);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));
        busyIndicator = findViewById(R.id.progressBar);
        task = new RefreshTask();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbaritems, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @NonNull
    private List<Country> createCountryList() {
        List<Country> countryList = new ArrayList<>();
        countryList.add(new Country(
                "Hello this is to check for fitting a multiple line content in 1 line with "
                        + "ellipsize Fit in 2 lines of text view. Let see how to content looks. "
                        + "It should have an ellipsize at the end",
                59.83, R.drawable.ic_local_atm_black_24dp, "Shuttle Badminton"));
        countryList.add(new Country("France",
                17363636336636363337373773636760.95959595959595959595956654658757656566656565565665555555,
                R.drawable.ic_local_atm_black_24dp, "Tennis"));
        countryList.add(new Country("", 80.62, R.drawable.ic_local_atm_black_24dp, "Tennis"));
        countryList.add(new Country("Spain", 46.77, R.drawable.ic_local_atm_black_24dp, "Tennis"));
        countryList.add(new Country("Portugal", 10.46, R.drawable.ic_local_atm_black_24dp, "Basket Ball"));
        countryList.add(new Country("Austria", 8.47, 0, "Basket Ball"));
        countryList.add(new Country("Netherlands", 16.8, R.drawable.ic_local_atm_black_24dp, "Swimming"));
        countryList.add(new Country("Belgium", 11.2, R.drawable.ic_local_atm_black_24dp, "Swimming"));
        countryList.add(new Country("Denmark", 5.67, R.drawable.ic_local_atm_black_24dp, "Table Tennis"));
        countryList.add(new Country("Ireland", 4.63, R.drawable.ic_local_atm_black_24dp, "Table Tennis"));
        countryList.add(new Country("Norway", 5.19, R.drawable.ic_local_atm_black_24dp, "Hockey"));
        countryList.add(new Country("Sweden", 9.85, R.drawable.ic_local_atm_black_24dp, "Hockey"));
        countryList.add(new Country("Finland", 5.47, R.drawable.ic_local_atm_black_24dp, "Hockey"));
        countryList.add(new Country("Greece", 10.76, R.drawable.ic_local_atm_black_24dp, "Basket Ball"));
        return countryList;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.filterDialog:
                FragmentManager fragmentManager = getSupportFragmentManager();
                FilterDialogFragmentTest filterDialogFragment = new FilterDialogFragmentTest();
                filterDialogFragment.setApplyListener(new FilterDialogFragmentTest.OnApplyListener() {
                    @Override
                    public void onApply() {
                        JSONObject filterValues = filterDialogFragment.getChangedValues();
                        applyFilter(filterValues);
                    }
                });
                filterDialogFragment.show(fragmentManager, "dialog");
                return true;
            case R.id.filter:
                Intent filterintent = new Intent(this, FilterActivityTest.class);
                startActivityForResult(filterintent,1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void applyFilter(@NonNull JSONObject filterValues) {
        String sport = filterValues.optString("sport", "");
        if (sport != null && sport.length() > 0) {
            runOnUiThread(new Runnable() {
                public void run() {
                    List<Country> countryList = createCountryList();
                    List<Country> filteredCountryList = new ArrayList<>();
                    for (Country country : countryList) {
                        if (country.sport.endsWith(sport)) {
                            filteredCountryList.add(country);
                        }
                    }
                    countryAdapter = new CountryAdapter(filteredCountryList);
                    countryAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(countryAdapter);
                }
            });
        } else {
            runOnUiThread(new Runnable() {
                public void run() {
                    List<Country> countryList = createCountryList();
                    countryAdapter = new CountryAdapter(countryList);
                    countryAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(countryAdapter);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        if (resultCode == RESULT_OK) {
            try {
                String dataString = data.getStringExtra("CHANGED_CELLS");
                if (dataString != null && dataString.length() > 0) {
                    JSONObject filterValues = new JSONObject(data.getStringExtra("CHANGED_CELLS"));
                    applyFilter(filterValues);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void refreshBackGround() {
        if (countryList.size() > 1) {

            if (task.getStatus() != AsyncTask.Status.RUNNING) {
                task = new RefreshTask();
                task.execute();
            }

        }
    }

    private class RefreshTask extends AsyncTask<String, Integer, Boolean> {
        @Override
        protected void onPreExecute() {
            busyIndicator.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            countryAdapter.notifyDataSetChanged();
            busyIndicator.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            super.onPostExecute(aBoolean);
        }

        @Nullable
        @Override
        protected Boolean doInBackground(String... strings) {
            countryList.remove(1);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }
}
