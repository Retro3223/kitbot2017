package org.usfirst.frc.team3223.robot;

public class SensorReadingsThread extends Thread {
	private double distanceFromTape;
	private double rotationValue; //-1 to turn fast left, 0 to stop turning, 1 to turn fast right
	private VisionState visionState;
	private boolean seesTape;
	
	public SensorReadingsThread() {
		super();
		visionState = new VisionState();
	}
	
	public void run() {
		setDistanceFromTape(visionState.getxOffsetHighGoal()); //read sensor reading for x offset and set as distance from tape
		setSeesTape(visionState.seesHighGoal()); //read sensor reading for whether or not the high goal can be seen
	}

	public double getDistanceFromTape() {
		return distanceFromTape;
	}

	public void setDistanceFromTape(double distanceFromTape) {
		this.distanceFromTape = distanceFromTape;
	}
	
	public boolean seesTape() {
		return seesTape;
	}
	
	public void setSeesTape(boolean seesTape) {
		this.seesTape = seesTape;
	}
	
//	float weightedAvg(float lr, float avg) {
//		return (lr + ALPHA * (avg-lr));
//	}

//	public double getRotationValue() {
//		return rotationValue;
//	}
//
//	public void setRotationValue(double rotationValue) {
//		this.rotationValue = rotationValue;
//	}

	
	
	
}
