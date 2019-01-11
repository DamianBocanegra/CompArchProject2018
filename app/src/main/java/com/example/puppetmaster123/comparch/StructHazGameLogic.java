package com.example.puppetmaster123.comparch;

import android.view.MotionEvent;

import java.util.ArrayList;

/*
This the logic for the tab which takes instructions provided by the user and then
detects if there are any data hazards in the provided instructions. Later versions will include
structural hazard detection.

Designed by Andrew Wheeler
Implemented by Damian Bocanegra
 */

public class StructHazGameLogic
{
       /*
         These lists store each instruction to be compared in the pipeline
         Elements are stored in the order:
         instruction, destination reg, source register 1, source register 2
       */
       private ArrayList<String> ins1 = new ArrayList<>();
       private ArrayList<String> ins2 = new ArrayList<>();
       private ArrayList<String> ins3 = new ArrayList<>();
       private ArrayList<String> ins4 = new ArrayList<>();
       private ArrayList<String> ins5 = new ArrayList<>();

       /*
       For keeping track of operations during selection process of instructions.
        */

       private int insIndex = 0;
       private int rdIndex = 0;
       private int sr1Index = 0;
       private int sr2Index = 0;
       private int instructionCount;

       //Delivers feedback to the user
       private String message;
       private String hz1;
       private String hz2;
       private String hz3;
       private String hz4;

       public String getHz1() {
              return hz1;
       }

       public String getHz2() {
              return hz2;
       }

       public String getHz3() {
              return hz3;
       }

       public String getHz4() {
              return hz4;
       }

       public String getMessage() {
              return message;
       }

       public void setMessage(String message) {
              this.message = message;
       }

       public ArrayList<String> getIns1() {
              return ins1;
       }

       public ArrayList<String> getIns2() {
              return ins2;
       }

       public ArrayList<String> getIns3() {
              return ins3;
       }

       public ArrayList<String> getIns4() {
              return ins4;
       }

       public ArrayList<String> getIns5() {
              return ins5;
       }

       public int getInstructionCount() {
              return instructionCount;
       }

       public void setInstructionCount() {
              if(instructionCount > 0)
              {
                     instructionCount--;
              }
       }

       public StructHazGameLogic()
       {
              insIndex = 0;
              instructionCount = 5;
              message = "empty";
              hz1 = "ERROR";
              hz2 = "ERROR";
              hz3 = "ERROR";
              hz4 = "ERROR";

       }


       public int getInsIndex() {
              return insIndex;
       }

       public void setInsIndex(int move) {
              if(move != 0)
              {
                  insIndex++;
              }
              else
              {
                     if(insIndex == 0)
                     {
                            insIndex = 4;
                     }
                     else
                     {
                            insIndex--;
                     }

              }
              insIndex = insIndex % 5;
       }

       public int getRdIndex() {
              return rdIndex;
       }

       public void setRdIndex(int move) {
              if(move != 0)
              {
                     rdIndex++;
              }
              else
              {
                     if(rdIndex == 0)
                     {
                            rdIndex = 9;
                     }
                     else
                     {
                            rdIndex--;
                     }
              }
              rdIndex = rdIndex % 10;
       }

       public int getSr1Index() {
              return sr1Index;
       }

       public void setSr1Index(int move) {
              if(move != 0)
              {
                     sr1Index++;
              }
              else
              {
                     if(sr1Index == 0)
                     {
                            sr1Index = 9;
                     }
                     else
                     {
                            sr1Index--;
                     }
              }
              sr1Index = sr1Index % 10;
       }

       public int getSr2Index() {
              return sr2Index;
       }

       //This index will be manipulated so since this field will not always be shown.
       public void setSr2Index(int move) {
              if(sr2Index < 10)
              {
                     if(move != 0)
                     {
                            sr2Index++;
                     }
                     else
                     {
                            if(sr2Index == 0)
                            {
                                   sr2Index = 9;
                            }
                            else
                            {
                                   sr2Index--;
                            }
                     }
                     sr2Index = sr2Index % 10;
              }
       }

       private String decodeIns()
       {
              if(insIndex == 4)
              {
                     return "BEQ";
              }
              if(insIndex == 3)
              {
                     return "SW";
              }
              if(insIndex == 2)
              {
                     return "LW";
              }
              if(insIndex == 1)
              {
                     return "SUB";
              }

                     return "ADD";

       }

