package com.qualcomm.ftcrobotcontroller.autonomous;

import com.qualcomm.ftcrobotcontroller.module.DriveTrainController;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.robocol.Telemetry;

/**
 * Created by Michael on 5/25/2016.
 */
public class DriveyMcDriveface extends AutonomousCommand{

	private static final int TICKS_PER_INCH = 0;

	private static final int ALLOWABLE_TICK_ERROR = 20;

	private final DriveTrainController driveTrain;
	private final Telemetry telemetry;

	private int initLeftPos;
	private int initRightPos;

	private int leftDest;
	private int rightDest;

	private final int distance;

	private int lastLeftPos;
	private int lastRightPos;


	public DriveyMcDriveface(int distance, DriveTrainController driveTrain, Telemetry telem){
		this.driveTrain = driveTrain;
		this.distance = distance;
		this.telemetry = telem;
	}

	@Override
	public void init(){
		driveTrain.resetEncoders();
		initLeftPos = lastLeftPos = driveTrain.getLeftPosition();
		initRightPos = lastRightPos = driveTrain.getRightPosition();
		leftDest = driveTrain.getLeftPosition() + distance;
		rightDest = driveTrain.getRightPosition() + distance;
	}

	@Override
	public void update(float delta) {
		printCurrentPos();
		int leftPos = driveTrain.getLeftPosition() - initLeftPos;
		int rightPos = driveTrain.getRightPosition() - initRightPos;
		int leftDelta = leftPos - lastLeftPos;
		int rightDelta = rightPos - lastRightPos;

		float flux = leftPos - rightPos; //more positive means left is moving to fast, visa versa

		if(isLeftComplete()){
			driveTrain.setLeftPower(0);
		}
		else{
			driveTrain.setLeftPower((distance - leftPos+ 0.0)/distance - flux/distance*10);
		}
		if(isRightComplete()){
			driveTrain.setRightPower(0);
		}else{
			driveTrain.setRightPower((distance - rightPos+ 0.0)/distance + flux/distance*10);
		}
		telemetry.addData("lPower", driveTrain.getLeftPower());
		telemetry.addData("rPower", driveTrain.getRightPower());
		telemetry.addData("flux", flux);

		lastLeftPos = leftPos;
		lastRightPos = rightPos;
	}

	public boolean isLeftComplete(){
		return Math.abs(driveTrain.getLeftPosition() - leftDest) < ALLOWABLE_TICK_ERROR;
	}

	public boolean isRightComplete(){
		return Math.abs(driveTrain.getRightPosition() - rightDest) < ALLOWABLE_TICK_ERROR;
	}

	public void printCurrentPos(){
		telemetry.addData(" left", driveTrain.getLeftPosition());
		telemetry.addData("right", driveTrain.getRightPosition());
	}

	@Override
	public boolean isComplete() {
		return isLeftComplete() && isRightComplete();
	}
}
