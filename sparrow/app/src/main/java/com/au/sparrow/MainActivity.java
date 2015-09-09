package com.au.sparrow;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.lang.Thread;

import dji.sdk.api.DJIDrone;
import dji.sdk.api.DJIDroneTypeDef;
import dji.sdk.api.DJIError;
import dji.sdk.api.GroundStation.DJIGroundStationTypeDef;
import dji.sdk.interfaces.DJIExecuteResultCallback;
import dji.sdk.interfaces.DJIGerneralListener;
import dji.sdk.interfaces.DJIGroundStationExecutCallBack;
import dji.sdk.interfaces.DJIGroundStationTakeOffCallBack;
import dji.sdk.interfaces.DJIReceivedVideoDataCallBack;
import dji.sdk.widget.DjiGLSurfaceView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "PhantomII";

    private DJIReceivedVideoDataCallBack mReceivedVideoDataCallBack = null;
    private DjiGLSurfaceView mDjiGLSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //It can't do without a thread\
                try

                {
                    //Your code goes here
                    DJIDrone.checkPermission(getApplicationContext(), new DJIGerneralListener() {
                        @Override
                        public void onGetPermissionResult(int result) {
                            Log.e(TAG, "onGetPermissionResult = " + result);

                            //Log.e(TAG, "onGetPermissionResultDescription = " +DJIError.getCheckPermissionErrorDescription(result));
                        }
                    });
                } catch (
                        Exception e
                        )

                {
                    e.printStackTrace();
                }

        onInitSDK();
    }

    private void onInitSDK() {
        Log.e(TAG, "initwithtype = " + DJIDrone.initWithType(getApplicationContext(), DJIDroneTypeDef.DJIDroneType.DJIDrone_Vision));

        Log.e(TAG, "GetLevel = " + DJIDrone.getLevel());
        //Log.e(TAG, "Before Connecting to The Drone.");
        boolean check = DJIDrone.connectToDrone();
        //Log.e(TAG, "After Connecting to The Drone.");
        Log.e(TAG, "connectToDrone = " + check);


        mDjiGLSurfaceView = (DjiGLSurfaceView)findViewById(R.id.DjiSurfaceView_02);
        mDjiGLSurfaceView.start();

        mReceivedVideoDataCallBack = new DJIReceivedVideoDataCallBack(){
            @Override
            public void onResult(byte[] videoBuffer, int size){
                mDjiGLSurfaceView.setDataToDecoder(videoBuffer, size);
            }
        };
        DJIDrone.getDjiCamera().setReceivedVideoDataCallBack(mReceivedVideoDataCallBack);

        DJIDrone.getDjiGroundStation().openGroundStation(new DJIGroundStationExecutCallBack() {
            @Override
            public void onResult(DJIGroundStationTypeDef.GroundStationResult groundStationResult) {
                Log.e(TAG, groundStationResult.toString());
            }
        });
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        DJIDrone.disconnectToDrone();
        super.onDestroy();
    }
}
    /*
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