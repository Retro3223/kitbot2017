package org.usfirst.frc.team3223.robot;

public class RotationalProfiler {
	double accel = 0.01*1000; //rad/s^2
	double vMaxTra = 2.3;//rad/s
	boolean isTrapezoid;
	double t1, t2, t3;// in seconds
	double vMaxTri;
	//@param angle in radians
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
	//gives velocity in rad/s
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

