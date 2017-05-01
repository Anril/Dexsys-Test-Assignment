package com.anril.dexsys_test_assignment.screens.imagelist;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.anril.dexsys_test_assignment.R;
import com.anril.dexsys_test_assignment.screens.imagepreview.ImagePreviewActivity;
import com.anril.dexsys_test_assignment.models.GalleryImage;
import com.anril.dexsys_test_assignment.usecases.LoadGalleryImages;

import java.util.ArrayList;
import java.util.List;

public class ImageListActivity extends AppCompatActivity implements ImageListContract.View,
        SwipeRefreshLayout.OnRefreshListener {

    private final static String TAG = ImageListActivity.class.getSimpleName();
    private final static int PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 262;

    private ImageListPresenter presenter;
    private ThumbnailAdapter thumbnailAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView permissionDeniedTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);

        LoadGalleryImages loadGalleryImages = new LoadGalleryImages(this);
        presenter = new ImageListPresenter(this, loadGalleryImages);
        thumbnailAdapter = new ThumbnailAdapter(new ArrayList<GalleryImage>(), presenter);

        RecyclerView thumbnailsRecyclerView = (RecyclerView) findViewById(R.id.rv_thumbnails);
        thumbnailsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        thumbnailsRecyclerView.setAdapter(thumbnailAdapter);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_refresher);
        swipeRefreshLayout.setOnRefreshListener(this);

        permissionDeniedTextView = (TextView) findViewById(R.id.tv_permission_denied);

        presenter.onCreate();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                presenter.onPermissionGranted();
            } else {
                presenter.onPermissionDenied();
            }
        }
    }

    @Override
    public void showThumbnails(List<GalleryImage> images) {
        thumbnailAdapter.replaceDataSet(images);
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
    public boolean shouldShowPermissionRationale() {
        if (Build.VERSION.SDK_INT >= 23) {
            return (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE));

        }
        return false;
    }

    @Override
    public void showPermissionRationale() {
        requestPermission();
    }

    @Override
    public void requestPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void showRefreshIndicator() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideRefreshIndicator() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showTextPermissionRequired() {
        permissionDeniedTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideTextPermissionRequired() {
        permissionDeniedTextView.setVisibility(View.GONE);
    }

    @Override
    public void goToImagePreviewActivity(GalleryImage image) {
        Intent intent = new Intent(this, ImagePreviewActivity.class);
        intent.putExtra("Image for preview", image);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        presenter.onRefresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
