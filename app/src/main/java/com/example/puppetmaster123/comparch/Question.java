package com.example.puppetmaster123.comparch;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by puppetmaster123 on 5/9/2018.
 */

public class Question extends GameObject
{
      private Bitmap image;
      private int answerID;

      public Question(Bitmap bmp, int ans)
      {
          image = bmp;
          answerID = ans;
          x = 0;
          y = 0;
      }

      public int getAnswerID()
      {
          return answerID;
      }

      public void draw(Canvas canvas)
      {
          canvas.drawBitmap(image, x , y, null);
      }

}
