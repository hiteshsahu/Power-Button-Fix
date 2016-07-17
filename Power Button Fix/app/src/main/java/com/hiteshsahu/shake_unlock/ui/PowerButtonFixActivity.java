package com.hiteshsahu.shake_unlock.ui;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hiteshsahu.myapplication.R;
import com.hiteshsahu.shake_unlock.unlock.ShakeDetectionService;
import com.hiteshsahu.shake_unlock.unlock.UnlockDeviceAdmin;
import com.hiteshsahu.shake_unlock.helper.PreferenceHelper;

public class PowerButtonFixActivity extends AppCompatActivity {

    static final int RESULT_ENABLE = 1;
    private DevicePolicyManager deviceManger;
    private ActivityManager activityManager;
    private ComponentName compName;
    private SwitchCompat switchCompat;
    private FloatingActionButton fab;
    private SeekBar proress;
    private CoordinatorLayout root;
    private TextView sensitivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deviceManger = (DevicePolicyManager) getSystemService(
                Context.DEVICE_POLICY_SERVICE);
        activityManager = (ActivityManager) getSystemService(
                Context.ACTIVITY_SERVICE);
        compName = new ComponentName(this, UnlockDeviceAdmin.class);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        switchCompat = (SwitchCompat) findViewById(R.id.tc_switch);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        proress = (SeekBar) findViewById(R.id.seekBar);
        root = (CoordinatorLayout) findViewById(R.id.root);
        sensitivity = (TextView) findViewById(R.id.sensitiy_value);

        proress.setProgress(PreferenceHelper.getPrefernceHelperInstace().getInteger(getApplicationContext(), "SENSITIVITY", 13));

        sensitivity.setText(String.valueOf(proress.getProgress()));


        switchCompat.setChecked(deviceManger.isAdminActive(compName));
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {

                    Intent intent = new Intent(DevicePolicyManager
                            .ACTION_ADD_DEVICE_ADMIN);
                    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
                            compName);
                    intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                            "Additional text explaining why this needs to be added.");
                    startActivityForResult(intent, RESULT_ENABLE);

                } else {

                    deviceManger.removeActiveAdmin(compName);

                }
            }
        });

        proress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                sensitivity.setText("" + i);
                PreferenceHelper.getPrefernceHelperInstace().setInteger(getApplicationContext(), "SENSITIVITY", 13);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        startService(new Intent(this, ShakeDetectionService.class));


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (deviceManger.isAdminActive(compName)) {
                    deviceManger.lockNow();
                } else {
                    Snackbar.make(view, "Enable Device Admin first", Snackbar.LENGTH_LONG)
                            .setAction("Lock", null).show();
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RESULT_ENABLE:
                if (resultCode == Activity.RESULT_OK) {
                    Snackbar.make(root, "Admin enabled", Snackbar.LENGTH_LONG)
                            .setAction("Lock", null).show();
                } else {

                    Snackbar.make(root, "Admin enable FAILED!", Snackbar.LENGTH_LONG)
                            .setAction("Lock", null).show();
                }
                return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
