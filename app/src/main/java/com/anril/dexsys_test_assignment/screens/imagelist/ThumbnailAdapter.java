package com.anril.dexsys_test_assignment.screens.imagelist;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.anril.dexsys_test_assignment.R;
import com.anril.dexsys_test_assignment.models.GalleryImage;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

/**
 * Created by Anril on 28.04.2017.
 */

class ThumbnailAdapter extends RecyclerView.Adapter<ThumbnailAdapter.ViewHolder> {

    private static final String TAG = ThumbnailAdapter.class.getSimpleName();

    private List<GalleryImage> images;
    private ImageListPresenter presenter;

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView thumbnailImageView;

        ViewHolder(View itemView) {
            super(itemView);
            thumbnailImageView = (ImageView) itemView.findViewById(R.id.img_thumbnail);
        }
    }

    ThumbnailAdapter(List<GalleryImage> images, ImageListPresenter presenter) {
        this.images = images;
        this.presenter = presenter;
    }

    public void replaceDataSet(List<GalleryImage> images) {
        this.images = images;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View currentView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_thumbnail, parent, false);

        return new ViewHolder(currentView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Picasso.with(holder.itemView.getContext())
                .load(images.get(position).getUri())
                .transform(new Transformation() {
                    @Override
                    public Bitmap transform(Bitmap source) {
                        Bitmap squaredBitmap;
                        int minSize = Math.min(source.getWidth(), source.getHeight());
                        int x = (source.getWidth() - minSize) / 2;
                        int y = (source.getHeight() - minSize) / 2;
                        squaredBitmap = Bitmap.createBitmap(source, x,y, minSize, minSize);
                        source.recycle();
                        return squaredBitmap;
                    }
                    @Override
                    public String key() {
                        return "to square";
                    }
                })
                .into(holder.thumbnailImageView);

            holder.thumbnailImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onThumbnailClick(images.get(position));
                }
            });
    }
}
