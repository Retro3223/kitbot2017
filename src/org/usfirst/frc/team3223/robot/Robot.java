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
   private SensorReadingsThread sensorReadingsThread;
   private VisionState visionState;
   private SpeedController leftMotor;
   private SpeedController rightMotor;
   private int bounds = 10;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
   public void robotInit() {
        // Initialize all subsystems
      joystick = new Joystick(0);
      sensorReadingsThread = new SensorReadingsThread();
      sensorReadingsThread.start();
      visionState = new VisionState();
      leftMotor = new Talon(0);
      rightMotor = new Talon(1);
      rightMotor.setInverted(true);
   }

   public void autonomousInit() {
        
   }

    /**
     * This function is called periodically during autonomous
     */
    
   public void autonomousPeriodic() {
      double rotationalValue;
      boolean seesTape = visionState.seesHighGoal();
      SmartDashboard.putString("DB/String 3", "TP="+seesTape);
      bounds = SmartDashboard.getNumber("DB/Slider 0",10.0);
      if (seesTape) {
         double pixels = visionState.getxOffsetHighGoal();
         if (pixels < bounds*-1 || pixels > bounds) {
            rotationalValue = ((pixels / 160) * 0.5);//Adjustable
            if(rotationalValue>0)
            {
               rotationalValue+=.2;//get over hump
            }
            else
            {
               rotationalValue-=.2;
            }
            leftMotor.set(rotationalValue);
            rightMotor.set(rotationalValue);
            SmartDashboard.putString("DB/String 0", "RV="+rotationalValue);
            SmartDashboard.putString("DB/String 1", "PX="+sensorReadingsThread.getDistanceFromTape());
         }
         else {
            leftMotor.set(0);
            rightMotor.set(0);
         	
         	//perform high goal
         	//return control to teleop
         }
      	//possibly sleep here for a couple ms if needed later
      }
      else
      {
         leftMotor.set(0);
         rightMotor.set(0);
      }
    	//leftMotor.set(sensorReadingsThread.getRotationValue()); //set to rotationValue
    	//rightMotor.set((sensorReadingsThread.getRotationValue()) * -1); //set to inverse of rotationValue
    	
   }

   public void teleopInit() {
    	
   }

    /**
     * This function is called periodically during operator control
     */
   public void teleopPeriodic() {
    	
    	
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
       
        
   }
}
