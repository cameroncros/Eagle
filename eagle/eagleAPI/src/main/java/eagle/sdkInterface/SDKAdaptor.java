package eagle.sdkInterface;

import eagle.navigation.positioning.Position;
import eagle.navigation.positioning.Bearing;
import eagle.sdkInterface.sensorAdaptors.*;

import java.util.ArrayList;
import java.util.HashMap;

/** Abstract SDKAdaptor Class
 * @since     09/04/2015
 * <p>
 * Date Modified	26/05/2015 - Nicholas
 * @version 0.0.1
 * @author          Nicholas Alards [7178301@student.swin.edu.au] */
public abstract class SDKAdaptor {

    private ArrayList<AdaptorAccelerometer> accelerometers = null;
    private ArrayList<AdaptorCamera> cameras = null;
    private ArrayList<AdaptorGyroscope> gyroscopes = null;
    private ArrayList<AdaptorLIDAR> lidars = null;
    private ArrayList<AdaptorRPLIDAR> rplidars = null;
    private ArrayList<AdaptorUltrasonic> ultrasonics = null;

    private String adaptorName = null;
    private String adaptorManufacturer = null;
    private String adaptorModel = null;
    private String sdkVersion = null;
    private String adaptorVersion = null;

    private Position homePosition;

    //TODO create way to set current assigned position
    private Position currentPositionAssigned;

    public SDKAdaptor(String adaptorManufacturer, String adaptorModel, String sdkVersion, String adaptorVersion){
        this.adaptorName=adaptorManufacturer+" "+adaptorModel;
        this.adaptorManufacturer=adaptorManufacturer;
        this.adaptorModel=adaptorModel;
        this.sdkVersion=sdkVersion;
        this.adaptorVersion=adaptorVersion;
        this.homePosition=new Position(0,0,0,0,0,new Bearing(0));
        this.currentPositionAssigned = new Position(0,0,0,0,0,new Bearing(0));
    }
    public abstract void loadDefaultSensorAdaptors(AdaptorLoader adaptorLoader);

    public abstract boolean connectToDrone();
    public abstract boolean disconnectFromDrone();
    public abstract boolean isConnectedToDrone();

    public abstract boolean standbyDrone();
    public abstract boolean resumeDrone();
    public abstract boolean shutdownDrone();

    public String getAdaptorVersion(){
        return adaptorVersion;
    };
    public String getSdkVersion(){
        return sdkVersion;
    }
    public String getAdaptorName(){
        return adaptorName;
    }
    public String getAdaptorManufacturer(){
        return adaptorManufacturer;
    }
    public String getAdaptorModel(){
        return adaptorModel;
    }

    public boolean flyToRelative(Position position, double speed){
        return flyToAbsolute(new Position(getPositionInFlight().getLongitude()+position.getLongitude(),getPositionInFlight().getLatitude()+position.getLatitude(),
                getPositionInFlight().getAltitude()+position.getAltitude(),0,0,new Bearing(getPositionInFlight().getYaw().getDegrees()+position.getYaw().getDegrees())),speed);
    }
    public boolean flyToRelative(Position position){
        return flyToAbsolute(new Position(getPositionInFlight().getLongitude()+position.getLongitude(),getPositionInFlight().getLatitude()+position.getLatitude(),
                getPositionInFlight().getAltitude()+position.getAltitude(),0,0,new Bearing(getPositionInFlight().getYaw().getDegrees()+position.getYaw().getDegrees())));
    }

    public abstract boolean flyToAbsolute(Position position, double speed);
    public abstract boolean flyToAbsolute(Position position);

    public boolean changeLongitudeRelative(double longitude,double speed){
        return flyToRelative(new Position(longitude, 0, 0, 0, 0, new Bearing(0)), speed);
    }
    public boolean changeLongitudeRelative(double longitude){
        return flyToRelative(new Position(longitude, 0, 0, 0, 0, new Bearing(0)));
    }
    public boolean changeLatitudeRelative(double latitude,double speed){
        return flyToRelative(new Position(0, latitude, 0, 0, 0, new Bearing(0)), speed);
    }
    public boolean changeLatitudeRelative(double latitude){
        return flyToRelative(new Position(0, latitude, 0, 0, 0, new Bearing(0)));
    }
    public boolean changeAltitudeRelative(double altitude,double speed){
        return flyToRelative(new Position(0, 0, altitude, 0, 0, new Bearing(0)), speed);
    }
    public boolean changeAltitudeRelative(double altitude){
        return flyToRelative(new Position(0,0,altitude,0,0,new Bearing(0)));
    }
    public boolean changeYawRelative(Bearing yaw,double speed){
        return flyToRelative(new Position(0, 0, 0, 0, 0, yaw), speed);
    }
    public boolean changeYawRelative(Bearing yaw){
        return flyToRelative(new Position(0, 0, 0, 0, 0, yaw));
    }

