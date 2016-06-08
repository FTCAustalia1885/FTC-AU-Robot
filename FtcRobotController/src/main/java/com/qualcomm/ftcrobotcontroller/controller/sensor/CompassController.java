package com.qualcomm.ftcrobotcontroller.controller.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Michael on 6/5/2016.
 */
public class CompassController implements SensorEventListener{

	private boolean azimuthInitialized;
	private SensorManager mSensorManager;
	private Sensor accelerometer;
	private Sensor magnetometer;

	private float[] mGravity;
	private float[] mGeomagnetic;

	private float initAzimuth;

	private float azimuth;
	private float pitch;
	private float roll;

	public CompassController(HardwareMap map){
		mSensorManager = (SensorManager) map.appContext.getSystemService(Context.SENSOR_SERVICE);
		accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
		mSensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);

		azimuthInitialized = false;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int i) {
		//not important
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			mGravity = event.values;
		}
		if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
			mGeomagnetic = event.values;
		}
		if (mGravity != null && mGeomagnetic != null) {  //make sure we have both before calling getRotationMatrix
			float R[] = new float[9];
			float I[] = new float[9];
			boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
			if (success) {
				float orientation[] = new float[3];
				SensorManager.getOrientation(R, orientation);
				// orientation contains: azimuth, pitch and roll
				// raw values ranges from -pi to pi
				if(!azimuthInitialized){
					initAzimuth = (float)(orientation[0] + Math.PI);
					azimuthInitialized = true;
				}
				azimuth = (float)(orientation[0] + Math.PI) - initAzimuth;
				if(azimuth > Math.PI){
					azimuth -= 2 * Math.PI;
				}
				else if(azimuth < -Math.PI){
					azimuth += 2 * Math.PI;
				}
				pitch = orientation[1];
				roll = orientation[2];
			}
		}
	}

	public float getAzimuth(){
		return azimuth;
	}

	public float getPitch(){
		return pitch;
	}

	public float getRoll(){
		return roll;
	}
}
