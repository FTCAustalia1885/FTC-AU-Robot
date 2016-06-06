package com.qualcomm.ftcrobotcontroller.autonomous;

import com.qualcomm.robotcore.robocol.Telemetry;

/**
 * Created by Michael on 5/11/2016.
 */
public abstract class AutonomousCommand {
    protected final Telemetry telemetry;
    public AutonomousCommand(Telemetry tel){
        telemetry = tel;
    }
    public abstract void update(float delta);
    public abstract boolean isComplete();
    public void finalize(){}
    public void init(){}
}
