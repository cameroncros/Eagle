package eagle.sdkInterface.sdkAdaptors.Flyver;

import eagle.navigation.positioning.BasicPosition;
import eagle.navigation.positioning.Bearing;
import eagle.navigation.positioning.GPSPosition;
import eagle.navigation.positioning.Position;
import eagle.navigation.positioning.RelativePosition;
import eagle.sdkInterface.AdaptorLoader;
import eagle.sdkInterface.SDKAdaptor;

/** F450Flamewheel SDKAdaptor
 * @since     09/04/2015
 * <p>
 * Date Modified	26/05/2015 - Nicholas
 * @version 0.0.1
 * @author          Nicholas Alards [7178301@student.swin.edu.au]
 * @author          Cameron Cross [7193432@student.swin.edu.au]*/
public class F450Flamewheel extends SDKAdaptor {
    public static String adapterVersion;

    //TODO Create method implementations

    public F450Flamewheel(){
        super("FLyver","F450 Flamewheel","alpha","0.0.1");
        setHomePosition(new BasicPosition(0,0,0,0,0,new Bearing(0)));
        setPositionAssigned(new BasicPosition(0, 0, 0, 0, 0, new Bearing(0)));
    }
    public void loadDefaultSensorAdaptors(AdaptorLoader adaptorLoader){
        addSensorAdaptorAccelerometer(adaptorLoader.getSensorAdaptorAccelerometer("AndroidAccelerometer"));
        addSensorAdaptorAltimeter(adaptorLoader.getSensorAdaptorAltimeter("AndroidAltimeter"));
        addSensorAdaptorCamera(adaptorLoader.getSensorAdaptorCamera("AndroidCamera"));
        addSensorAdaptorCompass(adaptorLoader.getSensorAdaptorCompass("AndroidCompass"));
        addSensorAdaptorGyroscope(adaptorLoader.getSensorAdaptorGyroscope("AndroidGyroscope"));
    }

    public boolean connectToDrone(){return false;}
    public boolean disconnectFromDrone(){return false;}
    public boolean isConnectedToDrone(){return false;}

    public boolean standbyDrone(){return false;}
    public boolean resumeDrone(){return false;}
    public boolean shutdownDrone(){return false;}

    @Override
    public boolean flyTo(RelativePosition position, double speed) {
        return false;
    }

    @Override
    public boolean flyTo(RelativePosition position) {
        return false;
    }

    @Override
    public boolean flyTo(GPSPosition position, double speed){return false;}

    @Override
    public boolean flyTo(GPSPosition position){return false;}

    @Override
    public boolean flyTo(BasicPosition position, double speed){return false;}

    @Override
    public boolean flyTo(BasicPosition position){return false;}

    public Position getPositionInFlight(){
        //TODO CREATE BELOW IMPLEMENTATION
        return new BasicPosition(0,0,0,0,0,new Bearing(0));
    }

    @Override
    public void delay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void updateCurrentPosition(){}


}