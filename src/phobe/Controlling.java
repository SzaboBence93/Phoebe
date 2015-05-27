package phobe;

import phobe_exceptions.*;

public class Controlling {
	protected Robot robot;
	protected int directionBuffer=-1;
	protected int actionBuffer;
	public final int NOACTION = -1;
	
	
	
	public void setRobot(Robot robot){
		this.robot = robot;
	}
	public void setDirection(int x) throws WrongDirectionException {
		if (x<4 && x>=0)
			robot.setDirection(x);
		else throw new WrongDirectionException();
			
				
			
	}
	public void setAction(int x) {
		if (x == Game.RAGACS){
			robot.createTrap(Game.RAGACS);
		}
		else if(x == Game.OLAJ)	{
			robot.createTrap(Game.OLAJ);
		}
	}
	
}