       public void hzMotion (MotionEvent motionEvent)
       {
              /*
              Moves through the instruction choices, first if is for up arrow and next is for the
              down arrow.
               */
              if((motionEvent.getX() > 100 && motionEvent.getX() < 400) && (motionEvent.getY() > 190 && motionEvent.getY() < 299))
              {
                     setInsIndex(0);
              }

              if((motionEvent.getX() > 100 && motionEvent.getX() < 400) && (motionEvent.getY() > 405 && motionEvent.getY() < 514))
              {
                     setInsIndex(1);
              }

             /*
             Moves through the register choices for rd. First if is for the up arrow and the
             next is for the down arrow.
              */
              if((motionEvent.getX() > 450 && motionEvent.getX() < 550) && (motionEvent.getY() > 200 && motionEvent.getY() < 299))
              {
                     setRdIndex(0);
              }

              if((motionEvent.getX() > 450 && motionEvent.getX() < 550) && (motionEvent.getY() > 400 && motionEvent.getY() < 499))
              {
                     setRdIndex(1);
              }


              /*
                Moves through the register choices for rs1. First if is for the up arrow and the
                next is for the down arrow.
               */

              if((motionEvent.getX() > 600 && motionEvent.getX() < 700) && (motionEvent.getY() > 200 && motionEvent.getY() < 299))
              {
                     setSr1Index(0);;
              }

              if((motionEvent.getX() > 600 && motionEvent.getX() < 700) && (motionEvent.getY() > 400 && motionEvent.getY() < 499))
              {
                     setSr1Index(1);
              }

              /*
                Moves through the register choices for rs2. First if is for the up arrow and the
                next is for the down arrow.
               */

              if((motionEvent.getX() > 750 && motionEvent.getX() < 850) && (motionEvent.getY() > 200 && motionEvent.getY() < 299))
              {
                     setSr2Index(0);
              }

              if((motionEvent.getX() > 750 && motionEvent.getX() < 850) && (motionEvent.getY() > 400 && motionEvent.getY() < 499))
              {
                     setSr2Index(1);
              }

              // Hitting the Submit Button
              if((motionEvent.getX() > 320 && motionEvent.getX() < 620) && (motionEvent.getY() > 600 && motionEvent.getY() < 700))
              {

                     if(instructionCount == 5)
                     {
                         fillInstruction(ins1);
                     }
                     else if(instructionCount == 4)
                     {
                            fillInstruction(ins2);
                     }
                     else if(instructionCount == 3)
                     {
                            fillInstruction(ins3);
                     }
                     else if(instructionCount == 2)
                     {
                            fillInstruction(ins4);
                     }
                     else if(instructionCount == 1)
                     {
                            fillInstruction(ins5);
                            hz1 = hazardsDetector(ins1, ins2);
                           hz2 =  hazardsDetector(ins2, ins3);
                            hz3 = hazardsDetector(ins3, ins4);
                           hz4 = hazardsDetector(ins4, ins5);
                     }
                     if(!message.contains("Error")){
                            setInstructionCount();
                     }





              }

       }

       private void fillInstruction(ArrayList<String> i)
       {
              i.add(decodeIns());
              if(i.get(0) == "ADD" || i.get(0) == "SUB")
              {
                     i.add(String.valueOf(rdIndex));
                     i.add(String.valueOf(sr1Index));
                     i.add(String.valueOf(sr2Index));
                     setMessage("Instruction Added!");
              }
              else if(i.get(0) == "LW" || i.get(0) == "SW")
              {
                     if(rdIndex == sr1Index)
                     {
                            setMessage("Error can't LW/SW into same register!");
                            i.clear();
                     }
                     else
                     {
                     i.add(String.valueOf(rdIndex));
                     i.add(String.valueOf(sr1Index));
                     setMessage("Instruction Added!");
                     }
              }
              else
              {
                     i.add(String.valueOf(rdIndex));
                     i.add(String.valueOf(sr1Index));
                     i.add("0x3339");
                     setMessage("Instruction Added!");
              }
       }

