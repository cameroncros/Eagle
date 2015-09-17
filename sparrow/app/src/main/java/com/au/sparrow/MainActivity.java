package com.au.sparrow;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import java.lang.Thread;
import dji.sdk.api.DJIDrone;
import dji.sdk.api.DJIDroneTypeDef;
import dji.sdk.api.GroundStation.DJIGroundStationFlyingInfo;
import dji.sdk.api.GroundStation.DJIGroundStationTask;
import dji.sdk.api.GroundStation.DJIGroundStationTypeDef;
import dji.sdk.api.GroundStation.DJIGroundStationWaypoint;
import dji.sdk.api.MainController.DJIMainControllerSystemState;
import dji.sdk.interfaces.DJIGerneralListener;
import dji.sdk.interfaces.DJIGroundStationExecutCallBack;
import dji.sdk.interfaces.DJIGroundStationFlyingInfoCallBack;
import dji.sdk.interfaces.DJIGroundStationTakeOffCallBack;
import dji.sdk.interfaces.DJIMcuUpdateStateCallBack;
import dji.sdk.interfaces.DJIReceivedVideoDataCallBack;
import dji.sdk.widget.DjiGLSurfaceView;
import dji.sdk.api.GroundStation.DJIGroundStationTypeDef.GroundStationResult;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "PhantomII";
    boolean permission = false;

    private DJIDrone drone = new DJIDrone();
    private DjiGLSurfaceView mDjiGLSurfaceView;
    private DJIReceivedVideoDataCallBack mReceivedVideoDataCallBack;
    private DJIMcuUpdateStateCallBack mMcuUpdateStateCallBack;
    private DJIGroundStationFlyingInfoCallBack mGroundStationFlyingInfoCallBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMcuUpdateStateCallBack = new DJIMcuUpdateStateCallBack(){
            public void onResult(DJIMainControllerSystemState state) {
                //Log.e(TAG, "DJIMainControllerSystemState altitude = " + state.altitude);
            }
        };
        mGroundStationFlyingInfoCallBack = new DJIGroundStationFlyingInfoCallBack() {
            @Override
            public void onResult(DJIGroundStationFlyingInfo djiGroundStationFlyingInfo) {
                //Log.e(TAG, "altitude = " + djiGroundStationFlyingInfo.altitude); //the results are rubbish
            }
        };

        //It can't do without a thread
        new Thread() {
            @Override
            public void run() {
                try {
                    //Your code goes here
                    drone.checkPermission(getApplicationContext(), new DJIGerneralListener() {
                        @Override
                        public void onGetPermissionResult(int result) {
                            //Log.e(TAG, "checkPermission = " + result);
                            if (result==0)
                            permission = true;
                            //else exit
                        }
                    });} catch (Exception e) {e.printStackTrace();}
            }
        }.start();

        while (permission!=true) {}
        onInitSDK();
    }
    private void onInitSDK() {
        //Log.e(TAG, "init with type = " + DJIDrone.initWithType(getApplicationContext(), DJIDroneTypeDef.DJIDroneType.DJIDrone_Vision));
        drone.initWithType(getApplicationContext(), DJIDroneTypeDef.DJIDroneType.DJIDrone_Vision);
        //Log.e(TAG, "GetLevel = " + DJIDrone.getLevel());
        //Log.e(TAG, "connectToDrone = " + DJIDrone.connectToDrone());
        drone.connectToDrone();
        cameraGo();
        GroundStationGo();
        MControllerGo();
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
        drone.getDjiCamera().setReceivedVideoDataCallBack(mReceivedVideoDataCallBack);
    }
    public void MControllerGo(){
        //DJIDrone.getDjiMC().
        //Log.e(TAG, "DJIDrone.getDjiMC.getClass = " + DJIDrone.getDjiMC().getClass()); //class dji.sdk.api.MainController.DJIPhantomMainController

        drone.getDjiMC().startUpdateTimer(1000);
        drone.getDjiMC().setMcuUpdateStateCallBack(mMcuUpdateStateCallBack);

        //if (drone.getDjiMC().getClass().equals(DJIPhantomMainController.class)) Log.e(TAG, "DJIPhantomMainController");


    }

    public void GroundStationGo(){
        drone.getDjiGroundStation().startUpdateTimer(1000);
        drone.getDjiGroundStation().setGroundStationFlyingInfoCallBack(mGroundStationFlyingInfoCallBack);
        drone.getDjiGroundStation().openGroundStation(new DJIGroundStationExecutCallBack() {
            @Override
            public void onResult(GroundStationResult result) {
                //Log.e(TAG, "openGroundStation = " + result);
            }
        });


        DJIGroundStationTask task1 = new DJIGroundStationTask();
        DJIGroundStationWaypoint way1 = new DJIGroundStationWaypoint(5, 5);
        way1.altitude = 5;
        task1.addWaypoint(way1);
        DJIGroundStationWaypoint way2 = new DJIGroundStationWaypoint(10, 10);
        way2.altitude = 10;
        task1.addWaypoint(way2);
        task1.addWaypoint(new DJIGroundStationWaypoint(15, 15));
        task1.addWaypoint(new DJIGroundStationWaypoint(25, 25));
        task1.addWaypoint(new DJIGroundStationWaypoint(30, 30));

        drone.getDjiGroundStation().uploadGroundStationTask(task1, new DJIGroundStationExecutCallBack() {
            @Override
            public void onResult(GroundStationResult groundStationResult) {
                Log.e(TAG, "uploadGroundStationTask = " + groundStationResult);
            }
        });

        drone.getDjiGroundStation().startGroundStationTask(new DJIGroundStationTakeOffCallBack() {
            @Override
            public void onResult(DJIGroundStationTypeDef.GroundStationTakeOffResult groundStationTakeOffResult) {
                //Log.e(TAG, "Uselessly hanging out here.......");
                Log.e(TAG, "startGroundStationTask = " + groundStationTakeOffResult);

            }
        });
    }

}