package eagle.navigation.positioning;

/**
 * Created by Cameron on 4/09/2015.
 */
//Position based on GPS Position
public class GPSPosition extends Position {
    public GPSPosition(double longitude, double latitude, double altitude, double roll, double pitch, Bearing yaw) {
        super(longitude, latitude, altitude, roll, pitch, yaw);
    }

    @Override
    public String toString(){
        return "GPSPosition: "+getLongitude()+" "+getLatitude()+" "+getAltitude()+" "+getRoll()+" "+getPitch()+" "+getYaw();
    }

    public String toPrettyString(){
        return "GPSPosition: "+"Longitude: "+getLongitude()+", Latitude: "+getLatitude()+", Altitude: "+getAltitude()+", Roll: "+getRoll()+", Pitch: "+getPitch()+", Yaw: "+getYaw().toPrettyString();
    }

    //add an offset to the GPSPosition
    public GPSPosition add(RelativePosition position) {
        //need to do GPS conversions, good luck whoever implements this...
        return null;
    }

    //minus an offset to the GPSPosition
    public GPSPosition minus(RelativePosition position) {
        //need to do GPS conversions, good luck whoever implements this...
        return null;
    }

    //get the differences between two GPS positions
    public RelativePosition minus(GPSPosition position) {
        //need to do GPS conversions, good luck whoever implements this...
        return null;
    }
}
