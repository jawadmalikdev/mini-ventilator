package com.example.miniventilator;

public class instructionhelper {
   public String breathperminute, breathlength, instructions;

    public instructionhelper() {
    }

    public instructionhelper(String breathperminute, String breathlength, String instructions) {
        this.breathperminute = breathperminute;
        this.breathlength = breathlength;
        this.instructions = instructions;
    }

    public String getBreathperminute() {
        return breathperminute;
    }

    public void setBreathperminute(String breathperminute) {
        this.breathperminute = breathperminute;
    }

    public String getBreathlength() {
        return breathlength;
    }

    public void setBreathlength(String breathlength) {
        this.breathlength = breathlength;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }


}
