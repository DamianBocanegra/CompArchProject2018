package com.example.puppetmaster123.comparch;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/*
MainThread class extends the Thread class
This house the loop that allows the app to function and in a sense is the heart of the app.
This class takes the surfaceHolder, a GamePanel, the main Canvas used for graphics,
and has a boolean value running. When running is set to true, the canvas is locked and surfaceHolder
is syncronized within a loop. Within this loop the GamePanel's draw method is called until the program is terminated
This is what allows for the custom graphics to be seen.

 */

public class MainThread extends Thread
{
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    public static Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel)
    {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    public void setRunning(boolean isRunning)
    {
        running = isRunning;
    }

    @Override
    public void run()
    {
        while(running)
        {
            canvas = null;

            try
            {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    //this.gamePanel.update();
                    this.gamePanel.draw(canvas);

                }

            }
            catch (Exception e){
            }
            finally
            {
               if(canvas != null)
               {
                   try
                   {
                       surfaceHolder.unlockCanvasAndPost(canvas);

                   }
                   catch (Exception e)
                   {
                       e.printStackTrace();
                   }
               }
            }
        }
    }

}
