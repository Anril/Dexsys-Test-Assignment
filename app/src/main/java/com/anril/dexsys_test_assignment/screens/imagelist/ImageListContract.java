package com.anril.dexsys_test_assignment.screens.imagelist;

import com.anril.dexsys_test_assignment.models.GalleryImage;

import java.util.List;

/**
 * Created by Anril on 28.04.2017.
 */

interface ImageListContract {

    interface View {
        void showThumbnails(List<GalleryImage> images);

        boolean isPermissionGranted();
        boolean shouldShowPermissionRationale();
        void showPermissionRationale();
        void requestPermission();

        void showRefreshIndicator();
        void hideRefreshIndicator();

        void showTextPermissionRequired();
        void hideTextPermissionRequired();

        void goToImagePreviewActivity(GalleryImage image);
    }

    interface  Presenter {
        void onCreate();

        void onPermissionGranted();
        void onPermissionDenied() ;

        void onRefresh();

        void onThumbnailClick(GalleryImage image);
    }

}
