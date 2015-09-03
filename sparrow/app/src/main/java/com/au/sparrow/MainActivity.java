package com.au.sparrow;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import dji.sdk.api.DJIDrone;
import dji.sdk.api.DJIDroneTypeDef;
import dji.sdk.api.DJIError;
import dji.sdk.interfaces.DJIGerneralListener;
import java.lang.Thread;

public class MainActivity extends ActionBarActivity {
    private static final String TAG = "PhantomII";
    //DJIDrone drone = new DJIDrone();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e(TAG, "initwithtype = " + DJIDrone.initWithType(
                getApplicationContext(),
                DJIDroneTypeDef.DJIDroneType.DJIDrone_Vision));
        DJIDrone.connectToDrone();

        //It can't do without a thread
        new Thread() {
            @Override
            public void run() {
                try {
                    //Your code goes here
                    DJIDrone.checkPermission(getApplicationContext(), new DJIGerneralListener() {
                        @Override
                        public void onGetPermissionResult(int result) {
                            //Log.e(TAG, "onGetPermissionResult = " + result);
                            Log.e(TAG, "onGetPermissionResultDescription = " +
                                    DJIError.getCheckPermissionErrorDescription(result));
                            if (result == 0)
                                onInitSDK();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void onInitSDK() {
        Log.e(TAG, "GetLevel = " + DJIDrone.getLevel());
        Log.e(TAG, "Before Connecting to The Drone.");
        boolean check = false || DJIDrone.connectToDrone();
        Log.e(TAG, "After Connecting to The Drone.");
        Log.e(TAG, "connectToDrone = "+check);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        DJIDrone.disconnectToDrone();
        super.onDestroy();
    }
}

/*
        if (ConnectToDrone) {
            final long waiting = 1000000;
            controller = drone.getDjiMainController();
            controller.turnOnMotor(new DJIExecuteResultCallback() {
                @Override
                public void onResult(DJIError mErr) {
                    Log.e(TAG, "mErr = " + mErr);
                    try {
                        wait(waiting);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(TAG, "************\n\n" + e + "\n\n");
                    }
                    controller.startTakeoff(new DJIExecuteResultCallback() {
                        @Override
                        public void onResult(DJIError mErr) {
                            Log.e(TAG, "mErr = " + mErr);
                            try {
                                wait(waiting);
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e(TAG, "************\n\n" + e + "\n\n");
                            }
                        }
                    });

                }

            });

        }
 */