    public boolean changeLongitudeAbsolute(double longitude,double speed){
        return flyToAbsolute(new Position(longitude, getPositionAssigned().getLatitude(), getPositionAssigned().getAltitude(),0,0, getPositionAssigned().getYaw()),speed);
    }
    public boolean changeLongitudeAbsolute(double longitude){
        return flyToAbsolute(new Position(longitude, getPositionAssigned().getLatitude(), getPositionAssigned().getAltitude(),0,0, getPositionAssigned().getYaw()));
    }
    public boolean changeLatitudeAbsolute(double latitude,double speed){
        return flyToAbsolute(new Position(getPositionAssigned().getLongitude(),latitude, getPositionAssigned().getAltitude(),0,0, getPositionAssigned().getYaw()),speed);
    }
    public boolean changeLatitudeAbsolute(double latitude){
        return flyToAbsolute(new Position(getPositionAssigned().getLongitude(),latitude, getPositionAssigned().getAltitude(),0,0, getPositionAssigned().getYaw()));
    }
    public boolean changeAltitudeAbsolute(double altitude,double speed){
        return flyToAbsolute(new Position(getPositionAssigned().getLongitude(), getPositionAssigned().getLatitude(),altitude,0,0, getPositionAssigned().getYaw()),speed);
    }
    public boolean changeAltitudeAbsolute(double altitude){
        return flyToAbsolute(new Position(getPositionAssigned().getLongitude(), getPositionAssigned().getLatitude(),altitude,0,0, getPositionAssigned().getYaw()));
    }
    public boolean changeYawAbsolute(Bearing yaw,double speed){
        return flyToAbsolute(new Position(getPositionAssigned().getLongitude(), getPositionAssigned().getLatitude(), getPositionAssigned().getAltitude(),0,0,yaw),speed);
    }
    public boolean changeYawAbsolute(Bearing yaw){
        return flyToAbsolute(new Position(getPositionAssigned().getLongitude(), getPositionAssigned().getLatitude(), getPositionAssigned().getAltitude(),0,0,yaw));
    }

    public void goHome(double speed) {
        flyToAbsolute(homePosition, speed);
    }
    public void goHome() {
        flyToAbsolute(homePosition);
    }

    public Position getPositionAssigned(){
        return currentPositionAssigned;
    }
    public void setPositionAssigned(Position pos) {
        currentPositionAssigned = pos;
    }
    public abstract Position getPositionInFlight();

    public void setHomePosition(Position position){
        homePosition=position;
    }
    public Position getHomePosition(){
        return homePosition;
    }

    public void addSensorAdaptorAccelerometer(AdaptorAccelerometer adaptorAccelerometer){
        if (this.accelerometers==null)
            this.accelerometers=new ArrayList<AdaptorAccelerometer>();
        this.accelerometers.add(adaptorAccelerometer);
    }
    public void addSensorAdaptorCamera(AdaptorCamera adaptorCamera){
        if (this.cameras==null)
            this.cameras=new ArrayList<AdaptorCamera>();
        this.cameras.add(adaptorCamera);
    }
    public void addSensorAdaptorGyroscope(AdaptorGyroscope gyroscopes){
        if (this.gyroscopes==null)
            this.gyroscopes=new ArrayList<AdaptorGyroscope>();
        this.gyroscopes.add(gyroscopes);
    }
    public void addSensorAdaptorLIDAR(AdaptorLIDAR lidar){
        if (this.lidars==null)
            this.lidars=new ArrayList<AdaptorLIDAR>();
        this.lidars.add(lidar);
    }
    public void addSensorAdaptorRPLIDAR(AdaptorRPLIDAR adaptorRPLIDAR){
        if (this.rplidars==null)
            this.rplidars=new ArrayList<AdaptorRPLIDAR>();
        this.rplidars.add(adaptorRPLIDAR);
    }
    public void addSensorAdaptorUltrasonic(AdaptorUltrasonic adaptorUltrasonic){
        if (this.ultrasonics==null)
            this.ultrasonics=new ArrayList<AdaptorUltrasonic>();
        this.ultrasonics.add(adaptorUltrasonic);
    }

    public abstract void delay(int milliseconds);

    public ArrayList<AdaptorAccelerometer> getAccelerometers() {
        return accelerometers;
    }

    //TODO Add Remove Adaptor Functions

}