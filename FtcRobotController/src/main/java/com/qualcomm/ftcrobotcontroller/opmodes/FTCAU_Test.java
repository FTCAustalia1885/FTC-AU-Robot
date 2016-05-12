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
    @Override
    public void init()
    {
        driveTrainController = new DriveTrainController(hardwareMap);
    }

    @Override
    public void loop()
    {
        telemetry.addData("FL", driveTrainController.getFrontLeftPosition());
        telemetry.addData("FR", driveTrainController.getFrontRightPosition());
        telemetry.addData("BL", driveTrainController.getBackLeftPosition());
        telemetry.addData("BR", driveTrainController.getBackRightPosition());
    }
}
