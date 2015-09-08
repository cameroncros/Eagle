package com.au.sparrow;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import dji.sdk.api.DJIDrone;
import dji.sdk.api.DJIDroneTypeDef;
import dji.sdk.api.DJIError;
import dji.sdk.api.MainController.DJIPhantomMainController;
import dji.sdk.interfaces.DJIExecuteResultCallback;
import dji.sdk.interfaces.DJIGerneralListener;
import dji.sdk.interfaces.DJIMcuUpdateStateCallBack;

import java.lang.Thread;

public class MainActivity extends ActionBarActivity {
    private static final String TAG = "PhantomII";
    DJIDrone drone = new DJIDrone();
    final long waiting = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e(TAG, "initwithtype = " + drone.initWithType(
                getApplicationContext(),
                DJIDroneTypeDef.DJIDroneType.DJIDrone_Vision));

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
        Log.e(TAG, "connectToDrone = " + check);
        if (check) fly();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        drone.disconnectToDrone();
        super.onDestroy();
    }
    
    protected void fly() {
        callback.INSTANCE.start();
        drone.getDjiMC().turnOnMotor(new DJIExecuteResultCallback() {
            @Override
            public void onResult(DJIError mErr) {
                Log.e(TAG, "mErr = " + mErr.errorDescription);
            }
        });
        }

    public void takeoff(){
        final long waiting = 1000000;
        drone.getDjiMC().startTakeoff(new DJIExecuteResultCallback() {
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
}

/*
drone.getDjiGroundStation().openGroundStation(new DJIGroundStationExecuteCallBack() {
            @Override
            public void onResult(DJIGroundStationTypeDef.GroundStationResult groundStationResult) {
                if (groundStationResult == DJIGroundStationTypeDef.GroundStationResult.GS_Result_Success) {
                    Log.e(TAG, "groundStationResult = " + groundStationResult.toString());
                }
            }
        });
 */

/*
drone.getDjiGroundStation().sendFlightControlData(0, 0, 0, 1, new DJIExecuteResultCallback() {
                    @Override
                    public void onResult(DJIError djiError) {
                        Log.e(TAG, "djiError = " + djiError.errorDescription);
                        try {
                            wait(waiting);
                            //takeoff();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e(TAG, "************\n\n" + e + "\n\n");
                        }
                    }
                }
        );
 */

/*
        drone.getDjiBattery().startUpdateTimer(10000);
        drone.getDjiBattery().getPartVoltages(new DJIBatteryGetPartVoltageCallBack() {
            @Override
            public void onResult(int[] ints) {
                Log.e(TAG, "ints = " + ints);

            }
        });

        drone.getDjiBattery().getBatteryConnectionStatus(new DJISmartBatteryExecuteResultCallback() {
            @Override
            public void onResult(double v, DJIError djiError) {
                Log.e(TAG, "djiError = " + djiError.errorDescription);

            }
        });
 */