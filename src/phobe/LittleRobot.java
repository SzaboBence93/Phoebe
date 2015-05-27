package phobe;

import java.util.ArrayList;
import java.util.List;

public class LittleRobot {
	private Field f;

	private int Id;
	private Field nextTrap;
	private Field probableNext;
	
	public LittleRobot (int Id, Field f){
		this.Id = Id;
		this.f = f;
	}
	
	public void setId (int newId){
		this.Id = newId;
	}
	
	public int getId(){
		return Id;
	}
	
	public Field getField(){
		return f;
	}
	
	public Field getNextTrap(){
		return nextTrap;
	}

	public void action(){
		if(f.hasTrap()){
			this.clean();
			return;
		}
		
		List<Field> targets = new ArrayList<>();
		
		int distances[] = new int[Field.NEIGHBOUR_FIELDSNUM] ;
		
		for(int i=0; i < Field.NEIGHBOUR_FIELDSNUM; i++){
			Field newf = f.getNeighbours(i);
			if(newf != null && newf.getClass() == Racetrack.class){
				distances[i] = search(newf);
				targets.add(probableNext);
			}
			else{ 
				distances[i] = Integer.MAX_VALUE;
				targets.add(null);
			}
		}
		
		int mindir = 0;
		
		for (int i = 1; i < distances.length; i++){
			if(distances[mindir] > distances[i])
				mindir = i;
		}
		
		if(distances[mindir] == Integer.MAX_VALUE)
			return;
		
		Field field = targets.get(mindir);
		if(field != null && nextTrap != null){
			if(!nextTrap.equals(field)){
				field.setTargeted(true);
				if(nextTrap != null){
					nextTrap.setTargeted(false);
				}
			}
			
		}
		
		nextTrap = field;
		this.move(mindir);
	}
	
	
	public int search(Field beginField){
		probableNext = null;
		List<Field> curFields = new ArrayList<>();
		List<Field> nextFields = new ArrayList<>();
		List<Field> marked = new ArrayList<>();
		boolean stop = false;
		int closestdistance = 1;
		curFields.add(beginField);
		marked.add(beginField);
		if(beginField.hasTrap() && (beginField.equals(nextTrap) || beginField.getTargeted() == false)){
			stop = true;
			probableNext = beginField;
		}

		while(!stop && !curFields.isEmpty()){
			nextFields.clear();
			closestdistance++;
			for(Field fields:curFields){
				if(!stop){
					for(int i=0; i < Field.NEIGHBOUR_FIELDSNUM && !stop; i++){
						Field f = fields.getNeighbours(i);
						if(!marked.contains(f) && f != null && f.getClass() == Racetrack.class){
							if( f.hasTrap() && (f.equals(nextTrap) || f.getTargeted() == false) ){
								stop = true;
								probableNext = f;
							} else{
								marked.add(f);
								nextFields.add(f);
							}
							
						}
							
					} 
				}
			} 
			
			curFields.clear();
			for(int i=0; i<nextFields.size(); i++)
				curFields.add(nextFields.get(i));
		}
		if(probableNext == null)
			return Integer.MAX_VALUE;
		
		return closestdistance; 
	}
	
	
	public void move(int dir){
		f = f.getNeighbours(dir);
	}
	
	public void clean(){
		f.deleteTrap();
		nextTrap = null;
	}
	
	public void onDestroy(){
		f.putTrap(new Oil());
		if(nextTrap != null){
			nextTrap.setTargeted(false);
		}
			
	}

}
