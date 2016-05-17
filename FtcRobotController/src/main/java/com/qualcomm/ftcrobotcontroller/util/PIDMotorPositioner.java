package com.qualcomm.ftcrobotcontroller.util;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Michael on 5/15/2016.
 */
public class PIDMotorPositioner {

	private static final double kP = 1.0;
	private static final double kI = 0.0;
	private static final double kD = 0.0;

	private double p, i, d;

	private DcMotor motor;
	private long lastTime;

	private int from;
	private int to;
	private int error;

	private boolean running;

	public PIDMotorPositioner(DcMotor motor){
		this(motor, motor.getCurrentPosition());
	}
	
	public PIDMotorPositioner(DcMotor motor, int position) {
		running = true;
		this.motor = motor;
		from = motor.getCurrentPosition();
		to = position;
		error = to - from;
	}

	public void update() {
		error = to - motor.getCurrentPosition();
		p = (error / Math.abs(to-from)) * kP;
	}

	public double getPID() {
		return p+i+d;
	}

}
