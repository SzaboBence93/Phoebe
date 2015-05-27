package phobe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class TrackGen {

	private static int TRACK_TIME = 60;
	public static void main(String[] s){
		
		TrackGen.generate();
		
		
		
	}
	
	public static void generate(){
		
		
		StringBuilder s = new StringBuilder();
		s.append(TRACK_TIME + "\n");
		for(int i=0; i < 1250 ; i++){
			s.append(i+" ");
			
			if(i<250 || i >= 1000 || i%50<5 || i%50>44 || (i%50>9 && i%50<41 && i>499 && i<750 ))
			{
				s.append("1 0 ");				
			}else{
				if(i%23==0){
					s.append("0 2 ");	
				}
				else if(i%37==0)
					s.append("0 1 ");
				else	
					s.append("0 0 ");
				
				
			}
			if(i==375)
				s.append("1 ");//robot
			else if (i==425)
				s.append("1 ");
			else if(i==420)
				s.append("2 ");//littlerobot
			else if(i==880)
				s.append("2 ");//littlerobot
			else{
				if(System.currentTimeMillis()%500==0)
					s.append("2 ");
				else
					s.append("0 ");
			}
			
			//szomsz�doss�g
			if(i<50){
				if(i==0){
					s.append("-1 "+(i+1)+" "+(i+50)+" -1\n" );
				}else{
					if(i==50)
						s.append("-1 "+" -1 "+(i+50)+" "+(i-1)+"\n");
					else{
						s.append("-1 "+(i+1)+" "+(i+50)+" "+(i-1)+"\n");
					}
				}
				
			}else{
				if(i<1200){
					if(i%50==0){
						s.append((i-50)+" "+(i+1)+" "+(i+50)+" -1\n");
					}else if((i+1)%50==0){
						s.append((i-50)+" -1 "+(i+50)+" "+(i-1)+"\n");
					}else{
						s.append((i-50)+" "+(i+1)+" "+(i+50)+" "+(i-1)+"\n");
					}
				}else{
					if(i%50==0){
						s.append((i-50)+" "+(i+1)+" -1 -1\n");
					}else if((i+1)%50==0){
						s.append((i-50)+" -1 -1 "+(i-1)+"\n");
					}else{
						s.append((i-50)+" "+(i+1)+" -1 "+(i-1)+"\n");
					}
					
				}
								
			}
			
		}
		
		
		PrintWriter p=null;
		try {
			p = new PrintWriter(new File("track.txt"));
			p.print(s.toString());
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(p!=null)
				p.close();
		}
	}
	
}
