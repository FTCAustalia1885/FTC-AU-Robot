package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.ftcrobotcontroller.autonomous.AutonomousCommand;
import com.qualcomm.ftcrobotcontroller.autonomous.StraightDriveEncoder;
import com.qualcomm.ftcrobotcontroller.module.DriveTrainController;

import java.util.Collection;

/**
 * Created by Michael on 5/29/2016.
 */
public class TestDriveAutonomous extends Autonomous{

	private DriveTrainController controller;

	public void init(){
		controller = new DriveTrainController(hardwareMap);
		System.out.println("Drive Train Controller Instantiated");
		super.init();
	}

	@Override
	public void addCommands(Collection<AutonomousCommand> c) {
		c.add(new StraightDriveEncoder(10000,controller,telemetry));
	}
}
