package com.qualcomm.ftcrobotcontroller.opmodes;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.ftcrobotcontroller.FtcConfig;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * An empty op mode serving as a template for custom OpModes
 */

/**
 * Created by
 * Modified by Cameron Rhodes on 9/30/2015.
 */

public class OrientOp extends OpMode implements SensorEventListener {
    private String startDate;
    private SensorManager mSensorManager;
    Sensor accelerometer;
    Sensor magnetometer;

    private boolean azimuthInitialized;
    private float initAzimuth;

    // orientation values
    private float azimuth = 0.0f;       // value in radians
    private float pitch = 0.0f;        // value in radians
    private float roll = 0.0f;         // value in radians

    private float[] mGravity;       // latest sensor values
    private float[] mGeomagnetic;   // latest sensor values

    @Override
    public void init() {
    }

    /*
     * Constructor
     */
    public OrientOp() {

    }

    /*
    * Code to run when the op mode is first enabled goes here
   * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
    */
    @Override
    public void start() {
        try {
            startDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
            // needed FtcConfig to get context for getSystemService
            // but this required change to FtcRobotControllerActivity to set the context for us
            mSensorManager = (SensorManager) hardwareMap.appContext.getSystemService(Context.SENSOR_SERVICE);
            //FtcConfig.context.getSystemService(Context.SENSOR_SERVICE);
            accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            // delay value is SENSOR_DELAY_UI which is ok for telemetry, maybe not for actual robot use
            mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
            mSensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);

            azimuthInitialized = false;
        } catch (Exception e) {
            telemetry.addData("Error", e.getStackTrace());
            DbgLog.msg(e.getStackTrace().toString());
        }
    }

    /*
     * This method will be called repeatedly in a loop
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#loop()
     */
    @Override
    public void loop() {
        telemetry.addData("1 Start", "OrientOp started at " + startDate);
        if (mGravity == null) {
            telemetry.addData("2 Gravity", "Gravity sensor values null ");
            telemetry.addData("3 Geomagnetic", "Geomagnetic sensor values null ");
        } else {
            telemetry.addData("2 Gravity", "Gravity sensor returning values ");
            telemetry.addData("3 Geomagnetic", "Geomagnetic sensor returning values ");
        }
        telemetry.addData("4 yaw", "yaw= " + String.format("%0 6.3f",Math.toDegrees(azimuth)));
        telemetry.addData("5 pitch", "pitch = " + String.format("%0 6.3f",Math.toDegrees(pitch)));
        telemetry.addData("6 roll", "roll = " + String.format("%0 6.3f",Math.toDegrees(roll)));
    }

    /*
    * Code to run when the op mode is first disabled goes here
    * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#stop()
    */
    @Override
    public void stop() {
        mSensorManager.unregisterListener(this);
    }


    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not sure if needed, placeholder just in case
    }

    public void onSensorChanged(SensorEvent event) {
        // we need both sensor values to calculate orientation
        // only one value will have changed when this method called, we assume we can still use the other value.
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
}
