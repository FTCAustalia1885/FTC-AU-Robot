package com.qualcomm.ftcrobotcontroller.module;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 5/17/2016.
 */
public abstract class Controller {

	private List<Controller> subcontrollers;

	public Controller(){
		subcontrollers = new ArrayList<Controller>();
	}

	public void addSubcontroller(Controller c){
		subcontrollers.add(c);
	}

	public void sUpdate(){
		for(Controller c : subcontrollers){
			c.sUpdate();
		}
		update();
	}

	public abstract void update();

}
