package phobe;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import phobe_exceptions.WrongDirectionException;
import phobe_test.main;

public class Game{
	public static final int OLAJ = 0;				
	public static final int RAGACS = 1;		
	private static final int DEFAULT_RUNNING_SPEED = 250;

	private boolean loopenabled;						
	private ArrayList<Robot> robots;					
	private ArrayList<LittleRobot> littleRobots;		
	protected ArrayList<Controlling> controllings;
	private PhobeTimer timer;
	private Timer timercounter;
	
	private Map map;									
	public int time=100;								
	private Field field1;	
	
	int gluelife=-1,glues=-1,oils=-1,oillife=-1;
	View view;
	
	
	public Game(){
	
	}
	
	public Game(View view){
		//timer = new PhobeTimer();	
		this.view=view;
	}
	
	public void init(BufferedReader initdata) {
		
		robots = new ArrayList<>();					
		controllings = new ArrayList<>();		
		littleRobots = new ArrayList<LittleRobot>();
		map = new Map();	
		timercounter = new Timer();
		timercounter.scheduleAtFixedRate(new TimerTask() {
			  public void run() {
				  time--;
			  }
			}, 1000, 1000);
		
				
		
		try {
			String line = initdata.readLine();
			String[] cmds = line.split(" ");
			time = Integer.parseInt(cmds[0]);
			
			if(cmds.length > 1){
				glues = Integer.parseInt(cmds[1]);
				gluelife = Integer.parseInt(cmds[2]);
		    }
	   	   	if(cmds.length > 3){
	   		   oils = Integer.parseInt(cmds[3]);
		   	   oillife =  Integer.parseInt(cmds[4]);
	   	   	}			
		}catch (IOException e) {
			
			e.printStackTrace();
		}
		
		if(oillife!=-1)
			Oil.defHealth=oillife;
		if(gluelife != -1){
			Glue.defHealth = gluelife;
		}
		
		HashMap<Integer, String> mapinfos =map.initMap(initdata);
		field1 = map.getField(0);
		Robot robot;
		int nextId = 0;
		int nextlrId =0;
		for(int i=0; i < Integer.parseInt(mapinfos.get(-1)) ; i++){
			if(mapinfos.get(i).equals("littlerobot")){
				LittleRobot lr = new LittleRobot(nextlrId,map.getField(i));
				littleRobots.add(lr);
				nextlrId++;
			}else if(mapinfos.get(i).equals("robot")){				
				robot = new Robot(nextId,map.getField(i));
				robots.add(robot);
				Controlling controlling;
				if (nextId==0) {
					controlling = new KeyBoard(new char[]{'w','d','s','a'},'q','e');
				} else if (nextId==1) {
					controlling = new KeyBoard(new char[]{'t','h','g','f'},'r','z');
				} else {
					controlling = new KeyBoard(new char[]{'i','l','k','j'},'u','o');
				}
				controlling.setRobot(robot);
				controllings.add(controlling);
				if(oils>-1 && glues > -1){
					robot.setGlues(glues);
					robot.setOils(oils);
				}
				nextId++;
			}			
		}	
		
		
	}
	
	public void collision(){
		int len = robots.size();
		boolean checked[] = new boolean[len];
		for(int i=0; i<len; i++)
			checked[i] = false;

		for(int i=0; i<len; i++){
			if(checked[i]==false){
				Field f = robots.get(i).getField();
				List<Robot> robotCollision = new ArrayList<>();
				robotCollision.add(robots.get(i));
				checked[i] = true;
				for(int j=i+1; j<len; j++){
					if(checked[j]==false && robots.get(j).getField().equals(f)){
						robotCollision.add(robots.get(j));
						checked[j] = true;
					}
				}
				
				if(robotCollision.size()>1){
	
					double meanup = 0;
					double meanright = 0;
					int vel = robotCollision.get(0).getV();
					int maxvel = robotCollision.get(0).getV();
					int dir = robotCollision.get(0).getPreviousdir();
					
					switch(dir){
						case 0: meanright = meanright+vel;
							break;
						case 1: meanup = meanup+vel;
							break;
						case 2: meanright = meanright-vel;
							break;
						case 3: meanup = meanup-vel;
							break;
					}
					
					for(int j=1; j<robotCollision.size(); j++){
						vel = robotCollision.get(j).getV();
						dir = robotCollision.get(j).getPreviousdir();
						switch(dir){
							case 0: meanright = meanright+vel;
								break;
							case 1: meanup = meanup+vel;
								break;
							case 2: meanright = meanright-vel;
								break;
							case 3: meanup = meanup-vel;
								break;
						}
						if(maxvel < vel){
							maxvel = vel;
						}
					}
	
					meanup = meanup/robotCollision.size();
					meanright = meanright/robotCollision.size();
					
					int livedir = 0;
					int livevel = 0;
					if(Math.abs(meanup)<Math.abs(meanright)){
						if(meanright >= 0){
							livedir = 0;
							livevel = (int) Math.ceil(meanright);
						} else {
							livedir = 2;
							livevel = (int) Math.ceil(meanright*-1);
						}
					} else {
						if(meanup >= 0){
							livedir = 1;
							livevel = (int) Math.ceil(meanup);
						} else {
							livedir = 3;
							livevel = (int) Math.ceil(meanup*-1);
						}
					}
					
					robotsCollidedChange(robotCollision,maxvel,livevel,livedir);
				}
				
				destroyLittleRobotsOnField(f);
			}
		}
	}
	
