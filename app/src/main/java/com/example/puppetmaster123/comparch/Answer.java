package com.example.puppetmaster123.comparch;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by puppetmaster123 on 5/9/2018.
 */

public class Answer extends GameObject
{
     private Bitmap image;
     private int ID;


   public Answer(Bitmap bmp, int a)
   {
       image = bmp;
       ID = a;
       x = 0;
       y = 0;
   }


    public int getID() {
        return ID;
    }


    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image, x , y, null);
    }


}
