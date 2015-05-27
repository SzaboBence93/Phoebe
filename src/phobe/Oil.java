package phobe;

public class Oil extends Trap{

	public static int defHealth = 100;
	
	public Oil(){
		health = defHealth;
	}
	
	public void stepOnMe(Robot robot){
		robot.setOiledState(true);	
	}

	@Override
	public void decHealth() {
		health--;
		
	}
	
}
