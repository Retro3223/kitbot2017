/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3223.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.SPI;

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
    SensorManager sensorManager;
    private boolean isRunning = false;
    private int countDown = 100;
    Encoder encoder = new Encoder(7,8, false, Encoder.EncodingType.k1X);

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
        sensorManager = new SensorManager();
        visionState.thetaHighGoal = Math.toRadians(-5);
        turningStateMachine = new TurningStateMachine(visionState, sensorManager, robotConfig);
        
        
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
    	networkTable.putNumber("Count", encoder.get());
    	networkTable.putNumber("Raw Count", encoder.getRaw());
    	SmartDashboard.putString("DB/String 0", "Count:" + encoder.get());
    	SmartDashboard.putString("DB/String 1", "Count:" + encoder.getRaw());
    	/*sensorManager.tick();
    	//robotConfig.turn(joystick.getRawAxis(3)-joystick.getRawAxis(2));
    	//robotConfig.forward(joystick.getRawAxis(1));
    	if(joystick.getRawButton(3))
    		isRunning=true;
    	if(joystick.getRawButton(2))
    		isRunning=false;
    	//visionState.thetaHighGoal = Math.toRadians();
    	
    	SmartDashboard.putString("DB/String 3", "isRunning: "+isRunning);
    	SmartDashboard.putString("DB/String 0","Theta:" + Math.toDegrees(visionState.thetaHighGoal));
    	if(countDown>0)
    		countDown--;
    	SmartDashboard.putString("DB/String 2","Countdown:" + countDown);
    	if(joystick.getRawButton(1) && countDown<=0 && turningStateMachine.isDone())
    	{
    		turningStateMachine.reset();
    		visionState.thetaHighGoal = Math.toRadians(Float.parseFloat(SmartDashboard.getString("DB/String 1","180")));
    		countDown = 100;
    	}
    	if(isRunning)
    	{
    		turningStateMachine.run();
    		SmartDashboard.putString("DB/String 5", "des:"+Math.toDegrees(turningStateMachine.desiredHeading));
    		SmartDashboard.putString("DB/String 6", "current:"+sensorManager.getAngle());
    	}*/
    	//turningStateMachine.recorderContext.tick();
    	
    	//robotConfig.turn(.7);
    	//Thread.sleep
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
