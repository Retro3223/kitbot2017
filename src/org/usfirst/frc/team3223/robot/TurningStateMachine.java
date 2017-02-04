package org.usfirst.frc.team3223.robot;

public class TurningStateMachine {
	RotationalProfiler profiler;
	VisionState visionState;
	TurningState state;
	long startTime; // ms
	long currentTime = 0; // ms
	int tickCount = 0;
	public RecorderContext recorderContext;
	double velocity; // rad/sec
	double voltage;
	double initialHeading; // rad
	double desiredHeading; // rad
	SensorManager sensorManager;
	RobotConfiguration robotConfig;
	double actualVelocity; // rad/sec
	long timeDelta; // ms
	boolean isMoving = false; // might move assignment to run() method
	boolean isSmallHeading;
	
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
		currentTime = System.currentTimeMillis();
		switch(state) {
		case Start:
			state = TurningState.Calculate;
			break;
		case Calculate:
			double angle = visionState.thetaHighGoal;
			isSmallHeading=angle<=Math.toRadians(5);
			initialHeading = sensorManager.headingRad();
			desiredHeading = initialHeading + angle;
			profiler.calculate(angle);
			startTime = currentTime;
			tickCount = 0;
			
			state = TurningState.Drive;
			break;
		case Drive:
			timeDelta = currentTime - startTime;//how long it has been running from start of turn to now
			
			actualVelocity = sensorManager.getAngularVelocity();
			
			velocity = profiler.getVelocity(timeDelta);//rad/s
			if(!isSmallHeading){
				if(tickCount == 5) {
					double currentHeading = sensorManager.headingRad();
					startTime = currentTime;
					timeDelta = 0;
					// todo: massage actual before giving it to profiler
					profiler.recalculate(desiredHeading-currentHeading, velocity);
					tickCount = 0;
				}else{
					tickCount ++;
				}
			} else{
				if(!isMoving&&actualVelocity>1E-04){
					profiler.t1+=timeDelta/1000.000;
					isMoving = true;
				}
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
			break;
		}
	}
	
	public boolean isDone() {
		return state == TurningState.End;
	}
}
