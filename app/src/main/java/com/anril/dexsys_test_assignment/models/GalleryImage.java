package com.anril.dexsys_test_assignment.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Anril on 28.04.2017.
 */

public class GalleryImage implements Parcelable {

    private int id;

    private String name;
    private long date;

    private String path;
    private Uri uri;

    public static final Creator<GalleryImage> CREATOR = new Creator<GalleryImage>() {
        @Override
        public GalleryImage createFromParcel(Parcel in) {
            return new GalleryImage(in);
        }

        @Override
        public GalleryImage[] newArray(int size) {
            return new GalleryImage[size];
        }
    };

    public GalleryImage () {}

    protected GalleryImage(Parcel in) {
        id = in.readInt();
        name = in.readString();
        path = in.readString();
        date = in.readLong();
        uri = in.readParcelable(Uri.class.getClassLoader());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long value) {
        this.date = value;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(path);
        dest.writeLong(date);
        dest.writeParcelable(uri, flags);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof GalleryImage))
            return false;

        GalleryImage otherObject = (GalleryImage) obj;
        return  (id == otherObject.getId());
    }
}
