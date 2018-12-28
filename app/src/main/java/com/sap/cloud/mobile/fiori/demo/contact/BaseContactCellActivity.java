package com.sap.cloud.mobile.fiori.demo.contact;

import android.animation.ObjectAnimator;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.sap.cloud.mobile.fiori.contact.ContactCell;
import com.sap.cloud.mobile.fiori.demo.AbstractDemoActivity;
import com.sap.cloud.mobile.fiori.demo.R;
import com.sap.cloud.mobile.fiori.object.KeylineDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class BaseContactCellActivity extends AbstractDemoActivity implements
        LoaderManager.LoaderCallbacks<List<Contact>> {
    private static final String TAG = "BaseContactCellActivity";

    public static final int NUM_CONTACTS = 300;

    protected ContactCellRecyclerAdapter mAdapter;
    protected RecyclerView mRecyclerView;
    private ConstraintLayout mConstraintLayout;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.content_base_object_cell);

        mProgressBar = findViewById(R.id.progress);
        mConstraintLayout = findViewById(R.id.my_constraint);

        mAdapter = createContactCellRecyclerAdapter();
        mRecyclerView = findViewById(R.id.my_recycler_view);
        configureRecyclerView();
        getLoaderManager().initLoader(0, null, this);
    }

    protected void configureRecyclerView() {
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(
                new KeylineDividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @NonNull
    protected ContactCellRecyclerAdapter createContactCellRecyclerAdapter() {
        return new ContactCellRecyclerAdapter(Glide.with(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @NonNull
    @Override
    public Loader<List<Contact>> onCreateLoader(int id, Bundle args) {
        return new ContactLoader(getBaseContext());
    }

    @Override
    public void onLoadFinished(Loader<List<Contact>> loader, List<Contact> data) {
        Log.d(TAG, "onLoadFinished");
        mAdapter.setObjects(data);
        // The list should now be shown.
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        ObjectAnimator animation = ObjectAnimator.ofFloat(mRecyclerView, "alpha", 0, 100);
        animation.setDuration(1000);
        animation.start();

        mConstraintLayout.postInvalidate();
    }

    @Override
    public void onLoaderReset(Loader<List<Contact>> loader) {
        mAdapter.setObjects(null);
    }

    public static class ContactLoader extends AsyncTaskLoader<List<Contact>> {

        public ContactLoader(Context context) {
            super(context);
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }

        @NonNull
        @Override
        public List<Contact> loadInBackground() {
            List<Contact> objects = Contact.createContactsList(NUM_CONTACTS);
            return objects;
        }
    }

    public static class ContactCellRecyclerAdapter extends
            RecyclerView.Adapter<ContactCellRecyclerAdapter.ViewHolder> {
        private static final String TAG = "ContactCellRecyclerAdapter";

        @Nullable
        protected List<Contact> mObjects = new ArrayList<Contact>();
        protected RequestManager mGlide;

        public ContactCellRecyclerAdapter(RequestManager glide) {
            mGlide = glide;
        }

        public void setObjects(@Nullable List<Contact> objects) {
            mObjects.clear();
            if (objects != null) {
                mObjects = objects;
            }
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(
                ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            ContactCell view;
            LayoutInflater inflater = LayoutInflater.from(context);
            view = (ContactCell) inflater.inflate(R.layout.contact_cell_1, parent,
                    false);
            view.setAsyncRendering(true);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder,
                int position) {
            Contact obj = mObjects.get(position);

            final ContactCell cell = holder.contactCell;

            //for espresso testing
            cell.setContentDescription("contactCell#"+obj.getId());

            if (obj.getImageResId() != 0) {
                cell.setDetailImageCharacter(null);
                cell.setDetailImage(obj.getImageResId());
                cell.setDetailImageDescription(R.string.avatar);
            } else if (obj.getImageUri() != null) {
                RequestOptions cropOptions = new RequestOptions().circleCrop().placeholder(
                        R.drawable.circle);
                cell.setDetailImageCharacter(null);
                holder.target = mGlide.load(obj.getImageUri()).apply(cropOptions).into(
                        cell.prepareDetailImageView());
                cell.setDetailImageDescription(R.string.avatar);
            } else {
                cell.setDetailImage(null);
                cell.setDetailImageCharacter(obj.getName().substring(0, 1));
            }

            cell.setHeadline(obj.getName());
            cell.setSubheadline(obj.getTitle());
            cell.setDescription(obj.getAddress());
            cell.setContactActions(ContactCell.ContactAction.CALL, ContactCell.ContactAction.EMAIL, ContactCell.ContactAction.VIDEO_CALL, ContactCell.ContactAction.MESSAGE);

            View.OnClickListener l = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(cell.getContext(), R.string.not_implemented, Toast.LENGTH_SHORT).show();
                }
            };
            cell.getContactActionView(0).setOnClickListener(l);
            if (TextUtils.isEmpty(obj.getEmail())){
                cell.getContactActionView(1).setEnabled(false);
                cell.getContactActionView(1).setOnClickListener(null);
            }else{
                cell.getContactActionView(1).setEnabled(true);
                cell.getContactActionView(1).setOnClickListener(l);
            }
            cell.getContactActionView(2).setOnClickListener(l);
            cell.getContactActionView(3).setOnClickListener(l);
        }

        @Override
        public void onViewRecycled(ViewHolder holder) {
            //when an object cell is recycled, image may become text view, in which case the
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

            public ContactCell contactCell;
            public Target target;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                if (itemView instanceof ContactCell){
                    contactCell = (ContactCell) itemView;
                }
            }
        }
    }

}
