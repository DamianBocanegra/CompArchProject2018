package com.example.puppetmaster123.comparch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import java.util.ArrayList;



/*
The GamePanel class is a generic class that is pulled from an old project and has been customized
 for the use of running the app. This class dictates the entire app's graphics. It holds the method that
 draws the graphics in draw().

 Designed and Implemented by Damian Bocanegra
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback
{
       public static final int WIDTH = 826;
       public static final int HEIGHT = 663;
       private MainThread thread;
       private MenuButton dataButton, controlButton, backbutton, subButton, kbutton, hzbutton;
       private Background bg;
       private MenuButton dg;
       BinaryGameLogic binaryGame;
       DataGameLogic dataGame;
       StructHazGameLogic hzGame;
       private Title t;
       private String mode = "MainMenu";
       private boolean feedback = false;


       //Demo only variables
       int counter = 1;

       public GamePanel(Context context)
       {
           super(context);

           getHolder().addCallback(this);
           thread = new MainThread(getHolder(), this);
           setFocusable(true);
       }

       @Override
       public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
       {

       }


      /*
      surfaceCreated is calls when the app is statrted and will create the main menu resources along with inialize some resources
      such as the back button which will always be used within the app. It will also start the main thread in order
      to begin a loop that will draw the graphics the user will see.
       */
       @Override
       public  void surfaceCreated(SurfaceHolder holder)
       {
              bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.maxresdefault));
              dataButton = new MenuButton(BitmapFactory.decodeResource(getResources(), R.drawable.databutton), 10, 100);
              controlButton = new MenuButton(BitmapFactory.decodeResource(getResources(), R.drawable.controlbutton), 10, 250);
              t = new Title(BitmapFactory.decodeResource(getResources(), R.drawable.title), 100, 50);
              dg = new MenuButton(BitmapFactory.decodeResource(getResources(), R.drawable.csoon),500, 360);
              backbutton = new MenuButton(BitmapFactory.decodeResource(getResources(), R.drawable.backbutton), 10, 620);
              subButton = new MenuButton(BitmapFactory.decodeResource(getResources(), R.drawable.submit), 320, 620);
              hzbutton = new MenuButton(BitmapFactory.decodeResource(getResources(), R.drawable.hzbutton), 10, 400);
              kbutton = new MenuButton(BitmapFactory.decodeResource(getResources(), R.drawable.kbutton), 10, 550);


              thread.setRunning(true);
              thread.start();
       }
       @Override
       public void surfaceDestroyed(SurfaceHolder holder)
       {
                  boolean retry = true;
                  while(retry)
                  {
                      try
                      {
                          thread.setRunning(false);
                          thread.join();
                      }
                      catch (InterruptedException e)
                      {
                          e.printStackTrace();
                      }
                      retry = false;
                  }
       }

       /*
       This method is the main heart of the graphics of the program.
       This method has other methods to assist in its job of drawing all of the graphics of the app.
       It acts as a manager for the most part, but also is responsible for drawing the main menu
        */
       @Override
       public void draw(Canvas canvas)
       {
           super.draw(canvas);
           if(canvas != null)
           {

               bg.draw(canvas);
               switch(mode)
               {

                   case "MainMenu":
                   t.draw(canvas);
                   controlButton.draw(canvas);
                   dataButton.draw(canvas);
                   hzbutton.draw(canvas);
                   //kbutton.draw(canvas); //Will be enabled when draw method is complete


                   break;
                   case "DataGame":
                       dg.draw(canvas);
                       backbutton.draw(canvas);
                       break;
                   case "DataGameTest":
                       drawDataGame(canvas);
                       break;
                   case "BinaryGame":
                       drawBinaryGame(canvas);
                       break;
                   case "HazardGame":
                       drawHazardGame(canvas);
                       break;



               }
           }
       }


       /*
       This method keeps track of all the touch events that occur within the app
        */
       @Override
       public boolean onTouchEvent(MotionEvent motionEvent)
       {

           switch (motionEvent.getAction() & MotionEvent.ACTION_MASK)
           {
               case MotionEvent.ACTION_DOWN:
                   if(mode == "MainMenu") {
                       //Control button for DataGame
                       if (dataButton.motion(motionEvent)) {
                           mode = "DataGameTest";
                           dataGame = new DataGameLogic();

                       }
                       //Control button is pushed for Control Signals
                       if (controlButton.motion(motionEvent)) {
                           mode = "BinaryGame";
                           binaryGame = new BinaryGameLogic();
                           counter = 0;
                       }
                       //Control for hazard button
                       if (hzbutton.motion(motionEvent)) {
                           mode = "HazardGame";
                           hzGame = new StructHazGameLogic();

                       }
                       //Control for knowledge button
                       if (kbutton.motion(motionEvent)) {
                           mode = "DataGame";
                       }
                   }
                   //Controls for Binary game
                   if (mode == "BinaryGame")
                   {
                       binaryGame.motionBinary(motionEvent);
                       //Hit submit button
                       if((motionEvent.getX() > 320 && motionEvent.getX() < 620) && (motionEvent.getY() > 600 && motionEvent.getY() < 700) )
                       {
                           if(binaryGame.play() == 1)
                           {

                               binaryGame.reset();
                               if(binaryGame.getInstruction() == "done")
                               {
                                   mode = "MainMenu";
                               }
                               feedback = false;

                           }
                           else
                           {
                               feedback = true;
                           }

                       }

                   }

                  //Controls for DataGameTesting
                   if(mode == "DataGameTest")
                   {
                       dataGame.motionData(motionEvent);
                   }

                   if (mode == "HazardGame")
                   {
                       hzGame.hzMotion(motionEvent);
                   }

                   //Hitting the back button
                   if((motionEvent.getX() > 10 && motionEvent.getX() < 300) && (motionEvent.getY() > 620 && motionEvent.getY() < 700))
                   {
                       mode = "MainMenu";
                   }
                   break;
           }

           return true;
       }


       /*
       drawBinaryGame is called when the mode is changed to BinaryGame when the user pushes Control Signals on the main menu
       This method is used as an assistant to draw(), in order to keep that method from being too unreadable.
        */
       public void drawBinaryGame(Canvas canvas)
       {
           MenuButton redSelction = new MenuButton(BitmapFactory.decodeResource(getResources(), R.drawable.selr),0,0);
           MenuButton blueSelction = new MenuButton(BitmapFactory.decodeResource(getResources(), R.drawable.selb),0,0);


           MenuButton reference = new MenuButton(BitmapFactory.decodeResource(getResources(), R.drawable.datapathnew),10, 50 );
           MenuButton regD = new MenuButton(BitmapFactory.decodeResource(getResources(), R.drawable.reg_d),650, 0 );
           MenuButton regW = new MenuButton(BitmapFactory.decodeResource(getResources(), R.drawable.reg_w),650, 100 );
           MenuButton memD = new MenuButton(BitmapFactory.decodeResource(getResources(), R.drawable.mem_r),650, 200 );
           MenuButton memW = new MenuButton(BitmapFactory.decodeResource(getResources(), R.drawable.mem_w),650, 300 );
           MenuButton memReg = new MenuButton(BitmapFactory.decodeResource(getResources(), R.drawable.memreg),650, 400 );
           MenuButton alu = new MenuButton(BitmapFactory.decodeResource(getResources(), R.drawable.alu),650, 500 );
           MenuButton branch = new MenuButton(BitmapFactory.decodeResource(getResources(), R.drawable.branch),650, 600 );

           Paint paint = new Paint();
           paint.setColor(Color.WHITE);
           paint.setTextSize(45);
           canvas.drawText("Instruction: " + binaryGame.getInstruction(), 10, 550, paint);

           Paint feedbackPaint = new Paint();
           feedbackPaint.setColor(Color.GREEN);
           feedbackPaint.setTextSize(45);
           if(feedback)
           {
               if(binaryGame.correct != 1)
               {
                   canvas.drawText("Incorrect", 10, 600, feedbackPaint);
               }
               else
               {
                   canvas.drawText("Correct", 10, 600, feedbackPaint);
               }
           }


           reference.draw(canvas);
           regD.draw(canvas);
           regW.draw(canvas);
           memD.draw(canvas);
           memW.draw(canvas);
           memReg.draw(canvas);
           alu.draw(canvas);
           branch.draw(canvas);
           backbutton.draw(canvas);
           subButton.draw(canvas);

           //Block for getRegD
           if(binaryGame.getRegD() == 0)
           {
               redSelction.setX(950);
               redSelction.setY(0);
               redSelction.draw(canvas);
           }
           else
           {
               blueSelction.setX(950);
               blueSelction.setY(0);
               blueSelction.draw(canvas);
           }

           //Block for getRegW
           if(binaryGame.getRegW() == 0)
           {
               redSelction.setX(950);
               redSelction.setY(100);
               redSelction.draw(canvas);
           }
           else
           {
               blueSelction.setX(950);
               blueSelction.setY(100);
               blueSelction.draw(canvas);
           }

           //Block for getmemD
           if(binaryGame.getMemD() == 0)
           {
               redSelction.setX(950);
               redSelction.setY(200);
               redSelction.draw(canvas);
           }
           else
           {
               blueSelction.setX(950);
               blueSelction.setY(200);
               blueSelction.draw(canvas);
           }

           //Block for getmemW
           if(binaryGame.getMemW() == 0)
           {
               redSelction.setX(950);
               redSelction.setY(300);
               redSelction.draw(canvas);
           }
           else
           {
               blueSelction.setX(950);
               blueSelction.setY(300);
               blueSelction.draw(canvas);
           }

           //Block for getMemReg
           if(binaryGame.getMemReg() == 0)
           {
               redSelction.setX(950);
               redSelction.setY(400);
               redSelction.draw(canvas);
           }
           else
           {
               blueSelction.setX(950);
               blueSelction.setY(400);
               blueSelction.draw(canvas);
           }
           //Block for getalu
           if(binaryGame.getAlu() == 0)
           {
               redSelction.setX(950);
               redSelction.setY(500);
               redSelction.draw(canvas);
           }
           else
           {
               blueSelction.setX(950);
               blueSelction.setY(500);
               blueSelction.draw(canvas);
           }

           //Block for getbranch
           if(binaryGame.getBranch() == 0)
           {
               redSelction.setX(950);
               redSelction.setY(600);
               redSelction.draw(canvas);
           }
           else
           {
               blueSelction.setX(950);
               blueSelction.setY(600);
               blueSelction.draw(canvas);
           }

       }


       /*
       drawDataGame is called when the mode is changed to DataGame when the user pushes DataPath on the main menu
       This method is used as an assistant to draw(), in order to keep that method from being unreadable.
        */

    public void drawDataGame(Canvas canvas)
       {
           backbutton.draw(canvas);

           //Image Resources for dots connecting the datapath components
           MenuButton greenSel = new MenuButton(BitmapFactory.decodeResource(getResources(), R.drawable.greenseldata),0,0);
           MenuButton redSel = new MenuButton(BitmapFactory.decodeResource(getResources(), R.drawable.redseldata), 0, 0);
           MenuButton yellowSel = new MenuButton(BitmapFactory.decodeResource(getResources(), R.drawable.yellowsel), 0, 0);

          //Image Resources for datapath components
           MenuButton registerfile = new MenuButton(BitmapFactory.decodeResource(getResources(), R.drawable.regfile),300, 25);
           MenuButton alusegment = new MenuButton(BitmapFactory.decodeResource(getResources(), R.drawable.aludata), 640,100);
           MenuButton insMem = new MenuButton(BitmapFactory.decodeResource(getResources(), R.drawable.insmem), 10, 25);
           MenuButton dataMem = new MenuButton(BitmapFactory.decodeResource(getResources(), R.drawable.datamem), 940, 25);
           MenuButton sign = new MenuButton(BitmapFactory.decodeResource(getResources(), R.drawable.signextend), 640, 400);


           /*
           This block will draw the repesintations of the Datapath components
           ALU, Instruction Memory, Register file, Data Memory, and Sign Extendor.
            */
           registerfile.draw(canvas);
           alusegment.draw(canvas);
           insMem.draw(canvas);
           dataMem.draw(canvas);
           sign.draw(canvas);

           //Set color to display text
           Paint paint = new Paint();
           paint.setColor(Color.WHITE);
           paint.setTextSize(45);
           //Display the instruction to input
           canvas.drawText("Connect for a store instruction.", 10, 600, paint);



           //Draw ReadData dot
           if(dataGame.getReadData() == 1)
           {
               greenSel.setX(1140);
               greenSel.setY(150);
               greenSel.draw(canvas);
           }
           else if(dataGame.getReadData() == 0)
           {
               redSel.setX(1140);
               redSel.setY(150);
               redSel.draw(canvas);
           }
           else if(dataGame.getReadData() == 2)
           {
               yellowSel.setX(1140);
               yellowSel.setY(150);
               yellowSel.draw(canvas);
           }

           //Draw Address dot
           if(dataGame.getAddr() == 1)
           {
               greenSel.setX(925);
               greenSel.setY(150);
               greenSel.draw(canvas);
           }
           else if(dataGame.getAddr() == 0)
           {
               redSel.setX(925);
               redSel.setY(150);
               redSel.draw(canvas);
           }
           else if(dataGame.getAddr() == 2)
           {
               yellowSel.setX(925);
               yellowSel.setY(150);
               yellowSel.draw(canvas);
           }

           //Draw writeDataMem dot
           if(dataGame.getWriteDataMem() == 1)
           {
               greenSel.setX(925);
               greenSel.setY(350);
               greenSel.draw(canvas);
           }
           else if(dataGame.getWriteDataMem() == 0)
           {
               redSel.setX(925);
               redSel.setY(350);
               redSel.draw(canvas);
           }
           else if(dataGame.getWriteDataMem() == 2)
           {
               yellowSel.setX(925);
               yellowSel.setY(350);
               yellowSel.draw(canvas);
           }

           //Draw Read1 dot
           if(dataGame.getRead1() == 1)
           {
               greenSel.setX(290);
               greenSel.setY(50);
               greenSel.draw(canvas);
           }
           else if(dataGame.getRead1() == 0)
           {
               redSel.setX(290);
               redSel.setY(50);
               redSel.draw(canvas);
           }
           else if(dataGame.getRead1() == 2)
           {
               yellowSel.setX(290);
               yellowSel.setY(50);
               yellowSel.draw(canvas);
           }

           //Draw Read2 dot
           if(dataGame.getRead2() == 1)
           {
               greenSel.setX(290);
               greenSel.setY(150);
               greenSel.draw(canvas);
           }
           else if(dataGame.getRead2() == 0)
           {
               redSel.setX(290);
               redSel.setY(150);
               redSel.draw(canvas);
           }
           else if(dataGame.getRead2() == 2)
           {
               yellowSel.setX(290);
               yellowSel.setY(150);
               yellowSel.draw(canvas);
           }

           //Draw writeAddr dot
           if(dataGame.getWriteAddr() == 1)
           {
               greenSel.setX(290);
               greenSel.setY(250);
               greenSel.draw(canvas);
           }
           else if(dataGame.getWriteAddr() == 0)
           {
               redSel.setX(290);
               redSel.setY(250);
               redSel.draw(canvas);
           }
           else if(dataGame.getWriteAddr() == 2)
           {
               yellowSel.setX(290);
               yellowSel.setY(250);
               yellowSel.draw(canvas);
           }

           //Draw writeData dot
           if(dataGame.getWriteData() == 1)
           {
               greenSel.setX(290);
               greenSel.setY(350);
               greenSel.draw(canvas);
           }
           else if(dataGame.getWriteData() == 0)
           {
               redSel.setX(290);
               redSel.setY(350);
               redSel.draw(canvas);
           }
           else if(dataGame.getWriteData() == 2)
           {
               yellowSel.setX(290);
               yellowSel.setY(350);
               yellowSel.draw(canvas);
           }

           //Draw ReadData1 dot
           if(dataGame.getReadData1() == 1)
           {
               greenSel.setX(500);
               greenSel.setY(150);
               greenSel.draw(canvas);
           }
           else if(dataGame.getReadData1() == 0)
           {
               redSel.setX(500);
               redSel.setY(150);
               redSel.draw(canvas);
           }
           else if(dataGame.getReadData1() == 2)
           {
               yellowSel.setX(500);
               yellowSel.setY(150);
               yellowSel.draw(canvas);
           }

           //Draw ReadData2 dot
           if(dataGame.getReadData2() == 1)
           {
               greenSel.setX(500);
               greenSel.setY(250);
               greenSel.draw(canvas);
           }
           else if(dataGame.getReadData2() == 0)
           {
               redSel.setX(500);
               redSel.setY(250);
               redSel.draw(canvas);
           }
           else if(dataGame.getReadData2() == 2)
           {
               yellowSel.setX(500);
               yellowSel.setY(250);
               yellowSel.draw(canvas);
           }

           //Draw sIn dot
           if(dataGame.getsIn() == 1)
           {
               greenSel.setX(635);
               greenSel.setY(500);
               greenSel.draw(canvas);
           }
           else if(dataGame.getsIn() == 0)
           {
               redSel.setX(635);
               redSel.setY(500);
               redSel.draw(canvas);
           }
           else if(dataGame.getsIn() == 2)
           {
               yellowSel.setX(635);
               yellowSel.setY(500);
               yellowSel.draw(canvas);
           }

           //Draw sOut dot
           if(dataGame.getsOut() == 1)
           {
               greenSel.setX(760);
               greenSel.setY(500);
               greenSel.draw(canvas);
           }
           else if(dataGame.getsOut() == 0)
           {
               redSel.setX(760);
               redSel.setY(500);
               redSel.draw(canvas);
           }
           else if(dataGame.getsOut() == 2)
           {
               yellowSel.setX(760);
               yellowSel.setY(500);
               yellowSel.draw(canvas);
           }

           //Draw Instruction dot
           if(dataGame.getIns() == 1)
           {
               greenSel.setX(215);
               greenSel.setY(150);
               greenSel.draw(canvas);
           }
           else if(dataGame.getIns() == 0)
           {
               redSel.setX(215);
               redSel.setY(150);
               redSel.draw(canvas);
           }
           else if(dataGame.getIns() == 2)
           {
               yellowSel.setX(215);
               yellowSel.setY(150);
               yellowSel.draw(canvas);
           }

           //Draw op1 dot
           if(dataGame.getOp1() == 1)
           {
               greenSel.setX(625);
               greenSel.setY(125);
               greenSel.draw(canvas);
           }
           else if(dataGame.getOp1() == 0)
           {
               redSel.setX(625);
               redSel.setY(125);
               redSel.draw(canvas);
           }
           else if(dataGame.getOp1() == 2)
           {
               yellowSel.setX(625);
               yellowSel.setY(125);
               yellowSel.draw(canvas);
           }

           //Draw op2 dot
           if(dataGame.getOp2() == 1)
           {
               greenSel.setX(625);
               greenSel.setY(265);
               greenSel.draw(canvas);
           }
           else if(dataGame.getOp2() == 0)
           {
               redSel.setX(625);
               redSel.setY(265);
               redSel.draw(canvas);
           }
           else if(dataGame.getOp2() == 2)
           {
               yellowSel.setX(625);
               yellowSel.setY(265);
               yellowSel.draw(canvas);
           }

           //Draw out dot
           if(dataGame.getOut() == 1)
           {
               greenSel.setX(760);
               greenSel.setY(200);
               greenSel.draw(canvas);
           }
           else if(dataGame.getOut() == 0)
           {
               redSel.setX(760);
               redSel.setY(200);
               redSel.draw(canvas);
           }
           else if(dataGame.getOut() == 2)
           {
               yellowSel.setX(760);
               yellowSel.setY(200);
               yellowSel.draw(canvas);
           }


           if(dataGame.getComp() > 7)
           {
               mode = "MainMenu";
           }
       }


       /*
       drawHazardGame is called when the mode is changed to hazardGame when the user pushes Hazards on the main menu
       This method is used as an assistant to draw(), in order to keep that method from being unreadable.
        */

    public void drawHazardGame(Canvas canvas)
       {
           //Draw button to return to main menu and submit button for instructions
           backbutton.draw(canvas);
           subButton.draw(canvas);

           /*
           This will hold a list of all images to be displayed of
           all the currently supported instructions
            */
           ArrayList<Bitmap> instructions = new ArrayList<>();
           instructions.add(BitmapFactory.decodeResource(getResources(), R.drawable.add));
           instructions.add(BitmapFactory.decodeResource(getResources(), R.drawable.sub));
           instructions.add(BitmapFactory.decodeResource(getResources(), R.drawable.lw));
           instructions.add(BitmapFactory.decodeResource(getResources(), R.drawable.sw));
           instructions.add(BitmapFactory.decodeResource(getResources(), R.drawable.beq));

       /*
        This will hold a list of registers 0-9 images to be displayed to the user in order for ease
         of reusable images.
       */
           ArrayList<Bitmap> registers = new ArrayList<>();
           registers.add(BitmapFactory.decodeResource(getResources(), R.drawable.zero));
           registers.add(BitmapFactory.decodeResource(getResources(), R.drawable.one));
           registers.add(BitmapFactory.decodeResource(getResources(), R.drawable.two));
           registers.add(BitmapFactory.decodeResource(getResources(), R.drawable.three));
           registers.add(BitmapFactory.decodeResource(getResources(), R.drawable.four));
           registers.add(BitmapFactory.decodeResource(getResources(), R.drawable.five));
           registers.add(BitmapFactory.decodeResource(getResources(), R.drawable.six));
           registers.add(BitmapFactory.decodeResource(getResources(), R.drawable.seven));
           registers.add(BitmapFactory.decodeResource(getResources(), R.drawable.eight));
           registers.add(BitmapFactory.decodeResource(getResources(), R.drawable.nine));

           //Navigate through instructions.
           MenuButton insUP = new MenuButton(BitmapFactory.decodeResource(getResources(), R.drawable.uparrow), 100, 190 );
           MenuButton insDN = new MenuButton(BitmapFactory.decodeResource(getResources(), R.drawable.downarrow), 100, 405);

           //Navigate through destination register
           MenuButton rdUP = new MenuButton(BitmapFactory.decodeResource(getResources(), R.drawable.smallup), 450, 200 );
           MenuButton rdDN = new MenuButton(BitmapFactory.decodeResource(getResources(), R.drawable.smalldown), 450, 400 );

           //Navigate through 1st source register
           MenuButton rs1UP = new MenuButton(BitmapFactory.decodeResource(getResources(), R.drawable.smallup), 600, 200 );
           MenuButton rs1DN = new MenuButton(BitmapFactory.decodeResource(getResources(), R.drawable.smalldown), 600, 400 );

           //Navigate through 2nd source register, When BEQ is selected as the instruction address is placed at the end
           MenuButton rs2UP = new MenuButton(BitmapFactory.decodeResource(getResources(), R.drawable.smallup), 750, 200 );
           MenuButton rs2DN = new MenuButton(BitmapFactory.decodeResource(getResources(), R.drawable.smalldown), 750, 400 );
           MenuButton address = new MenuButton(BitmapFactory.decodeResource(getResources(), R.drawable.address), 750, 300);


           //Fields the user will see
           MenuButton ins = new MenuButton(instructions.get(hzGame.getInsIndex()), 100, 300);
           MenuButton rd = new MenuButton(registers.get(hzGame.getRdIndex()),450, 300 );
           MenuButton rs1 = new MenuButton(registers.get(hzGame.getSr1Index()),600, 300 );
           MenuButton rs2 = new MenuButton(registers.get(hzGame.getSr2Index()),750, 300 );

           //Set color to display text
           Paint paint = new Paint();
           paint.setColor(Color.WHITE);
           paint.setTextSize(45);

           /*
           For this section if the user has not submitted five instructions it will draw the interface
           to select instructions, and the registers, and will give feedback on whether or not the instruction was added.
            */
           if (hzGame.getInstructionCount() > 0)
           {
               canvas.drawText("Instruction Count: " + String.valueOf(hzGame.getInstructionCount()), 800, 50, paint);
               if(hzGame.getMessage() != "empty")
               {
                   canvas.drawText(hzGame.getMessage(), 10, 100, paint);

               }

               insUP.draw(canvas);
               ins.draw(canvas);
               insDN.draw(canvas);

               rdUP.draw(canvas);
               rd.draw(canvas);
               rdDN.draw(canvas);

               rs1UP.draw(canvas);
               rs1.draw(canvas);
               rs1DN.draw(canvas);

               if(hzGame.getInsIndex() < 2)
               {
                rs2UP.draw(canvas);
                rs2.draw(canvas);
                rs2DN.draw(canvas);
               }
               else if(hzGame.getInsIndex() == 4)
               {
                   address.draw(canvas);
               }
           }
           else
           {
               canvas.drawText("Instruction: " + hzGame.instructionString(hzGame.getIns1()), 10, 100, paint);
               canvas.drawText(hzGame.getHz1(), 170, 150, paint);
               canvas.drawText("Instruction: " + hzGame.instructionString(hzGame.getIns2()), 10, 200, paint);
               canvas.drawText(hzGame.getHz2(), 170, 250, paint);
               canvas.drawText("Instruction: " + hzGame.instructionString(hzGame.getIns3()), 10, 300, paint);
               canvas.drawText(hzGame.getHz3(), 170, 350, paint);
               canvas.drawText("Instruction: " + hzGame.instructionString(hzGame.getIns4()), 10, 400, paint);
               canvas.drawText(hzGame.getHz4(), 170, 450, paint);
               canvas.drawText("Instruction: " + hzGame.instructionString(hzGame.getIns5()), 10, 500, paint);
           }



       }


       /*
       This method will draw the quiz game that is started when Test your knowledge is selected by
       the user. AS of 5/9/18 this feature will not be implemented until the next version
        */
       public void drawKnowledge(Canvas canvas)
    {
        backbutton.draw(canvas);

        //First create list of questions

        ArrayList<Question> questionBank = new ArrayList<>();
        Question q1 = new Question(BitmapFactory.decodeResource(getResources(), R.drawable.question), 0);

        //Then create list of answers

        ArrayList<Answer> answerBank = new ArrayList<>();
        Answer a1 = new Answer(BitmapFactory.decodeResource(getResources(), R.drawable.answer), 0);

        //Grab one question, its correct answer, and 3 random answers

        //Draw question and answers

        //If user is correct new question is asked

        //else user has to try again.
    }

}
