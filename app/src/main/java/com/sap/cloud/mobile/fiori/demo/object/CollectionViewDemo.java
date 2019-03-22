package com.sap.cloud.mobile.fiori.demo.object;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.sap.cloud.mobile.fiori.common.FioriItemClickListener;
import com.sap.cloud.mobile.fiori.demo.AbstractDemoActivity;
import com.sap.cloud.mobile.fiori.demo.R;
import com.sap.cloud.mobile.fiori.demo.contact.BaseContactCellActivity;
import com.sap.cloud.mobile.fiori.formcell.FilterFormCell;
import com.sap.cloud.mobile.fiori.formcell.FormCell;
import com.sap.cloud.mobile.fiori.object.AbstractEntityCell;
import com.sap.cloud.mobile.fiori.object.CollectionView;
import com.sap.cloud.mobile.fiori.object.CollectionViewItem;

import java.util.ArrayList;
import java.util.List;

public class CollectionViewDemo extends AbstractDemoActivity {
    public static final int NUM_BIZ_OBJECTS = 3000;
    private List<Integer> mSelectedContentType = new ArrayList<Integer>();

    private static final int PEOPLE = 0;
    private static final int OBJECTS = 1;
    private static final int SHOW_IMAGES = 2;
    private static final int HEADLINE = 3;
    private static final int SUB_HEADLINE = 4;
    private static final int ATTRIBUTE = 5;

