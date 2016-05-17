package com.qualcomm.ftcrobotcontroller.util;

/**
 * Created by Michael on 5/11/2016.
 */
public class PID {

    private final double kP;
    private final double kI;
    private final double kD;
    private double destination;
    private double delta;

    private double oP;
    private double oI;
    private double oD;
    private double outputP;
    private double errorP;

    private double integral;
    private double derivative;

    public PID(double P, double I, double D){
        this(P, I, D, 0, 0);
    }

    public PID(double P, double I, double D, double dest, double current){
        kP = P;
        kI = I;
        kD = D;
        destination = dest;
        delta = dest - current;
    }

    public void update(double current, float delta){
        if(delta != 0) {
            double lastErrorP = errorP;
            errorP = (destination - current) / delta;

            oP = errorP * kP;

            integral += (0.5 * (errorP - lastErrorP) + errorP) * delta;
            oI = integral * kI;

            derivative = ((errorP - lastErrorP) / delta);
            oD = derivative * kP;

            outputP = oP + oI + oD;
        }
    }

    public void reset(int current, int goal) {
        integral = 0;
        derivative = 0;
        destination = goal;
        delta = goal - current;
    }

    public double getOutput(){
        return outputP;
    }

}
