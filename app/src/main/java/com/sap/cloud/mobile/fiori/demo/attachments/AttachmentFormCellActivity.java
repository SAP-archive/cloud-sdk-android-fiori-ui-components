package com.sap.cloud.mobile.fiori.demo.attachments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.sap.cloud.mobile.fiori.attachment.Attachment;
import com.sap.cloud.mobile.fiori.attachment.AttachmentAction;
import com.sap.cloud.mobile.fiori.attachment.AttachmentFormCell;
import com.sap.cloud.mobile.fiori.attachment.AttachmentItemClickListener;
import com.sap.cloud.mobile.fiori.attachment.actions.AttachmentActionSelectDocuments;
import com.sap.cloud.mobile.fiori.attachment.actions.AttachmentActionSelectFile;
import com.sap.cloud.mobile.fiori.attachment.actions.AttachmentActionSelectPicture;
import com.sap.cloud.mobile.fiori.attachment.actions.AttachmentActionTakePicture;
import com.sap.cloud.mobile.fiori.attachment.actions.AttachmentActionTakeVideo;
import com.sap.cloud.mobile.fiori.demo.AbstractDemoActivity;
import com.sap.cloud.mobile.fiori.demo.BuildConfig;
import com.sap.cloud.mobile.fiori.demo.R;
import com.sap.cloud.mobile.fiori.formcell.FormCell;

import java.util.ArrayList;
import java.util.List;

public class AttachmentFormCellActivity extends AbstractDemoActivity {

    private AttachmentFormCell mAttachmentFormCell;
    @Nullable
    private MenuItem mItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attachment_form_cell);
        mAttachmentFormCell = findViewById(R.id.attachmentFormCell);
        mAttachmentFormCell.addAttachmentActions(getAttachmentOptions());

        mAttachmentFormCell.setCellValueChangeListener(new FormCell.CellValueChangeListener<List<Attachment>>() {
            @Override
            public void cellChangeHandler(List<Attachment> value) {
            }

            @Override
            public CharSequence updatedDisplayText(List<Attachment> value) {
                return String.format(getApplication().getString(com.sap.cloud.mobile.fiori.R.string.attachment_default_key), value.size());
            }
        });

        mAttachmentFormCell.setSeparatorPosition(3);

        // If you want to add callbacks for touch events on Attachments. Please notice that this does not override
        // default behavior. However it add the new behavior on along with the existing one.
        mAttachmentFormCell.addAttachmentTouchListener(new AttachmentItemClickListener() {
            /**
             * Behavior on single tap/click
             *
             * @param view     View
             * @param position int
             */
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getApplicationContext(), "Opening File", Toast.LENGTH_SHORT).show();
            }

            /**
             * Behavior on long press
             *
             * @param view     View
             * @param position int
             */
            @Override
            public void onLongClick(View view, int position) {

            }

            @Override
            public void onClickDelete(Attachment position) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mItem != null) {
            mItem.setTitle(mAttachmentFormCell.isEditable() ? "Save" : "Edit");
        }
        AttachmentFormCell.onReceiveSelection(requestCode, resultCode, data, this);
    }

    /**
     * Callback for the result from requesting permissions. This method
     * is invoked for every call on {@link #requestPermissions(String[], int)}.
     * <p>
     * <strong>Note:</strong> It is possible that the permissions request interaction
     * with the user is interrupted. In this case you will receive empty permissions
     * and results arrays which should be treated as a cancellation.
     * </p>
     *
     * @param requestCode  The request code passed in {@link #requestPermissions(String[], int)}.
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either {@link PackageManager#PERMISSION_GRANTED}
     *                     or {@link PackageManager#PERMISSION_DENIED}. Never null.
     * @see #requestPermissions(String[], int)
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AttachmentFormCell.onRequestPermissionsResult(requestCode, permissions, grantResults, getBaseContext());
    }

    @NonNull
    private List<AttachmentAction> getAttachmentOptions() {
        List<AttachmentAction> actions = new ArrayList<>();
        actions.add(new AttachmentActionSelectPicture("Gallery", mAttachmentFormCell));
        actions.add(new AttachmentActionSelectFile("Attach File", mAttachmentFormCell));
        actions.add(new AttachmentActionSelectDocuments("Attach Document", mAttachmentFormCell));
        actions.add(new AttachmentActionTakeVideo("Capture Video", mAttachmentFormCell));
        actions.add(new AttachmentActionTakePicture("Take Picture", mAttachmentFormCell, BuildConfig.APPLICATION_ID + ".attachment.provider"));
        return actions;
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.attachment_menu, menu);
        mItem = menu.findItem(R.id.attachment_edit_menu);
        if (mItem != null) {
            if (mAttachmentFormCell.isEditable()) {
                mItem.setTitle("Save");
            } else {
                mItem.setTitle("Edit");
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.attachment_edit_menu:
                if (mAttachmentFormCell.isEditable()) {
                    mAttachmentFormCell.setEditable(false);
                    item.setTitle("Edit");
                } else {
                    mAttachmentFormCell.setEditable(true);
                    item.setTitle("Save");
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("wasEditable", mAttachmentFormCell.isEditable());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mAttachmentFormCell.setEditable(savedInstanceState.getBoolean("wasEditable"));
    }
}
