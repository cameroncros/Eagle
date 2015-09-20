package eagle.navigation.positioning;

import org.apache.commons.lang3.builder.HashCodeBuilder;

/** Bearing
 * @since     06/09/2015
 * <p>
 * Date Modified	06/09/2015 - Nicholas
 * @version 0.0.1
 * @author          Nicholas Alards [7178301@student.swin.edu.au]
 */
public class PositionGPS extends Position {
    private static double r_earth = 6378 * 1000; //Work in meters for everything

    public PositionGPS(double latitude, double longitude, double altitude, Angle yaw) {
        super(latitude, longitude, altitude, yaw);
    }

    public PositionGPS(double latitude, double longitude, double altitude, Angle roll, Angle pitch, Angle yaw) {
        super(latitude, longitude, altitude, roll, pitch, yaw);
    }


    public PositionGPS(PositionGPS positionGPS){
        super(positionGPS.getLatitude(), positionGPS.getLongitude(), positionGPS.getAltitude(), positionGPS.getRoll(), positionGPS.getPitch(), positionGPS.getYaw());
    }
    public PositionGPS(Position position){
        super(position.getLatitude(), position.getLongitude(), position.getAltitude(), position.getRoll(), position.getPitch(), position.getYaw());
    }

    @Override
    public Position add(PositionDisplacement position) {
        //code taken from org.apache.sis:sis-core:0.2-incubating: https://github.com/apache/sis/blob/trunk/core/sis-referencing/src/main/java/org/apache/sis/distance/DistanceUtils.java
        double bearing=0;
        if(position.getLongitude()>0)
            bearing = 90 - Math.toDegrees(Math.atan((position.getLatitude())/ (position.getLongitude())));
        else if (position.getLongitude()<0)
            bearing = 270 - Math.toDegrees(Math.atan((position.getLatitude())/ (position.getLongitude())));
        else if (position.getLatitude()<0)
            bearing = 180;
        double dist = Math.sqrt(position.getLongitude() * position.getLongitude() + position.getLatitude() * position.getLatitude());
        double latitudeRadians = Math.toRadians(latitude);
        double longitudeRadians = Math.toRadians(longitude);
        double angularDistance = dist/6371000;
        double bearingRadians = Math.toRadians(bearing);

        return new PositionGPS(
            Math.toDegrees(Math.asin((Math.sin(latitudeRadians)*Math.cos(angularDistance))+
                (Math.cos(latitudeRadians)*Math.sin(angularDistance)*Math.cos(bearingRadians)))),
            Math.toDegrees(longitudeRadians + Math.atan2(Math.sin(bearingRadians) * Math.sin(angularDistance) * Math.cos(latitudeRadians),
                    Math.cos(angularDistance) - Math.sin(latitudeRadians) * Math.sin(Math.toRadians(latitude)))),

            altitude+position.getAltitude(),
            roll.add(position.getRoll()),
            pitch.add(position.getPitch()),
            yaw.add(position.getYaw())
        );
    }

    public PositionDisplacement compare(PositionGPS position) {
        //code taken from org.apache.sis:sis-core:0.2-incubating: https://github.com/apache/sis/blob/trunk/core/sis-referencing/src/main/java/org/apache/sis/distance/DistanceUtils.java
        double latitudeRadians = Math.toRadians(latitude);
        double longitudeRadians = Math.toRadians(longitude);
        double latitude2Radians = Math.toRadians(position.getLatitude());
        double longitude2Radians = Math.toRadians(position.getLongitude());

        double latitudeDisplacement = 6371000*Math.acos(Math.sin(latitudeRadians)*Math.sin(latitude2Radians)+Math.cos(latitudeRadians)*Math.cos(latitude2Radians)*Math.cos(longitudeRadians-longitudeRadians));
        double longitudeDisplacement = 6371000*Math.acos(Math.sin(latitudeRadians)*Math.sin(latitudeRadians)+Math.cos(latitudeRadians)*Math.cos(latitudeRadians)*Math.cos(longitudeRadians-longitude2Radians));

        if (latitude < position.getLatitude()) {
            latitudeDisplacement = -latitudeDisplacement;
        }
        if (longitude < position.getLongitude() && Math.abs(longitude - position.getLongitude()) < 180) {
            longitudeDisplacement = -longitudeDisplacement;
        }

        return new PositionDisplacement(latitudeDisplacement,
                longitudeDisplacement,
                getAltitude()-position.getAltitude(),
                getRoll().compare(position.getRoll()),
                getPitch().compare(position.getPitch()),
                getYaw().compare(position.getYaw()));
    }

    @Override
    public Position copy() {
        return new PositionGPS(latitude, longitude, altitude, roll.copy(), pitch.copy(), yaw.copy());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PositionGPS))
            return false;
        if (obj == this)
            return true;

        PositionGPS position = (PositionGPS)obj;
        if(Double.compare(longitude,position.getLongitude())==0&&
                Double.compare(latitude,position.getLatitude())==0&&
                Double.compare(altitude,position.getAltitude())==0&&
                roll.equals(position.getRoll())&&
                pitch.equals(position.getPitch())&&
                yaw.equals(position.getYaw()))
            return true;
        else
            return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31) // two randomly chosen prime numbers
                .appendSuper(super.hashCode())
                .append(longitude)
                .append(latitude)
                .append(altitude)
                .append(roll)
                .append(pitch)
                .append(yaw)
                .toHashCode();
    }
}
