package com.anril.dexsys_test_assignment.screens.imagelist;

import com.anril.dexsys_test_assignment.models.GalleryImage;
import com.anril.dexsys_test_assignment.usecases.LoadGalleryImages;

import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Anril on 28.04.2017.
 */

class ImageListPresenter implements ImageListContract.Presenter {

    private ImageListContract.View view;
    private LoadGalleryImages loadGalleryImages;
    private Disposable loadImagesSubscription;

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
            view.hideTextPermissionRequired();

            loadImagesSubscription = Observable.just(1)
                    .doOnNext(x -> view.showRefreshIndicator())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .flatMap(x -> loadGalleryImages.execute())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnComplete(view::hideRefreshIndicator)
                    .subscribe(images -> view.showThumbnails(images));

        } else {
            view.hideRefreshIndicator();
            if (view.shouldShowPermissionRationale()) {
                view.showPermissionRationale();
            } else {
                view.requestPermission();
            }
        }
    }

    @Override
    public void onDestroy() {
        loadImagesSubscription.dispose();
    }
}
