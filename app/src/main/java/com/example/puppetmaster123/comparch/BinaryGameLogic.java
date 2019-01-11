package com.example.puppetmaster123.comparch;

import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.*;

/*
This controls the logic for the Control Signals game
This currently only uses four instructions SW, LW, Branch, R-format

Designed by Damian Bocanegra, Jake Montegomery, Andrew Wheeler
Implemented by Damian Bocanegra
 */

public class BinaryGameLogic
{
    private int regD = 0;
    private int regW = 0;
    private int memD = 0;
    private int memW = 0;
    private int memReg = 0;
    private int alu = 0;
    private int branch = 0;
    private String instruction;
    private ArrayList<String> instructions = new ArrayList<>();
    public int correct = 0;

    public BinaryGameLogic()
    {
        instructions.add("R-Format");
        instructions.add("BEQ");
        instructions.add("LW");
        instructions.add("SW");

        Collections.shuffle(instructions);

        instruction = instructions.get(0);
        instructions.remove(0);

    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public int getRegD() {
        return regD;
    }

    public void setRegD() {
        if(regD == 0)
            regD = 1;
        else
            regD = 0;
    }

    public int getRegW() {
        return regW;
    }

    public void setRegW() {
        if(regW == 0)
            regW = 1;
        else
            regW = 0;
    }

    public int getMemD() {
        return memD;
    }

    public void setMemD() {
        if(memD == 0)
            memD = 1;
        else
            memD = 0;
    }

    public int getMemW() {
        return memW;
    }

    public void setMemW() {
        if(memW == 0)
            memW = 1;
        else
            memW = 0;
    }

    public int getMemReg() {
        return memReg;
    }

    public void setMemReg() {
        if(memReg == 0)
            memReg = 1;
        else
            memReg = 0;
    }

    public int getAlu() {
        return alu;
    }

    public void setAlu() {
        if(alu == 0)
            alu = 1;
        else
            alu = 0;
    }

    public int getBranch() {
        return branch;
    }

    public void setBranch() {
        if(branch == 0)
            branch = 1;
        else
            branch = 0;
    }


    public int play()
    {
        switch(instruction)
        {
            case "SW":
                if(regD != 0)
                    return 0;
                if(regW != 0)
                    return 0;
                if(memD != 0)
                    return 0;
                if(memW != 1)
                    return 0;
                if(memReg != 0)
                    return 0;
                if(alu != 1)
                    return 0;
                if(branch != 0)
                    return 0;
                break;
            case "R-Format":
                if(regD != 1)
                    return 0;
                if(regW != 1)
                    return 0;
                if(memD != 0)
                    return 0;
                if(memW != 0)
                    return 0;
                if(memReg != 0)
                    return 0;
                if(alu != 0)
                    return 0;
                if(branch != 0)
                    return 0;
                break;
            case "LW":
                if(regD != 0)
                    return 0;
                if(regW != 1)
                    return 0;
                if(memD != 1)
                    return 0;
                if(memW != 0)
                    return 0;
                if(memReg != 1)
                    return 0;
                if(alu != 1)
                    return 0;
                if(branch != 0)
                    return 0;
                break;
            case "BEQ":
                if(regD != 0)
                    return 0;
                if(regW != 0)
                    return 0;
                if(memD != 0)
                    return 0;
                if(memW != 0)
                    return 0;
                if(memReg != 0)
                    return 0;
                if(alu != 0)
                    return 0;
                if(branch != 1)
                    return 0;
                break;
        }

        correct = 1;
        return 1;
    }

    public void reset()
    {
        regD = 0;
        regW = 0;
        memD = 0;
        memW = 0;
        memReg = 0;
        alu = 0;
        branch = 0;

        correct = 0;

        if(instructions.size() != 0 )
        {
            Collections.shuffle(instructions);
            instruction = instructions.get(0);
            instructions.remove(0);
        }
        else
        {
            instruction = "done";
        }
    }

    public void motionBinary(MotionEvent motionEvent)
    {
        if((motionEvent.getX() > 950 && motionEvent.getX() < 1050) && (motionEvent.getY() > 0 && motionEvent.getY() < 100) )
        {
            setRegD();
        }

        if((motionEvent.getX() > 950 && motionEvent.getX() < 1050) && (motionEvent.getY() > 100 && motionEvent.getY() < 200) )
        {
            setRegW();
        }
        if((motionEvent.getX() > 950 && motionEvent.getX() < 1050) && (motionEvent.getY() > 200 && motionEvent.getY() < 300) )
        {
            setMemD();
        }
        if((motionEvent.getX() > 950 && motionEvent.getX() < 1050) && (motionEvent.getY() > 300 && motionEvent.getY() < 400) )
        {
            setMemW();
        }
        if((motionEvent.getX() > 950 && motionEvent.getX() < 1050) && (motionEvent.getY() > 400 && motionEvent.getY() < 500) )
        {
            setMemReg();
        }
        if((motionEvent.getX() > 950 && motionEvent.getX() < 1050) && (motionEvent.getY() > 500 && motionEvent.getY() < 600) )
        {
            setAlu();
        }
        if((motionEvent.getX() > 950 && motionEvent.getX() < 1050) && (motionEvent.getY() > 600 && motionEvent.getY() < 700) )
        {
            setBranch();
        }
    }


}
