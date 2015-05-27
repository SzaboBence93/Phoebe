package phobe;

public class Racetrack extends Field{
	
	public Racetrack(int fieldId){
		super(fieldId);
	}
	
	public void stepOnMe(Robot robot){
		if(this.hasTrap())							
			trap.stepOnMe(robot);
	}

}
