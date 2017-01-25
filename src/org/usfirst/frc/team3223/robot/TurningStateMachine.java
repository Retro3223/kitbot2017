package org.usfirst.frc.team3223.robot;

public class TurningStateMachine {
	RotationalProfiler profiler;
	VisionState visionState;
	TurningState state;
	long startTime;
	public RecorderContext recorderContext;
	double velocity;
	double voltage;
	SensorManager sensorManager;
	RobotConfiguration robotConfig;
	
	public TurningStateMachine(VisionState visionState, SensorManager sensorManager, RobotConfiguration robotConfig) {
		profiler = new RotationalProfiler();
		this.visionState = visionState;
		this.sensorManager = sensorManager;
		this.robotConfig = robotConfig;
		voltage = 0;
		recorderContext =  new RecorderContext("robostate");
		recorderContext.add("expected velocity", () -> velocity);
        recorderContext.add("measured velocity (deg/s)", () -> sensorManager.getAngleVelocity()); // actual velocity
        // expected heading 
        recorderContext.add("actual heading (deg)", () -> sensorManager.getAngle());// actual heading (from navx)
        // actual theta (from vision)
        // elapsed time since turning started?
        // motor outputs?
		recorderContext.add("time1", () -> profiler.t1);
		recorderContext.add("time2", () -> profiler.t2);
		recorderContext.add("time3", () -> profiler.t3);
		recorderContext.add("voltage", () -> voltage);
		state = TurningState.Start;
		
	}
	
	public void run() {
		long currentTime = System.currentTimeMillis();
		
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
			long timeDelta = currentTime - startTime;
			velocity = profiler.getVelocity(timeDelta);//rad/s
			double actualVelocity = sensorManager.getAngleVelocity();//rad/s
			if(actualVelocity<-180){
				actualVelocity+=360;
			}
			double error = velocity-actualVelocity;
			voltage = .5* error;
			robotConfig.turn(voltage);
			recorderContext.tick();
			if(profiler.isDone(timeDelta)) {
				state = TurningState.End;
			}
			break;
		case End:
			voltage = 0;
			break;
		}
	}
	
	public boolean isDone() {
		return state == TurningState.End;
	}
}
