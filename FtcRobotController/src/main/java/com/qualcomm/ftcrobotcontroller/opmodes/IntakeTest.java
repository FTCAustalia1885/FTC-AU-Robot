package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.ftcrobotcontroller.module.DriveTrainController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Michael on 5/15/2016.
 */
public class IntakeTest extends OpMode {

	private DcMotor intake;

	private static final double BASE_SPEED = 0.75;
	private static final double INTAKE_SLOW = 0.25;
	private static final double INTAKE_FAST = 0.5;

	private DriveTrainController driveTrain;

	@Override
	public void init() {
		intake = hardwareMap.dcMotor.get("intake");
		driveTrain = new DriveTrainController(hardwareMap);
		driveTrain.setPower(0, 0);
	}

	@Override
	public void loop() {
		driveTrain.setPower(gamepad1.left_stick_y * BASE_SPEED, -gamepad1.right_stick_y * BASE_SPEED);
		setIntakeSpeed();
	}

	/**
	 * Reads the joystick inputs and sets the intake speed accordingly
	 */
	private void setIntakeSpeed() {
		if (gamepad1.left_bumper) {
			intake.setPower(-INTAKE_SLOW);
		} else if (gamepad1.left_trigger > 0) {
			intake.setPower(INTAKE_SLOW);
		} else if (gamepad1.right_bumper) {
			intake.setPower(-INTAKE_FAST);
		} else if (gamepad1.right_trigger > 0) {
			intake.setPower(INTAKE_FAST);
		} else {
			intake.setPower(0);
		}
	}
}
