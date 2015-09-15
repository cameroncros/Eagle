package com.au.sparrow;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import java.lang.Thread;
import dji.sdk.api.DJIDrone;
import dji.sdk.api.DJIDroneTypeDef;
import dji.sdk.api.MainController.DJIMainControllerSystemState;
import dji.sdk.api.MainController.DJIPhantomMainController;
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
    private DJIMcuUpdateStateCallBack mMcuUpdateStateCallBack = null;
    private DJIDrone drone = new DJIDrone();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMcuUpdateStateCallBack = new DJIMcuUpdateStateCallBack(){
            public void onResult(DJIMainControllerSystemState state) {
                Log.e(TAG, "DJIMainControllerSystemState = " + state);
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
                            //Log.e(TAG, "onGetPermissionResult = " + result);
                            if (result==0)
                            permission = true;
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

    public void GroundStationGo(){
        drone.getDjiGroundStation().openGroundStation(new DJIGroundStationExecutCallBack() {
            @Override
            public void onResult(GroundStationResult result) {
                Log.e(TAG, "openGroundStation = " + result);
            }
        });
        //Came back successful
    }

    public void MControllerGo(){
        //DJIDrone.getDjiMC().
        //Log.e(TAG, "DJIDrone.getDjiMC.getClass = " + DJIDrone.getDjiMC().getClass()); //class dji.sdk.api.MainController.DJIPhantomMainController

        drone.getDjiMC().startUpdateTimer(1000);
        drone.getDjiMC().setMcuUpdateStateCallBack(mMcuUpdateStateCallBack);

        if (drone.getDjiMC().getClass().equals(DJIPhantomMainController.class)) Log.e(TAG, "DJIPhantomMainController");

        Log.e(TAG, "Uselessly hanging out here.......");
    }
}