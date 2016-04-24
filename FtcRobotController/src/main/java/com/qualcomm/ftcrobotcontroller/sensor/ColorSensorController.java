package com.qualcomm.ftcrobotcontroller.sensor;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import static com.qualcomm.robotcore.hardware.DigitalChannelController.Mode.*;

/**
 * Created by Michael on 4/24/2016.
 */
public class ColorSensorController {
    private final ColorSensor sensor;
    private final DeviceInterfaceModule cdim;
    private final int LED_CHANNEL;

    private boolean active;

    public ColorSensorController(ColorSensor sensor, DeviceInterfaceModule cdim, final int LED_CHANNEL){
        this.sensor = sensor;
        this.cdim = cdim;
        this.LED_CHANNEL = LED_CHANNEL;

        cdim.setDigitalChannelMode(LED_CHANNEL, OUTPUT);
        setActive(false);
    }

    /**
     * Enables/Disables the color sensor. The color sensor must be enabled to pull values off of it
     * the status of the color sensor can be seen from the LED on the ADAFruit board
     * @param on whether to enable or disable the sensor
     */
    public void setActive(boolean on) {
        this.active = on;
        cdim.setDigitalChannelState(LED_CHANNEL, active);
    }

    /**
     * @return a 3 float array in the format" {HUE, SAT, VAL}
     */
    public float[] getHSVArray() {
        if (active){
            float[] hsvvalues = new float[3];
            Color.RGBToHSV(sensor.red(), sensor.green(), sensor.blue(), hsvvalues);
            return hsvvalues;
        }else{
            throw new IllegalStateException("Color sensor is not activated!");
        }
    }

    /**
     * @return an integer representation of the color detected in 32-bit ARGB
     */
    public int getARGB() {
        if (active){
            return Color.argb(sensor.alpha(), sensor.red(), sensor.green(), sensor.blue());
        }else {
            throw new IllegalStateException("Color sensor is not activated!");
        }
    }
}