    private ObjectCellCollectionViewAdapterDemo mCollectionViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_view_item_demo);

        mSelectedContentType.add(PEOPLE);
        mSelectedContentType.add(SHOW_IMAGES);
        mSelectedContentType.add(HEADLINE);
        mSelectedContentType.add(SUB_HEADLINE);
        mSelectedContentType.add(ATTRIBUTE);

        CollectionView collectionView = findViewById(R.id.collectionView);
        collectionView.setHeader("Collection View Demo");
        collectionView.setFooter("SEE ALL (" + NUM_BIZ_OBJECTS + ")");
        collectionView.setFooterClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if (mCollectionViewAdapter.mShowPeople) {
                    intent.setClass(CollectionViewDemo.this, BaseContactCellActivity.class);
                } else {
                    intent.setClass(CollectionViewDemo.this, BaseObjectCellActivity.class);
                }
                CollectionViewDemo.this.startActivity(intent);
            }
        });

        collectionView.setItemClickListener(new FioriItemClickListener() {
            @Override
            public void onClick(@NonNull View view, int position) {
                Toast.makeText(getApplicationContext(), "You clicked on: " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(@NonNull View view, int position) {
                Toast.makeText(getApplicationContext(), "You long clicked on: " + position, Toast.LENGTH_SHORT).show();
            }
        });

        FilterFormCell filterFormCell = findViewById(R.id.filterFormCell);
        filterFormCell.setSingleLine(false);
        filterFormCell.setValueOptions(new String[]{"People", "Objects", "Detail Image", "Headline", "Sub Headline", "Attribute"});
        filterFormCell.setValue(mSelectedContentType);

        FormCell.CellValueChangeListener<List<Integer>> cellValueChangeListener = new FormCell.CellValueChangeListener<List<Integer>>() {
            @Override
            protected void cellChangeHandler(@Nullable List<Integer> value) {
                if (value != null) {
                    if (!value.contains(PEOPLE) && !value.contains(OBJECTS)) {
                        value.add(PEOPLE);
                    } else if (value.contains(PEOPLE) && value.contains(OBJECTS)) {
                        if (mSelectedContentType.contains(OBJECTS)) {
                            value.remove(Integer.valueOf(OBJECTS));
                        } else if (mSelectedContentType.contains(PEOPLE)) {
                            value.remove(Integer.valueOf(PEOPLE));
                        }
                    }
                    mSelectedContentType.clear();
                    mSelectedContentType.addAll(value);

                    if (value.isEmpty()) {
                        mSelectedContentType.add(0);
                    } else {
                        mSelectedContentType.addAll(value);
                    }
                    filterFormCell.setCellValueChangeListener(null);
                    filterFormCell.setValue(mSelectedContentType);
                    filterFormCell.setCellValueChangeListener(this);

                    mCollectionViewAdapter.setShowPeople(mSelectedContentType.contains(PEOPLE));
                    mCollectionViewAdapter.setShowHeadline(mSelectedContentType.contains(HEADLINE));
                    mCollectionViewAdapter.setShowSubHeadline(mSelectedContentType.contains(SUB_HEADLINE));
                    mCollectionViewAdapter.setShowAttribute(mSelectedContentType.contains(ATTRIBUTE));
                    mCollectionViewAdapter.setShowDetailImage(mSelectedContentType.contains(SHOW_IMAGES));
                    mCollectionViewAdapter.notifyDataSetChanged();
                }
            }
        };
        filterFormCell.setCellValueChangeListener(cellValueChangeListener);

        // only if you need to override the gradient
        collectionView.setPartialChildGradient(getResources().getColor(R.color.white, getTheme()));


        mCollectionViewAdapter = new ObjectCellCollectionViewAdapterDemo(Glide.with(this), BizObject.createBizObjectsList(NUM_BIZ_OBJECTS));

        collectionView.setCollectionViewAdapter(mCollectionViewAdapter);
    }

    public static class ObjectCellCollectionViewAdapterDemo extends CollectionView.CollectionViewAdapter {
        private boolean mShowDetailImage = true;
        private boolean mShowPeople = true;
        private boolean mShowHeadline = true;
        private boolean mShowSubHeadline = true;
        private boolean mShowAttribute = true;
        private RequestManager mGlide;
        private List<BizObject> mObjects;

        ObjectCellCollectionViewAdapterDemo(RequestManager glide, List<BizObject> objects) {
            mGlide = glide;
            mObjects = objects;
        }

        @Override
        public void onBindViewHolder(@NonNull CollectionView.CollectionViewAdapter.CollectionViewItemHolder collectionViewItemHolder, int pos) {
            CollectionViewItem collectionViewItem = collectionViewItemHolder.collectionViewItem;
            BizObject obj = mObjects.get(pos);

            collectionViewItem.setImageOutlineShape(mShowPeople ? AbstractEntityCell.IMAGE_SHAPE_OVAL : AbstractEntityCell.IMAGE_SHAPE_ROUND_RECT);
            String headline = mShowHeadline ? obj.getHeadline().trim() : null;
            String subHeadline = mShowSubHeadline ? obj.getSubHeadline().trim() : null;
            String attribute = "";

            if (mShowSubHeadline) {
                subHeadline = subHeadline.substring(0, subHeadline.indexOf(" ")).trim();
            }

            if (mShowAttribute) {
                attribute = obj.getDescription();
                attribute = attribute != null ? attribute.trim() : null;
                attribute = attribute != null ? attribute.substring(0, attribute.indexOf(" ")).trim() : null;
            } else {
                attribute = null;
            }


            if (mShowPeople && !mShowDetailImage) {
                // if user is showing profile images and has disabled the detail images show the texts
                collectionViewItem.setDetailImage(null);
                String str = obj.getHeadline();
                if (str != null) {
                    str = str.replaceFirst("([0-9]+)", "");
                    str = str.trim();
                    collectionViewItem.setDetailImageCharacter(str.substring(0, 1));
                }
                int colorPos = pos % 9;
                int color = R.color.sap_ui_collection_view_default_color;
                switch (colorPos) {
                    case 0:
                        color = com.sap.cloud.mobile.fiori.R.color.sap_ui_contact_placeholder_color_1;
                        break;
                    case 1:
                        color = com.sap.cloud.mobile.fiori.R.color.sap_ui_contact_placeholder_color_2;
                        break;
                    case 2:
                        color = com.sap.cloud.mobile.fiori.R.color.sap_ui_contact_placeholder_color_3;
                        break;
                    case 3:
                        color = com.sap.cloud.mobile.fiori.R.color.sap_ui_contact_placeholder_color_4;
                        break;
                    case 4:
                        color = com.sap.cloud.mobile.fiori.R.color.sap_ui_contact_placeholder_color_5;
                        break;
                    case 5:
                        color = com.sap.cloud.mobile.fiori.R.color.sap_ui_contact_placeholder_color_6;
                        break;
                    case 6:
                        color = com.sap.cloud.mobile.fiori.R.color.sap_ui_contact_placeholder_color_7;
                        break;
                    case 7:
                        color = com.sap.cloud.mobile.fiori.R.color.sap_ui_contact_placeholder_color_8;
                        break;
                    default:

                }
                collectionViewItem.setDetailCharacterBackgroundTintList(color);
            } else {
                // if detail image is enabled and obj has some detail image related info then set the detail image
                if (mShowDetailImage) {
                    if (obj.getDetailImageUri() != null) {
                        /*
                            How to use detail image view with glide.
                         */
                        mGlide.load(obj.getDetailImageUri())
                                .into(collectionViewItem.prepareDetailImageView());
                        collectionViewItem.setDetailImageDescription(R.string.avatar);
                    } else if (obj.getDetailImageResId() != 0) {
                        collectionViewItem.setDetailImage(obj.getDetailImageResId());
                        collectionViewItem.setDetailImageDescription(R.string.avatar);
                    } else if (!mShowPeople) {
                        collectionViewItem.prepareDetailImageView();
                    } else {
                        collectionViewItem.setDetailImage(R.drawable.basketball);
                    }
                } else {
                    collectionViewItem.prepareDetailImageView();
                    reset(collectionViewItemHolder);
                }
            }
            collectionViewItem.setHeadline(headline);
            collectionViewItem.setSubheadline(subHeadline);
            collectionViewItem.setAttribute(attribute);
        }

        @Override
        public int getItemCount() {
            return NUM_BIZ_OBJECTS;
        }


        void setShowDetailImage(boolean showDetailImage) {
            mShowDetailImage = showDetailImage;
        }

        void setShowPeople(boolean showPeople) {
            mShowPeople = showPeople;
        }

        public void setObjects(List<BizObject> objects) {
            mObjects.clear();
            mObjects.addAll(objects);
        }

        void setShowHeadline(boolean showHeadline) {
            mShowHeadline = showHeadline;
        }

        void setShowSubHeadline(boolean showSubHeadline) {
            mShowSubHeadline = showSubHeadline;
        }

        void setShowAttribute(boolean showAttribute) {
            mShowAttribute = showAttribute;
        }
    }
}

