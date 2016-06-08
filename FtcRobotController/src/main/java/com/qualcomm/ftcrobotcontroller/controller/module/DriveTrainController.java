package com.qualcomm.ftcrobotcontroller.controller.module;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Michael on 5/11/2016.
 */
public class DriveTrainController {

    private final String FLN = "motorLeftFront";
    private final String FRN = "motorRightFront";
    private final String BLN = "motorLeftBack";
    private final String BRN = "motorRightBack";

    private double leftPower, rightPower;

    private final DcMotor frontLeft;
    private final DcMotor frontRight;
    private final DcMotor backLeft;
    private final DcMotor backRight;

    private long lastTime;

    public DriveTrainController(HardwareMap hardwareMap){
        frontLeft  = hardwareMap.dcMotor.get(FLN);
        frontRight = hardwareMap.dcMotor.get(FRN);
        backLeft   = hardwareMap.dcMotor.get(BLN);
        backRight  = hardwareMap.dcMotor.get(BRN);

        frontLeft.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        frontRight.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        backLeft.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        backRight.setMode(DcMotorController.RunMode.RESET_ENCODERS);

        backLeft.setDirection(DcMotor.Direction.REVERSE);
	    frontRight.setDirection(DcMotor.Direction.REVERSE);
	    frontLeft.setDirection(DcMotor.Direction.REVERSE);
    }

    public void driveTo(int delta) {
        frontLeft.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + delta);
        backLeft.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        backLeft.setTargetPosition(backLeft.getCurrentPosition() + delta);

        frontRight.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        frontRight.setTargetPosition(frontRight.getCurrentPosition() + delta);
        backRight.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        backRight.setTargetPosition(backRight.getCurrentPosition() + delta);
    }

    public void setLeftPower(double level){
        level = Math.max(Math.min(level, 1),-1);
        leftPower = level;

        backLeft.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        backLeft.setPower(leftPower);
        frontLeft.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        frontLeft.setPower(leftPower);
    }

    public double getLeftPower(){
        return leftPower;
    }

    public int getLeftPosition(){
        return getFrontLeftPosition();
    }

    public void setRightPower(double level){
        level = Math.max(Math.min(level, 1),-1);
        rightPower = level;

        backRight.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        backRight.setPower(rightPower);
        frontRight.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        frontRight.setPower(rightPower);
    }

    public double getRightPower(){
        return rightPower;
    }

    public int getRightPosition(){
        return getFrontRightPosition();
    }


    public void setPower(double left, double right){
        setLeftPower(left);
        setRightPower(right);
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

    public void resetEncoders(){
        backLeft.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        backRight.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        frontLeft.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        frontRight.setMode(DcMotorController.RunMode.RESET_ENCODERS);
    }

}
