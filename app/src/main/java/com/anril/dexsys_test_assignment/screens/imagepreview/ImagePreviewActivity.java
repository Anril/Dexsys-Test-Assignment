package com.anril.dexsys_test_assignment.screens.imagepreview;

import android.Manifest;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.anril.dexsys_test_assignment.R;
import com.anril.dexsys_test_assignment.models.GalleryImage;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Anril on 28.04.2017.
 */

public class ImagePreviewActivity extends AppCompatActivity
        implements ImagePreviewContract.View {

    private ImageView bigImageView;
    private TextView imageNameTextView;
    private TextView imageDateTextView;
    private TextView permissionDeniedTextView;

    private ImagePreviewPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);

        bigImageView = (ImageView) findViewById(R.id.img_big_image);
        imageNameTextView = (TextView) findViewById(R.id.tv_name);
        imageDateTextView = (TextView) findViewById(R.id.tv_date);
        permissionDeniedTextView = (TextView) findViewById(R.id.tv_permission_denied);


        presenter = new ImagePreviewPresenter(this);
        presenter.onCreate();
    }

    @Override
    public void showImage(GalleryImage image) {
        Picasso.with(this)
                .load(image.getUri())
                .into(bigImageView);
    }

    @Override
    public void showImageInfo(GalleryImage image) {
        imageNameTextView.setText(image.getName());

        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        Date date = new Date(image.getDate());
        imageDateTextView.setText(dateFormat.format(date));
    }

    @Override
    public GalleryImage getGalleryImage() {
        Intent intent = getIntent();
        return (GalleryImage) intent.getParcelableExtra("Image for preview");
    }

    @Override
    public boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            return ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    @Override
    public void showTextPermissionRequired() {
        permissionDeniedTextView.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideTextPermissionRequired() {
        permissionDeniedTextView.setVisibility(View.GONE);

    }

}
