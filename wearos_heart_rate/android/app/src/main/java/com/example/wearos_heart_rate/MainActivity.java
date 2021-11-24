package com.example.wearos_heart_rate;

import io.flutter.embedding.android.FlutterActivity;
import androidx.annotation.NonNull;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodChannel;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.content.Context;
import io.flutter.plugins.GeneratedPluginRegistrant;
import android.Manifest;

public class MainActivity extends FlutterActivity implements SensorEventListener {
    private static final String CHANNEL = "flutter.dev/heart_rate";
    private SensorManager mSensorManager;
    private Sensor mHeartRateSensor;
    private int heart_rate;
    private int counter;


    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        counter = 0;
        super.configureFlutterEngine(flutterEngine);
        new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL)
            .setMethodCallHandler(
            (call, result) -> {
                    if (call.method.equals("getHeartRate")){

                        startMeasure();
                        result.success(String.valueOf(heart_rate));
                        
                    }
                }
            );
    }

    private void startMeasure() {
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mHeartRateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        boolean sensorRegistered = mSensorManager.registerListener(this, mHeartRateSensor, SensorManager.SENSOR_DELAY_FASTEST);
        Log.d("Sensor Status:", " Sensor registered: " + (sensorRegistered ? "yes" : "no"));
    }

    private void stopMeasure() {
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float mHeartRateFloat = event.values[0];
        heart_rate = Math.round(mHeartRateFloat);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
