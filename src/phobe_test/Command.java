package phobe_test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import phobe.*;
import phobe_exceptions.WrongDirectionException;

public class Command {

	public boolean exit = false;
	private Game game;
	

	public void exec(String in) {
		
		String[] cmd = in.split(" ");
		if (cmd[0].equals("newgame")) {
			newgame();

		} else if (cmd[0].equals("exitgame")) {
			exitgame();

		} else if (cmd[0].equals("loadlevel")) {
			loadlevel(cmd[1]);

		} else if (cmd[0].equals("putoil")) {
			putoil(Integer.parseInt(cmd[1]));

		} else if (cmd[0].equals("putglue")) {
			putglue(Integer.parseInt(cmd[1]));
		} 
		else if (cmd[0].equals("changedirection")) {
			changedirection(Integer.parseInt(cmd[1]), Integer.parseInt(cmd[2]));

		} else if (cmd[0].equals("listrobots")) {
			listrobots();

		} else if (cmd[0].equals("listfields")) {
			listfields();

		} else if (cmd[0].equals("listtraps")) {
			listtraps();
		} else if (cmd[0].equals("listlittlerobots")) {
			listlittlerobots();
		} 
		else if (cmd[0].equals("stepon")) {
		}
		else if (cmd[0].equals("step")) {
			step();
		}
		else if (cmd[0].equals("stepoff")) {
		}
		else {
			System.out.println("No such command");
		}
	}

	private void newgame() {
		game = new Game();
	}

	private void exitgame() {
		exit = true;
	}
	
	private void gameover(){
		int winner = game.getWinner();
		System.out.println("game over robot " + winner);
		fajlbair("game over robot " + winner);
	}

	private void loadlevel(String levelName) {
		BufferedReader level = null;
		try {
			level = new BufferedReader(new FileReader(main.loadTestPath + levelName));
			System.out.println("loadlevel " + levelName + " SUCCEED ");
			fajlbair("loadlevel " + levelName + " SUCCEED ");
		} catch (FileNotFoundException e) {
			fajlbair("loadlevel " + levelName + " FAILED ");
			System.out.println("loadlevel " + levelName + " FAILED ");
		}
		
		game.init(level);
		
	}

	private void putoil(int robotId) {
		game.getRobots().get(robotId).createTrap(game.OLAJ);
	}

	private void putglue(int robotId) {
		game.getRobots().get(robotId).createTrap(game.RAGACS);
	}

	private void changedirection(int robotId, int direction) {
		try {
			game.getRobots().get(robotId).setDirection(direction);
		} catch (WrongDirectionException e) {		
			e.printStackTrace();
		}
	}

	private void listrobots() {
		ArrayList<Robot> robot = game.getRobots();
		for (Robot r : robot) {
			System.out.println("robotid: " + r.getId() + " fieldid: " + r.getField().getFieldId()
					+ " v: " + r.getV() + " glue: " + r.getGlues() + " oil: " + r.getOils()
					+ " oiledstate: " + r.isOiledstate());
			fajlbair("robotid: " + r.getId() + " fieldid: " + r.getField().getFieldId()
					+ " v: " + r.getV() + " glue: " + r.getGlues() + " oil: " + r.getOils()
					+ " oiledstate: " + r.isOiledstate());
		}
	}

	private void listfields() {
		ArrayList<Field> field = game.getMap().getFields();
		for(Field f: field){
			System.out.println("field: " + f.getFieldId() + " fieldtype: " + f.getClass().getSimpleName() + 
					" trap: " + (!f.hasTrap() ? "none" : (f.getTrap().getClass().getSimpleName().equals("Oil") ? "oil" : "glue")) );
			fajlbair("field: " + f.getFieldId() + " fieldtype: " + f.getClass().getSimpleName() + 
					" trap: " + (!f.hasTrap() ? "none" : (f.getTrap().getClass().getSimpleName().equals("Oil") ? "oil" : "glue")) );
		}
	}	

	private void listtraps() {
		ArrayList<Field> field = game.getMap().getFields();
		for(Field f: field){
			if (f.hasTrap()){
				System.out.println("field: " + f.getFieldId() + " fieldtype: " + f.getClass().getSimpleName() + 
					" trap: " + (f.getTrap().getClass().getSimpleName().equals("Oil") ? "oil " : "glue ") + (f.getTrap().getHealth()));
			fajlbair("field: " + f.getFieldId() + " fieldtype: " + f.getClass().getSimpleName() + 
					" trap: " + (f.getTrap().getClass().getSimpleName().equals("Oil") ? "oil " : "glue ") + (f.getTrap().getHealth()));
			}
		}
	} 
	
	private void listlittlerobots(){
		ArrayList<LittleRobot> little = game.getLittleRobots();
		for(LittleRobot l: little){
			System.out.println("littlerobotid: "+ l.getId() + " field: " 
		+ l.getField().getFieldId());
			fajlbair("littlerobotid: "+ l.getId() + " field: " 
		+ l.getField().getFieldId());
			
		}
		
	}
	
	private void step(){
		game.step();
		game.time--;
		System.out.println("time: "+game.time);	
		fajlbair("time: "+game.time);
		if (game.time == 0)
			gameover();
	}

	private void fajlbair(String msg){	
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(main.loadTestPath +"out.txt",true));
			writer.write(msg);
			writer.newLine();
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
