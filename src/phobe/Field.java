package phobe;

import java.util.ArrayList;
import java.util.List;


public abstract class Field{
    public static final int NEIGHBOUR_FIELDSNUM = 4;
	private List<Field> neighbours;			
	protected Trap trap;						
	private boolean hastrap;	
	private boolean targeted;
	protected int fieldId;
	boolean finishline;
	boolean checkpoint;
	
		void setFinishLine(boolean x) {
			finishline=x;
		}
		boolean getFinishLine() {
			return finishline;
		}
		boolean getCheckPoint() {
			return checkpoint;
		}
		void setCheckPoint(boolean x) {
			checkpoint=x;
		}
		
	Field(int fieldId){
	    this.fieldId = fieldId;
	    this.targeted = false;
	}
	 
	public void setNeighbours(ArrayList<Field> n){
		 ArrayList<Field> uj = new ArrayList<Field>(n);
		 neighbours =uj;
    }
	 
	public abstract void stepOnMe(Robot robot);
	 
	public Trap getTrap(){
		return trap; 
	}
	 
	public void putTrap(Trap trap){
		 this.trap = trap;
		 hastrap = true;
	}
	 
	public void deleteTrap(){
		 trap = null;
		 hastrap = false;
	}
	 
	public boolean hasTrap(){
		 return hastrap;
	}

	public Field getNeighbours(int direction){
		 if(direction >= 0 && direction < neighbours.size())
			 return neighbours.get(direction);
		 return null;
	}
	
	public boolean getTargeted(){
		return targeted;
	}
	
	public void setTargeted(boolean b){
		targeted = b;
	}
	 
	public int getFieldId(){
		 return fieldId;
	}
	
}
