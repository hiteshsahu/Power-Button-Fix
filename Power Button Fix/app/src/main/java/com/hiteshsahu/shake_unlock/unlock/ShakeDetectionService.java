package com.hiteshsahu.shake_unlock.unlock;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.PowerManager;
import android.widget.Toast;

import com.hiteshsahu.shake_unlock.helper.PreferenceHelper;
import com.hiteshsahu.shake_unlock.helper.Shake;

public class ShakeDetectionService extends Service implements Shake.Listener {

    @Override
    public void onCreate() {
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Shake sd = new Shake(this);
        sd.setAccelerationThreshold(PreferenceHelper.getPrefernceHelperInstace().getInteger(getApplicationContext(), "SENSITIVITY", 13));
        sd.start(sensorManager);
    }

    @Override
    public void hearShake() {
        Toast.makeText(getApplicationContext(), "Shaked", Toast.LENGTH_LONG).show();

        unlock(getApplicationContext());
    }

    public void onSensorChanged(SensorEvent event) {
        // TODO Auto-generated method stub

    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

    }

    public void unlock(Context context) {

        KeyguardManager.KeyguardLock lock = ((KeyguardManager) getSystemService(Activity.KEYGUARD_SERVICE)).newKeyguardLock(KEYGUARD_SERVICE);
        PowerManager powerManager = ((PowerManager) getSystemService(Context.POWER_SERVICE));
        PowerManager.WakeLock wake = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");


        lock.disableKeyguard();
        wake.acquire();
//        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
//                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
//                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
//                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
//                | WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}