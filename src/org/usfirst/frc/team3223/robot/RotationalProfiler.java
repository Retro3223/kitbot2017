package org.usfirst.frc.team3223.robot;

public class RotationalProfiler {
	double accel = 0.01*1000; //rad/s^2
	double vMaxTra = 2.3;//rad/s
	boolean isTrapezoid;
	
	/**
	 * time, in seconds, of acceleration phase
	 */
	double t1; 
	
	/**
	 * time, in seconds, of constant velocity phase
	 */
	double t2;
	
	/**
	 * time, in seconds, of deceleration phase
	 */
	double t3;
	
	double vMaxTri;
	
	/**
	 * calculate profile assuming current velocity is 0 and
	 * @param angle the desired distance to travel in radians
	 */
	public void calculate(double angle){
		vMaxTri = accel*Math.sqrt(angle/accel);
		isTrapezoid = vMaxTri > vMaxTra;
		if(isTrapezoid){
			t1 = (vMaxTra / accel);
			t2 = ((angle/vMaxTra)-(vMaxTra/accel));
			t3 = t1;
		} else{
			t1 = (vMaxTri / accel);
			t2 = 0;
			t3 = t1;
		}
		
	}
	
	/**
	 * recalculate profile given
	 * @param remainingAngle desired distance to travel in radians
	 * @param currentVelocity radians/second
	 */
	public void recalculate(double remainingAngle, double currentVelocity) {
		// if we started decelerating now, this is the shortest distance we can travel
		double minimumAngle = currentVelocity * currentVelocity / 2 / accel;
		
		if(minimumAngle > remainingAngle) {
			// and we're going to overshoot.
			isTrapezoid = false;
			t1 = t2 = 0;
			t3 = currentVelocity / accel;
			return;
		}
		
		// ta is time we can accelerate assuming no max velocity until we have to start decelerating
		double ta = quadratic1(accel, 2 * currentVelocity, minimumAngle - remainingAngle);
		double vMaxTri = currentVelocity + accel * ta;
		
		isTrapezoid = vMaxTri > vMaxTra;
		if(isTrapezoid) {
			t1 = (vMaxTra - currentVelocity) / accel;
			t3 = Math.sqrt(2 * vMaxTra / accel);
			
			double theta1 = currentVelocity * t1 + 0.5 * accel * t1 * t1;
			double theta3 = vMaxTra * t3 - 0.5 * accel * t3 * t3;
			t2 = (remainingAngle - theta1 - theta3) / vMaxTra;
		}else{
			t1 = ta;
			t2 = 0;
			t3 = vMaxTri / accel;
		}
	}
	
	/**
	 * quadratic formula, the part that might give us a positive number
	 */
	private double quadratic1(double a, double b, double c) {
		return (-b + Math.sqrt(b * b - 4 * a * c)) / 2 / a;
	}
	
	/**
	 * @param timeMs
	 * @return velocity at timeMs in rad/s
	 */
	public double getVelocity(long timeMs){
        double time = timeMs/1000.00;
		if(0 <= time && time < t1) {
            return accel * time;
        }else if(t1 <= time && time < t1 + t2) {
            return vMaxTra;
        }else if(t1 + t2 <= time && time < t1 + t2 + t3) {
            if(isTrapezoid) {
                return vMaxTra - accel * (time - (t1 + t2));
            }else{
                return vMaxTri - accel * (time - t1);
            }
        }
		return 0;
	}

    public boolean isDone(long timeMs) {
        return timeMs/1000.00 >= t1 + t2 + t3;
    }
}