       public String instructionString(ArrayList<String> i)
       {
              String instruction = i.get(0);
              instruction = instruction + " " + i.get(1);


              for(int k = 2; k < i.size(); k++)
              {
                     instruction = instruction + ", " + i.get(k);
              }

              return instruction;
       }
       /*
       This function will detect any data hazards that are present from the user's supplied instructions
       It will be called five times and will fill the hzX string provided to it.
        */
       private String hazardsDetector(ArrayList<String> a1, ArrayList<String> a2)
       {
              /*
              If the first instruction is an add or sub instruction
               */
              if(a1.get(0) == "ADD" || a1.get(0) == "SUB")
              {
                 if(a2.get(0) == "ADD" || a2.get(0) == "SUB")
                 {
                    if(a1.get(1).contentEquals(a2.get(1))||a1.get(1).contentEquals(a2.get(2)) || a1.get(1).equals(a2.get(3)))
                    {
                           return "Conflict detected, instruction calculation not complete.";
                    }
                    else
                    {
                           return "No conflict detected.";
                    }
                 }
                 else if(a2.get(0) == "SW")
                 {
                        if(a1.get(1).contentEquals(a2.get(1))|| a1.get(1).contentEquals(a2.get(2)))
                        {
                               return "Conflict detected, instruction calculation not complete.";
                        }
                        else
                        {
                               return "No conflict detected.";
                        }
                 }
                 else if(a2.get(0) == "LW")
                 {
                        if(a1.get(1).contentEquals(a2.get(1))|| a1.get(1).contentEquals(a2.get(2)))
                        {
                               return "Conflict detected, instruction calculation not complete.";
                        }
                        else
                        {
                               return "No conflict detected.";
                        }
                 }
                 else
                 {
                        if(a1.get(1).contentEquals(a2.get(1))|| a1.get(1).contentEquals(a2.get(2)) )
                        {
                               return "Conflict detected, instruction calculation not complete.";
                        }
                        else
                        {
                               return "No conflict detected.";
                        }
                 }
          }

          /*
          If first instruction is LW
           */
          else if(a1.get(0) == "LW")
          {
                 if(a2.get(0) == "ADD" || a2.get(0) == "SUB")
                 {
                        if(a2.get(1).contentEquals(a1.get(1))||a2.get(2).contentEquals(a1.get(1)) || a2.get(3).equals(a1.get(1)))
                        {
                               return "Conflict detected, instruction calculation not complete.";
                        }
                        else
                        {
                               return "No conflict detected.";
                        }

                 }
                 else if(a2.get(0) == "SW")
                 {
                        if(a1.get(1).contentEquals(a2.get(2)))
                        {
                               return "Conflict detected, instruction calculation not complete.";
                        }
                        else
                        {
                               return "No conflict detected.";
                        }
                 }
                 else if(a2.get(0) == "LW")
                 {
                        if(a1.get(1).contentEquals(a2.get(1)))
                        {
                               return "Conflict detected, instruction calculation not complete.";
                        }
                        else
                        {
                               return "No conflict detected.";
                        }
                 }
                 else
                 {
                        if(a1.get(1).contentEquals(a2.get(1))|| a1.get(1).contentEquals(a2.get(2)) )
                        {
                               return "Conflict detected, instruction calculation not complete.";
                        }
                        else
                        {
                               return "No conflict detected.";
                        }
                 }
          }
          /*
          If the first instruction is SW
           */
              else if(a1.get(0) == "SW")
              {
                     if(a2.get(0) == "ADD" || a2.get(0) == "SUB")
                     {
                            if(a2.get(1).contentEquals(a1.get(2))||a2.get(2).contentEquals(a1.get(2)) || a2.get(3).equals(a1.get(2)))
                            {
                                   return "Conflict detected, instruction calculation not complete.";
                            }
                            else
                            {
                                   return "No conflict detected.";
                            }

                     }
                     else if(a2.get(0) == "SW")
                     {
                            if(a1.get(2).contentEquals(a2.get(2)))
                            {
                                   return  "Conflict detected, instruction calculation not complete.";
                            }
                            else
                            {
                                   return "No conflict detected.";
                            }
                     }
                     else if(a2.get(0) == "LW")
                     {
                            if(a1.get(2).contentEquals(a2.get(1)))
                            {
                                   return "Conflict detected, instruction calculation not complete.";
                            }
                            else
                            {
                                   return "No conflict detected.";
                            }
                     }
                     else
                     {
                            if(a1.get(2).contentEquals(a2.get(1))|| a1.get(2).contentEquals(a2.get(2)) )
                            {
                                   return  "Conflict detected, instruction calculation not complete.";
                            }
                            else
                            {
                                   return "No conflict detected.";
                            }
                     }
              }
         /*
         If the first instraction is BEQ
          */
          else
          {
                 if(a2.get(0) == "ADD" || a2.get(0) == "SUB")
                 {
                        if(a2.get(1).contentEquals(a1.get(2))||a2.get(2).contentEquals(a1.get(2)) || a2.get(3).equals(a1.get(2)))
                        {
                               return  "Conflict detected, instruction calculation not complete.";
                        }
                        else if (a2.get(1).contentEquals(a1.get(1))||a2.get(2).contentEquals(a1.get(1)) || a2.get(3).equals(a1.get(1)))
                        {
                               return  "Conflict detected, instruction calculation not complete.";
                        }
                        else
                        {
                               return "No conflict detected.";
                        }

                 }
                 else if(a2.get(0) == "SW")
                 {
                        if(a1.get(1).contentEquals(a2.get(2)) || a1.get(2).contentEquals(a2.get(2)))
                        {
                               return "Conflict detected, instruction calculation not complete.";
                        }
                        else
                        {
                               return "No conflict detected.";
                        }
                 }
                 else if(a2.get(0) == "LW")
                 {
                        if(a1.get(1).contentEquals(a2.get(1)) || a1.get(2).contentEquals(a2.get(1)))
                        {
                               return "Conflict detected, instruction calculation not complete.";
                        }
                        else
                        {
                               return "No conflict detected.";
                        }
                 }
                 else
                 {
                              //BEQ can't conflict with BEQ
                               return "No conflict detected.";
                 }
          }



       }

}
