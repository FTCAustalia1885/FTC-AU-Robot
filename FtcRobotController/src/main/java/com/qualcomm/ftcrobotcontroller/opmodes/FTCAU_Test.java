package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Aaron on 2/8/2016.
 * Modified by Cameron on 2/10/2016.
 */
public class FTCAU_Test extends OpMode
{
    private static final double DEADZONE = .05;
    DcMotor leftDrive,rightDrive;
    DcMotor WinchL, WinchR;
    Servo Test;
    @Override
    public void init()
    {
        leftDrive = hardwareMap.dcMotor.get("Left");
        rightDrive = hardwareMap.dcMotor.get("Right");
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        WinchR = hardwareMap.dcMotor.get("WinchR");
        WinchL = hardwareMap.dcMotor.get("WinchL");
        WinchL.setDirection(DcMotor.Direction.REVERSE);
        Test = hardwareMap.servo.get("Flip");
    }

    @Override
    public void loop()
    {
        //If you are trying to go straight
        if(Math.abs(gamepad1.left_stick_y - gamepad1.right_stick_y)<.1)
        {
                leftDrive.setPower(deadzone(gamepad1.left_stick_y));
                rightDrive.setPower(deadzone(gamepad1.left_stick_y));
        }
        else
        {
            leftDrive.setPower(deadzone(gamepad1.left_stick_y));
            rightDrive.setPower(deadzone(gamepad1.right_stick_y));
        }
        if(gamepad1.a)
        {
            WinchR.setPower(1);
            WinchL.setPower(1);
        }
        else if(gamepad1.y)
        {
            WinchR.setPower(-1);
            WinchL.setPower(-1);
        }
        else
        {
            WinchR.setPower(0);
            WinchL.setPower(0);
        }
        if(gamepad1.dpad_up)
        {
            Test.setPosition(0);
        }
        else
        {
            Test.setPosition(1);
        }
        //OuttakeBelts and Intake
    }
    public static double deadzone(double axis)
    {
        if(Math.abs(axis) < DEADZONE)
        {
            return 0;
        }
        return axis;
    }
}
