package com.example.puppetmaster123.comparch;

import android.view.MotionEvent;

/*
 This class handles all of the logic for the DataPath processor game.
 The objective is to connect the correct parts from the components of the Datapath
 based on the instruction

 Written and Implemented by Damian Bocanegra
 */

public class DataGameLogic
{
    // Keep track of amount of selected dots
    private int selcted = 0;
    //This value is incrimented to find out if the Store instruction has been input correctly
    private int comp = 0;
    //Instruction Memory control
    private int ins = 0;

    //Modules in Reg File
    private int read1 = 0;
    private int read2 = 0;
    private int writeAddr = 0;
    private int writeData = 0;
    private int readData1 = 0;
    private int readData2 = 0;



    //ALU Modules
    private int op1 = 0;
    private int op2 = 0;
    private int out = 0;

    //Sign Extender
    private int sIn = 0;
    private int sOut = 0;

    //Data Memory modules
    private int addr = 0;
    private int writeDataMem = 0;
    private int readData = 0;

    public int getComp() {
        return comp;
    }

    public int getOut() {
        return out;
    }

    public void setOut() {
        if(out == 0)
            out = 1;
        else if (out == 1)
            out = 0;
    }

    public int getsIn() {
        return sIn;
    }

    public void setsIn() {
        if(sIn == 0)
            sIn = 1;
        else if (sIn == 1)
            sIn = 0;
    }

    public int getsOut() {
        return sOut;
    }

    public void setsOut() {
        if(sOut == 0)
            sOut = 1;
        else if (sOut == 1)
            sOut = 0;
    }

    public int getIns() {
        return ins;
    }

    public void setIns() {
        if(ins == 0)
            ins = 1;
        else if (ins == 1)
            ins = 0;
    }

    public int getRead1() {
        return read1;
    }

    public void setRead1() {
        if(read1 == 0)
            read1 = 1;
        else if (read1 == 1)
            read1 = 0;
    }

    public int getRead2() {
        return read2;
    }

    public void setRead2() {
        if(read2 == 0)
            read2 = 1;
        else if (read2 == 1)
            read2 = 0;
    }

    public int getWriteAddr() {
        return writeAddr;
    }

    public void setWriteAddr() {
        if(writeAddr == 0)
            writeAddr = 1;
        else if(writeAddr == 1)
            writeAddr = 0;
    }

    public int getWriteData() {
        return writeData;
    }

    public void setWriteData() {
        if(writeData == 0)
            writeData = 1;
        else if (writeData == 1)
            writeData = 0;
    }

    public int getReadData1() {
        return readData1;
    }

    public void setReadData1() {
        if(readData1 == 0)
            readData1 = 1;
        else if (readData1 == 1)
            readData1 = 0;
    }

    public int getReadData2() {
        return readData2;
    }

    public void setReadData2() {
        if(readData2 == 0)
            readData2 = 1;
        else if (readData2 == 1)
            readData2 = 0;
    }

    public int getOp1() {
        return op1;
    }

    public void setOp1() {
        if(op1 == 0)
            op1 = 1;
        else if (op1 == 1)
            op1 = 0;
    }

    public int getOp2() {
        return op2;
    }

    public void setOp2() {
        if(op2 == 0)
            op2 = 1;
        else if (op2 == 1)
            op2 = 0;
    }

    public int getAddr() {
        return addr;
    }

    public void setAddr() {
        if(addr == 0)
            addr = 1;
        else if (addr == 1)
            addr = 0;
    }

    public int getWriteDataMem() {
        return writeDataMem;
    }

    public void setWriteDataMem() {
        if(writeDataMem == 0)
            writeDataMem = 1;
        else if (writeDataMem == 1)
            writeDataMem = 0;
    }

    public int getReadData() {
        return readData;
    }

    public void setReadData() {
        if(readData == 0)
            readData = 1;
        else if (readData == 1)
            readData = 0;
    }

    public boolean checkWiring(int a, int b)
    {
         if(a != b)
         {
             return false;
         }

         return true;
    }

