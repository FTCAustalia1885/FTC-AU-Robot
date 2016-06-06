package com.qualcomm.ftcrobotcontroller.autonomous;

import com.qualcomm.ftcrobotcontroller.module.Compass;
import com.qualcomm.ftcrobotcontroller.module.DriveTrainController;
import com.qualcomm.robotcore.robocol.Telemetry;

/**
 * Created by Michael on 6/5/2016.
 */
public class StraightDriveCompass extends AutonomousCommand {

	private static final float MAX_POWER  = 1.0f;
	private static final float V_PER_RAD_SEC = 0.2f;

	private DriveTrainController driveTrain;
	private Compass compass;
	private int distanceToGo;
	private int lastPosition;
	private float initialAzimuth;

	public StraightDriveCompass(int distance, DriveTrainController driveTrainController, Compass compass, Telemetry telem){
		super(telem);
		this.driveTrain = driveTrainController;
		this.compass = compass;

		distanceToGo = distance;
	}

	public void init(){
		lastPosition = getPosition();
		driveTrain.setPower(MAX_POWER, MAX_POWER);
	}

	public boolean isComplete(){
		return distanceToGo <= 0;
	}

	@Override
	public void update(float delta) {
		float compassAzimuth = compass.getAzimuth();
		float az = distance(compassAzimuth, initialAzimuth);
		driveTrain.setLeftPower(driveTrain.getLeftPower()   - ( (az / 2) * V_PER_RAD_SEC * delta));
		driveTrain.setRightPower(driveTrain.getRightPower() + ( (az / 2) * V_PER_RAD_SEC * delta));

		int position = getPosition();
		distanceToGo -= position - lastPosition;
		lastPosition = position;
	}

	/**
	 * Returns the current tick position of the dominant encoder
	 * @return
	 */
	public int getPosition(){
		return driveTrain.getBackRightPosition();
	}

	/**
	 * Helper method to provide the distance between two angle measures
	 * @param a angle Alpha (in radians)
	 * @param b angle Beta (in radians)
	 * @return The distance between the two angles in radians
	 */
	private float distance(float a, float b) {
		return (float)Math.min((a-b)<0?a-b+(Math.PI*2):a-b,
							   (b-a)<0?b-a+(Math.PI*2):b-a);
	}}
