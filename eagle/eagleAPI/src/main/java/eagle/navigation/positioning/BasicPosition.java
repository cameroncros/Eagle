package eagle.navigation.positioning;

/**
 * Created by Cameron on 4/09/2015.
 */
// Basic position relative to the drones home position
public class BasicPosition extends Position {
    public BasicPosition(double longitude, double latitude, double altitude, double roll, double pitch, Bearing yaw) {
        super(longitude, latitude, altitude, roll, pitch, yaw);
    }

    @Override
    public String toString(){
        return "BasicPosition: "+getLongitude()+" "+getLatitude()+" "+getAltitude()+" "+getRoll()+" "+getPitch()+" "+getYaw();
    }

    public String toPrettyString(){
        return "BasicPosition: "+"Longitude: "+getLongitude()+", Latitude: "+getLatitude()+", Altitude: "+getAltitude()+", Roll: "+getRoll()+", Pitch: "+getPitch()+", Yaw: "+getYaw().toPrettyString();
    }

    //No logical reason for add(GPSPosition) doesnt make sense

    //add a BasicPosition offset
    public BasicPosition add(RelativePosition position) {
        return new BasicPosition(
                this.getLongitude() + position.getLongitude(),
                this.getLatitude() + position.getLatitude(),
                this.getAltitude() + position.getAltitude(),
                this.getRoll(),
                this.getPitch(),
                this.yaw.add(position.getYaw()));
    }

    //No logical reason for this either?
    public void add(BasicPosition position) {
        this.longitude += position.getLongitude();
        this.latitude += position.getLatitude();
        this.altitude += position.getAltitude();
        this.yaw.add(position.getYaw());
    }

    //subtract a BasicPosition offset
    public BasicPosition minus(RelativePosition position) {
        return new BasicPosition(
                this.getLongitude() - position.getLongitude(),
                this.getLatitude() - position.getLatitude(),
                this.getAltitude() - position.getAltitude(),
                this.getRoll(),
                this.getPitch(),
                this.yaw.minus(position.getYaw()));
    }

    //get the difference between to BasicPositions, which should return a RelativePosition?
    public RelativePosition minus(BasicPosition position) {
        return new RelativePosition(
                this.longitude - position.getLongitude(),
                this.latitude - position.getLatitude(),
                this.altitude - position.getAltitude(),
                this.getRoll(),
                this.getPitch(),
                this.yaw.minus(position.getYaw()));
    }
}
