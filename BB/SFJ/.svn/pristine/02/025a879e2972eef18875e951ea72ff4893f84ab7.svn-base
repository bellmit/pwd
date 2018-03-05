package com.tastebychance.sfj.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import com.tastebychance.sfj.R;

/**
 * picasso 图片处理工具类<BR/>
 * 优缺点：对图片处理强大，取消已经不在视野范围的ImageView图片资源的加载（否则会导致图片错位），
 * 使用4.0+系统上的HTTP缓存来代替磁盘缓存；只能得到结果，不能监听图片下载过程
 * <BR/> Picasso 可以与okhttp搭配
 * <p>
 * 如果使用Picasso同时也使用了okhttp库，那么项目运行的时候可能会报出一下异常：
 * Picasso detected an unsupported OkHttp on the classpath
 * 针对该情况，网上说需要引入：compile 'com.squareup.okhttp:okhttp-urlconnection:2.2.0'
 * 即同时引入一下三个包：
 * compile 'com.squareup.okhttp:okhttp:2.4.0'
 * compile 'com.squareup.okhttp:okhttp-urlconnection:2.2.0'
 * compile 'com.squareup.picasso:picasso:2.4.0'
 */
public class PicassoUtils {
    private static PicassoUtils instance;
    /**
     * 圆形
     */
    public static String PICASSO_BITMAP_SHOW_CIRCLE_TYPE = "PicassoUtils_Circle_Type";
    /**
     * 圆角
     */
    public static String PICASSO_BITMAP_SHOW_ROUND_TYPE = "PicassoUtils_Round_Type";
    /**
     * 正常
     */
    public static String PICASSO_BITMAP_SHOW_NORMAL_TYPE = "PicassoUtils_Normal_Type";

    public static PicassoUtils getinstance() {
        if (instance == null) {
            synchronized (PicassoUtils.class) {
                if (instance == null) {
                    instance = new PicassoUtils();
                }
            }
        }
        return instance;
    }
    //Picasso使用的方法汇总：
    //Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(imageView);
    //Picasso.with(context).load(url).into(view);
    //Picasso.with(context).load(url) .resize(50, 50).centerCrop().into(imageView)
    ////这里的placeholder将resource传入通过getResource.getDrawable取资源，所以可以是张图片也可以是color id
    //Picasso.with(context).load(url).placeholder(R.drawable.user_placeholder).error(R.drawable.user_placeholder_error).into(imageView);
    //
    // Resources, assets, files, content providers 加载图片都支持
    //Picasso.with(context).load(R.drawable.landing_screen).into(imageView1);
    //Picasso.with(context).load("file:///android_asset/DvpvklR.png").into(imageView2);
    //Picasso.with(context).load(new File(...)).into(imageView3);
    ////这里显示notification的图片
    //Picasso.with(activity).load(Data.URLS[new Random().nextInt(Data.URLS.length)]).resizeDimen(R.dimen.notification_icon_width_height,    R.dimen.notification_icon_width_height).into(remoteViews, R.id.photo, NOTIFICATION_ID, notification);
    ////这里是通过设置tag标签，就是当前传过来的context，这样就可以根据这个context tag来pause和resume显示了
    //Picasso.with(context).load(url).placeholder(R.drawable.placeholder).error(R.drawable.error).fit().tag(context).into(view);
    ////监听onScrollStateChanged的时候调用执行
    //picasso.resumeTag(context);
    //picasso.pauseTag(context);
    //Picasso.with(context).load(contactUri).placeholder(R.drawable.contact_picture_placeholder).tag(context).into(holder.icon);

    public void loadNormalGroupImgByPath(Context context, String path, ImageView imageView) {
        if (StringUtil.isEmpty(path)) {
            loadImageById(context, R.drawable.pic1, imageView, PICASSO_BITMAP_SHOW_NORMAL_TYPE, 24);
            return;
        }
        loadImageByPath(context, path, imageView, PICASSO_BITMAP_SHOW_NORMAL_TYPE, 0);
    }

    public void loadNormalByPath(Context context, String path, ImageView imageView) {
        if (StringUtil.isEmpty(path)) {
            loadImageById(context, R.drawable.pic1, imageView, PICASSO_BITMAP_SHOW_NORMAL_TYPE, 24);
            return;
        }
        loadImageByPath(context, path, imageView, PICASSO_BITMAP_SHOW_NORMAL_TYPE, 0);
    }

