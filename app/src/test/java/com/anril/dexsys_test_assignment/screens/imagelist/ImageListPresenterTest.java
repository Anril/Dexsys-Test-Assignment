package com.anril.dexsys_test_assignment.screens.imagelist;

import com.anril.dexsys_test_assignment.models.GalleryImage;
import com.anril.dexsys_test_assignment.usecases.LoadGalleryImages;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Anril on 02.05.2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class ImageListPresenterTest {

    ImageListContract.Presenter imageListPresenter;

    ImageListContract.View imageListActivityMock;
    LoadGalleryImages loadGalleryImagesMock;

    Observable<List<GalleryImage>> galleryImagesObservableFake;
    List<GalleryImage> galleryImagesFake = new ArrayList<>();

    @BeforeClass
    public static void setupClass(){
        RxJavaPlugins.setIoSchedulerHandler(
                scheduler -> Schedulers.trampoline());
        RxJavaPlugins.setComputationSchedulerHandler(
                scheduler -> Schedulers.trampoline());
        RxJavaPlugins.setNewThreadSchedulerHandler(
                scheduler -> Schedulers.trampoline());
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(
                scheduler -> Schedulers.trampoline());
    }

    @Before
    public void setupMethod() {
        imageListActivityMock = mock(ImageListActivity.class);
        loadGalleryImagesMock = mock(LoadGalleryImages.class);


        GalleryImage galleryImageFake = new GalleryImage();
        galleryImageFake.setId(1111);

        galleryImagesFake = new ArrayList<>();
        galleryImagesFake.add(galleryImageFake);

        galleryImagesObservableFake = Observable.just(galleryImagesFake);

        imageListPresenter = new ImageListPresenter(imageListActivityMock, loadGalleryImagesMock);

    }

    @Test
    public void onCreate_withPermissionGranted() throws Exception {
        when(imageListActivityMock.isPermissionGranted()).thenReturn(true);
        when(loadGalleryImagesMock.execute()).thenReturn(galleryImagesObservableFake);

        imageListPresenter.onCreate();

        verify(imageListActivityMock).isPermissionGranted();
        verify(imageListActivityMock).showRefreshIndicator();
        verify(imageListActivityMock).hideRefreshIndicator();
        verify(imageListActivityMock).showThumbnails(galleryImagesFake);
    }

    @Test
    public void onCreate_withPermissionDenied() throws Exception {
        when(imageListActivityMock.isPermissionGranted()).thenReturn(false);

        imageListPresenter.onCreate();

        verify(imageListActivityMock).isPermissionGranted();
        verify(imageListActivityMock).showTextPermissionRequired();
        verify(imageListActivityMock).requestPermission();
    }

    @Test
    public void onPermissionGranted() throws Exception {
        when(imageListActivityMock.isPermissionGranted()).thenReturn(true);
        when(loadGalleryImagesMock.execute()).thenReturn(galleryImagesObservableFake);

        imageListPresenter.onPermissionGranted();

        verify(imageListActivityMock).hideTextPermissionRequired();
    }

    @Test
    public void onPermissionDenied() throws Exception {
        imageListPresenter.onPermissionDenied();

        verify(imageListActivityMock).showTextPermissionRequired();
    }

}