	public void robotsCollidedChange(List<Robot> robotCollision,int maxvel,int livevel,int livedir){
		for(int j=0; j<robotCollision.size(); j++){
			if(robotCollision.get(j).getV() < maxvel)
				robotCollision.get(j).setToBeDestroyed();
			else{
				try{
					if(livevel!=0){
						robotCollision.get(j).setDirection(livedir);
					}//Ha az �tk�z� robotok sebess�g�nek vektor�sszege nulla, akkor az ir�nyt nem v�ltoztatjuk
				}
				catch(WrongDirectionException e){
					e.printStackTrace();
				}
				if(livevel>=1){
					robotCollision.get(j).setV(livevel);
				} else {
					robotCollision.get(j).setV(1);
				}
			}
		}
	}
	
	public void destroyLittleRobotsOnField(Field f){
		int j=0;
		while(j<littleRobots.size()){
			Field littleRobF = littleRobots.get(j).getField();
			if(littleRobF.equals(f)){
				destroyLittleRobot(j);
			}
			else{
				j++;
			}
		}
	}
	
	public void destroyLittleRobot(int index){
		littleRobots.get(index).onDestroy();
		littleRobots.remove(index);
	}
	

	public void run(){
		loopenabled = true;								
		while(loopenabled){	
			//while(!timer.getnextStep()){				
			//}
			//timer.setnextStep(false);
			long time1= System.currentTimeMillis();
			step();
			long time2=System.currentTimeMillis();
			long dif= (time2-time1) / 1000000;
			try {
				Thread.sleep(DEFAULT_RUNNING_SPEED);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

	}
	
	public void step(){
		if(time>0 && robots.size() > 0){
			this.moveRobots();
			this.decreaseTrapsHealth();			
			this.moveLittleRobots();
			this.collision();
			this.deleteRobots();
			view.drawgame();
			
		}else{
			loopenabled=false;
			 view.close();
			 String message;
			 if(time <1){
				 Robot win = robots.get(0);
				 for(Robot r : robots){
						if (r.getRounds() > win.getRounds())
							win = r;
						else if (r.getRounds() == win.getRounds())
							if (r.getGonedistance() > win.getGonedistance())
								win = r;
					}
			     JOptionPane.showMessageDialog(null, "Robot " + win.getId() + " won the game! Distance: " + win.getGonedistance(), "The time is up! " , JOptionPane.INFORMATION_MESSAGE);
				 message= "times_up";	 
			 }

			 else
				 message="game finished";
			 Menu.main(new String[]{message});	
		}
		
		
	}
	
	public void moveLittleRobots(){
		for(int i=0; i<littleRobots.size();i++){
			littleRobots.get(i).action();
		}
	}
	
	public void decreaseTrapsHealth(){
		for(Field f : map.getFields()){
			if(f.hasTrap() && f.getTrap() instanceof Oil){
				if (f.getTrap().health > 0)
					f.getTrap().health--;
			}
			if(f.hasTrap()){
				if(f.getTrap().health <= 0){
					f.deleteTrap();
				}
			}
		}
	}
	
	private void moveRobots(){
			
			for(Robot r : robots){
				r.moveRobot();
			}
	}
	
	private void deleteRobots(){
		int i=0;
		while(i<robots.size()){
			if(robots.get(i).getToBeDestroyed()){
				robots.remove(i);
			}else{
				i++;
			}
				
		}
	}
	
	public int getWinner(){
		int max = 0;
		for(int i = 0; i < robots.size(); i++){
			if (robots.get(i).getGonedistance() > robots.get(max).getGonedistance())
				max = i;
		}
		return max;
	}
	
	private void finish(){
	
		loopenabled = false;
	}

	public ArrayList<Robot> getRobots() {
		return robots;
	}

	public ArrayList<LittleRobot> getLittleRobots() {
		return littleRobots;
	}

	public Map getMap() {
		return map;
	}
	
	public int getTime(){
		return time;
	}
	
}
