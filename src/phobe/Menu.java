package phobe;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;


public class Menu {
	View view;
	Game game;
	
	public Menu(){			
			init();		
	}
	
	public static void main(String [] s){
		Menu mMenu = new Menu();
		
		
	}	
	public void init(){
		view = new View(this);
		view.drawmenu();
	}
	
	public void play(){ 
		final Game game = new Game(view); 
		this.game=game;
		
		try {
			game.init(new BufferedReader(new FileReader("track.txt")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		new Thread(new Runnable() {
		    public void run()
		    {
		    	game.run();	
		    }}).start();
		
									
	}
	
}