    public void loadNormalByUri(Context context, Uri uri, ImageView imageView) {
        if (null == uri) {
            loadImageById(context, R.drawable.pic1, imageView, PICASSO_BITMAP_SHOW_NORMAL_TYPE, 24);
            return;
        }
        loadImageByUri(context, uri, imageView, PICASSO_BITMAP_SHOW_NORMAL_TYPE, 0);
    }

    public void loadNormalById(Context context, int id, ImageView imageView) {
        loadImageById(context, id, imageView, PICASSO_BITMAP_SHOW_NORMAL_TYPE, 0);
    }

    /**
     * 头像，加载圆形图片，通过地址
     *
     * @param context
     * @param path
     * @param imageView
     * @param roundRadius
     */
    public void loadCircleHead(Context context, String path, ImageView imageView, float roundRadius) {
        if (StringUtil.isEmpty(path)) {
            loadImageById(context, R.drawable.person_defaultheadportrait, imageView, PICASSO_BITMAP_SHOW_CIRCLE_TYPE, roundRadius);
            return;
        }
        loadImageByPath(context, path, imageView, R.drawable.person_defaultheadportrait, R.drawable.person_defaultheadportrait, PICASSO_BITMAP_SHOW_CIRCLE_TYPE, roundRadius);
    }

    /**
     * 加载圆角图片，通过地址
     *
     * @param context
     * @param path
     * @param imageView
     * @param roundRadius
     */
    public void loadRoundImage(Context context, String path, ImageView imageView, float roundRadius) {
        if (StringUtil.isEmpty(path)) {
            loadImageById(context, R.drawable.pic1, imageView, PICASSO_BITMAP_SHOW_ROUND_TYPE, roundRadius);
            return;
        }
        loadImageByPath(context, path, imageView, PICASSO_BITMAP_SHOW_ROUND_TYPE, roundRadius);
    }

    /**
     * 加载圆形图片，通过地址
     *
     * @param context
     * @param path
     * @param imageView
     * @param roundRadius
     */
    public void loadCircleImage(Context context, String path, ImageView imageView, float roundRadius) {
        if (StringUtil.isEmpty(path)) {
            loadImageById(context, R.drawable.pic1, imageView, PICASSO_BITMAP_SHOW_CIRCLE_TYPE, roundRadius);
            return;
        }
        loadImageByPath(context, path, imageView, PICASSO_BITMAP_SHOW_CIRCLE_TYPE, roundRadius);
    }

    /**
     * 加载图片通过地址
     *
     * @param context
     * @param path
     * @param imageView
     * @param bitmapShowType PICASSO_BITMAP_SHOW_CIRCLE_TYPE ， PICASSO_BITMAP_SHOW_ROUND_TYPE ，PICASSO_BITMAP_SHOW_NORMAL_TYPE
     * @param roundRadius    设置圆角半径
     */
    public void loadImageByPath(Context context, String path, ImageView imageView, String bitmapShowType, float roundRadius) {
        if (StringUtil.isEmpty(path)) {
            loadImageById(context, R.drawable.pic1, imageView, bitmapShowType, roundRadius);
            return;
        }
        loadImageByPath(context, path, imageView, R.drawable.pic1, R.drawable.pic1, bitmapShowType, roundRadius);
    }

