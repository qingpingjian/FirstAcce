package com.nku.netlab.pete.firstacce;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener{
    public static final String TAG = "MainActivity";
    private SensorManager m_sensorManager;
    private Sensor m_acceSensor;
    private TextView m_tvAcceX;
    private TextView m_tvAcceY;
    private TextView m_tvAcceZ;
    private TextView m_tvMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        m_sensorManager = (SensorManager) getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        m_tvAcceX = findViewById(R.id.tvAXValue);
        m_tvAcceY = findViewById(R.id.tvAYValue);
        m_tvAcceZ = findViewById(R.id.tvAZValue);
        m_tvMessage = findViewById(R.id.tvNoSensorMessage);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (m_sensorManager == null)
            m_sensorManager = (SensorManager) getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        m_acceSensor = m_sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (m_acceSensor != null) {
            m_sensorManager.registerListener(this, m_acceSensor, SensorManager.SENSOR_DELAY_UI);
        }
        else {
            m_sensorManager = null;
            m_tvMessage.setText(R.string.tv_no_sensor);
        }
    }

    @Override
    protected void onStop() {
        if (m_sensorManager != null) {
            m_sensorManager.unregisterListener(this);
        }
        super.onStop();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            m_tvAcceX.setText(String.format("%.5f", sensorEvent.values[0]));
            m_tvAcceY.setText(String.format("%.5f", sensorEvent.values[1]));
            m_tvAcceZ.setText(String.format("%.5f", sensorEvent.values[2]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
