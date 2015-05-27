package phobe;

public abstract class Trap {

	protected int health;

	public Trap() {
		
	}

	public abstract void decHealth();

	public abstract void stepOnMe(Robot robot);

	public int getHealth() {
		return health;
	}

}
