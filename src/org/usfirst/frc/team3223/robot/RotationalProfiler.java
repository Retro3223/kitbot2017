package org.usfirst.frc.team3223.robot;

public class RotationalProfiler {
	double accel = 2;
	double vMaxTra = 4;
	public void calculate(double angle){
		double vMaxTri = accel*Math.sqrt(angle/accel);
		if(vMaxTri > vMaxTra){
			//use vMaxTra
		} else{
			//use vMaxTri
		}
		
	}
	public double getVelocity(long time){
		return 0;
	}
}

