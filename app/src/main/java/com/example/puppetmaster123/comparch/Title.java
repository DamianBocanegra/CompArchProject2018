package com.example.puppetmaster123.comparch;

import android.graphics.Bitmap;
import android.graphics.Canvas;
/**
 * Created by puppetmaster123 on 4/24/2018.
 */

public class Title extends GameObject
{
    private Bitmap image;

    public Title(Bitmap bmp, int w, int h )
    {

        x = 300;
        y = 10;
        dy = 0;
        height = h;
        width = w;
        image = bmp;
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image, x , y, null);
    }

}
