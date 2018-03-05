package company.webdemo.agile.com.technologycompany.home.banner;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;

import company.webdemo.agile.com.technologycompany.util.ImageDownLoad;

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
        ImageDownLoad.getDownLoadSmallImg(url,imageView);
//        ImageDownLoad.getDownLoadImg(url,imageView);
    }
}
