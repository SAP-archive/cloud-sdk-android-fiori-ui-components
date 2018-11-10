package com.sap.cloud.mobile.fiori.demo.attachments;

import static android.os.Environment.getExternalStoragePublicDirectory;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.sap.cloud.mobile.fiori.attachment.AttachmentAction;
import com.sap.cloud.mobile.fiori.attachment.AttachmentFormCell;
import com.sap.cloud.mobile.fiori.attachment.actions.AttachmentActionSelectDocuments;
import com.sap.cloud.mobile.fiori.attachment.actions.AttachmentActionSelectFile;
import com.sap.cloud.mobile.fiori.attachment.actions.AttachmentActionSelectPicture;
import com.sap.cloud.mobile.fiori.attachment.actions.AttachmentActionTakePicture;
import com.sap.cloud.mobile.fiori.attachment.actions.AttachmentActionTakeVideo;
import com.sap.cloud.mobile.fiori.demo.AbstractDemoActivity;
import com.sap.cloud.mobile.fiori.demo.BuildConfig;
import com.sap.cloud.mobile.fiori.demo.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * This activity will crash on the devices where the URIs used in getAttachmentsParcelable method are not valid
 */
public class ProgrammaticAttachmentsActivity extends AbstractDemoActivity {
    AttachmentFormCell mAttachmentFormCell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programmatic_attachments);
        mAttachmentFormCell = findViewById(R.id.programmatic_attachments);
        mAttachmentFormCell.addAttachmentActions(getAttachmentOptions());
        String[] permission = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(this, permission, AttachmentFormCell.REQUEST_CODE);
    }

    @NonNull
    private List<AttachmentAction> getAttachmentOptions() {
        List<AttachmentAction> actions = new ArrayList<>();
        actions.add(new AttachmentActionSelectPicture("Gallery", mAttachmentFormCell));
        actions.add(
                new AttachmentActionTakePicture("Take Picture", mAttachmentFormCell, BuildConfig.APPLICATION_ID + ".attachment.provider"));
        actions.add(new AttachmentActionSelectFile("Attach File", mAttachmentFormCell));
        actions.add(new AttachmentActionSelectDocuments("Attach Document", mAttachmentFormCell));
        actions.add(new AttachmentActionTakeVideo("Capture Video", mAttachmentFormCell));
        return actions;
    }

    @NonNull
    private Intent getAllAttachmentsIntent() {
        File directory = getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File[] files = directory.listFiles();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        if (files != null) {
            ClipData data = null;
            for (File file : files) {
                if (!file.isDirectory()) {
                    Uri uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".attachment.provider", file);
                    if (data == null) {
                        data = new ClipData(new ClipDescription(null, new String[]{getContentResolver().getType(uri)}), new ClipData.Item(uri));
                    } else {
                        data.addItem(new ClipData.Item(uri));
                    }
                    Log.d("Files", "FileName:" + file.getName());
                }
            }
            intent.setClipData(data);
        }
        return intent;
    }

    /**
     * Allow more attachments
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AttachmentFormCell.onReceiveSelection(requestCode, resultCode, data, getBaseContext());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mAttachmentFormCell.setValue(getAllAttachmentsIntent());
    }
}
