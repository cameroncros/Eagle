package com.au.sparrow;

import dji.sdk.api.DJIDrone;
import dji.sdk.api.MainController.DJIMainControllerSystemState;
import dji.sdk.interfaces.DJIMcuUpdateStateCallBack;
import android.util.Log;

/**
 * Created by lara on 8/09/2015.
 */
public class callback extends Thread implements DJIMcuUpdateStateCallBack {
    private static final String TAG = "PhantomII callback";
    public static final callback INSTANCE = new callback();
    @Override
    public void onResult(DJIMainControllerSystemState systemState){
        Log.e(TAG, "DJIMainControllerSystemState = "+systemState.toString());
    }

    @Override
    public void run(){
        Log.e(TAG, "callback.run()");
        DJIDrone.getDjiGroundStation().startUpdateTimer(1000);
        DJIDrone.getDjiMC().startUpdateTimer(1000);
    }
}
