package com.example.puppetmaster123.comparch;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by puppetmaster123 on 4/24/2018.
 */

public class Background
{
    private Bitmap image;
    private  int x, y;

    public Background(Bitmap res)
    {
        image = res;
    }

    public void update()
    {

    }
    public void draw(Canvas canvas)
    {
         canvas.drawBitmap(image, x, y, null);
    }
}
