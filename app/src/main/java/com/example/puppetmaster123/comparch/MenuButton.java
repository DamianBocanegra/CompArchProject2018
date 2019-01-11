package com.example.puppetmaster123.comparch;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by puppetmaster123 on 4/24/2018.
 */

public class MenuButton extends GameObject
{
      private Bitmap image;
      public boolean pushed = false;

      public MenuButton(Bitmap bmp, int xp, int yp)
      {

          x = xp;
          y = yp;
          dy = 0;
          image = bmp;
      }

      public void changeImage(Bitmap bmp)
      {
          image = bmp;
      }
      public void draw(Canvas canvas)
      {
           canvas.drawBitmap(image, x , y, null);
      }

      public Boolean motion(MotionEvent motionEvent)
      {
          if( (motionEvent.getX() < x + 290 && motionEvent.getX() > x) && (motionEvent.getY() > y && motionEvent.getY() < y + 99) )          {
              return true;
          }

          return false;
      }
}
