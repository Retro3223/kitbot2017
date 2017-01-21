package org.usfirst.frc.team3223.robot;

public class TurningStateMachine {
	RotationalProfiler profiler;
	VisionState visionState;
	TurningState state;
	long startTime;
	RecorderContext recorderContext;
	double velocity;
	
	public TurningStateMachine(VisionState visionState) {
		profiler = new RotationalProfiler();
		this.visionState = visionState;
		recorderContext =  new RecorderContext("robostate");
		recorderContext.add("velocity", () -> velocity);
		recorderContext.add("time1", () -> profiler.t1);
		recorderContext.add("time2", () -> profiler.t2);
		recorderContext.add("time3", () -> profiler.t3);
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
			velocity = profiler.getVelocity(timeDelta);
			// do something with velocity
			recorderContext.tick();
			if(profiler.isDone(timeDelta)) {
				state = TurningState.End;
			}
			break;
		case End:
			break;
		}
	}
	
	public boolean isDone() {
		return state == TurningState.End;
	}
}