    /*
    This fucntion resets all values so a new question can be asked
     */

    public void reset()
    {
        ins = 0;
        read1 = 0;
        read2 = 0;
        writeAddr = 0;
        writeData = 0;
        readData1 = 0;
        readData2 = 0;
        op1 = 0;
        op2 = 0;
        addr = 0;
        writeDataMem = 0;
        readData = 0;
    }

    /*
    This function handles all motion events for the DataPath game.
    All motion events that are passed in are ACTION_DOWN
     */

    public void motionData(MotionEvent motionEvent)
    {
        /*
        Motion Event when the dot for the address in the Data Memory is tapped
        When tapped it will change colors from red to green to show it is selected.
        If already selected will change from green to red.
        If two dots are selected and they are the dots that need to be connected
        they will both turn yellow unless one of them requires more than one connection,
        in  that case it will turn back yellow once all connections are made.
        */
        if((motionEvent.getX() > 925 && motionEvent.getX() < 975) && (motionEvent.getY() > 150 && motionEvent.getY() < 200))
        {
            setAddr();
            if(addr != 0 && selcted < 2)
            {
                selcted++;
            }
            else
            {
                selcted--;
            }
            if(selcted == 2)
            {
               play();
            }
        }

        /*
        Motion Event when the dot for Instruction inside of the Instruction Memory is tapped
        When tapped it will change colors from red to green to show it is selected.
        If already selected will change from green to red.
        If two dots are selected and they are the dots that need to be connected
        they will both turn yellow unless one of them requires more than one connection,
        in  that case it will turn back yellow once all connections are made.
        */

        if((motionEvent.getX() > 215 && motionEvent.getX() < 265) && (motionEvent.getY() > 150 && motionEvent.getY() < 200))
        {
            setIns();
            if(ins != 0 && selcted < 2)
            {
                selcted++;
            }
            else
            {
                selcted--;
            }
            if(selcted == 2)
            {
                play();
            }
        }

        /*
        Motion Event when the dot for ReadData inside of the Data Memory is tapped
        When tapped it will change colors from red to green to show it is selected.
        If already selected will change from green to red.
        If two dots are selected and they are the dots that need to be connected
        they will both turn yellow unless one of them requires more than one connection,
        in  that case it will turn back yellow once all connections are made.
        */

        if((motionEvent.getX() > 1140 && motionEvent.getX() < 1190) && (motionEvent.getY() > 150 && motionEvent.getY() < 200))
        {
            setReadData();
            if(readData != 0 && selcted < 2)
            {
                selcted++;
            }
            else
            {
                selcted--;
            }
            if(selcted == 2)
            {
                play();
            }
        }

        /*
        Motion Event when the dot for WriteData inside of the Data Memory is tapped
        When tapped it will change colors from red to green to show it is selected.
        If already selected will change from green to red.
        If two dots are selected and they are the dots that need to be connected
        they will both turn yellow unless one of them requires more than one connection,
        in  that case it will turn back yellow once all connections are made.
        */

        if((motionEvent.getX() > 925 && motionEvent.getX() < 975) && (motionEvent.getY() > 350 && motionEvent.getY() < 400))
        {
            setWriteDataMem();
            if(writeDataMem != 0 && selcted < 2)
            {
                selcted++;
            }
            else
            {
                selcted--;
            }
            if(selcted == 2)
            {
                play();
            }
        }

                /*
        Motion Event when the dot for Read Reg 1 inside of the Register file is tapped
        When tapped it will change colors from red to green to show it is selected.
        If already selected will change from green to red.
        If two dots are selected and they are the dots that need to be connected
        they will both turn yellow unless one of them requires more than one connection,
        in  that case it will turn back yellow once all connections are made.
        */

        if((motionEvent.getX() > 290 && motionEvent.getX() < 340) && (motionEvent.getY() > 50 && motionEvent.getY() < 100))
        {
            setRead1();
            if(read1 != 0 && selcted < 2)
            {
                selcted++;
            }
            else
            {
                selcted--;
            }
            if(selcted == 2)
            {
                play();
            }
        }

        /*
        Motion Event when the dot for Read Reg2 inside of the Register File is tapped
        When tapped it will change colors from red to green to show it is selected.
        If already selected will change from green to red.
        If two dots are selected and they are the dots that need to be connected
        they will both turn yellow unless one of them requires more than one connection,
        in  that case it will turn back yellow once all connections are made.
        */

        if((motionEvent.getX() > 290 && motionEvent.getX() < 340) && (motionEvent.getY() > 150 && motionEvent.getY() < 200))
        {
            setRead2();
            if(read2 != 0 && selcted < 2)
            {
                selcted++;
            }
            else
            {
                selcted--;
            }
            if(selcted == 2)
            {
                play();
            }
        }

        /*
        Motion Event when the dot for Write Address inside of the Register File is tapped
        When tapped it will change colors from red to green to show it is selected.
        If already selected will change from green to red.
        If two dots are selected and they are the dots that need to be connected
        they will both turn yellow unless one of them requires more than one connection,
        in  that case it will turn back yellow once all connections are made.
        */

        if((motionEvent.getX() > 290 && motionEvent.getX() < 340) && (motionEvent.getY() > 250 && motionEvent.getY() < 300))
        {
            setWriteAddr();
            if(writeAddr != 0 && selcted < 2)
            {
                selcted++;
            }
            else
            {
                selcted--;
            }
            if(selcted == 2)
            {
                play();
            }
        }

        /*
        Motion Event when the dot for Write Data inside of the Register File is tapped
        When tapped it will change colors from red to green to show it is selected.
        If already selected will change from green to red.
        If two dots are selected and they are the dots that need to be connected
        they will both turn yellow unless one of them requires more than one connection,
        in  that case it will turn back yellow once all connections are made.
        */

        if((motionEvent.getX() > 290 && motionEvent.getX() < 340) && (motionEvent.getY() > 350 && motionEvent.getY() < 400))
        {
            setWriteData();
            if(writeData != 0 && selcted < 2)
            {
                selcted++;
            }
            else
            {
                selcted--;
            }
            if(selcted == 2)
            {
                play();
            }
        }

        /*
        Motion Event when the dot for Read Data 1 inside of the Register File is tapped
        When tapped it will change colors from red to green to show it is selected.
        If already selected will change from green to red.
        If two dots are selected and they are the dots that need to be connected
        they will both turn yellow unless one of them requires more than one connection,
        in  that case it will turn back yellow once all connections are made.
        */

        if((motionEvent.getX() > 500 && motionEvent.getX() < 550) && (motionEvent.getY() > 150 && motionEvent.getY() < 200))
        {
            setReadData1();
            if(readData1 != 0 && selcted < 2)
            {
                selcted++;
            }
            else
            {
                selcted--;
            }
            if(selcted == 2)
            {
                play();
            }
        }


        /*
        Motion Event when the dot for Read Data 2 inside of the Register File is tapped
        When tapped it will change colors from red to green to show it is selected.
        If already selected will change from green to red.
        If two dots are selected and they are the dots that need to be connected
        they will both turn yellow unless one of them requires more than one connection,
        in  that case it will turn back yellow once all connections are made.
        */

        if((motionEvent.getX() > 500 && motionEvent.getX() < 550) && (motionEvent.getY() > 250 && motionEvent.getY() < 300))
        {
            setReadData2();
            if(readData2 != 0 && selcted < 2)
            {
                selcted++;
            }
            else
            {
                selcted--;
            }
            if(selcted == 2)
            {
               play();
            }
        }

        /*
        Motion Event when the dot for the Input of the Sign Extender is tapped
        When tapped it will change colors from red to green to show it is selected.
        If already selected will change from green to red.
        If two dots are selected and they are the dots that need to be connected
        they will both turn yellow unless one of them requires more than one connection,
        in  that case it will turn back yellow once all connections are made.
        */

        if((motionEvent.getX() > 635 && motionEvent.getX() < 685) && (motionEvent.getY() > 500 && motionEvent.getY() < 550))
        {
            setsIn();
            if(sIn != 0 && selcted < 2)
            {
                selcted++;
            }
            else
            {
                selcted--;
            }
            if(selcted == 2)
            {
                play();
            }
        }

        /*
        Motion Event when the dot for Output of the Sign Extender is tapped
        When tapped it will change colors from red to green to show it is selected.
        If already selected will change from green to red.
        If two dots are selected and they are the dots that need to be connected
        they will both turn yellow unless one of them requires more than one connection,
        in  that case it will turn back yellow once all connections are made.
        */

        if((motionEvent.getX() > 760 && motionEvent.getX() < 810) && (motionEvent.getY() > 500 && motionEvent.getY() < 550))
        {
            setsOut();
            if(sOut != 0 && selcted < 2)
            {
                selcted++;
            }
            else
            {
                selcted--;
            }
            if(selcted == 2)
            {
                play();
            }
        }

        /*
        Motion Event when the dot for OP1 of the ALU is tapped
        When tapped it will change colors from red to green to show it is selected.
        If already selected will change from green to red.
        If two dots are selected and they are the dots that need to be connected
        they will both turn yellow unless one of them requires more than one connection,
        in  that case it will turn back yellow once all connections are made.
        */

        if((motionEvent.getX() > 625 && motionEvent.getX() < 675) && (motionEvent.getY() > 125 && motionEvent.getY() < 175))
        {
            setOp1();
            if(op1 != 0 && selcted < 2)
            {
                selcted++;
            }
            else
            {
                selcted--;
            }
            if(selcted == 2)
            {
                play();
            }
        }

        /*
        Motion Event when the dot for OP2 of the ALU is tapped
        When tapped it will change colors from red to green to show it is selected.
        If already selected will change from green to red.
        If two dots are selected and they are the dots that need to be connected
        they will both turn yellow unless one of them requires more than one connection,
        in  that case it will turn back yellow once all connections are made.
        */

        if((motionEvent.getX() > 625 && motionEvent.getX() < 675) && (motionEvent.getY() > 265 && motionEvent.getY() < 315))
        {
            setOp2();
            if(op2 != 0 && selcted < 2)
            {
                selcted++;
            }
            else
            {
                selcted--;
            }
            if(selcted == 2)
            {
                play();
            }
        }


        /*
        Motion Event when the dot for the Output of the ALU is tapped
        When tapped it will change colors from red to green to show it is selected.
        If already selected will change from green to red.
        If two dots are selected and they are the dots that need to be connected
        they will both turn yellow unless one of them requires more than one connection,
        in  that case it will turn back yellow once all connections are made.
        */

        if((motionEvent.getX() > 760 && motionEvent.getX() < 810) && (motionEvent.getY() > 200 && motionEvent.getY() < 250))
        {
            setOut();
            if(out != 0 && selcted < 2)
            {
                selcted++;
            }
            else
            {
                selcted--;
            }
            if(selcted == 2)
            {
                play();
            }
        }
    }

    /*
    This the method that will determine what happens on screen. This method as of version 1.5.9.18
    will only support the store instruction but other instructions will be added for version 2.
     */
    private void play()
    {
        if(read1 == 1 && ins == 1)
        {
            read1 = 2;
            comp++;
        }

        if(read2 == 1 && ins == 1)
        {
            read2 = 2;
            comp++;
        }

        if(writeAddr == 1 && ins == 1)
        {
            writeAddr = 2;
            comp++;
        }

        if(read1 == 2 && read2 == 2 && writeAddr == 2)
        {
            ins = 2;
            comp++;
        }

        if(op1 == 1 && readData1 == 1)
        {
            op1 = 2;
            readData1 = 2;
            comp++;
        }
        if(op2 == 1 && readData2 == 1)
        {
            op2 = 2;
            readData2 = 2;
            comp++;
        }
        if(out == 1 && addr == 1)
        {
            out = 2;
            addr = 2;
            comp++;
        }
        if(readData == 1 && writeData == 1)
        {
            readData = 2;
            writeData = 2;
            comp++;
        }
    }


}
