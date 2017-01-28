package org.usfirst.frc.team3223.robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurningStateMachine {
	RotationalProfiler profiler;
	VisionState visionState;
	TurningState state;
	long startTime;
	long previousTime = 0;
	long currentTime = 0;
	public RecorderContext recorderContext;
	double velocity;
	double voltage;
	SensorManager sensorManager;
	RobotConfiguration robotConfig;
	double actualVelocity;
	long timeDelta;
	boolean isMoving = false; // might move assignment to run() method
	
	public TurningStateMachine(VisionState visionState, SensorManager sensorManager, RobotConfiguration robotConfig) {
		profiler = new RotationalProfiler();
		this.visionState = visionState;
		this.sensorManager = sensorManager;
		this.robotConfig = robotConfig;
		voltage = 0;
		recorderContext =  new RecorderContext("robostate");
		recorderContext.add("expected velocity", () -> velocity);
        recorderContext.add("actual velocity", () -> actualVelocity); 
        // expected heading 
        recorderContext.add("actual heading (deg)", () -> sensorManager.getAngle());// actual heading (from navx)
        // actual theta (from vision)
        // elapsed time since turning started?
        // motor outputs?
		recorderContext.add("time1", () -> profiler.t1);
		recorderContext.add("time2", () -> profiler.t2);
		recorderContext.add("time3", () -> profiler.t3);
		recorderContext.add("voltage", () -> voltage);
		recorderContext.add("time delta", () -> timeDelta);
		state = TurningState.Start;
		
	}
	
	public void run() {
		previousTime = currentTime;
		currentTime = System.currentTimeMillis();
		switch(state) {
		case Start:
			state = TurningState.Calculate;
			break;
		case Calculate:
			double angle = visionState.thetaHighGoal;
			profiler.calculate(angle);
			startTime = currentTime;
			state = TurningState.Drive;
			break;
		case Drive:
			timeDelta = currentTime - startTime;//how long it has been running from start of turn to now
			velocity = profiler.getVelocity(timeDelta);//rad/s
			actualVelocity = Math.toRadians(sensorManager.getDeltaAngle())/((currentTime-previousTime)/1000.000); //divides delta angle by delta time in rad/s
			
			//should reset time Delta to 0 when the robot starts moving
			if(!isMoving&&actualVelocity>1E-04){
				profiler.t1+=(timeDelta/2)/1000.000;
				profiler.t3 = profiler.t1;
				isMoving = true;
			}
			
			// fixes actualVelocity
			if(actualVelocity<-180){
				actualVelocity+=360;
			}
			
			//Calculates error between expected velocity and actual velocity
			double error = velocity-actualVelocity;
			voltage = .5* error;
			robotConfig.turn(voltage);
			recorderContext.tick();
			if(profiler.isDone(timeDelta)) {
				state = TurningState.End;
			}
			break;
		case End:
			//stops turning and resets for next call;
			voltage = 0;
			robotConfig.turn(voltage);
			isMoving = false;
			previousTime = 0;
			currentTime = 0;		
			break;
		}
	}
	
	public boolean isDone() {
		return state == TurningState.End;
	}
}
