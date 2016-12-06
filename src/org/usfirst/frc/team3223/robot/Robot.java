/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3223.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    Command autonomousCommand;
    
    private Joystick joystick;
    private SpeedController left_motor, right_motor;
    long startTime = System.currentTimeMillis();

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        // Initialize all subsystems
        joystick = new Joystick(0);
        left_motor = new Talon(0);
		right_motor = new Talon(1);
        // instantiate the command used for the autonomous perio

        // Show what command your subsystem is running on the SmartDashboard
        //SmartDashboard.putData(drivetrain);
        
    }

    public void autonomousInit() {
        startTime = System.currentTimeMillis();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	
    	long currentTime = System.currentTimeMillis();
    	if(currentTime<=startTime+5000){
    		left_motor.set(0.5);
    		right_motor.set(-0.5);
    	}else{
    		left_motor.set(0);
    		right_motor.set(0);
    	}
        
    }

    public void teleopInit() {
    	
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	double l = -joystick.getRawAxis(1);//y of l stick
        double r = joystick.getRawAxis(5);//y of r stick
        System.out.println("r:"+r);
        System.out.println("l:"+l);
        left_motor.set(l);
        right_motor.set(r);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	if(joystick.getRawButton(1)){
    		left_motor.set(0.5);
    	}
    	if(joystick.getRawButton(2)){
    		right_motor.set(0.5);
    	}
        
    }

	/**
	 * The log method puts interesting information to the SmartDashboard.
	 */
    private void log() {
        //wrist.log();
        
    }
}
