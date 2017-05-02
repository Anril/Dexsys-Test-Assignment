package com.anril.dexsys_test_assignment.screens.imagepreview;

import com.anril.dexsys_test_assignment.models.GalleryImage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Anril on 02.05.2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class ImagePreviewPresenterTest {

    ImagePreviewContract.View imagePreviewActivityMock;
    ImagePreviewContract.Presenter imagePreviewPresenter;

    GalleryImage galleryImageFake;

    @Before
    public void setupMethod() {
        imagePreviewActivityMock = mock(ImagePreviewActivity.class);
        imagePreviewPresenter = new ImagePreviewPresenter(imagePreviewActivityMock);

        galleryImageFake = new GalleryImage();
        galleryImageFake.setId(1111);
    }

    @Test
    public void onCreate_withPermissionDenied(){
        when(imagePreviewActivityMock.isPermissionGranted()).thenReturn(false);

        imagePreviewPresenter.onCreate();

        verify(imagePreviewActivityMock).showTextPermissionRequired();
    }

    @Test
    public void onCreate_withPermissionGranted(){
        when(imagePreviewActivityMock.isPermissionGranted()).thenReturn(true);
        when(imagePreviewActivityMock.getGalleryImage()).thenReturn(galleryImageFake);

        imagePreviewPresenter.onCreate();

        verify(imagePreviewActivityMock).showImage(galleryImageFake);
        verify(imagePreviewActivityMock).showImageInfo(galleryImageFake);
    }
}
