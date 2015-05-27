package phobe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.awt.font.*;

import javax.swing.*;

public class View extends JPanel {

	boolean firstdraw=true;
	Menu menu;
	JFrame frame= new JFrame();
	JPanel panel;
	int width=500;
	int height=250;
	private ArrayList<Robot> robots;
	public View(Menu m){
		menu=m;
		frame.setSize(500,250);
		frame.setResizable(false);

	}
	
	public void drawmenu(){
		JButton button = new JButton("New Game");
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				menu.play();
				
			}
			
		});

		panel = new JPanel();
		panel.add(button);
		frame.add(panel,BorderLayout.NORTH);
		
		JButton exitbutton = new JButton("Exit");
		exitbutton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.dispose();
			}
			
		});

		JPanel centerpanel = new JPanel();
		centerpanel.add(exitbutton);
		frame.add(centerpanel,BorderLayout.CENTER);	
		
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public void drawgame(){
		if(firstdraw){
			frame.remove(panel);
			frame.revalidate();
			frame.setSize(width, height);
			frame.add(this);
			this.setFocusable(true);
			for(Controlling c : menu.game.controllings){
				
				this.addKeyListener((KeyBoard) c);
			}
			frame.setVisible(true);
			this.requestFocusInWindow();
			
			firstdraw=false;
		}
		
		
		this.repaint();
	}
	
	public void paint(Graphics g){
		drawmap(g);
		
	}
	

	public void drawmap(Graphics g){
		robots = menu.game.getRobots();
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.BLACK);
		String tim = ("Time: " + Integer.toString(menu.game.getTime()));
		g.setFont(new Font("Arial", Font.BOLD, 18));
		g.drawString(tim,2,15);
		for(int i=0;i<robots.size();i++) {
			String id = Integer.toString(robots.get(i).getId()) + " rob: " + Integer.toString(robots.get(i).getRounds());
			System.out.println(id);
			Font font = new Font("Serif", Font.BOLD, 12);
		    g.setFont(font);
			g.drawString(id,2,i*10 + 30);
		}
		
	
		int i=0;
		for(Field f: menu.game.getMap().getFields())
		{
			
			
			if(f instanceof Racetrack)
				drawfield(f,g,i);
			
			for(Robot r: menu.game.getRobots()){
				if(r.getField().equals(f)){
					drawrobot(g,(i%50)*(width/50), (i/50)*(height/25));
				}
			}
			for(LittleRobot r: menu.game.getLittleRobots()){
				if(r.getField().equals(f)){
					drawlittlerobot(g,(i%50)*(width/50), (i/50)*(height/25));
				}
			}
			
			
			i++;
		}
		
		
	}
	
	public void drawfield(Field f,Graphics g,int i){
		g.setColor(Color.black);
		if (f.getCheckPoint()) {
			g.setColor(Color.yellow);
		}
		if (f.getFinishLine()) {
			g.setColor(Color.white);
		}
		if(f.hasTrap() && f.getTrap() instanceof Glue)
			g.setColor(Color.pink);
		if(f.hasTrap() && f.getTrap() instanceof Oil)
			g.setColor(Color.gray);
		g.fillRect((i%50)*(width/50), (i/50)*(height/25), width/25, height/25);
		
	}
	
	public void drawrobot(Graphics g,int x, int y){
		g.setColor(Color.CYAN);
		g.fillOval(x, y, 5, 5);
		
	}
	
	public void drawlittlerobot(Graphics g,int x, int y){
		g.setColor(Color.red);
		g.fillOval(x, y, 5, 5);
		
	}

	public void close() {
		frame.setVisible(false);
		
	}
	
}
