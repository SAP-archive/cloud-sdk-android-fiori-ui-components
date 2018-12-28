package com.sap.cloud.mobile.fiori.demo.formcell;

import android.animation.ObjectAnimator;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.sap.cloud.mobile.fiori.demo.AbstractDemoActivity;
import com.sap.cloud.mobile.fiori.demo.R;
import com.sap.cloud.mobile.fiori.demo.object.BaseObjectCellActivity;
import com.sap.cloud.mobile.fiori.demo.object.BizObject;
import com.sap.cloud.mobile.fiori.demo.object.ObjectCellSpec;
import com.sap.cloud.mobile.fiori.demo.object.Priority;
import com.sap.cloud.mobile.fiori.object.KeylineDividerItemDecoration;
import com.sap.cloud.mobile.fiori.object.ObjectCell;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FilterDemoMainActivity extends AbstractDemoActivity implements FilterOptionSelectedCallBack, LoaderManager.LoaderCallbacks<List<Country>> {
    protected FilterDemoMainActivity.ObjectCellCountryRecyclerAdapter mAdapter;
    private ProgressBar busyIndicator;
    private RecyclerView recyclerView;
    private RefreshTask task;
    public static List<Country> countryList;
    public static List<Country> filteredCountryList;
    private ObjectCellSpec mObjectCellSpec;
    public static final int NUM_OBJECTS = 14;

    protected static int sSapUiFieldActiveBorderColor;
    protected static int sSapUiNegativeText;
    protected static int sSapUiPositiveText;
    protected static int sSapUiCriticalText;
    protected static int sSapUiNeutralText;

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
        sSapUiFieldActiveBorderColor = getColor(R.color.sap_ui_field_active_border_color);
        sSapUiNegativeText = getColor(R.color.sap_ui_negative_text);
        sSapUiPositiveText = getColor(R.color.sap_ui_positive_text);
        sSapUiCriticalText = getColor(R.color.sap_ui_critical_text);
        sSapUiNeutralText = getColor(R.color.sap_ui_neutral_text);

        mAdapter = createObjectCellRecyclerAdapter();
        recyclerView = findViewById(R.id.recycle_view);
        configureRecyclerView();
        getLoaderManager().initLoader(0, null, this);
        countryList = createCountryList();
        filteredCountryList = createCountryList();
    }

    @NonNull
    protected FilterDemoMainActivity.ObjectCellCountryRecyclerAdapter createObjectCellRecyclerAdapter() {
        return new FilterDemoMainActivity.ObjectCellCountryRecyclerAdapter(Glide.with(this), getObjectCellSpec());
    }

    /**
     * Subclasses need to override this method to specify how the object_placeholder cells are to be rendered.
     */
    protected ObjectCellSpec getObjectCellSpec() {
        if (mObjectCellSpec == null) {
            mObjectCellSpec = new ObjectCellSpec(R.layout.object_cell_1, NUM_OBJECTS, 3, 1,
                    true,
                    true, true, true,
                    false, true, true);
        }
        return mObjectCellSpec;
    }

    protected void configureRecyclerView() {
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
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
                50.0,
                R.drawable.ic_local_atm_black_24dp, "Tennis"));
        countryList.add(new Country("Canada", 45.0, R.drawable.ic_local_atm_black_24dp, "Hockey"));
        countryList.add(new Country("Spain", 40.0, R.drawable.ic_local_atm_black_24dp, "Tennis"));
        countryList.add(new Country("Portugal", 35.0, R.drawable.ic_local_atm_black_24dp, "Basketball"));
        countryList.add(new Country("Austria", 30.0, R.drawable.ic_local_atm_black_24dp, "Basket Ball"));
        countryList.add(new Country("Netherlands", 25.0, R.drawable.ic_local_atm_black_24dp, "Swimming"));
        countryList.add(new Country("Belgium", 20.0, R.drawable.ic_local_atm_black_24dp, "Swimming"));
        countryList.add(new Country("Denmark", 15.0, R.drawable.ic_local_atm_black_24dp, "Table Tennis"));
        countryList.add(new Country("Ireland", 10.0, R.drawable.ic_local_atm_black_24dp, "Table Tennis"));
        countryList.add(new Country("Norway", 5.0, R.drawable.ic_local_atm_black_24dp, "Hockey"));
        countryList.add(new Country("Sweden", 4.0, R.drawable.ic_local_atm_black_24dp, "Hockey"));
        countryList.add(new Country("Finland", 1.0, R.drawable.ic_local_atm_black_24dp, "Hockey"));
        countryList.add(new Country("Greece", 28.0, R.drawable.ic_local_atm_black_24dp, "Basketball"));
        return countryList;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.filterDialog:
                FragmentManager fragmentManager = getSupportFragmentManager();
                FilterDialogFragmentTest filterDialogFragment = new FilterDialogFragmentTest();
                filterDialogFragment.setApplyListener(() -> {
                    JSONObject filterValues = filterDialogFragment.getChangedValues();
                    applyFilter(filterValues);
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
        String population = filterValues.optString("population", "");
        if(population == null || population.length() <= 0) population = "0";

        if(population != null && population.length() > 0) {
            String finalPopulation = population;
            runOnUiThread(new Runnable() {
                public void run() {
                    filteredCountryList = new ArrayList<>();
                    for (Country country : countryList) {
                        if (country.population <= Double.parseDouble(finalPopulation)) {
                            filteredCountryList.add(country);
                        }
                    }
                    mAdapter = createObjectCellRecyclerAdapter();
                    recyclerView = findViewById(R.id.recycle_view);
                    configureRecyclerView();
                    getLoaderManager().initLoader(0, null, FilterDemoMainActivity.this);
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
//        if (countryList.size() > 1) {

//            if (task.getStatus() != AsyncTask.Status.RUNNING) {
//                task = new RefreshTask();
//                task.execute();
//            }

//        }
    }

    @NonNull
    @Override
    public Loader<List<Country>> onCreateLoader(int id, Bundle args) {
        return new FilterDemoMainActivity.CountryObjectLoader(getBaseContext(), getObjectCellSpec());
    }

    public static class CountryObjectLoader extends AsyncTaskLoader<List<Country>> {
        private ObjectCellSpec mObjectCellSpec;

        public CountryObjectLoader(Context context, ObjectCellSpec objectCellSpec) {
            super(context);
            mObjectCellSpec = objectCellSpec;
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }

        @NonNull
        @Override
        public List<Country> loadInBackground() {
            return FilterDemoMainActivity.filteredCountryList;
        }
    }

    @Override
    public void onLoadFinished(Loader<List<Country>> loader, List<Country> data) {
        Log.d("myDebuggingTag", "onLoadFinished");
        mAdapter.setObjects(filteredCountryList);
        // The list should now be shown.
        recyclerView.setVisibility(View.VISIBLE);
        ObjectAnimator animation = ObjectAnimator.ofFloat(recyclerView, "alpha", 0, 100);
        animation.setDuration(1000);
        animation.start();
    }

    @Override
    public void onLoaderReset(Loader<List<Country>> loader) {
        mAdapter.setObjects(null);
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
//            countryAdapter.notifyDataSetChanged();
            busyIndicator.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            super.onPostExecute(aBoolean);
        }

        @Nullable
        @Override
        protected Boolean doInBackground(String... strings) {
            filteredCountryList.remove(1);
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

    public static class ObjectCellCountryRecyclerAdapter extends
            RecyclerView.Adapter<BaseObjectCellActivity.ObjectCellRecyclerAdapter.ViewHolder> {
        private static final String TAG = "ObjectCellCountryRecyclerAdapter";

        @Nullable
        protected List<Country> mObjects = new ArrayList<Country>();
        protected RequestManager mGlide;
        protected ObjectCellSpec mObjectCellSpec;

        public ObjectCellCountryRecyclerAdapter(RequestManager glide, ObjectCellSpec ocSpec) {
            mGlide = glide;
            mObjectCellSpec = ocSpec;
        }

        public void setObjects(@Nullable List<Country> objects) {
            mObjects.clear();
            if (objects != null) {
                mObjects = objects;
            }
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public BaseObjectCellActivity.ObjectCellRecyclerAdapter.ViewHolder onCreateViewHolder(
                ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            ObjectCell view;
            if (mObjectCellSpec.getLayoutId() != 0) {
                LayoutInflater inflater = LayoutInflater.from(context);
                view = (ObjectCell) inflater.inflate(mObjectCellSpec.getLayoutId(), parent,
                        false);
            } else {
                view = new ObjectCell(context);
            }
            if (view.getLines() != mObjectCellSpec.getLines()) {
                view.setLines(mObjectCellSpec.getLines());
            }
            if (view.getHeadlineLines() != mObjectCellSpec.getHeadlineLines()) {
                view.setHeadlineLines(mObjectCellSpec.getHeadlineLines());
            }
            view.setPreserveIconImageContainer(mObjectCellSpec.hasContainerLayout());
            //only use async rendering when we need to recycle ObjectCells
            //view.setAsyncRendering(mObjectCellSpec.getCounts() >20);
            return new BaseObjectCellActivity.ObjectCellRecyclerAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(BaseObjectCellActivity.ObjectCellRecyclerAdapter.ViewHolder holder,
                                     int position) {
            Country obj = mObjects.get(position);


            ObjectCell cell = holder.objectCell;
            //for espresso testing
            cell.setContentDescription("ObjectCell#" + obj.name);

            if (mObjectCellSpec.hasDetailImage() && (!mObjectCellSpec.hasContainerLayout())) {
                cell.setDetailImage(obj.flagpic);
            } else {
                cell.setPreserveDetailImageSpacing(false);
            }

            if (cell.getHeadlineLines() > 1) {
                cell.setHeadline(obj.name + " " + obj.sport);
            } else {
                cell.setHeadline(obj.name);
            }
            if (mObjectCellSpec.hasSubheadline()) {
                cell.setSubheadline(obj.sport);
            }
            if (mObjectCellSpec.hasFootnote()) {
                cell.setFootnote("" + obj.population);
            }
            cell.setDescription("" + obj.population);
            cell.clearStatuses();
            boolean shuffleStatus = mObjectCellSpec.isShuffleStatus() && position % 2 == 1;
            if (shuffleStatus) {
                int index = 0;
                if (mObjectCellSpec.hasSecondStatus()) {
                    cell.setStatusColor(sSapUiPositiveText, index);
                    index++;
                }
                if (mObjectCellSpec.hasFirstStatus()) {
                    cell.setStatusColor(cell.getDefaultStatusColor(), index);
                }

            } else {
                if (mObjectCellSpec.hasFirstStatus()) {
                    cell.setStatusColor(cell.getDefaultStatusColor(), 0);
                }
                if (mObjectCellSpec.hasSecondStatus()) {
                    cell.setStatusColor(FilterDemoMainActivity.sSapUiPositiveText, 1);
                }
            }
            if (mObjectCellSpec.hasAction()) {
                cell.setSecondaryActionIcon(R.drawable.ic_cloud_download_black_24dp);
                cell.setSecondaryActionIconDescription(R.string.download);
                cell.setSecondaryActionOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(@NonNull View v) {
                        ObjectCell parent = (ObjectCell) v.getParent();
                        Toast toast = Toast.makeText(v.getContext(),
                                "Item: " + parent.getHeadline() + " is clicked.",
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
            }
        }

        @Override
        public void onViewRecycled(BaseObjectCellActivity.ObjectCellRecyclerAdapter.ViewHolder holder) {
            //when an object_placeholder cell is recycled, image may become text view, in which case the
            // clear won't be automatically called by glide
            if (holder.target != null) {
                mGlide.clear(holder.target);
            }
            super.onViewRecycled(holder);
        }

        @Override
        public int getItemCount() {
            return mObjects.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            public ObjectCell objectCell;
            public Target target;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                if (itemView instanceof ObjectCell) {
                    objectCell = (ObjectCell) itemView;
                }
            }
        }
    }


}
