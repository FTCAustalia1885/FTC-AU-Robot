package com.qualcomm.ftcrobotcontroller.autonomous;

/**
 * Created by Michael on 5/11/2016.
 */
public abstract class AutonomousCommand {
    public abstract void update(float delta);
    public abstract boolean isComplete();
    public void finalize(){}
    public void init(){}
}