    /**
     * 加载图片通过地址
     *
     * @param context
     * @param path             <BR/>
     *                         String imagePath = "/mnt/sdcard/phone_pregnancy/header.png";  <BR/>
     *                         String imagefileUrl = Scheme.FILE.wrap(imagePath); <BR/>
     *                         //图片来源于Content provider
     *                         String contentprividerUrl = "content://media/external/audio/albumart/13";   <BR/>
     *                         //图片来源于assets
     *                         //  String assetsUrl = Scheme.ASSETS.wrap("image.png");  <BR/>
     *                         String assetsUrl = "assets://fail_image.9.png";  <BR/>
     *                         //图片来源于  drawable
     *                         //  String drawableUrl = Scheme.DRAWABLE.wrap("R.drawable.ic_launcher.png");<BR/>
     *                         String drawableUrl = "drawable://" + R.drawable.ic_add; <BR/>
     *                         //图片来源于  网络
     *                         String neturi = "http://ww2.sinaimg.cn/large/49aaa343jw1dgwd0qvb4pj.jpg";<BR/>
     * @param imageView
     * @param placeholderimage 占位图片
     * @param errorimage       加载错误图片
     * @param bitmapShowType   PICASSO_BITMAP_SHOW_CIRCLE_TYPE ， PICASSO_BITMAP_SHOW_ROUND_TYPE ，PICASSO_BITMAP_SHOW_NORMAL_TYPE
     * @param roundRadius      设置圆角半径
     */
    public void loadImageByPath(Context context, String path, ImageView imageView, int placeholderimage, int errorimage, String bitmapShowType, float roundRadius) {
        if (StringUtil.isEmpty(path)) {
            loadImageById(context, R.drawable.pic1, imageView, bitmapShowType, roundRadius);
            return;
        }

        if (!path.contains("http:") && !path.contains("https:")) {
            path = UrlManager.REQUESTIMGURL + path;
        }

        if (bitmapShowType.equals(PICASSO_BITMAP_SHOW_CIRCLE_TYPE)) {
            Picasso.with(context).load(path).placeholder(placeholderimage).error(errorimage).transform(new CircleTransform()).into(imageView);
        } else if (bitmapShowType.equals(PICASSO_BITMAP_SHOW_ROUND_TYPE)) {
            Picasso.with(context).load(path).placeholder(placeholderimage).error(errorimage).transform(new PicassoRoundTransform(roundRadius)).into(imageView);
        } else {
            Picasso.with(context).load(path).placeholder(placeholderimage).error(errorimage).into(imageView);
        }
    }

    /**
     * 加载本地圆形图片，通过id
     *
     * @param context
     * @param localimage
     * @param imageView
     * @param roundRadius
     */
    public void loadCircleImageById(Context context, int localimage, ImageView imageView, float roundRadius) {
        loadImageById(context, localimage, imageView, PICASSO_BITMAP_SHOW_CIRCLE_TYPE, roundRadius);
    }

    /**
     * 加载图片本地 通过id
     *
     * @param context
     * @param localimage     R.drawable.landing_screen
     * @param imageView
     * @param bitmapShowType PICASSO_BITMAP_SHOW_CIRCLE_TYPE ， PICASSO_BITMAP_SHOW_ROUND_TYPE ，PICASSO_BITMAP_SHOW_NORMAL_TYPE
     * @param roundRadius    设置圆角半径
     */
    public void loadImageById(Context context, int localimage, ImageView imageView, String bitmapShowType, float roundRadius) {
        if (bitmapShowType.equals(PICASSO_BITMAP_SHOW_CIRCLE_TYPE)) {
            Picasso.with(context).load(localimage).placeholder(R.drawable.person_defaultheadportrait).error(R.drawable.pic1).transform(new CircleTransform()).into(imageView);
        } else if (bitmapShowType.equals(PICASSO_BITMAP_SHOW_ROUND_TYPE)) {
            Picasso.with(context).load(localimage).transform(new PicassoRoundTransform(roundRadius)).into(imageView);
        } else {
            Picasso.with(context).load(localimage).into(imageView);
        }
    }

    /**
     * 加载本地圆形图片，通过uri
     *
     * @param context
     * @param uri
     * @param imageView
     * @param roundRadius
     */
    public void loadCircleImageByUri(Context context, Uri uri, ImageView imageView, float roundRadius) {
        if (uri == null) {
            loadImageById(context, R.drawable.pic1, imageView, PICASSO_BITMAP_SHOW_CIRCLE_TYPE, roundRadius);
            return;
        }
        loadImageByUri(context, uri, imageView, PICASSO_BITMAP_SHOW_CIRCLE_TYPE, roundRadius);
    }

