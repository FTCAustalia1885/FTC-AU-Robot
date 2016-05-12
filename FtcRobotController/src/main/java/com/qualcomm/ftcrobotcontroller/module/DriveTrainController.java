package com.qualcomm.ftcrobotcontroller.module;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Michael on 5/11/2016.
 */
public class DriveTrainController {

    private final String FLN = "Left";
    private final String FRN = "Right";
    private final String BLN = "Left2";
    private final String BRN = "Right2";

    private final DcMotor frontLeft;
    private final DcMotor frontRight;
    private final DcMotor backLeft;
    private final DcMotor backRight;


    public DriveTrainController(HardwareMap hardwareMap){
        frontLeft  = hardwareMap.dcMotor.get(FLN);
        frontRight = hardwareMap.dcMotor.get(FRN);
        backLeft   = hardwareMap.dcMotor.get(BLN);
        backRight  = hardwareMap.dcMotor.get(BRN);

        frontLeft.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        frontRight.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        backLeft.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        backRight.setMode(DcMotorController.RunMode.RESET_ENCODERS);
    }

    public void driveTo(int delta) {
        frontLeft.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + delta);
        backLeft.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        backLeft.setTargetPosition(frontLeft.getCurrentPosition() + delta);

        frontRight.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        frontRight.setTargetPosition(frontLeft.getCurrentPosition() + delta);
        backRight.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        backRight.setTargetPosition(frontLeft.getCurrentPosition() + delta);
    }

    public int getBackLeftPosition(){
        return backLeft.getCurrentPosition();
    }

    public int getFrontLeftPosition(){
        return frontLeft.getCurrentPosition();
    }

    public int getBackRightPosition(){
        return backRight.getCurrentPosition();
    }

    public int getFrontRightPosition(){
        return frontRight.getCurrentPosition();
    }

}
