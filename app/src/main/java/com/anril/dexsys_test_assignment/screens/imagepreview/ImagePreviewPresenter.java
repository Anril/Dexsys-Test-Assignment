package com.anril.dexsys_test_assignment.screens.imagepreview;

import com.anril.dexsys_test_assignment.models.GalleryImage;

/**
 * Created by Anril on 28.04.2017.
 */

class ImagePreviewPresenter implements ImagePreviewContract.Presenter {

    private ImagePreviewContract.View view;

    ImagePreviewPresenter(ImagePreviewContract.View view) {
        this.view = view;
    }

    @Override
    public void onCreate() {
        if (view.isPermissionGranted()) {
            view.hideTextPermissionRequired();
            GalleryImage imageForPreview = view.getGalleryImage();
            view.showImage(imageForPreview);
            view.showImageInfo(imageForPreview);
        } else {
            view.showTextPermissionRequired();
        }
    }

}
