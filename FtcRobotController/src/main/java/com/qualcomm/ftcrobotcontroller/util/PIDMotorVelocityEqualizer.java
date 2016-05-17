package com.qualcomm.ftcrobotcontroller.util;

import com.qualcomm.ftcrobotcontroller.module.Controller;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Michael on 5/17/2016.
 */
public class PIDMotorVelocityEqualizer extends Controller{

	private DcMotor motora;
	private DcMotor motorb;

	private float aOutput;
	private float bOutput;

	public PIDMotorVelocityEqualizer(DcMotor a, DcMotor b){
		motora = a;
		motorb = b;
	}

	public void update(){

	}
}
