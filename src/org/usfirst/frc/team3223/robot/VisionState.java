package org.usfirst.frc.team3223.robot;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class VisionState implements ITableListener {
	private boolean seesHighGoal;
	private double xOffsetHighGoal;
	
    public VisionState() {
    	NetworkTable networkTable = NetworkTable.getTable("SmartDashboard");
    	networkTable.addTableListener(this);
    }

	@Override
	public void valueChanged(ITable source, String key, Object value, boolean isNew) {
        if(key.equals("seesHighGoal")) {
            seesHighGoal = (boolean) value;
        }
        if(key.equals("xOffsetHighGoal")) {
            xOffsetHighGoal = (double) value;
        }

	}

	public boolean seesHighGoal() {
		return seesHighGoal;
	}
	
	public double getxOffsetHighGoal() {
		return xOffsetHighGoal;
	}
}
