package eagle.sdkInterface.sensorAdaptors.cameraAdaptors;

import eagle.sdkInterface.sensorAdaptors.AdaptorCamera;

/** Link Sprite SEN-12804 Camera Adaptor
 * @since     19/06/2015
 * <p>
 * Date Modified	19/06/2015 - Nicholas
 * @version 0.0.1
 * @author          Nicholas Alards [7178301@student.swin.edu.au] */
public class LinkSpriteSEN12804 extends AdaptorCamera {
    public LinkSpriteSEN12804(){
        super("Link Sprite","SEN-12804","0.0.1");
    }

    //TODO Following Method Need Proper Implementation
    public boolean connectToSensor(){
        return true;
    }
    //TODO Following Method Need Proper Implementation
    public boolean isConnectedToSensor(){
        return true;
    }
}
