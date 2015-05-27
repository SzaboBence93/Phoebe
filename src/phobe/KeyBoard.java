package phobe;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import phobe_exceptions.WrongActionException;
import phobe_exceptions.WrongDirectionException;

public class KeyBoard extends Controlling implements KeyListener {
	char keyCode;
	final char[] keys;
	final char oil;
	final char glue;
	boolean first = true;
	public KeyBoard(char[] directions, char oil, char glue) {
		keys = new char[directions.length];
		for(int i=0;i<keys.length;i++) {
			keys[i] = directions[i];
		}
		this.oil = oil;
		this.glue = glue;
	}
	@Override
	public void keyPressed(KeyEvent arg0) {}

	@Override
	public void keyReleased(KeyEvent arg0) {
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {		
		keyCode = arg0.getKeyChar();
		if (keyCode == oil){
			setAction(Game.OLAJ);
		}
		else if (keyCode == glue){
			setAction(Game.RAGACS);
		}
		else {
			for (int i=0;i<keys.length;i++) {
				if (keys[i]==keyCode) {
					try {
						setDirection(i);
					} catch (WrongDirectionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
			}
		}
		//System.out.println(keyCode);
		
		
		
		
	}
	
}
