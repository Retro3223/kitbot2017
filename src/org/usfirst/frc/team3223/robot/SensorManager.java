package org.usfirst.frc.team3223.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;

public class SensorManager {
	private AHRS ahrs;
	public SensorManager() {
		ahrs = new AHRS(SPI.Port.kMXP);
	}
	
	public double getAngle() {
		return ahrs.getAngle();
	}
	public double getDeltaAngle() {
		return ahrs.getRate();
	}
}