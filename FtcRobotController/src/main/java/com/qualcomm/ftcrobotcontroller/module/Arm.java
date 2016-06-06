package com.qualcomm.ftcrobotcontroller.module;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Michael on 6/5/2016.
 */
public class Arm {

	private static final String AM = "arm";
	private static final String LL = "lowerLimit";
	private static final String UL = "upperLimit";

	private DcMotor armMotor;
	private DigitalChannel upperLimitSwitch;
	private DigitalChannel lowerLimitSwitch;


	public Arm(HardwareMap map){
		armMotor = map.dcMotor.get(AM);
		upperLimitSwitch = map.digitalChannel.get(UL);
		lowerLimitSwitch = map.digitalChannel.get(LL);
	}

	public void setArmPower(float power){
		armMotor.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
		armMotor.setPower(power);
	}

	public boolean isUpperLimit(){
		return upperLimitSwitch.getState();
	}

	public boolean isLowerLimit(){
		return lowerLimitSwitch.getState();
	}

}
