package com.ajm.employee;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * Created by Anish on 8/24/2020.
 */
public class SharePhotoGalery extends AppCompatActivity {
    String PARAM_MULTIPLE_IMAGE = "param_multiple";
    String PARAM_IMAGE = "param_image";
    String PARAM_VIDEO = "param_video";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String type = intent.getType();
        String action = intent.getAction();
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                handleSendImage(intent);
            } else if (type.startsWith("video/")) {
                handleSendVideo(intent);
            }

        }
        // for multiple images
        if (Intent.ACTION_SEND_MULTIPLE.equals(action) && getIntent().hasExtra(Intent.EXTRA_STREAM)) {
            ArrayList<Parcelable> list = getIntent().getParcelableArrayListExtra(Intent.EXTRA_STREAM);
            Intent i = new Intent(this, RequestListingActivity.class);
            i.putExtra(PARAM_MULTIPLE_IMAGE, list);
            startActivity(i);
            finish();
        }
    }

    private void handleSendImage(Intent intent) {
        Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);

        if (imageUri != null) {
            Intent i = new Intent(this, SingleAddActivity.class);
            i.putExtra(PARAM_IMAGE, imageUri.toString());
            startActivity(i);
            finish();
        } else {
            Toast.makeText(this, "Error occured, URI is invalid", Toast.LENGTH_LONG).show();
        }
    }

    // For video
    private void handleSendVideo(Intent intent) {
        Uri videoUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        String url_of_video = getRealPathFromURI(videoUri);
        if (videoUri != null) {
            Intent i = new Intent(this, VideoPlayerActivity.class);
            i.putExtra(PARAM_VIDEO, url_of_video);
            startActivity(i);
            finish();
        } else {
            Toast.makeText(this, "Error occured, URI is invalid", Toast.LENGTH_LONG).show();
        }
    }

    private String getRealPathFromURI(Uri contentURI) {

        String thePath = "no-path-found";
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentURI, filePathColumn, null, null, null);
        assert cursor != null;
        if(cursor.moveToFirst()){
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            thePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return  thePath;
    }
}