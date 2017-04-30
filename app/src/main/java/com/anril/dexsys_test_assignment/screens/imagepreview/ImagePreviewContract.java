package com.anril.dexsys_test_assignment.screens.imagepreview;

import com.anril.dexsys_test_assignment.models.GalleryImage;

/**
 * Created by Anril on 28.04.2017.
 */

interface ImagePreviewContract {

    interface View {
        GalleryImage getGalleryImage();

        void showImage(GalleryImage image);
        void showImageInfo(GalleryImage image);

        boolean isPermissionGranted();

        void showTextPermissionRequired();
        void hideTextPermissionRequired();
    }

    interface Presenter {
        void onCreate();
    }

}
