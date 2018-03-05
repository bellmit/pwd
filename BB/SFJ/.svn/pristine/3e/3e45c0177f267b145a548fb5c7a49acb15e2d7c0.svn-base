package com.tastebychance.sfj.view.banner;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.tastebychance.sfj.util.PicassoUtils;


/**
 * Created by shenbh on 2017/9/2.
 */

public class ImageViewHolder implements Holder<String> {
    private ImageView imageView;

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, String url) {
//        imageView.setImageResource(id);
//        ImageDownLoad.getDownLoadImg(url,imageView);
//        ImageDownLoad.getBannerImg(url, imageView);
        PicassoUtils.getinstance().loadImageByPath(context, url, imageView, PicassoUtils.PICASSO_BITMAP_SHOW_NORMAL_TYPE, 0);
    }
}
