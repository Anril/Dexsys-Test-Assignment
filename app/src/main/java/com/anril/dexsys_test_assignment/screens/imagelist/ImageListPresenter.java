package com.anril.dexsys_test_assignment.screens.imagelist;

import com.anril.dexsys_test_assignment.models.GalleryImage;
import com.anril.dexsys_test_assignment.usecases.LoadGalleryImages;

/**
 * Created by Anril on 28.04.2017.
 */

class ImageListPresenter implements ImageListContract.Presenter {

    private ImageListContract.View view;
    private LoadGalleryImages loadGalleryImages;

    ImageListPresenter(ImageListContract.View view, LoadGalleryImages loadGalleryImages) {
        this.loadGalleryImages = loadGalleryImages;
        this.view = view;
    }

    @Override
    public void onCreate() {
        loadImages();
    }

    @Override
    public void onPermissionGranted() {
        loadImages();
    }

    @Override
    public void onPermissionDenied() {
        view.showTextPermissionRequired();
    }

    @Override
    public void onRefresh() {
        loadImages();
    }

    @Override
    public void onThumbnailClick(GalleryImage image) {
        view.goToImagePreviewActivity(image);
    }

    private void loadImages() {
        if (view.isPermissionGranted()) {
            view.showRefreshIndicator();
            view.showThumbnails(loadGalleryImages.execute());
            view.hideRefreshIndicator();
            view.hideTextPermissionRequired();
        } else {
            view.hideRefreshIndicator();
            if (view.shouldShowPermissionRationale()) {
                view.showPermissionRationale();
            } else {
                view.requestPermission();
            }
        }
    }

}
