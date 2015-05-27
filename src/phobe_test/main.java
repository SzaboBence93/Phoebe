package phobe_test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class main {
	
	public static final String loadTestPath = "tesztek/";
	
	private static void run(BufferedReader in){
		Command cmd = new Command();
		String nextCmd;
		try {
			while(!cmd.exit && (nextCmd = in.readLine()) != null){
				cmd.exec(nextCmd);
			} 
		}
			catch (IOException e) {
			}
		}
	
	private static void compare(BufferedReader out, BufferedReader expected){
		String tmpOut;
		String tmpExpected = null;
		int failedLine = 1;
		boolean success = true;
		try {
			while((tmpOut = out.readLine()) != null && (tmpExpected = expected.readLine()) != null){
				if(tmpOut.compareTo(tmpExpected) != 0){
					success = false;
					break;
				}
				failedLine++;
			}
			if (success && (tmpOut = out.readLine()) == null && (tmpExpected = expected.readLine()) == null)
					System.out.println("TEST SUCCEED");
			else
				System.out.println("TEST FAILED AT LINE "+failedLine);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static void main(String[] args) {
		System.out.println("adja meg a parancsfajlt(pl egy): ");
		Scanner commandInput = new Scanner(System.in);
		String fileName = null;
		
		if (commandInput != null){
			fileName = commandInput.nextLine();
			try {				
				BufferedReader br = new BufferedReader(new FileReader(loadTestPath + fileName + ".be.txt"));
				File out = new File(loadTestPath + "out.txt");
				if(out.exists()){
					out.delete();
				}
				run(br);
				commandInput.close();
				
			} catch (IOException e) {
				System.out.println("Unable to open file " + fileName);
			}
		}		
		try {
			BufferedReader out = new BufferedReader(new FileReader(loadTestPath + "out.txt"));
			BufferedReader expected = new BufferedReader(new FileReader(loadTestPath + fileName + ".ki.txt"));
			compare(out, expected);
			out.close();
			expected.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(0);
		
	}

}
