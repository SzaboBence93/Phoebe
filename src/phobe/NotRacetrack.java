package phobe;

public class NotRacetrack extends Field{
	
	public NotRacetrack(int fieldId){
		super(fieldId);
	}
	
	
	public void stepOnMe(Robot robot){
		robot.setToBeDestroyed();								
	}

}
