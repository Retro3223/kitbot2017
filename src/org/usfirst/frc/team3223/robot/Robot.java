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
import edu.wpi.first.wpilibj.RobotDrive;
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
   private VisionState visionState;
   private SpeedController leftMotor;
   private SpeedController rightMotor;
   private RobotDrive robotDrive;
   private int mode = 0;//0 = Tele-Op, 1 = findHighGoal
   private int bounds = 10;
   private double factor = .3;
   private double bump = .4;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
   public void robotInit() {
        // Initialize all subsystems
      joystick = new Joystick(0);
      visionState = new VisionState();
      leftMotor = new Talon(0);
      rightMotor = new Talon(1);
      robotDrive = new RobotDrive(leftMotor,rightMotor);
      //rightMotor.setInverted(true);
      SmartDashboard.putString("DB/String 5","Bounds");
      SmartDashboard.putString("DB/String 6","Bump");
      SmartDashboard.putString("DB/String 7","Factor");

   }

   public void autonomousInit() {
        
   }

    /**
     * This function is called periodically during autonomous
     */
    
   public void autonomousPeriodic() {
         	
   }
   private void findHighGoal()
   {
	   double rotationalValue;
	      boolean seesTape = visionState.seesHighGoal();
	      
	      SmartDashboard.putString("DB/String 2", "TP="+seesTape);
	      bounds = (int)SmartDashboard.getNumber("DB/Slider 0",bounds);
	      bump = SmartDashboard.getNumber("DB/Slider 1",bump);
	      factor = SmartDashboard.getNumber("DB/Slider 2",factor);
	      
	      if (seesTape) {
	         double pixels = visionState.getxOffsetHighGoal();
	         if (pixels < bounds*-1 || pixels > bounds) {
	            rotationalValue = ((pixels / 160) * factor);//Adjustable
	            if(rotationalValue>0)
	            {
	               rotationalValue+=bump;//get over hump
	            }
	            else
	            {
	               rotationalValue-=bump;
	            }
	            
	            leftMotor.set(-1*rotationalValue);
	            rightMotor.set(-1*rotationalValue);
	            	
	            SmartDashboard.putString("DB/String 0", "RV="+rotationalValue);
	            SmartDashboard.putString("DB/String 1", "PX="+visionState.getxOffsetHighGoal());
	         }
	         else {
	            leftMotor.set(0);
	            rightMotor.set(0);
	         	mode = 0;
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
    	mode = 0;
   }

    /**
     * This function is called periodically during operator control
     */
   public void teleopPeriodic() {
    	switch(mode)
    	{
    	case 0:
    		robotDrive.arcadeDrive(joystick.getRawAxis(0),joystick.getRawAxis(1));
    		if(joystick.getRawButton(3))
    			mode=1;
    		break;
    	case 1:
    		findHighGoal();
    		break;
    	}
    	SmartDashboard.putString("DB/4", "Mode:"+mode);
    	
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
