package org.usfirst.frc.team3223.robot;

public class RotationalProfiler {
	double accel = Math.toRadians(30)/1000/1000; //rad/ms^2
	double vMaxTra = Math.toRadians(2.29)/1000; //rad/ms
	boolean isTrapezoid;
	long t1, t2, t3;
	double vMaxTri;
	public void calculate(double angle){
		vMaxTri = accel*Math.sqrt(angle/accel);
		isTrapezoid = vMaxTri > vMaxTra;
		if(isTrapezoid){
			t1 = (long) (vMaxTra / accel);
			t2 = (long) ((angle/vMaxTra)-(vMaxTra/accel));
			t3 = t1;
		} else{
			t1 = (long) (vMaxTri / accel);
			t2 = 0;
			t3 = t1;
		}
		
	}
	public double getVelocity(long time){
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

    public boolean isDone(long time) {
        return time >= t1 + t2 + t3;
    }
}

