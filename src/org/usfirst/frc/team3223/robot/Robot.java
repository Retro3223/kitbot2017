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
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    
    private Joystick joystick;
    long startTime = System.currentTimeMillis();
    RobotConfiguration robotConfig;
    NetworkTable networkTable;
    TurningStateMachine turningStateMachine;
    VisionState visionState;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        // Initialize all subsystems
        joystick = new Joystick(0);
        robotConfig = new RobotConfiguration();
        networkTable = NetworkTable.getTable("SmartDashboard");
        visionState = new VisionState();
        visionState.thetaHighGoal = Math.toRadians(30);
        turningStateMachine = new TurningStateMachine(visionState);
        
    }

    public void autonomousInit() {
        startTime = System.currentTimeMillis();
    }

    /**
     * This function is called periodically during autonomous
     */

    public void teleopInit() {
    	
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	robotConfig.turn(joystick.getRawAxis(3)-joystick.getRawAxis(2));
    	networkTable.putNumber("Joystick Input", joystick.getRawAxis(4));
    	networkTable.putString("cwd", System.getProperty("user.dir"));
    	turningStateMachine.run();
    	
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	
        
    }

	/**
	 * The log method puts interesting information to the SmartDashboard.
	 */
    private void log() {
        //wrist.log();
        
    }
}
