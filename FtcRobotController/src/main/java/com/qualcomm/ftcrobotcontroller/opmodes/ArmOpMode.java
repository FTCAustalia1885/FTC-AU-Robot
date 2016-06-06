package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.ftcrobotcontroller.module.Arm;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by Michael on 6/5/2016.
 */
public class ArmOpMode extends OpMode{

	private Arm arm;

	public void init(){
		arm = new Arm(hardwareMap);
	}

	public void loop(){
		if(!arm.isLowerLimit() && !arm.isUpperLimit()) {
			arm.setArmPower(gamepad1.right_stick_y);
		}else{
			arm.setArmPower(0);
		}
	}
}
