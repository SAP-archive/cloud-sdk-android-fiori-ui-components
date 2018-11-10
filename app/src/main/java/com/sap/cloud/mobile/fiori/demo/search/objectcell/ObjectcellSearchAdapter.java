package com.sap.cloud.mobile.fiori.demo.search.objectcell;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.sap.cloud.mobile.fiori.demo.R;
import com.sap.cloud.mobile.fiori.demo.object.BaseObjectCellActivity;
import com.sap.cloud.mobile.fiori.demo.object.BizObject;
import com.sap.cloud.mobile.fiori.demo.object.ObjectCellSpec;
import com.sap.cloud.mobile.fiori.demo.object.Priority;
import com.sap.cloud.mobile.fiori.object.ObjectCell;

import java.util.ArrayList;
import java.util.List;


public class ObjectcellSearchAdapter extends
        RecyclerView.Adapter<BaseObjectCellActivity.ObjectCellRecyclerAdapter.ViewHolder> {
    private static final String TAG = ObjectcellSearchAdapter.class.getCanonicalName();

    @Nullable
    private List<BizObject> mObjects = new ArrayList<>();
    @Nullable
    private List<BizObject> mOriginals = null;
    private RequestManager mGlide;
    private ObjectCellSpec mObjectCellSpec;

    ObjectcellSearchAdapter(RequestManager glide, ObjectCellSpec ocSpec) {
        mGlide = glide;
        mObjectCellSpec = ocSpec;
    }

    void setObjects(@Nullable List<BizObject> objects) {
        if (objects != null) {
            mObjects = objects;
        } if(mOriginals == null || mOriginals.isEmpty()) {
            mOriginals = objects;
//            mOriginals = copy(objects);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseObjectCellActivity.ObjectCellRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        ObjectCell view;
        if (mObjectCellSpec.getLayoutId() != 0){
            LayoutInflater inflater = LayoutInflater.from(context);
            view = (ObjectCell) inflater.inflate(mObjectCellSpec.getLayoutId(), parent,
                    false);
        }else{
            view = new ObjectCell(context);
        }
        if (view.getLines() != mObjectCellSpec.getLines()) {
            view.setLines(mObjectCellSpec.getLines());
        }
        if (view.getHeadlineLines() != mObjectCellSpec.getHeadlineLines()) {
            view.setHeadlineLines(mObjectCellSpec.getHeadlineLines());
        }
        //only use async rendering when we need to recycle ObjectCells
        //view.setAsyncRendering(mObjectCellSpec.getCounts() >20);
        return new BaseObjectCellActivity.ObjectCellRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseObjectCellActivity.ObjectCellRecyclerAdapter.ViewHolder holder, int position) {
        BizObject obj = mObjects.get(position);

        ObjectCell cell = holder.objectCell;

        //for espresso testing
        cell.setContentDescription("ObjectCell#"+obj.getId());

        if (mObjectCellSpec.hasDetailImage()) {

            if (obj.getDetailImageResId() != 0) {
                cell.setDetailImageCharacter(null);
                //Glide won't improve vector image performance. Basically it's the same as: cell
                // .setDetailImage(obj.getDetailImageResId()); Also circleCrop won't work.
                //This is just to demonstrate how to use local vector resource with Glide
//                mGlide.load(null).apply(cropOptions.fallback(obj.getDetailImageResId())).into(
//                        cell.prepareDetailImageView());
                cell.setDetailImage(obj.getDetailImageResId());
                cell.setDetailImageDescription(R.string.avatar);
            } else if (obj.getDetailImageUri() != null) {
                RequestOptions cropOptions = new RequestOptions().circleCrop().placeholder(
                        R.drawable.circle);
                cell.setDetailImageCharacter(null);
                holder.target = mGlide.load(obj.getDetailImageUri()).apply(cropOptions).into(
                        cell.prepareDetailImageView());
                cell.setDetailImageDescription(R.string.avatar);
            } else {
                cell.setDetailImage(null);
                cell.setDetailImageCharacter(obj.getSubHeadline().substring(0, 1));
            }
        }

        if (cell.getHeadlineLines()>1){
            cell.setHeadline(obj.getHeadline() + " " + obj.getFootnote());
        }else{
            cell.setHeadline(obj.getHeadline());
        }
        if (mObjectCellSpec.hasSubheadline()) {
            cell.setSubheadline(obj.getSubHeadline());
        }
        if (mObjectCellSpec.hasFootnote()) {
            cell.setFootnote(obj.getFootnote());
        }
        cell.setDescription(obj.getDescription());
        cell.clearStatuses();
        if (mObjectCellSpec.hasFirstStatus()) {
            cell.setStatus(obj.getPriority().toString(), 0);
            if (obj.getPriority() == Priority.HIGH){
                cell.setStatusColor(Color.RED, 0);
            }
        }
        if (mObjectCellSpec.hasSecondStatus()) {
            int statusDescId = android.R.string.ok;

            if (obj.getStatusId() == R.drawable.ic_error_black_24dp){
                statusDescId = R.string.error;
                //colors of other 2 icons are set in drawble
                cell.setStatusColor(Color.RED, 1);
            }else if (obj.getStatusId() == R.drawable.ic_warning_black_24dp){
                statusDescId = R.string.warning;
            }

            cell.setStatus(obj.getStatusId(), 1, statusDescId);
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

    @Nullable
    List<BizObject> getOriginals() {
        return mOriginals;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

    }
}
