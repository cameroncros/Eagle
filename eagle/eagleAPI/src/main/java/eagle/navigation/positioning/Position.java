package eagle.navigation.positioning;
/** Position
 * @since     09/04/2015
 * <p>
 * Date Modified	26/05/2015 - Nicholas
 * @version 0.0.1
 * @author          Nicholas Alards [7178301@student.swin.edu.au]
 * @author          Cameron Cross [7193432@student.swin.edu.au]*/

public abstract class Position{
	protected double longitude;
    protected double latitude;
    protected double altitude;
    private double roll;
	private double pitch;
	protected Bearing yaw;

    public Position(double longitude, double latitude, double altitude,double roll, double pitch, Bearing yaw){
        this.longitude=longitude;
        this.latitude=latitude;
        this.altitude=altitude;
        this.roll=roll;
        this.pitch=pitch;
        this.yaw=yaw;
    }
    public Position(Position position){
        this.longitude=position.longitude;
        this.latitude=position.latitude;
        this.altitude=position.altitude;
        this.roll=position.roll;
        this.pitch=position.pitch;
        this.yaw=position.yaw;
    }

    public double getLongitude(){return this.longitude;};
    public double getLatitude(){return this.latitude;};
    public double getAltitude(){return this.altitude;};
    public double getRoll(){return this.roll;};
    public double getPitch(){return this.pitch;};
    public Bearing getYaw(){return this.yaw;};

    @Override
    public String toString(){
        return getLongitude()+" "+getLatitude()+" "+getAltitude()+" "+getRoll()+" "+getPitch()+" "+getYaw();
    }

    public String toPrettyString(){
        return "Longitude: "+getLongitude()+", Latitude: "+getLatitude()+", Altitude: "+getAltitude()+", Roll: "+getRoll()+", Pitch: "+getPitch()+", Yaw: "+getYaw().toPrettyString();
    }

    public boolean isEqual(Position position) {
        return (Math.abs(position.getAltitude() - altitude) < 0.00001 &&
                Math.abs(position.getLatitude() - latitude) < 0.00001 &&
                Math.abs(position.getLongitude() - longitude) < 0.00001 &&
                Math.abs(position.getPitch() - pitch) < 0.00001 &&
                Math.abs(position.getRoll() - roll) < 0.00001 &&
                position.getYaw().isEqual(yaw));
    }
}
