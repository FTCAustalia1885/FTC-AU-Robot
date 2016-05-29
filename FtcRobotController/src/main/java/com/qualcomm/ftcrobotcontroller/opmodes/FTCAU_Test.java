package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.ftcrobotcontroller.module.DriveTrainController;
import com.qualcomm.ftcrobotcontroller.sensor.ColorSensorController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Aaron on 2/8/2016.
 * Modified by Cameron on 2/10/2016.
 */
public class FTCAU_Test extends OpMode {
	private DriveTrainController driveTrainController;
	private ColorSensorController colorSensor;
	private boolean first;

	@Override
	public void init() {
		driveTrainController = new DriveTrainController(hardwareMap);
		first = true;
		colorSensor = new ColorSensorController(hardwareMap.colorSensor.get("color"), hardwareMap.deviceInterfaceModule.get("dim"), 2);
		colorSensor.setActive(true);
	}

	@Override
	public void loop() {
		telemetry.addData("hue", colorSensor.getHue());
		telemetry.addData("isRed", colorSensor.getHue() < 20 || colorSensor.getHue() > 340 );
		telemetry.addData("isBlue", colorSensor.getHue() < 250 && colorSensor.getHue() > 190 );
	}

	@Override
	public void stop() {
		colorSensor.setActive(false);
	}
}
