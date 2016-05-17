package com.qualcomm.ftcrobotcontroller.module;

import com.qualcomm.ftcrobotcontroller.util.PID;
import com.qualcomm.ftcrobotcontroller.util.PIDMotorPositioner;
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

    private final DcMotor frontLeft;
    private final DcMotor frontRight;
    private final DcMotor backLeft;
    private final DcMotor backRight;

    private long lastTime;

    private static final PID backLeftPID = new PID(0.1, 0.0, 0.0);

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
        backLeft.setTargetPosition(backLeft.getCurrentPosition() + delta);

        frontRight.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        frontRight.setTargetPosition(frontRight.getCurrentPosition() + delta);
        backRight.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        backRight.setTargetPosition(backRight.getCurrentPosition() + delta);
    }

    public void setLeftPower(double level){
        backLeft.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        backLeft.setPower(level);
        frontLeft.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        frontLeft.setPower(level);
    }

    public void setRightPower(double level){
        backRight.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        backRight.setPower(level);
        frontRight.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        frontRight.setPower(level);
    }

    public void setPower(double left, double right){
        setLeftPower(left);
        setRightPower(right);
    }

    public void setLeftPosition(int position){
        backLeft.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        backLeft.setPower(0.2);
        backLeft.setTargetPosition(position);
        frontLeft.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        frontLeft.setPower(0.2);
        frontLeft.setTargetPosition(position);
    }

    public double getBackLeftPIDOutput(){
        return backLeftPID.getOutput();
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

    public void resetPIDs(){
        backLeftPID.reset(backLeft.getCurrentPosition(), backLeft.getTargetPosition());
        lastTime = System.currentTimeMillis();
    }

    public long updatePIDs(){
        long currentTime = System.currentTimeMillis();
        long deltaTime = currentTime - lastTime;

        backLeftPID.update(backLeft.getCurrentPosition(), deltaTime);

        lastTime = currentTime;
        return deltaTime;
    }

}
