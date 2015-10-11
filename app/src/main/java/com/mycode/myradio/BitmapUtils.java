package com.mycode.myradio;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

public class BitmapUtils {

    private BitmapUtils()
    {}

    public static Bitmap scaleTo(Bitmap source, int size)
    {
        int destWidth = source.getWidth();

        int destHeight = source.getHeight();

        destHeight = destHeight * size / destWidth;
        destWidth = size;

        if (destHeight < size)
        {
            destWidth = destWidth * size / destHeight;
            destHeight = size;
        }

        Bitmap destBitmap = Bitmap.createBitmap(destWidth, destHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(destBitmap);
        canvas.drawBitmap(source, new Rect(0, 0, source.getWidth(), source.getHeight()), new Rect(0, 0, destWidth, destHeight), new Paint(Paint.ANTI_ALIAS_FLAG));
        return destBitmap;
    }


    public static Bitmap getCircleMaskedBitmapUsingPorterDuff(Bitmap source, int radius)
    {
        if (source == null)
        {
            return null;
        }

        int diam = radius << 1;
        Bitmap scaledBitmap = scaleTo(source, diam);

        Bitmap targetBitmap = Bitmap.createBitmap(diam, diam, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);

        final int color = 0xff424242;
        final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        final Rect rect = new Rect(0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight());

        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);

        canvas.drawCircle(radius, radius, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(scaledBitmap, rect, rect, paint);
        return targetBitmap;
    }

}
