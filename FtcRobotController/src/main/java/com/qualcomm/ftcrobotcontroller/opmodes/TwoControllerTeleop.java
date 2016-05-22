package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.ftcrobotcontroller.module.DriveTrainController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DigitalChannelController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import java.util.*;

/**
 * Created by kristengiesler on 4/12/16.
 */
public class TwoControllerTeleop extends OpMode {


    //declares variables
    private float left, right;
    private float intakePower;
    /*
    private float winchPow;
    private float armPow;
    private static final double DOOR_DELTA = 0.5 ;
    private double leftDoorPos, rightDoorPos;
    private double tiltPower;
    private double blockPos;
    private static final double BLOCK_DELTA = 0.5;
    */
    private double churroPos = 0.0;
    private static final double CHURRO = 0.6;


    //declares motors and servos
    DriveTrainController driveTrain;
    DcMotor intake;
    /*
    DcMotor winch1, winch2;
    DcMotor arm;
    Servo leftDoor, rightDoor;
    Servo tilt;
    Servo intakeBlocker;
    */
    Servo churroClamp;

    @Override
    public void init() {
        /* Use the hardwareMap to get the dc motors and servos by name.
         * Names of the devices must match the names used
		 * creating robot config file
		 */

        driveTrain = new DriveTrainController(hardwareMap);

        intake = hardwareMap.dcMotor.get("intake");

        /*
        arm = hardwareMap.dcMotor.get("arm");
        winch1 = hardwareMap.dcMotor.get("winch1");
        winch2 = hardwareMap.dcMotor.get("winch2");
        leftDoor = hardwareMap.servo.get("leftDoor");
        rightDoor = hardwareMap.servo.get("rightDoor");
        tilt = hardwareMap.servo.get("tilt");
        intakeBlocker = hardwareMap.servo.get("intakeBlocker");
        */
        churroClamp = hardwareMap.servo.get("churroClamp");
    }

    @Override
    public void loop() {
        //decide which controls go to what (hardware mapping)
        //Note: range is 1 to -1 where full up= -1 and full down= 1
        //                             full left = -1 and full right=1

        /*
        armPow = gamepad2.right_stick_y;
        winchPow = gamepad2.left_stick_y;
        tiltPower = gamepad2.right_stick_x;
        */

        //set the values to the motors
        driveTrain.setPower(gamepad1.left_stick_y, gamepad1.right_stick_y);

        intake.setPower(gamepad1.right_trigger);
        /*
        arm.setPower(armPow);
        winch1.setPower(winchPow);
        winch2.setPower(winchPow);
         */

        //set the values to the servos
        /*
        tilt.setPower(tiltPower);
        */

        //if left bumper clicked, left door opens .5, else to 0
        /*if ( gamepad2.left_bumper){
            leftDoorPos = DOOR_DELTA;
        }
        else {
            leftDoorPos = 0;
        }*/

        // if right bumper clicked, right door opens .5, else to 0
        /*if( gamepad2.right_bumper){
            rightDoorPos = DOOR_DELTA;
        }
        else {
            rightDoorPos = 0;
        }
        */
        //set the values to the door servos
        /*
        rightDoor.setPosition(rightDoorPos);
        leftDoor.setPosition(leftDoorPos);
        */

        // if left bumper clicked, churro goes up; if left trigger down atleast halfway, churro goes down
        if ( gamepad1.left_bumper ) {
            churroPos = CHURRO;
        }
        if ( gamepad1.left_trigger >= 0.5 ) {
            churroPos = 0;
        }
        // set the values to the churro clamp
        churroClamp.setPosition(churroPos);

        /*if (gamepad2.dpad_up){
              blockPos = BLOCK_DELTA;
        }
        if (gampepad2.dpad_down){
            blockPos = 0;
        }*/
        // set the value to the intake blocker
        // intakeBlocker.setPosition( blockPos );

        //send telemetry back to the drivers station
        telemetry.addData("left pwr", "left  pwr: " + String.format("%.2f", left));
        telemetry.addData("right pwr", "right pwr: " + String.format("%.2f", right));
        telemetry.addData("intake pwr", "intake pwr:" + String.format("%.2f", intakePower));
        //telemetry.addData("arm pwr", "arm pwr:" + String.format("%.2f", armPow));
        //telemetry.addData("winch pwr", "winch pwr:" + String.format("%.2f", winchPow));
        //telemetry.addData("left door pwr", "leftDoor pwr:" + String.format("%.2f", leftDoorPos));
        //telemetry.addData("right door pwr", "rightDoor pwr:" + String.format("%.2f", rightDoorPos));
        telemetry.addData("churro pwr", "churro pwr:" + String.format("%.2f", churroPos));
        //telemetry.addData("block pwr", "block pwr:" + String.format("%.2f", blockPos));
    }

    @Override
    public void stop() {
        System.out.print("WE DID IT, ACCOMPLISHMENT");
    }
}
