package phobe;

import java.util.ArrayList;
import java.util.List;

import phobe_exceptions.WrongDirectionException;


public class Robot {
	
	private Field field;						
	private int gonedistance;				
	private int rounds;
	private boolean hadcheck;
	private int v;							
	private int oils;						
	private int glues;						
	private int id;							
	private int previousdir;				
	private int direction;					
	private boolean oiledstate;				
	private boolean tobedestroyed;			
	
	public final int DEFAULT_ROBOT_V = 2;
	public final int DEFAULT_OIL_NUMBER = 2;
	public final int DEFAULT_GLUE_NUMBER = 2;
	public final int STATE_NON_TRAPED = 1;
	public final int STATE_GLUED = 2;
	public final int STATE_OILED = 3;
	public final int STATE_GLUED_OILED = 4;

	
	
	Robot(int id, Field beginpos){
		this.id = id;								
		field = beginpos;						
		previousdir = 1;					
		v = DEFAULT_ROBOT_V;								
		oiledstate = false;					
		tobedestroyed = false;
		direction = -1;
		oils = DEFAULT_OIL_NUMBER;
		glues = DEFAULT_GLUE_NUMBER;
		hadcheck=false;
		rounds=0;
	}
	
	public void setToBeDestroyed(){
		tobedestroyed = true;					
	}
	
	public boolean getToBeDestroyed(){
		return tobedestroyed;
	}
	
	public int getPreviousdir() {
		return previousdir;
	}
	
	public int getV(){
		return v;
	}
	
	public void setV(int v){
		if (v > 0)
			this.v=v;		
	}
	
	public void setOiledState(boolean b){
		
		oiledstate = b;
	}
	
	public int getState(){
		if (v < DEFAULT_ROBOT_V){															
			if (oiledstate){													
				return STATE_GLUED_OILED;
			}
			else{																
				return STATE_GLUED;
			}
		} else if (oiledstate){													
			return STATE_OILED;
		} else{																	
			return STATE_NON_TRAPED;
		}
	}
	
	public int getGlues(){
		return glues;
	}
	
	public int getOils(){
		return oils;
	}
	
	public void moveRobot(){
		if(direction!= -1){
			Field nextfield;
			for (int i = 0; i < v; i++) {
				if(oiledstate == true)
					nextfield = field.getNeighbours(previousdir);
				else{
					nextfield = field.getNeighbours(direction);
					previousdir = direction;
				}
		
				if(nextfield != null){
					field = nextfield;
				}
				gonedistance++;
				if (field.getFinishLine() && hadcheck) {
					rounds++;
					hadcheck=false;
				}
				if (field.getCheckPoint()) {
					hadcheck=true;
				}
			}
			field.stepOnMe(this);
			//direction=-1;
		}
	}

	public void createTrap(int type){
		if(field.hasTrap() == false)
			if(type == 0){
				if(oils > 0){ 
					Oil oil = new Oil();
					field.putTrap(oil);
					oils--;
				}
			}
			else{
				if(glues > 0){
					Glue glue = new Glue();
					field.putTrap(glue);
					glues--;
				}
			}
	}
	
	public Field getField(){
		return field;		
	}

	public int getId() {
		return id;
	}
	public int getRounds() {
		return rounds;
	}
	public boolean isOiledstate() {
		return oiledstate;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) throws WrongDirectionException {
		if (direction >= 0 && field.getNeighbours(direction) != null || direction==-1)
			this.direction = direction;
		else
			throw new WrongDirectionException("Direction " + direction + "is not valid!");
	}
	
	public void setOils(int o){oils = o;}

	public void setGlues(int g){glues = g;}

	public int getGonedistance() {
		return gonedistance;
	}
}
