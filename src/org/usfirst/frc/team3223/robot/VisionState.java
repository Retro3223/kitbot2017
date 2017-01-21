package org.usfirst.frc.team3223.robot;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class VisionState implements ITableListener {
	public boolean seesHighGoal;
    public long seesHighGoalLastUpdated;
	public double xOffsetHighGoal;
    public long xOffsetHighGoalLastUpdated;
	public double zOffsetHighGoal;
    public long zOffsetHighGoalLastUpdated;
	public double thetaHighGoal;
    public long thetaHighGoalLastUpdated;
    public double psiBoiler;
    public long psiBoilerLastUpdated;

    public boolean seesLift;
    public long seesLiftLastUpdated;
    public double xOffsetLift;
    public long xOffsetLiftLastUpdated;
    public double zOffsetLift;
    public long zOffsetLiftLastUpdated;
    public double thetaLift;
    public long thetaLiftLastUpdated;
    public double psiLift;
    public long psiLiftLastUpdated;
    
    public VisionState() {
    	NetworkTable networkTable = NetworkTable.getTable("SmartDashboard");
    }

	@Override
	public void valueChanged(ITable source, String key, Object value, boolean isNew) {
        long currentTime = System.currentTimeMillis();
        if(key.equals("seesHighGoal")) {
            seesHighGoal = (boolean) value;
            seesHighGoalLastUpdated = currentTime;
        }
        if(key.equals("xOffsetHighGoal")) {
            xOffsetHighGoal = (double) value;
            xOffsetHighGoalLastUpdated = currentTime;
        }
        if(key.equals("zOffsetHighGoal")) {
            zOffsetHighGoal = (double) value;
            zOffsetHighGoalLastUpdated = currentTime;
        }
        if(key.equals("thetaHighGoal")) {
            thetaHighGoal = (double) value;
            thetaHighGoal = currentTime;
        }
        if(key.equals("psiBoiler")) {
            psiBoiler = (double) value;
            psiBoilerLastUpdated = currentTime;
        }
        if(key.equals("seesLift")) {
            seesLift = (boolean) value;
            seesLiftLastUpdated = currentTime;
        }
        if(key.equals("xOffsetLift")) {
            xOffsetLift = (double) value;
            xOffsetLiftLastUpdated = currentTime;
        }
        if(key.equals("zOffsetLift")) {
            zOffsetLift = (double) value;
            zOffsetLiftLastUpdated = currentTime;
        }
        if(key.equals("thetaLift")) {
            thetaLift = (double) value;
            thetaLiftLastUpdated = currentTime;
        }
        if(key.equals("psiLift")) {
            psiLift = (double) value;
            psiLiftLastUpdated = currentTime;
        }
	}
}
