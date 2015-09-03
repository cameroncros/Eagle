package eagle.navigation.positioning;

/**
 * Created by Cameron on 4/09/2015.
 */
//Position based on GPS Position
public class PositionGPS extends Position {
    public PositionGPS(double longitude, double latitude, double altitude, double roll, double pitch, Bearing yaw) {
        super(longitude, latitude, altitude, roll, pitch, yaw);
    }

    //add an offset to the PositionGPS
    public PositionGPS add(PositionRelative position) {
        //need to do GPS conversions, good luck whoever implements this...
        return null;
    }

    //minus an offset to the PositionGPS
    public PositionGPS minus(PositionBasic position) {
        //need to do GPS conversions, good luck whoever implements this...
        return null;
    }

    //get the differences between two GPS positions
    public PositionRelative minus(PositionGPS position) {
        //need to do GPS conversions, good luck whoever implements this...
        return null;
    }
}
