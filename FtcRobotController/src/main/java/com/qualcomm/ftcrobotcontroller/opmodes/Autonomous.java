package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.ftcrobotcontroller.autonomous.AutonomousCommand;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Michael on 5/25/2016.
 */
public abstract class Autonomous extends OpMode{

	private Queue<AutonomousCommand> commands;

	private long lastTime = -1L;

	@Override
	public void init(){
		commands = new LinkedList<AutonomousCommand>();
		addCommands(commands);
		if(!commands.isEmpty())commands.peek().init();
	}

	public abstract void addCommands(Collection<AutonomousCommand> coms);

	@Override
	public void loop(){
		long currentTime = System.currentTimeMillis();
		if(lastTime == -1L){
			lastTime = currentTime;
		}
		if(!commands.isEmpty()){
			AutonomousCommand current = commands.peek();
			current.update((currentTime - lastTime) / 1000.0f);
			if(current.isComplete()){
				current.finalize();
				commands.remove();
				if(!commands.isEmpty())commands.peek().init();
			}
		}
		lastTime = currentTime;
	}
}
