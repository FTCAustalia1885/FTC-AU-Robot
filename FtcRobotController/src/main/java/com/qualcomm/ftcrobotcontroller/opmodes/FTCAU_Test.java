package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.ftcrobotcontroller.module.DriveTrainController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Aaron on 2/8/2016.
 * Modified by Cameron on 2/10/2016.
 */
public class FTCAU_Test extends OpMode
{
    private DriveTrainController driveTrainController;
    private boolean first;

    @Override
    public void init()
    {
        driveTrainController = new DriveTrainController(hardwareMap);
        first = true;
    }

    @Override
    public void loop()
    {
    }

    @Override
    public void stop(){
    }
}
