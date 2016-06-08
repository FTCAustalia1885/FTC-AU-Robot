package com.qualcomm.ftcrobotcontroller.controller.module;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * A controller class which makes the operations of the arm mechanism simpler.
 *
 * @author Michael Kelly
 */
public class Arm {

	private static final String AM = "arm";
	private static final String LL = "lowerLimit";
	private static final String UL = "upperLimit";

	private DcMotor armMotor;
	private DigitalChannel upperLimitSwitch;
	private DigitalChannel lowerLimitSwitch;

	private float power;

	/**
	 * Builds a new arm controller and initializes the motors and sensors required to run the arm
	 * @param map the hardware map from which motors and sensors will be retrieved
	 */
	public Arm(HardwareMap map){
		armMotor = map.dcMotor.get(AM);
		upperLimitSwitch = map.digitalChannel.get(UL);
		lowerLimitSwitch = map.digitalChannel.get(LL);
	}

	/**
	 * Sets the output power for the arm motor(s) restricted between -1.0f and 1.0f (inclusively)
	 * @param p power level (will be restricted between +/-1)
	 */
	public void setArmPower(float p){
		power = Math.min(Math.max(p*getPowerScale(), 1), -1);
		armMotor.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
		armMotor.setPower(p);
	}

	/**
	 * @return the current power being applied to the arm in the domain [-1.0, 1.0]
	 */
	public float getArmPower(){
		return power;
	}

	/**
	 * @return The state of the upper limit switch
	 */
	public boolean isAtUpperLimit(){
		return upperLimitSwitch.getState();
	}

	/**
	 * @return The state of the lower limit switch
	 */
	public boolean isAtLowerLimit(){
		return lowerLimitSwitch.getState();
	}

	/**
	 * Calculates the rate at which the maximum power applied to the arm motor should be restricted
	 * @return 0.0f to 1.0f (inclusively) representing the scalar for the maximum power
	 */
	public float getPowerScale(){
		return 1.0f;
	}

}
