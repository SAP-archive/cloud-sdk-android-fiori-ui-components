package com.sap.cloud.mobile.fiori.demo.formcell;

import static com.sap.cloud.mobile.fiori.demo.formcell.CustomFormCell1.CUSTOM_FORM_CELL_1;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.sap.cloud.mobile.fiori.attachment.Attachment;
import com.sap.cloud.mobile.fiori.attachment.AttachmentFormCell;
import com.sap.cloud.mobile.fiori.attachment.AttachmentItemClickListener;
import com.sap.cloud.mobile.fiori.attachment.actions.AttachmentActionSelectFile;
import com.sap.cloud.mobile.fiori.demo.AbstractDemoActivity;
import com.sap.cloud.mobile.fiori.demo.R;
import com.sap.cloud.mobile.fiori.formcell.DateTimePickerFormCell;
import com.sap.cloud.mobile.fiori.formcell.Duration;
import com.sap.cloud.mobile.fiori.formcell.DurationPickerFormCell;
import com.sap.cloud.mobile.fiori.formcell.FormCell;
import com.sap.cloud.mobile.fiori.formcell.FormCellCreator;
import com.sap.cloud.mobile.fiori.formcell.NoteFormCell;
import com.sap.cloud.mobile.fiori.formcell.SectionedRecyclerViewAdapter;
import com.sap.cloud.mobile.fiori.formcell.SeparatorFormCell;
import com.sap.cloud.mobile.fiori.formcell.SliderFormCell;
import com.sap.cloud.mobile.fiori.object.KeylineDividerItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SectionedRecyclerDemoActivity extends AbstractDemoActivity {

    @NonNull
    ArrayList<Attachment> selectedAttachment1 = new ArrayList<>();
    @NonNull
    ArrayList<Attachment> selectedAttachment2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sectioned_recycler_demo);
        RecyclerView recyclerView = findViewById(R.id.sectionedRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        int px = (int) (16 * getResources().getDisplayMetrics().density + 0.5f);

        SectionedRecyclerViewAdapter adapter = new SectionedRecyclerViewAdapter() {

            @NonNull
            @Override
            public FormCellHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                FormCellHolder holder = super.onCreateViewHolder(parent, viewType);

                if (viewType == FormCell.WidgetType.ATTACHMENT.ordinal()) {
                    AttachmentFormCell cell = (AttachmentFormCell) holder.itemView;
                    cell.setEditable(true);
                    cell.addAttachmentActions(Collections.singletonList(
                            new AttachmentActionSelectFile("Select Files", cell)));
                    return new FormCellHolder(cell);
                }
                return holder;
            }

            @Override
            public int getItemCountForSection(int section) {
                switch (section) {
                    case 0:
                        return 2;
                    case 1:
                        return 3;
                    case 2:
                        return 3;
                    case 3:
                        return 2;
                    case 4:
                        return 2;
                    case 5:
                        return 2;
                    case 6:
                        return 2;
                    default:
                        return 3;
                }
            }


            @Override
            public void onBindCellHolder(@NonNull FormCellHolder cellHolder, int section, int row) {
                switch (section) {
                    case 0:
                        switch (row) {
                            case 0:
                                AttachmentFormCell mAttachmentFormCell = (AttachmentFormCell) cellHolder.itemView;
                                mAttachmentFormCell.clear();
                                mAttachmentFormCell.setValue(selectedAttachment1);
                                mAttachmentFormCell.setCellValueChangeListener(new FormCell.CellValueChangeListener<List<Attachment>>() {
                                    @Override
                                    public void cellChangeHandler(List<Attachment> value) {

                                        for (Attachment attachment : value) {
                                            if (!selectedAttachment1.contains(attachment)) {
                                                selectedAttachment1.add(attachment);
                                            }
                                        }
                                    }

                                    @Nullable
                                    @Override
                                    public CharSequence updatedDisplayText(@Nullable List<Attachment> value) {
                                        return String.format(getString(R.string.attachment_default_key), mAttachmentFormCell.getValue().size());
                                    }
                                });
                                if (!mAttachmentFormCell.getAttachmentItemClickListeners().isEmpty()) {
                                    mAttachmentFormCell.getAttachmentItemClickListeners().clear();
                                }
                                mAttachmentFormCell.addAttachmentTouchListener(new AttachmentItemClickListener() {
                                    @Override
                                    public void onClick(View view, int position) {

                                    }

                                    @Override
                                    public void onLongClick(View view, int position) {

                                    }

                                    @Override
                                    public void onClickDelete(Attachment attachment) {
                                        selectedAttachment1.remove(attachment);
                                    }
                                });
                                break;

                            default:
                                AttachmentFormCell mAttachmentFormCell2 = (AttachmentFormCell) cellHolder.itemView;
                                mAttachmentFormCell2.clear();
                                mAttachmentFormCell2.setValue(selectedAttachment2);
                                mAttachmentFormCell2.setCellValueChangeListener(new FormCell.CellValueChangeListener<List<Attachment>>() {
                                    @Override
                                    public void cellChangeHandler(List<Attachment> value) {
                                        for (Attachment attachment : value) {
                                            if (!selectedAttachment2.contains(attachment)) {
                                                selectedAttachment2.add(attachment);
                                            }
                                        }
                                        mAttachmentFormCell2.setKey(String.format(getString(R.string.attachment_default_key), mAttachmentFormCell2.getValue().size()));
                                    }

                                    @Nullable
                                    @Override
                                    public CharSequence updatedDisplayText(@Nullable List<Attachment> value) {
                                        return String.format(getString(R.string.attachment_default_key), mAttachmentFormCell2.getValue().size());
                                    }
                                });
                                if (!mAttachmentFormCell2.getAttachmentItemClickListeners().isEmpty()) {
                                    mAttachmentFormCell2.getAttachmentItemClickListeners().clear();
                                }
                                mAttachmentFormCell2.addAttachmentTouchListener(new AttachmentItemClickListener() {
                                    @Override
                                    public void onClick(View view, int position) {

                                    }

                                    @Override
                                    public void onLongClick(View view, int position) {

                                    }

                                    @Override
                                    public void onClickDelete(Attachment attachment) {
                                        selectedAttachment2.remove(attachment);
                                    }
                                });
                                break;
                        }
                        break;
                    case 1:
                        NoteFormCell noteFormCell = (NoteFormCell) cellHolder.itemView;
                        noteFormCell.setValue("Note: " + section + " and row: " + row);
                        break;
                    case 2:
                        DurationPickerFormCell durationPickerFormCell = (DurationPickerFormCell) cellHolder.itemView;
                        durationPickerFormCell.setKey("Duration picker: " + section + " and row: " + row);
                        durationPickerFormCell.setValue(new Duration(section * row, row));
                        break;
                    case 3:
                        SliderFormCell slider = (SliderFormCell) cellHolder.itemView;
                        slider.setKey("Slider: " + section + " and row: " + row);
                        slider.setValue(section * row * 10);
                        break;
                    case 4:
                        DateTimePickerFormCell dateTimePickerFormCell = (DateTimePickerFormCell) cellHolder.itemView;
                        dateTimePickerFormCell.setKey("Date time picker: " + section + " and row: " + row);
                        break;
                    case 5:
                    case 6:
                        CustomFormCell1 customFormCell1 = (CustomFormCell1) cellHolder.itemView;
                        customFormCell1.setValue("CustomView: " + section + " and row: " + row);
                        break;
                    default:
                        NoteFormCell noteFormCell1 = (NoteFormCell) cellHolder.itemView;
                        noteFormCell1.setValue("Note: " + section + " and row: " + row);
                        break;
                }
            }

            @Override
            public int getItemViewType(int section, int row) {
                switch (section) {
                    case 0:
                        return FormCell.WidgetType.ATTACHMENT.ordinal();
                    case 1:
                        return FormCell.WidgetType.NOTE.ordinal();
                    case 2:
                        return FormCell.WidgetType.DURATION_PICKER.ordinal();
                    case 3:
                        return FormCell.WidgetType.SLIDER.ordinal();
                    case 4:
                        return FormCell.WidgetType.DATE_TIME_PICKER.ordinal();
                    case 5:
                    case 6:
                        return CUSTOM_FORM_CELL_1;
                    default:
                        return FormCell.WidgetType.NOTE.ordinal();
                }
            }

            @Override
            public int getNumberOfSections() {
                return 80;
            }

            @Override
            protected void onBindHeader(@NonNull FormCellHolder header, int section) {
                ((SectionHeaderFooter) header.itemView).setValue("Header For Section: " + section);
                ((SectionHeaderFooter) header.itemView).setTextColor(getColor(R.color.sap_ui_formcell_key));
            }

            @Override
            protected void onBindFooter(@NonNull FormCellHolder footer, int section) {
                ((SectionHeaderFooter) footer.itemView).setValue("Footer For Section: " + section);
                ((SectionHeaderFooter) footer.itemView).setTextColor(getColor(R.color.sap_ui_base_color));
            }

            /*
            protected void onBindSeparatorViewHolder(@NonNull SectionedRecyclerViewAdapter.FormCellHolder holder, int section) {
                SeparatorFormCell separatorFormCell = ((SeparatorFormCell) holder.itemView);
                separatorFormCell.setCaption("Captioned Separator");
                separatorFormCell.setTextColor(getResources().getColor(R.color.white, getBaseContext().getTheme()));
            }
            */

            @Override
            protected boolean hasFooter(int section) {
                return section % 3 == 0;
            }

            @Override
            protected boolean hasHeader(int section) {
                return section % 2 == 0;
            }
        };

        adapter.registerCellCreator(CUSTOM_FORM_CELL_1, new FormCellCreator() {
            @NonNull
            @Override
            public View onCreateCell(@NonNull ViewGroup parent) {
                return new CustomFormCell1(parent.getContext());
            }
        });

        adapter.setFooterEnabled(true);
        adapter.setHeaderEnabled(true);
        adapter.setSeparatorEnabled(true);

        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new CustomDivider(recyclerView.getContext(), DividerItemDecoration.VERTICAL, px));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AttachmentFormCell.onReceiveSelection(requestCode, resultCode, data, getBaseContext());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("attachments1", selectedAttachment1);
        outState.putParcelableArrayList("attachments2", selectedAttachment2);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        List<Attachment> attachments = savedInstanceState.getParcelableArrayList("attachments1");
        if (attachments != null) {
            selectedAttachment1.addAll(attachments);
        }

        attachments = savedInstanceState.getParcelableArrayList("attachments2");
        if (attachments != null) {
            selectedAttachment2.addAll(attachments);
        }
    }
}


class CustomDivider extends KeylineDividerItemDecoration {

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private final int px;
    private Drawable mDivider;

    public CustomDivider(Context context, int orientation, int px) {
        super(context, orientation);
        this.px = px;
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() == null) {
            return;
        }

        int left = px;
        int right = parent.getWidth() - px;

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            if (child instanceof SectionedRecyclerViewAdapter.SectionHeaderFooter && ((SectionedRecyclerViewAdapter.SectionHeaderFooter) child).getValue().toString().contains("Footer")
                    || child instanceof SeparatorFormCell) continue;
            else if (i < childCount - 1) {
                View nextChild = parent.getChildAt(i + 1);
                if (nextChild instanceof SectionedRecyclerViewAdapter.SectionHeaderFooter && ((SectionedRecyclerViewAdapter.SectionHeaderFooter) nextChild).getValue().toString().contains("Footer")
                        || nextChild instanceof SeparatorFormCell) continue;
            }

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        }
    }
}