    /**
     * 加载本地图片 通过uri
     *
     * @param context
     * @param uri
     * @param imageView
     * @param bitmapShowType
     * @param roundRadius
     */
    public void loadImageByUri(Context context, Uri uri, ImageView imageView, String bitmapShowType, float roundRadius) {
        if (uri == null) {
            loadImageById(context, R.drawable.pic1, imageView, bitmapShowType, roundRadius);
            return;
        }
        if (bitmapShowType.equals(PICASSO_BITMAP_SHOW_CIRCLE_TYPE)) {
            Picasso.with(context).load(uri).placeholder(R.drawable.person_defaultheadportrait).error(R.drawable.pic1).transform(new CircleTransform()).into(imageView);
        } else if (bitmapShowType.equals(PICASSO_BITMAP_SHOW_ROUND_TYPE)) {
            Picasso.with(context).load(uri).placeholder(R.drawable.person_defaultheadportrait).error(R.drawable.pic1).transform(new PicassoRoundTransform(roundRadius)).into(imageView);
        } else {
            Picasso.with(context).load(uri).placeholder(R.drawable.person_defaultheadportrait).error(R.drawable.pic1).into(imageView);
        }
    }

    /**
     * 加载图片 设置宽高  图片默认居中 （centerCrop():会根据控件进行裁剪图片，显示图片的中间部分； centerInside():整张图片都显示在控件中，导致控件旁边可能会有留白）
     *
     * @param context
     * @param path
     * @param imageView
     * @param targetWidth
     * @param targetHeight
     * @param bitmapShowType PICASSO_BITMAP_SHOW_CIRCLE_TYPE ， PICASSO_BITMAP_SHOW_ROUND_TYPE ，PICASSO_BITMAP_SHOW_NORMAL_TYPE
     * @param roundRadius    设置圆角半径
     */
    public void LoadImageWithWidtAndHeight(Context context, String path, ImageView imageView, int targetWidth, int targetHeight, String bitmapShowType, float roundRadius) {
        if (bitmapShowType.equals(PICASSO_BITMAP_SHOW_CIRCLE_TYPE)) {
            Picasso.with(context).load(path).resize(targetWidth, targetHeight).centerCrop().transform(new CircleTransform()).into(imageView);
        } else if (bitmapShowType.equals(PICASSO_BITMAP_SHOW_ROUND_TYPE)) {
            //此法会有picasso Transformation round crashed with exception 的错误
//            Picasso.with(context).load(path).resize(targetWidth, targetHeight).centerCrop().transform(new RoundTransform(roundRadius)).into(imageView);

            if (targetWidth != -1 && targetHeight != -1) {//对图片进行裁剪，会导致图片失真
                Picasso.with(context).load(path).resize(targetWidth, targetHeight).centerCrop().error(R.drawable.pic1).transform(new PicassoRoundTransform(roundRadius)).into(imageView);
            }else {//直接使图片自适应控件大小，不失真
                Picasso.with(context).load(path).error(R.drawable.pic1).fit().transform(new PicassoRoundTransform(roundRadius)).into(imageView);
            }
        } else {
            Picasso.with(context).load(path).resize(targetWidth, targetHeight).centerCrop().into(imageView);
        }
    }

    //--------------------------------------------------

    /**
     * 设置圆形头像
     */
    public class CircleTransform implements Transformation {

        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());

            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }

            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap,
                    BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            squaredBitmap = null;
            return bitmap;
        }

        @Override
        public String key() {
            return "circle";
        }
    }

    public class PicassoRoundTransform implements Transformation {

        private float radius;

        public PicassoRoundTransform(float radius){
            this.radius = radius;
        }


        @Override
        public Bitmap transform(Bitmap source) {
            int widthLight = source.getWidth();
            int heightLight = source.getHeight();

            Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_4444);

            Canvas canvas = new Canvas(output);
            Paint paintColor = new Paint();
            paintColor.setFlags(Paint.ANTI_ALIAS_FLAG);
            paintColor.setColor(0xffffffff);

            RectF rectF = new RectF(new Rect(0, 0, widthLight, heightLight));

//            canvas.drawRoundRect(rectF, widthLight / 5, heightLight / 5, paintColor);
            canvas.drawRoundRect(rectF, widthLight/radius, heightLight/radius, paintColor);

            Paint paintImage = new Paint();
            paintImage.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
            canvas.drawBitmap(source, 0, 0, paintImage);
            source.recycle();
            source = null;
            return output;
        }

        @Override
        public String key() {
            return "roundcorner";
        }
    }
}
