package eagle.navigation.positioning;

/**
 * Created by Cameron on 4/09/2015.
 */
public class PositionRelative extends Position {
    public PositionRelative(double longitude, double latitude, double altitude, double roll, double pitch, Bearing yaw) {
        super(longitude, latitude, altitude, roll, pitch, yaw);
    }

    //No logical reason for add(PositionGPS) doesnt make sense, reverse is not true though

    public PositionRelative add(PositionRelative position) {
        return new PositionRelative(
                this.getLongitude() + position.getLongitude(),
                this.getLatitude() + position.getLatitude(),
                this.getAltitude() + position.getAltitude(),
                this.getRoll(),
                this.getPitch(),
                this.yaw.add(position.getYaw()));
    }

    //Unsure of the semantic meaning of this, probably not needed?
    public PositionBasic add(PositionBasic position) {
        return new PositionBasic(
                this.getLongitude() + position.getLongitude(),
                this.getLatitude() + position.getLatitude(),
                this.getAltitude() + position.getAltitude(),
                this.getRoll(),
                this.getPitch(),
                this.yaw.add(position.getYaw()));
    }

    //compare two relative positions
    public PositionRelative minus(PositionRelative position) {
        return new PositionRelative(
                this.getLongitude() - position.getLongitude(),
                this.getLatitude() - position.getLatitude(),
                this.getAltitude() - position.getAltitude(),
                this.getRoll(),
                this.getPitch(),
                this.yaw.add(position.getYaw()));
    }

    //really unsure of this one as well
//    public void minus(PositionBasic position) {
//        this.longitude -= position.getLongitude();
//        this.latitude -= position.getLatitude();
//        this.altitude -= position.getAltitude();
//        this.yaw.minus(position.getYaw());
//    }

}
