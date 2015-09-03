package eagle.navigation.positioning;

/**
 * Created by Cameron on 4/09/2015.
 */
// Difference between two positions
public class RelativePosition extends Position {
    public RelativePosition(double longitude, double latitude, double altitude, double roll, double pitch, Bearing yaw) {
        super(longitude, latitude, altitude, roll, pitch, yaw);
    }

    @Override
    public String toString(){
        return "RelativePosition: "+getLongitude()+" "+getLatitude()+" "+getAltitude()+" "+getRoll()+" "+getPitch()+" "+getYaw();
    }

    public String toPrettyString(){
        return "RelativePosition: "+"Longitude: "+getLongitude()+", Latitude: "+getLatitude()+", Altitude: "+getAltitude()+", Roll: "+getRoll()+", Pitch: "+getPitch()+", Yaw: "+getYaw().toPrettyString();
    }

    //No logical reason for add(GPSPosition) doesnt make sense, reverse is not true though

    public RelativePosition add(RelativePosition position) {
        return new RelativePosition(
                this.getLongitude() + position.getLongitude(),
                this.getLatitude() + position.getLatitude(),
                this.getAltitude() + position.getAltitude(),
                this.getRoll(),
                this.getPitch(),
                this.yaw.add(position.getYaw()));
    }

    //Unsure of the semantic meaning of this, probably not needed?
    public BasicPosition add(BasicPosition position) {
        return new BasicPosition(
                this.getLongitude() + position.getLongitude(),
                this.getLatitude() + position.getLatitude(),
                this.getAltitude() + position.getAltitude(),
                this.getRoll(),
                this.getPitch(),
                this.yaw.add(position.getYaw()));
    }

    //compare two relative positions
    public RelativePosition minus(RelativePosition position) {
        return new RelativePosition(
                this.getLongitude() - position.getLongitude(),
                this.getLatitude() - position.getLatitude(),
                this.getAltitude() - position.getAltitude(),
                this.getRoll(),
                this.getPitch(),
                this.yaw.add(position.getYaw()));
    }

    //really unsure of this one as well
//    public void minus(BasicPosition position) {
//        this.longitude -= position.getLongitude();
//        this.latitude -= position.getLatitude();
//        this.altitude -= position.getAltitude();
//        this.yaw.minus(position.getYaw());
//    }

}
