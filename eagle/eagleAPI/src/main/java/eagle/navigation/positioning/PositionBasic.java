package eagle.navigation.positioning;

/**
 * Created by Cameron on 4/09/2015.
 */
// Basic position relative to the drones home position
public class PositionBasic extends Position {
    public PositionBasic(double longitude, double latitude, double altitude, double roll, double pitch, Bearing yaw) {
        super(longitude, latitude, altitude, roll, pitch, yaw);
    }

    //No logical reason for add(PositionGPS) doesnt make sense

    //add a PositionBasic offset
    public PositionBasic add(PositionRelative position) {
        return new PositionBasic(
                this.getLongitude() + position.getLongitude(),
                this.getLatitude() + position.getLatitude(),
                this.getAltitude() + position.getAltitude(),
                this.getRoll(),
                this.getPitch(),
                this.yaw.add(position.getYaw()));
    }

    //No logical reason for this either?
    public void add(PositionBasic position) {
        this.longitude += position.getLongitude();
        this.latitude += position.getLatitude();
        this.altitude += position.getAltitude();
        this.yaw.add(position.getYaw());
    }

    //subtract a PositionBasic offset
    public PositionBasic minus(PositionRelative position) {
        return new PositionBasic(
                this.getLongitude() - position.getLongitude(),
                this.getLatitude() - position.getLatitude(),
                this.getAltitude() - position.getAltitude(),
                this.getRoll(),
                this.getPitch(),
                this.yaw.minus(position.getYaw()));
    }

    //get the difference between to BasicPositions, which should return a PositionRelative?
    public PositionRelative minus(PositionBasic position) {
        return new PositionRelative(
                this.longitude - position.getLongitude(),
                this.latitude - position.getLatitude(),
                this.altitude - position.getAltitude(),
                this.getRoll(),
                this.getPitch(),
                this.yaw.minus(position.getYaw()));
    }
}
