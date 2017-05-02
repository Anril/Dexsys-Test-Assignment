package com.anril.dexsys_test_assignment.usecases;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import com.anril.dexsys_test_assignment.models.GalleryImage;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by Anril on 28.04.2017.
 */

public class LoadGalleryImages {

    private ContentResolver contentResolver;

    public LoadGalleryImages(Context context) {
        contentResolver = context.getContentResolver();
    }

    public Observable<List<GalleryImage>> execute() {
        return Observable.create(emitter -> {
            try {
                // long loading imitation
                SystemClock.sleep(1_00);

                emitter.onNext(getGalleryImages());
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }

    @NonNull
    private List<GalleryImage> getGalleryImages() {
        List<GalleryImage> images = new ArrayList<>();
        Cursor cursor = null;
        try {
            String[] projection = {
                    MediaStore.Images.Media._ID,
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media.DISPLAY_NAME,
                    MediaStore.Images.Media.DATE_TAKEN,
            };

            cursor = contentResolver.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
                int dataIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                int nameIndex = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME);
                int dateIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN);
                do {
                    GalleryImage tmpImage = new GalleryImage();
                    tmpImage.setId(cursor.getInt(idIndex));
                    tmpImage.setPath(cursor.getString(dataIndex));
                    tmpImage.setName(cursor.getString(nameIndex));
                    tmpImage.setDate(cursor.getLong(dateIndex));
                    Uri imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                            .buildUpon()
                            .appendPath(String.valueOf(tmpImage.getId()))
                            .build();
                    tmpImage.setUri(imageUri);
                    images.add(tmpImage);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return images;
    }
}
