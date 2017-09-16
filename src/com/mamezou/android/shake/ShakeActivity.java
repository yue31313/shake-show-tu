package com.mamezou.android.shake;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;

public class ShakeActivity extends Activity implements SensorEventListener {
  private SensorManager sensorManager;
  private ValueHolder x;
  private ValueHolder y;
  private ValueHolder z;
  private View layout;
  // なんちゃってAndroidの画像(Andy君)
  private BitmapDrawable andy;
  
  private static final int ELEMENT_COUNT = 30;
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
    layout = (View) findViewById(R.id.layout);
    andy = (BitmapDrawable) getResources().getDrawable(R.drawable.andy);
  }
  @Override
  protected void onResume() {
    super.onResume();
    sensorManager.registerListener(this,
    sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
        SensorManager.SENSOR_DELAY_FASTEST);
    x = new ValueHolder(ELEMENT_COUNT);
    y = new ValueHolder(ELEMENT_COUNT);
    z = new ValueHolder(ELEMENT_COUNT);
  }
  @Override
  protected void onStop() {
    sensorManager.unregisterListener(this);
    super.onStop();
  }
  public void onAccuracyChanged(Sensor sensor, int accuracy) {
  }
  public void onSensorChanged(SensorEvent e) {
    float sensorValueX = e.values[SensorManager.DATA_X];
    float sensorValueY = e.values[SensorManager.DATA_Y];
    float sensorValueZ = e.values[SensorManager.DATA_Z];
    x.add(sensorValueX);
    y.add(sensorValueY);
    z.add(sensorValueZ);
    float valueX = sensorValueX - x.getMedian();
    float valueY = sensorValueX - x.getMedian();
    float valueZ = sensorValueX - x.getMedian();
    andy.setAlpha(15 * (int)(Math.abs(valueX) + Math.abs(valueY) + Math.abs(valueZ)));
    layout.setBackgroundDrawable(andy);
  }
}