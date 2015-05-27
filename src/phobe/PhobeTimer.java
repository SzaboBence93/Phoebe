package phobe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class PhobeTimer implements ActionListener{

	private final Timer stopper;
	private boolean nextStep;

	PhobeTimer(){
		nextStep = true;
		stopper = new Timer(50, this);
		stopper.restart();
	}

	public void actionPerformed(ActionEvent e){
	    if(e.getSource() == stopper){
	        nextStep = true;
	    }
	}

	public boolean getnextStep(){
		return nextStep;
	}

	public void setnextStep(boolean b){
		nextStep = b;
	}
}
