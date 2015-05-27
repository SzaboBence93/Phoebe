package phobe;

public class Glue extends Trap{

	public static int defHealth= 100;
	
	public Glue(){
		health = defHealth;
	}
	
	public void stepOnMe(Robot robot){
		int velocity = robot.getV();
		if ((velocity % 2) == 0)
		robot.setV(velocity/2);
		decHealth();
	}

	@Override
	public void decHealth() {
		health--;
		
	}
}
