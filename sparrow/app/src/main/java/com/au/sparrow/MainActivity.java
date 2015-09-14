package com.au.sparrow;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import java.lang.Thread;
import dji.sdk.api.DJIDrone;
import dji.sdk.api.DJIDroneTypeDef;
import dji.sdk.api.GroundStation.DJIGroundStation;
import dji.sdk.api.GroundStation.DJIGroundStationTask;
import dji.sdk.api.GroundStation.DJIGroundStationWaypoint;
import dji.sdk.api.MainController.DJIMainControllerSystemState;
import dji.sdk.api.MainController.DJIPhantomMainController;
import dji.sdk.interfaces.DJIExecuteBooleanResultCallback;
import dji.sdk.interfaces.DJIExecuteFloatResultCallback;
import dji.sdk.interfaces.DJIGerneralListener;
import dji.sdk.interfaces.DJIGroundStationExecutCallBack;
import dji.sdk.interfaces.DJIMcuUpdateStateCallBack;
import dji.sdk.interfaces.DJIReceivedVideoDataCallBack;
import dji.sdk.widget.DjiGLSurfaceView;
import dji.sdk.api.GroundStation.DJIGroundStationTypeDef.GroundStationResult;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "PhantomII";
    boolean permission = false;

    private DJIReceivedVideoDataCallBack mReceivedVideoDataCallBack = null;
    private DjiGLSurfaceView mDjiGLSurfaceView;
    DJIPhantomMainController mc = new DJIPhantomMainController();
    DJIGroundStation gs = new DJIGroundStation();
    private DJIMcuUpdateStateCallBack mMcuUpdateStateCallBack = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //This doesn't currently do callback
        mMcuUpdateStateCallBack = new DJIMcuUpdateStateCallBack(){
            public void onResult(DJIMainControllerSystemState state) {
                Log.e(TAG,"DJIMainControllerSystemState = "+state);
            }
        };

        //It can't do without a thread
        new Thread() {
            @Override
            public void run() {
                try {
                    //Your code goes here
                    DJIDrone.checkPermission(getApplicationContext(), new DJIGerneralListener() {
                        @Override
                        public void onGetPermissionResult(int result) {
                            Log.e(TAG, "onGetPermissionResult = " + result);
                            if (result==0)
                            permission = true;
                        }
                    });} catch (Exception e) {e.printStackTrace();}
            }
        }.start();

        while (permission!=true)
        {

        }
        onInitSDK();
    }

    private void onInitSDK() {
        Log.e(TAG, "init with type = " + DJIDrone.initWithType(getApplicationContext(), DJIDroneTypeDef.DJIDroneType.DJIDrone_Vision));
        Log.e(TAG, "GetLevel = " + DJIDrone.getLevel());
        Log.e(TAG, "connectToDrone = " + DJIDrone.connectToDrone());
        cameraGo();
        GroundStationGo();
    }

    public void cameraGo(){
        mDjiGLSurfaceView = (DjiGLSurfaceView)findViewById(R.id.DjiSurfaceView_02);
        mDjiGLSurfaceView.start();

        mReceivedVideoDataCallBack = new DJIReceivedVideoDataCallBack(){
            @Override
            public void onResult(byte[] videoBuffer, int size){
                mDjiGLSurfaceView.setDataToDecoder(videoBuffer, size);
            }
        };
        DJIDrone.getDjiCamera().setReceivedVideoDataCallBack(mReceivedVideoDataCallBack);
    }

    public void GroundStationGo(){
        mc.startUpdateTimer(1000);
        gs.startUpdateTimer(1000);

        mc.getGohomeAltitude(new DJIExecuteFloatResultCallback() {
            @Override
            public void onResult(float v) {
                Log.e(TAG, "get Go home Altitude = " + v);
            }
        });

        mc.getSmartBatteryGohomeFlag(new DJIExecuteBooleanResultCallback() {
            @Override
            public void onResult(boolean b) {
                Log.e(TAG, "getSmartBatteryGohomeFlag = " + b);
            }
        });

        //This doesn't come back with any result either
        Log.e(TAG, "getMcuVersion = " + mc.getMcuVersion());

        //This doesn't come back with any result either
        gs.openGroundStation(new DJIGroundStationExecutCallBack() {
            @Override
            public void onResult(GroundStationResult result) {
                Log.e(TAG, "Uselessly hanging out here.");
                Log.e(TAG, "openGroundStation = " + result);
            }
        });

        //DJIGroundStationWaypoint waypoint = new DJIGroundStationWaypoint(); //lat, long : dounbles
        DJIGroundStationTask task1 = new DJIGroundStationTask();
        //DJIDrone.getDjiCamera().setReceivedVideoDataCallBack(mReceivedVideoDataCallBack);
        DJIDrone.getDjiGroundStation().openGroundStation(new DJIGroundStationExecutCallBack() {
            @Override
            public void onResult(GroundStationResult result) {
                Log.e(TAG, "Uselessly hanging out here.");
                Log.e(TAG, "openGroundStation = " + result);
            }
        });
        //Came back successful

    }
}