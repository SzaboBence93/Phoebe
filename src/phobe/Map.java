package phobe;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Map {
	private ArrayList<Field> map;			
		
	Map(){
		
		map = new ArrayList<Field>();
	}
		
	public HashMap<Integer, String> initMap(BufferedReader mapBuffer){
		
		int num = 0;		
        ArrayList<String> ans= new ArrayList<String>();		
		HashMap<Integer, String> infoMap = new HashMap<Integer, String>();
		
		try{
		for (String line = mapBuffer.readLine(); line != null; line = mapBuffer.readLine()) {
			ans.add(line);
				
					String [] cmds= line.split(" ");
					Field f = null;
										
					for(int i=1;i < cmds.length;i++){
						
							num = Integer.parseInt(cmds[0]);
						
						if(i==1){
							if(Integer.parseInt(cmds[i])==0)
								f= new Racetrack(num);
							if(Integer.parseInt(cmds[i])==1)
								f= new NotRacetrack(num);
							
						} else if(i==2){
							if(Integer.parseInt(cmds[i])==1)
								f.putTrap(new Oil());
							if(Integer.parseInt(cmds[i])==2)
								f.putTrap(new Glue());
						} else if(i==3){
							if(Integer.parseInt(cmds[i])==0){							
								infoMap.put(num, "nothing");
							}
							if(Integer.parseInt(cmds[i])==2){							
								infoMap.put(num, "littlerobot");
							}
							if(Integer.parseInt(cmds[i])==1){
								infoMap.put(num, "robot");
							}							
						}
					}
					if (f.getFieldId()>270 && f.getFieldId()<730 && (f.getFieldId()%25 == 0 || f.getFieldId()%25 == 24)) {
						f.setFinishLine(true);
					} else if (f.getFieldId()>730 && (f.getFieldId()%25 == 0 || f.getFieldId()%25 == 24)) {
						f.setCheckPoint(true);
					}
					map.add(f);	
		    }		
		}catch(IOException e){
			e.printStackTrace();
		}
		
		
		int k=0;
		for (String line: ans) {
		
				String [] cmds= line.split(" ");
				num = Integer.parseInt(cmds[0]);
				ArrayList<Field> neighbours = new ArrayList<Field>();
				for(int i=0; i<6 ;i++){
					 neighbours.add(null);
				}
				for(int i=4; i < cmds.length; i++){
					if(Integer.parseInt(cmds[i]) >= 0)
					neighbours.set(i-4, map.get(Integer.parseInt(cmds[i])));					
				}
				map.get(k).setNeighbours(neighbours);
				k++;
		}		
		infoMap.put(-1,Integer.toString(num+1));		
		
		return infoMap;		
	}
		
	public ArrayList<Field> getFields() {
		return map;
	}

	
	public Field getField(int n){
		
		if(map.size()>n)				
		{
			return map.get(n);
		}else{						
		return null;
		}
	}
}
