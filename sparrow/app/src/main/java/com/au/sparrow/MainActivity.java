package com.au.sparrow;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import dji.sdk.api.DJIDrone;
import dji.sdk.api.DJIDroneTypeDef;
import dji.sdk.api.DJIError;
import dji.sdk.api.MainController.DJIMainControllerSystemState;
import dji.sdk.interfaces.DJIExecuteResultCallback;
import dji.sdk.interfaces.DJIGerneralListener;
import dji.sdk.api.MainController.DJIMainController;
import dji.sdk.interfaces.DJIMcuUpdateStateCallBack;

import java.lang.Thread;

public class MainActivity extends ActionBarActivity {
    private static final String TAG = "PhantomII";
    DJIMainController controller = new DJIMainController();
    DJIDrone drone = new DJIDrone();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e(TAG, "initwithtype = " + drone.initWithType(
                getApplicationContext(),
                DJIDroneTypeDef.DJIDroneType.DJIDrone_Vision));
        drone.connectToDrone();

        //It can't do without a thread
        new Thread() {
            @Override
            public void run() {
                try {
                    //Your code goes here
                    drone.checkPermission(getApplicationContext(), new DJIGerneralListener() {
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
        Log.e(TAG, "GetLevel = " + drone.getLevel());
        //Log.e(TAG, "Before Connecting to The Drone.");
        boolean check = drone.connectToDrone();
        //Log.e(TAG, "After Connecting to The Drone.");
        Log.e(TAG, "connectToDrone = "+check);
        fly(check);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        drone.disconnectToDrone();
        super.onDestroy();
    }
    protected void fly(boolean ConnectToDrone){

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

    }
}