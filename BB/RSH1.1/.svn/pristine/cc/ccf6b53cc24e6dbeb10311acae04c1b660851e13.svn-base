package rongshanghui.tastebychance.com.rongshanghui.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by shenbinghong on 2018/1/12.
 */

public class BitmapUtils {
    public static int getDrawableResId(Context context, String name){
        return context.getResources().getIdentifier(name, "drawable", context.getApplicationInfo().packageName);
    }


    public static Bitmap toRectBmp(Bitmap srcBitmap, int width, int height) {
        return to(srcBitmap, width, height, 10f, 10f);
    }

    public static Bitmap toCircleBmp(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx = width <= height ? width / 2 : height / 2;
        int l = width <= height ? width : height;

        return to(bitmap, l, l, roundPx, roundPx);
    }

    private static Bitmap to(final Bitmap srcBitmap, final int retWidth,
                             final int retHeight, final float rx, final float ry) {
        int width = srcBitmap.getWidth();
        int height = srcBitmap.getHeight();
        final Rect src;
        final Rect dst = new Rect(0, 0, retWidth, retHeight);

        if (width <= height) {
            src = new Rect(0, 0, width, width);
        } else {
            float clip = (width - height) / 2;
            src = new Rect((int) clip, 0, (int) (width - clip), height);
        }
        Bitmap output = Bitmap.createBitmap(retWidth, retHeight,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        final RectF rectF = new RectF(dst);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(0xff424242);
        canvas.drawRoundRect(rectF, rx, ry, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(srcBitmap, src, new Rect(0, 0, retWidth, retHeight),
                paint);
        return output;
    }

    /**
     * 从SDCard上读取图片 * @param pathName * @return
     */
    public static Bitmap getBitmapFromSDCard(String pathName) {
        return BitmapFactory.decodeFile(pathName);
    }

    /**
     * 缩放图片 * @param bitmap * @param width * @param height * @return
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale((float) width / w, (float) height / h);
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
    }

    /**
     * 将Drawable转化为Bitmap * @param drawable * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
                .getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 获得圆角图片 * @param bitmap * @param roundPx * @return
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
        Bitmap output = null;
        try {
            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
                    Bitmap.Config.ARGB_8888);
        } catch (Exception e) {
            // TODO: handle exception
            output = Bitmap.createBitmap(bitmap.getWidth() / 3,
                    bitmap.getHeight() / 3, Bitmap.Config.ARGB_8888);
        }
        ;
        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /**
     * 获得带倒影的图片 * @param bitmap * @return
     */
    public static Bitmap getReflectionImageWithOrigin(Bitmap bitmap) {
        final int reflectionGap = 4;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);
        Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2,
                width, height / 2, matrix, false);
        Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
                (height + height / 2), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(bitmap, 0, 0, null);
        Paint defaultPaint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap, defaultPaint);
        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
                bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
                0x00ffffff, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
                + reflectionGap, paint);
        return bitmapWithReflection;
    }

    public static byte[] readStream(InputStream inStream) throws Exception {
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inStream.close();
        return outStream.toByteArray();
    }

    public static Bitmap decodeBitmap(InputStream is) throws Exception {
        if (is == null) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        //设置该属性可以不占用内存，并且能够得到bitmap的宽高等属性，此时得到的bitmap是空
        options.inJustDecodeBounds = true;
        byte[] data = readStream(is);//将InputStream转为byte数组，可以多次读取
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
        //设置计算得到的压缩比例
        options.inSampleSize = 4;
        //设置为false，确保可以得到bitmap != null
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
        return bitmap;
    }

    public static Bitmap getPicFromBytes(byte[] bytes,
                                         BitmapFactory.Options opts) {
        if (bytes != null)
            if (opts != null)
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
                        opts);
            else
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return null;
    }

    public static boolean setBitmapToFile(Bitmap mImg, String path) {
        BufferedOutputStream bos = null;
        /*File mFile = new File(path);
        if (!mFile.exists()){
            mFile.mkdirs();
        }*/

        File mFile = new File(Environment.getExternalStorageDirectory(), "compress.jpg");
        if (!mFile.exists()) {
            try {
                mFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            bos = new BufferedOutputStream(new FileOutputStream(mFile));
            if (mImg != null) {
                mImg.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            }
            // finish();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.flush();
                bos.close();
                mFile = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public static Bitmap getWebImage(String url) {
        Log.i("returnBitMap", "url=" + url);
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

//  public static void getMediaFile(String url, String fileName)
//          throws IOException {
//      Log.i("getMediaFile", "url=" + url);
//      URL myFileUrl = null;
//      InputStream is = null;
//      HttpURLConnection conn;
//      File file = new File(DataBase.CARD_FILE_PATH + fileName + ".amr");
//      if (!file.exists()) {
//          file.createNewFile();
//      }
//      try {
//          myFileUrl = new URL(url);
//      } catch (MalformedURLException e) {
//          e.printStackTrace();
//      }
//      try {
//          conn = (HttpURLConnection) myFileUrl.openConnection();
//          conn.setDoInput(true);
//          conn.connect();
//          is = conn.getInputStream();
//      } catch (IOException e) {
//          e.printStackTrace();
//      }
//      OutputStream os = new FileOutputStream(file);
//      byte[] buffer = new byte[1024 * 2];
//      while ((is.read(buffer)) != -1) {
//          os.write(buffer);
//      }
//      if (null != os) {
//          os.flush();
//          os.close();
//      }
//      if (null != is) {
//          is.close();
//      }
//
//  }

    /**
     * resize
     *
     * @author c.y
     */
    public static Bitmap loadResizedBitmap(String filename, int width,
                                           int height, boolean exact) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filename, options);
        if (options.outHeight > 0 && options.outWidth > 0) {
            options.inJustDecodeBounds = false;
            options.inSampleSize = 2;
            while (options.outWidth / options.inSampleSize > width
                    && options.outHeight / options.inSampleSize > height) {
                options.inSampleSize++;
            }
            bitmap = BitmapFactory.decodeFile(filename, options);
            if (bitmap != null && exact) {
                bitmap = Bitmap
                        .createScaledBitmap(bitmap, width, height, false);
            }
        }
        return bitmap;
    }

    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
}
