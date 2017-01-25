package org.usfirst.frc.team3223.robot;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

public class RobotConfiguration {
	private SpeedController left_motor, right_motor;
	public RobotConfiguration(){
		left_motor = new Talon(0);
		right_motor = new Talon(1);
	}
	
	public void turn(double voltage){
			left_motor.set(voltage);
			right_motor.set(voltage);
	}
	
	public void forward(double voltage){
		left_motor.set(voltage);
		right_motor.set(-voltage);
	}
	
	public void autonomous(long startTime){
		long currentTime = System.currentTimeMillis();
    	if(currentTime<=startTime+5000){
    		left_motor.set(0.5);
    		right_motor.set(-0.5);
    	}else{
    		left_motor.set(0);
    		right_motor.set(0);
    	}
	}

}
