package eagle.navigation.positioning;
/** Bearing
 * @since     09/04/2015
 * <p>
 * Date Modified	26/05/2015 - Nicholas
 * @version 0.0.1
 * @author          Nicholas Alards [7178301@student.swin.edu.au]
 * @author          Cameron Cross */
public class Bearing 
{
	private double degrees;
	
	/**
	 * Constructor for bearing class
	 * @param angle input angle
	 */
	public Bearing(double angle){
		degrees = angle % 360;
		while(degrees < 0) {
			degrees += 360;
		}
		while(degrees > 360) {
			degrees -= 360;
		}
	}
	public Bearing(Bearing bearing){
		this.degrees=bearing.getDegrees();
	}

	public Bearing add(Bearing val) {
		return new Bearing (this.degrees+val.getDegrees());
	}

	public Bearing minus(Bearing val) {
		return new Bearing (this.degrees-val.getDegrees());
	}

	public double getDegrees() {
		return degrees;
	}

	public boolean equals(Bearing bearing){
		if(degrees!=bearing.getDegrees())
			return false;
		else
			return true;
		//return Math.abs(degrees - bearing.degrees) < 0.01;
	}

	/**
	 * convert to degrees minutes and seconds.
	 * based of: http://zonalandeducation.com/mmts/trigonometryRealms/degMinSec/degMinSec.htm
	 * @return string containing degrees minutes seconds
	 */
	//TODO Incorrectly Coded. Seconds should exist when minute is 0
	public String toPrettyString(){
		StringBuilder sb = new StringBuilder();
		sb.append(new Double(degrees).intValue());
		sb.append("\u00B0");
		Double minutes = (degrees - Math.floor(degrees))*60;
		if (minutes != 0){
			sb.append(minutes.intValue());
			sb.append("\'");
			Double seconds = (minutes - Math.floor(minutes))*60;
			if (seconds != 0){
				sb.append(Math.round(seconds));
				sb.append("\"");
			}
		}
		return sb.toString();
	}

	@Override
	public String toString(){
		return Double.toString(degrees);
	}

	public boolean isEqual(Bearing bearing) {
		return Math.abs(degrees - bearing.degrees) < 0.01;
	}
}
